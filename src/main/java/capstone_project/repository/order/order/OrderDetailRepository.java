package capstone_project.repository.order.order;

import capstone_project.entity.order.order.OrderDetailEntity;
import capstone_project.repository.common.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends BaseRepository<OrderDetailEntity> {
    List<OrderDetailEntity> findOrderDetailEntitiesByOrderEntityId(UUID orderId);
}
