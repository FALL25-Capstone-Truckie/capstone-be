package capstone_project.service.mapper.issue;

import capstone_project.dtos.response.issue.GetBasicIssueResponse;
import capstone_project.dtos.response.issue.GetIssueTypeResponse;
import capstone_project.dtos.response.issue.OrderDetailForIssueResponse;
import capstone_project.entity.issue.IssueEntity;
import capstone_project.entity.issue.IssueTypeEntity;
import capstone_project.entity.order.order.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    GetIssueTypeResponse toIssueTypeResponse(IssueTypeEntity issueType);

    List<GetIssueTypeResponse> toIssueTypeResponses(List<IssueTypeEntity> issueTypes);

    @Mapping(source = "issueTypeEntity.issueCategory", target = "issueCategory")
    @Mapping(source = "issueTypeEntity", target = "issueTypeEntity")
    @Mapping(target = "orderDetail", expression = "java(mapOrderDetail(issue))")
    GetBasicIssueResponse toIssueBasicResponse(IssueEntity issue);

    List<GetBasicIssueResponse> toIssueBasicResponses(List<IssueEntity> issues);
    
    // Map order detail for issue response
    default OrderDetailForIssueResponse mapOrderDetail(IssueEntity issue) {
        if (issue.getOrderDetails() == null || issue.getOrderDetails().isEmpty()) {
            return null;
        }
        // Return the first order detail affected by this issue
        OrderDetailEntity orderDetail = issue.getOrderDetails().get(0);
        return new OrderDetailForIssueResponse(
            orderDetail.getTrackingCode(),
            orderDetail.getDescription(),
            orderDetail.getWeightBaseUnit(),
            orderDetail.getUnit()
        );
    }

}
