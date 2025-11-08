package capstone_project.repository.entityServices.refund;

import capstone_project.entity.order.order.RefundEntity;
import capstone_project.repository.entityServices.common.BaseEntityService;

import java.util.Optional;
import java.util.UUID;

public interface RefundEntityService extends BaseEntityService<RefundEntity, UUID> {
    Optional<RefundEntity> findByIssueId(UUID issueId);
}
