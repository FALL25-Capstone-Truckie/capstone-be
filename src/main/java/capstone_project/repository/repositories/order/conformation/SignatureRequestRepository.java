package capstone_project.repository.repositories.order.conformation;

import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.order.conformation.SignatureRequestEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.repository.repositories.common.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface SignatureRequestRepository extends BaseRepository<SignatureRequestEntity> {
    SignatureRequestEntity findByOrderEntity(OrderEntity orderId);

    List<SignatureRequestEntity> findByUser(UserEntity userId);
}
