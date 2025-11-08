package capstone_project.repository.entityServices.refund.impl;

import capstone_project.entity.order.order.RefundEntity;
import capstone_project.repository.entityServices.refund.RefundEntityService;
import capstone_project.repository.repositories.refund.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundEntityServiceImpl implements RefundEntityService {
    private final RefundRepository refundRepository;

    @Override
    public RefundEntity save(RefundEntity entity) {
        return refundRepository.save(entity);
    }

    @Override
    public Optional<RefundEntity> findEntityById(UUID id) {
        return refundRepository.findById(id);
    }

    @Override
    public List<RefundEntity> findAll() {
        return refundRepository.findAll();
    }

    
    @Override
    public Optional<RefundEntity> findByIssueId(UUID issueId) {
        return refundRepository.findByIssueEntityId(issueId);
    }
}
