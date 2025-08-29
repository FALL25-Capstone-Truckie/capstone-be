package capstone_project.service.services.issue;

import capstone_project.dtos.request.issue.CreateBasicIssueRequest;
import capstone_project.dtos.request.issue.UpdateBasicIssueRequest;
import capstone_project.dtos.response.issue.GetBasicIssueResponse;
import capstone_project.dtos.response.issue.GetIssueTypeResponse;

import java.util.List;
import java.util.UUID;

public interface IssueService {
    GetBasicIssueResponse getBasicIssue(UUID issueId);

    GetBasicIssueResponse getByVehicleAssignment(UUID vehicleAssignmentId);

    List<GetBasicIssueResponse> getByStaffId(UUID staffId);

    List<GetIssueTypeResponse> getByActiveStatus();

    GetIssueTypeResponse getIssueType(UUID issueTypeId);

    GetIssueTypeResponse createIssue(CreateBasicIssueRequest request);

    GetIssueTypeResponse updateIssue(UpdateBasicIssueRequest request);
}
