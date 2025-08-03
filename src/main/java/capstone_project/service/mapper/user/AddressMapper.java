package capstone_project.service.mapper.user;

import capstone_project.dtos.request.user.AddressRequest;
import capstone_project.dtos.request.vehicle.VehicleTypeRequest;
import capstone_project.dtos.response.user.AddressResponse;
import capstone_project.entity.user.address.AddressEntity;
import capstone_project.entity.vehicle.VehicleTypeEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "id", source = "address.id")
    @Mapping(target = "customerId", source = "address.customer.id")
    AddressResponse toAddressResponse(AddressEntity address);

    AddressEntity mapRequestToAddressEntity(AddressRequest addressRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAddressEntity(AddressRequest request, @MappingTarget AddressEntity entity);
}
