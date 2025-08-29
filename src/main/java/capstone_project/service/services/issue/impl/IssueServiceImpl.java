package capstone_project.service.services.issue.impl;

import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.issue.CreateBasicIssueRequest;
import capstone_project.dtos.request.issue.UpdateBasicIssueRequest;
import capstone_project.dtos.response.issue.GetBasicIssueResponse;
import capstone_project.dtos.response.issue.GetIssueTypeResponse;
import capstone_project.entity.issue.IssueEntity;
import capstone_project.service.entityServices.auth.UserEntityService;
import capstone_project.service.entityServices.issue.IssueEntityService;
import capstone_project.service.entityServices.issue.IssueTypeEntityService;
import capstone_project.service.entityServices.vehicle.VehicleAssignmentEntityService;
import capstone_project.service.mapper.issue.IssueMapper;
import capstone_project.service.services.issue.IssueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final VehicleAssignmentEntityService vehicleAssignmentEntityService;
    private final UserEntityService userEntityService;
    private final IssueEntityService issueEntityService;
    private final IssueTypeEntityService issueTypeEntityService;
    private final IssueMapper issueMapper;


    @Override
    public GetBasicIssueResponse getBasicIssue(UUID issueId) {
        Optional<IssueEntity> getIssue = issueEntityService.findContractRuleEntitiesById(issueId);
        if (getIssue.isEmpty()) {
            throw new NotFoundException(ErrorEnum.NOT_FOUND.getMessage()+issueId,ErrorEnum.NOT_FOUND.getErrorCode());
        }
        return issueMapper.toIssueBasicResponse(getIssue.get());
    }

    @Override
    public GetBasicIssueResponse getByVehicleAssignment(UUID vehicleAssignmentId) {
        return null;
    }

    @Override
    public List<GetBasicIssueResponse> getByStaffId(UUID staffId) {
        return List.of();
    }

    @Override
    public List<GetIssueTypeResponse> getByActiveStatus() {
        return List.of();
    }

    @Override
    public GetIssueTypeResponse getIssueType(UUID issueTypeId) {
        return null;
    }

    @Override
    public GetIssueTypeResponse createIssue(CreateBasicIssueRequest request) {
        return null;
    }

    @Override
    public GetIssueTypeResponse updateIssue(UpdateBasicIssueRequest request) {
        return null;
    }
}
