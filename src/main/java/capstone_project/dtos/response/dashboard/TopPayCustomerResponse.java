package capstone_project.dtos.response.dashboard;

import java.math.BigDecimal;

public record TopPayCustomerResponse(
        String customerId,
        String cusName,
        String companyName,
        BigDecimal payCount
) {
}
