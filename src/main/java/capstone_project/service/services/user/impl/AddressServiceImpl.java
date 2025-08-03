package capstone_project.service.services.user.impl;

import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.BadRequestException;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.user.AddressRequest;
import capstone_project.dtos.response.user.GeocodingResponse;
import capstone_project.dtos.response.user.AddressResponse;
import capstone_project.entity.user.address.AddressEntity;
import capstone_project.entity.user.customer.CustomerEntity;
import capstone_project.service.entityServices.user.AddressEntityService;
import capstone_project.service.entityServices.user.CustomerEntityService;
import capstone_project.service.mapper.user.AddressMapper;
import capstone_project.service.services.user.impl.VietMapGeocodingServiceImpl;
import capstone_project.service.services.user.AddressService;
import capstone_project.common.utils.AddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressEntityService addressEntityService;
    private final CustomerEntityService customerEntityService;
    private final AddressMapper addressMapper;
    private final VietMapGeocodingServiceImpl geocodingService;

    @Override
    public List<AddressResponse> getAllAddresses() {
        log.info("Fetching all addresses");
        List<AddressEntity> addresses = addressEntityService.findAll();

        if (addresses.isEmpty()) {
            log.warn("No addresses found");
            throw new NotFoundException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }

        return addresses.stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }


    @Override
    public AddressResponse calculateLatLong(String address) {
        log.info("Calculating lat/long for address: {}", address);

        Optional<GeocodingResponse> geocodingResult = geocodingService.geocodeAddress(address);

        if (geocodingResult.isPresent()) {
            AddressResponse response = AddressUtil.buildResponseFromGeocoding(geocodingResult.get());
            log.info("Successfully resolved coordinates - Lat: {}, Long: {}",
                    response.latitude(), response.longitude());
            return response;
        }

        log.warn("Could not resolve coordinates via API, using fallback for address: {}", address);
        return AddressUtil.buildFallbackResponse(address);
    }

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        try {
            String fullAddress = AddressUtil.buildFullAddress(request);
            AddressResponse locationData = calculateLatLong(fullAddress);

            AddressEntity addressEntity = addressMapper.mapRequestToAddressEntity(request);
            AddressUtil.setCoordinatesOnEntity(addressEntity, locationData.latitude(), locationData.longitude());

            LocalDateTime now = LocalDateTime.now();
            addressEntity.setCreatedAt(now);

            CustomerEntity customer = customerEntityService.findById(UUID.fromString(request.customerId()))
                    .orElseThrow(() -> new NotFoundException(
                            ErrorEnum.NOT_FOUND.getMessage(),
                            ErrorEnum.NOT_FOUND.getErrorCode()
                    ));
            addressEntity.setCustomer(customer);

            AddressEntity saved = addressEntityService.save(addressEntity);
            return addressMapper.toAddressResponse(saved);

        } catch (Exception e) {
            log.error("Error creating address: {}", request, e);
            throw new RuntimeException("Failed to create address: " + e.getMessage(), e);
        }
    }

    @Override
    public AddressResponse updateAddress(UUID id, AddressRequest request) {
        log.info("Updating address\n with ID: {}", id);

        AddressEntity existing = addressEntityService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Address with ID {} not found", id);
                    return new NotFoundException(
                            ErrorEnum.NOT_FOUND.getMessage(),
                            ErrorEnum.NOT_FOUND.getErrorCode()
                    );
                });

        addressMapper.toAddressEntity(request, existing);
        AddressEntity updatedVehicleType = addressEntityService.save(existing);
        return addressMapper.toAddressResponse(updatedVehicleType);
    }

    @Override
    public AddressResponse getAddressById(UUID id) {
        log.info("Fetching address with ID: {}", id);
        Optional<AddressEntity> addressEntity = addressEntityService.findById(id);

        return addressEntity
                .map(addressMapper::toAddressResponse)
                .orElseThrow(() -> {
                    log.warn("Address with ID {} not found", id);
                    return new NotFoundException(
                            ErrorEnum.NOT_FOUND.getMessage(),
                            ErrorEnum.NOT_FOUND.getErrorCode()
                    );
                });
    }

}