package capstone_project.repository.entityServices.order.conformation.impl;

import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.order.conformation.SignatureRequestEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.repository.repositories.order.conformation.SignatureRequestRepository;
import capstone_project.repository.entityServices.order.conformation.SignatureRequestEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignatureRequestEntityServiceImpl implements SignatureRequestEntityService {

    private final SignatureRequestRepository signatureRequestRepository;

    @Override
    public SignatureRequestEntity save(SignatureRequestEntity entity) {
        return signatureRequestRepository.save(entity);
    }

    @Override
    public Optional<SignatureRequestEntity> findEntityById(UUID uuid) {
        return signatureRequestRepository.findById(uuid);
    }

    @Override
    public List<SignatureRequestEntity> findAll() {
        return signatureRequestRepository.findAll();
    }

    @Override
    public SignatureRequestEntity findByOrderEntity(OrderEntity orderId) {
        return signatureRequestRepository.findByOrderEntity(orderId);
    }

    @Override
    public List<SignatureRequestEntity> findByUser(UserEntity userId) {
        return signatureRequestRepository.findByUser(userId);
    }
}
