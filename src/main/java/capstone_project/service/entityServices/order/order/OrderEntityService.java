package capstone_project.service.entityServices.order.order;

import capstone_project.entity.order.order.OrderEntity;
import capstone_project.service.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.UUID;

public interface OrderEntityService extends BaseEntityService<OrderEntity, UUID> {
    List<OrderEntity> findBySenderId(UUID senderId);

    List<OrderEntity> findByDeliveryAddressId(UUID deliveryAddressId);
}
