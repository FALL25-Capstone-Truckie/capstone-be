package capstone_project.dtos.request.order;

public record UpdateContractRequest(
    String contractName,
    String effectiveDate,
    String expirationDate,
    String totalValue,
    String description,
    String attachFileUrl,
    String status,
    String orderId,
    String pricingRuleId
) {
}
