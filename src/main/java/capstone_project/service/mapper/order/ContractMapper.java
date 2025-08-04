package capstone_project.service.mapper.order;

import capstone_project.dtos.request.order.ContractRequest;
import capstone_project.dtos.response.order.ContractResponse;
import capstone_project.entity.order.contract.ContractEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.entity.pricing.VehicleRuleEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(source = "orderEntity.id", target = "orderId")
    @Mapping(source = "vehicleRuleEntity.id", target = "vehicleRuleId")
    ContractResponse toContractResponse(ContractEntity contractEntity);

    @Mapping(source = "orderId", target = "orderEntity", qualifiedByName = "orderFromId")
    @Mapping(source = "vehicleRuleId", target = "vehicleRuleEntity", qualifiedByName = "vehicleRuleFromId")
    @Mapping(target = "totalValue", ignore = true)
    ContractEntity mapRequestToEntity(ContractRequest contractRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "orderEntity", source = "orderId", qualifiedByName = "orderFromId")
    @Mapping(target = "vehicleRuleEntity", source = "vehicleRuleId", qualifiedByName = "vehicleRuleFromId")
    void toContractEntity(ContractRequest request, @MappingTarget ContractEntity entity);

    @Named("orderFromId")
    default OrderEntity mapOrderFromId(String orderId) {
        OrderEntity entity = new OrderEntity();
        entity.setId(UUID.fromString(orderId));
        return entity;
    }

    @Named("vehicleRuleFromId")
    default VehicleRuleEntity mapVehicleRuleFromId(String vehicleRuleId) {
        VehicleRuleEntity entity = new VehicleRuleEntity();
        entity.setId(UUID.fromString(vehicleRuleId));
        return entity;
    }
}

