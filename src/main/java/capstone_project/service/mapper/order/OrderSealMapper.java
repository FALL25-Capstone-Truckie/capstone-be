package capstone_project.service.mapper.order;

import capstone_project.dtos.response.order.seal.GetOrderSealResponse;
import capstone_project.entity.order.order.OrderSealEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderSealMapper {
    @Mapping(source = "seal.id", target = "sealId")
    GetOrderSealResponse toGetOrderSealResponse(OrderSealEntity orderSealEntity);

    List<GetOrderSealResponse> toGetOrderSealResponses(List<OrderSealEntity> orderSealEntities);
}
