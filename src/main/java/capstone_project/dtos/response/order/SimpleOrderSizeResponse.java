package capstone_project.dtos.response.order;

import java.math.BigDecimal;

public record SimpleOrderSizeResponse(
    String id,
    String description,
    BigDecimal minLength, 
    BigDecimal maxLength,
    BigDecimal minHeight,
    BigDecimal maxHeight,
    BigDecimal minWidth,
    BigDecimal maxWidth
) {}