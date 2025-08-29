package capstone_project.service.entityServices.issue;

import capstone_project.entity.issue.IssueTypeEntity;
import capstone_project.service.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.UUID;

public interface IssueTypeEntityService extends BaseEntityService<IssueTypeEntity, UUID> {
    List<IssueTypeEntity> findByIssueTypeNameContaining(String name);

    IssueTypeEntity findByIssueTypeName(String name);
}
