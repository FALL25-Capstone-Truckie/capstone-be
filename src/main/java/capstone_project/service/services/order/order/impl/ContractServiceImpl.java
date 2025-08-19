package capstone_project.service.services.order.order.impl;

import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.BadRequestException;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.order.ContractRequest;
import capstone_project.dtos.response.order.ContractResponse;
import capstone_project.entity.order.contract.ContractEntity;
import capstone_project.entity.order.contract.ContractRuleEntity;
import capstone_project.entity.order.order.CategoryPricingDetailEntity;
import capstone_project.entity.order.order.OrderDetailEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.entity.pricing.BasingPriceEntity;
import capstone_project.entity.pricing.DistanceRuleEntity;
import capstone_project.entity.pricing.VehicleRuleEntity;
import capstone_project.entity.user.address.AddressEntity;
import capstone_project.service.entityServices.order.contract.ContractEntityService;
import capstone_project.service.entityServices.order.contract.ContractRuleEntityService;
import capstone_project.service.entityServices.order.order.CategoryPricingDetailEntityService;
import capstone_project.service.entityServices.order.order.OrderDetailEntityService;
import capstone_project.service.entityServices.order.order.OrderEntityService;
import capstone_project.service.entityServices.pricing.BasingPriceEntityService;
import capstone_project.service.entityServices.pricing.DistanceRuleEntityService;
import capstone_project.service.entityServices.pricing.VehicleRuleEntityService;
import capstone_project.service.mapper.order.ContractMapper;
import capstone_project.service.services.order.order.ContractService;
import jakarta.transaction.Transactional;
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
    private final ContractRuleEntityService contractRuleEntityService;
    private final VehicleRuleEntityService vehicleRuleEntityService;
    private final CategoryPricingDetailEntityService categoryPricingDetailEntityService;
    private final OrderEntityService orderEntityService;
    private final DistanceRuleEntityService distanceRuleEntityService;
    private final BasingPriceEntityService basingPriceEntityService;
    private final OrderDetailEntityService orderDetailEntityService;

    private final ContractMapper contractMapper;


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
    @Transactional
    public ContractResponse createContract(ContractRequest contractRequest) {
        log.info("Creating new contract");

        if (contractRequest.orderId() == null) {
            log.error("Order ID is null in contract request");
            throw new IllegalArgumentException("Order ID must not be null");
        }

        UUID orderUuid = UUID.fromString(contractRequest.orderId());

        // check nếu đã tồn tại contract cho order
        if (contractEntityService.getContractByOrderId(orderUuid).isPresent()) {
            log.error("Contract already exists for order ID: {}", orderUuid);
            throw new BadRequestException(ErrorEnum.ALREADY_EXISTED.getMessage(),
                    ErrorEnum.ALREADY_EXISTED.getErrorCode());
        }

        // lấy order
        OrderEntity order = orderEntityService.findById(orderUuid)
                .orElseThrow(() -> new NotFoundException("Order not found", ErrorEnum.NOT_FOUND.getErrorCode()));

        BigDecimal distanceKm = calculateDistanceKm(order.getPickupAddress(), order.getDeliveryAddress());

        // 1. map request -> ContractEntity và save trước (chưa set totalValue)
        ContractEntity contractEntity = contractMapper.mapRequestToEntity(contractRequest);
        contractEntity.setStatus("ACTIVE");
        contractEntity.setOrderEntity(order); // gắn với order

        ContractEntity savedContract = contractEntityService.save(contractEntity);

        // 2. Sinh ContractRule tự động
        List<VehicleRuleEntity> vehicleRules = vehicleRuleEntityService.findAllByCategoryId(order.getCategory().getId());

        for (VehicleRuleEntity vehicleRule : vehicleRules) {
            int numOfVehicles = calculateNumOfVehicles(order, vehicleRule);

            if (numOfVehicles > 0) {
                ContractRuleEntity rule = ContractRuleEntity.builder()
                        .contractEntity(savedContract)
                        .vehicleRuleEntity(vehicleRule)
                        .numOfVehicles(numOfVehicles)
                        .status("ACTIVE")
                        .build();

                contractRuleEntityService.save(rule);
            }
        }

        // 3. Tính totalPrice dựa trên contractId + distanceKm (lúc này contract đã có contractRules)
        BigDecimal totalPrice = calculateTotalPrice(savedContract, distanceKm);

        // 4. cập nhật lại contract với totalValue
        savedContract.setTotalValue(totalPrice);
        ContractEntity updatedContract = contractEntityService.save(savedContract);

        return contractMapper.toContractResponse(updatedContract);
    }




    @Override
    public ContractResponse updateContract(UUID id, ContractRequest contractRequest) {
        return null;
    }

    @Override
    public void deleteContract(UUID id) {

    }

    public BigDecimal calculateTotalPrice(ContractEntity contract, BigDecimal distanceKm) {
        log.info("Calculating total price for contract ID: {}", contract.getId());

        // 1. Lấy danh sách contractRules của contract
        List<ContractRuleEntity> contractRules =
                contractRuleEntityService.findContractRuleEntitiesByContractEntityId(contract.getId());
        if (contractRules.isEmpty()) {
            throw new RuntimeException("No contract rules defined for this contract");
        }

        // 2. Lấy distance rules
        List<DistanceRuleEntity> distanceRules = distanceRuleEntityService.findAll()
                .stream()
                .sorted(Comparator.comparing(DistanceRuleEntity::getFromKm))
                .toList();

        if (distanceRules.isEmpty()) {
            throw new RuntimeException("No distance rules found");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ContractRuleEntity contractRule : contractRules) {
            VehicleRuleEntity vehicleRule = contractRule.getVehicleRuleEntity();
            if (vehicleRule == null) {
                log.warn("ContractRule {} has no associated VehicleRule", contractRule.getId());
                continue;
            }

            BigDecimal ruleTotal = BigDecimal.ZERO;
            BigDecimal remaining = distanceKm;

            // 3. Tính tiền theo distanceRules + basePrice
            for (DistanceRuleEntity distanceRule : distanceRules) {
                if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;

                BigDecimal from = distanceRule.getFromKm();
                BigDecimal to = distanceRule.getToKm();

                BasingPriceEntity basePriceEntity = basingPriceEntityService
                        .findBasingPriceEntityByVehicleRuleEntityIdAndDistanceRuleEntityId(
                                vehicleRule.getId(), distanceRule.getId())
                        .orElseThrow(() -> new RuntimeException("No base price found for tier "
                                + from + "-" + to + " and vehicleRule=" + vehicleRule.getId()));

                if (from.compareTo(BigDecimal.ZERO) == 0 && to.compareTo(BigDecimal.valueOf(4)) == 0) {
                    log.debug("Tier (0-4km): price={}", basePriceEntity.getBasePrice());
                    ruleTotal = ruleTotal.add(basePriceEntity.getBasePrice());
                    remaining = remaining.subtract(to); // trừ 4km đầu
                } else {
                    BigDecimal tierDistance = (to == null)
                            ? remaining
                            : remaining.min(to.subtract(from));

                    log.debug("Tier {}-{}: applying {} km with base price {}",
                            from, to, tierDistance, basePriceEntity.getBasePrice());
                    ruleTotal = ruleTotal.add(basePriceEntity.getBasePrice().multiply(tierDistance));
                    remaining = remaining.subtract(tierDistance);
                }
            }

            // 4. Nhân với số lượng vehicle
            if (contractRule.getNumOfVehicles() != null && contractRule.getNumOfVehicles() > 0) {
                ruleTotal = ruleTotal.multiply(BigDecimal.valueOf(contractRule.getNumOfVehicles()));
            }

            total = total.add(ruleTotal);
        }

        // 5. Điều chỉnh theo Category
        CategoryPricingDetailEntity adjustment =
                categoryPricingDetailEntityService.findByCategoryId(contract.getOrderEntity().getCategory().getId());
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

    private boolean canFit(OrderDetailEntity detail, VehicleRuleEntity vehicleRule) {
        return detail.getWeight().compareTo(vehicleRule.getMaxWeight()) <= 0
                && detail.getLength().compareTo(vehicleRule.getMaxLength()) <= 0
                && detail.getWidth().compareTo(vehicleRule.getMaxWidth()) <= 0
                && detail.getHeight().compareTo(vehicleRule.getMaxHeight()) <= 0;
    }

    public int calculateNumOfVehicles(OrderEntity order, VehicleRuleEntity vehicleRule) {
        List<OrderDetailEntity> details = orderDetailEntityService.findOrderDetailEntitiesByOrderEntityId(order.getId());

        int vehicles = 0;
        BigDecimal currentWeight = BigDecimal.ZERO;

        for (OrderDetailEntity detail : details) {
            if (!canFit(detail, vehicleRule)) {
                // kiện này vượt quá khả năng xe -> cần xe riêng
                vehicles++;
                continue;
            }

            if (currentWeight.add(detail.getWeight()).compareTo(vehicleRule.getMaxWeight()) > 0) {
                // quá tải -> mở thêm xe mới
                vehicles++;
                currentWeight = BigDecimal.ZERO;
            }
            currentWeight = currentWeight.add(detail.getWeight());
        }

        if (currentWeight.compareTo(BigDecimal.ZERO) > 0) {
            vehicles++; // còn thừa -> thêm 1 xe
        }

        return vehicles;
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
