package capstone_project.dtos.request.order;

public record UpdateContractRuleRequest(
    String numOfVehicles,
    String note,
    String info1,
    String info2,
    String status,
    String vehicleRuleId,
    String contractEntityId
) {
}
