package capstone_project.dtos.response.order.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimpleContractResponse(
    String id,
    String contractName,
    String effectiveDate,
    String expirationDate,
    String totalValue,
    String supportedValue,
    String description,
    String attachFileUrl,
    String status,
    String staffName
) {}