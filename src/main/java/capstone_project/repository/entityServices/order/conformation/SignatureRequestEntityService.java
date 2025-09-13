package capstone_project.repository.entityServices.order.conformation;

import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.order.conformation.SignatureRequestEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.repository.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.UUID;

public interface SignatureRequestEntityService extends BaseEntityService<SignatureRequestEntity, UUID> {
    SignatureRequestEntity findByOrderEntity(OrderEntity orderId);

    List<SignatureRequestEntity> findByUser(UserEntity userId);
}
