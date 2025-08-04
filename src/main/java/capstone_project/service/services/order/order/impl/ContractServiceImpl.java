package capstone_project.service.services.order.order.impl;

import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.BadRequestException;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.order.ContractRequest;
import capstone_project.dtos.response.order.ContractResponse;
import capstone_project.entity.order.contract.ContractEntity;
import capstone_project.entity.order.order.CategoryPricingDetailEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.entity.pricing.BasingPriceEntity;
import capstone_project.entity.pricing.DistanceRuleEntity;
import capstone_project.entity.pricing.VehicleRuleEntity;
import capstone_project.entity.user.address.AddressEntity;
import capstone_project.service.entityServices.order.contract.ContractEntityService;
import capstone_project.service.entityServices.order.order.CategoryPricingDetailEntityService;
import capstone_project.service.entityServices.order.order.OrderEntityService;
import capstone_project.service.entityServices.pricing.BasingPriceEntityService;
import capstone_project.service.entityServices.pricing.DistanceRuleEntityService;
import capstone_project.service.entityServices.pricing.VehicleRuleEntityService;
import capstone_project.service.mapper.order.ContractMapper;
import capstone_project.service.services.order.order.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractEntityService contractEntityService;
    private final ContractMapper contractMapper;
    private final VehicleRuleEntityService vehicleRuleEntityService;
    private final CategoryPricingDetailEntityService categoryPricingDetailEntityService;
    private final OrderEntityService orderEntityService;
    private final DistanceRuleEntityService distanceRuleEntityService;
    private final BasingPriceEntityService basingPriceEntityService;

    private static final double EARTH_RADIUS_KM = 6371.0;

    @Override
    public List<ContractResponse> getAllContracts() {
        log.info("Getting all contracts");
        List<ContractEntity> contractEntities = contractEntityService.findAll();
        if (contractEntities.isEmpty()) {
            log.warn("No contracts found");
            throw new NotFoundException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }
        return contractEntities.stream()
                .map(contractMapper::toContractResponse)
                .toList();
    }

    @Override
    public ContractResponse getContractById(UUID id) {
        log.info("Getting contract by ID: {}", id);
        ContractEntity contractEntity = contractEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorEnum.NOT_FOUND.getMessage(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));
        return contractMapper.toContractResponse(contractEntity);
    }

    @Override
    public ContractResponse createContract(ContractRequest contractRequest) {
        log.info("Creating new contract");

        if (contractRequest.orderId() == null || contractRequest.vehicleRuleId() == null) {
            log.error("Customer ID or Vehicle ID is null in contract request");
            throw new IllegalArgumentException("Order ID and Vehicle ID must not be null");
        }

        UUID orderUuid = UUID.fromString(contractRequest.orderId());
        UUID vehicleRuleUuid = UUID.fromString(contractRequest.vehicleRuleId());

        if (contractEntityService.getContractByOrderId(orderUuid).isPresent()) {
            log.error("Contract already exists for order ID: {}", orderUuid);
            throw new BadRequestException(ErrorEnum.ALREADY_EXISTED.getMessage(),
                    ErrorEnum.ALREADY_EXISTED.getErrorCode());
        }

        VehicleRuleEntity vehicleRuleEntity = vehicleRuleEntityService.findById(vehicleRuleUuid)
                .orElseThrow(() -> new NotFoundException("pricing rule not found", ErrorEnum.NOT_FOUND.getErrorCode()));

        UUID categoryId = vehicleRuleEntity.getCategory().getId();
        UUID vehicleTypeId = vehicleRuleEntity.getVehicleTypeEntity().getId();

        OrderEntity order = orderEntityService.findById(orderUuid)
                .orElseThrow(() -> new NotFoundException("Order not found", ErrorEnum.NOT_FOUND.getErrorCode()));

        BigDecimal distanceKm = calculateDistanceKm(order.getPickupAddress(), order.getDeliveryAddress());

        // 2. Tính totalPrice
        BigDecimal totalPrice = calculateTotalPrice(categoryId, vehicleTypeId, vehicleRuleEntity.getVehicleRuleName(), distanceKm);

        ContractEntity contractEntity = contractMapper.mapRequestToEntity(contractRequest);

        contractEntity.setStatus("ACTIVE");
        contractEntity.setTotalValue(totalPrice);

        ContractEntity savedContract = contractEntityService.save(contractEntity);

        return contractMapper.toContractResponse(savedContract);
    }

    @Override
    public ContractResponse updateContract(UUID id, ContractRequest contractRequest) {
        return null;
    }

    @Override
    public void deleteContract(UUID id) {

    }

    public BigDecimal calculateTotalPrice(UUID categoryId, UUID vehicleTypeId, String vehilceRuleName, BigDecimal distanceKm) {
        // 1. Lấy VehicleRuleEntity
        VehicleRuleEntity vehicleRule = vehicleRuleEntityService
                .findByCategoryIdAndVehicleTypeEntityIdAndVehicleRuleName(categoryId, vehicleTypeId, vehilceRuleName)
                .orElseThrow(() -> new RuntimeException("Vehicle rule not found"));

        // 2. Lấy tất cả DistanceRule
        List<DistanceRuleEntity> distanceRules = distanceRuleEntityService.findAll()
                .stream()
                .sorted(Comparator.comparing(DistanceRuleEntity::getFromKm))
                .toList();

        if (distanceRules.isEmpty()) {
            throw new RuntimeException("No distance rules found");
        }

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal remaining = distanceKm;

        for (DistanceRuleEntity distanceRule : distanceRules) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal from = distanceRule.getFromKm();
            BigDecimal to = distanceRule.getToKm();

            // Tìm giá base tương ứng (distance + vehicle)
            BasingPriceEntity basePriceEntity = basingPriceEntityService
                    .findBasingPriceEntityByVehicleRuleEntityIdAndDistanceRuleEntityId(vehicleRule.getId(), distanceRule.getId())
                    .orElseThrow(() -> new RuntimeException("No base price found for tier "
                            + from + "-" + to + " and vehicleRule=" + vehicleRule.getId()));

            if (from.compareTo(BigDecimal.ZERO) == 0 && to.compareTo(java.math.BigDecimal.valueOf(4)) == 0) {
                log.debug("Tier (0-4km): price={}", basePriceEntity.getBasePrice());
                total = total.add(basePriceEntity.getBasePrice());
                remaining = remaining.subtract(to); // đã tính 4km đầu
            } else {
                BigDecimal tierDistance = (to == null)
                        ? remaining
                        : remaining.min(to.subtract(from));
                log.debug("Tier {}-{}: applying {} km with base price {}", from, to, tierDistance, basePriceEntity.getBasePrice());
                total = total.add(basePriceEntity.getBasePrice().multiply(tierDistance));
                remaining = remaining.subtract(tierDistance);
            }
        }

        // 3. Điều chỉnh theo Category (nếu có)
        CategoryPricingDetailEntity adjustment = categoryPricingDetailEntityService.findByCategoryId(categoryId);
        if (adjustment != null) {
            log.info("Adjustment data: multiplier={} extraFee={}",
                    adjustment.getPriceMultiplier(), adjustment.getExtraFee());
            BigDecimal multiplier = adjustment.getPriceMultiplier() != null ? adjustment.getPriceMultiplier() : BigDecimal.ONE;
            BigDecimal extraFee = adjustment.getExtraFee() != null ? adjustment.getExtraFee() : BigDecimal.ZERO;
            total = total.multiply(multiplier).add(extraFee);
        }

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price must not be negative");
        }

        log.info("Total price calculated successfully: {}", total);
        return total;
    }


    public BigDecimal calculateDistanceKm(AddressEntity from, AddressEntity to) {
        double lat1 = from.getLatitude().doubleValue();
        double lon1 = from.getLongitude().doubleValue();
        double lat2 = to.getLatitude().doubleValue();
        double lon2 = to.getLongitude().doubleValue();

        log.info("Pickup coords: lat={}, lon={}", lat1, lon1);
        log.info("Delivery coords: lat={}, lon={}", lat2, lon2);

        if (lat1 == lat2 && lon1 == lon2) {
            return BigDecimal.ZERO;
        }

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = EARTH_RADIUS_KM * c;

        log.info("Calculated raw distance: {} km", distanceKm);
        return BigDecimal.valueOf(distanceKm);
    }

//    private String generateContractPdf(ContractEntity contract) {
//        String folderPath = "D:/contracts/";
//        File folder = new File(folderPath);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//
//        String filePath = folderPath + "contract_" + contract.getId() + ".pdf";
//
//        Document document = new Document();
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//            document.add(new Paragraph("CONTRACT INFORMATION"));
//            document.add(new Paragraph("Contract ID: " + contract.getId()));
//            document.add(new Paragraph("Order ID: " + contract.getOrderEntity().getId()));
//            document.add(new Paragraph("Vehicle Rule: " + contract.getVehicleRuleEntity().getVehicleRuleName()));
//            document.add(new Paragraph("Total Value: " + contract.getTotalValue()));
//            document.add(new Paragraph("Status: " + contract.getStatus()));
//            document.add(new Paragraph("Created At: " + contract.getCreatedAt()));
//        } catch (Exception e) {
//            log.error("Error generating PDF", e);
//            throw new RuntimeException("Error generating PDF", e);
//        } finally {
//            document.close();
//        }
//        return filePath;
//    }


}
