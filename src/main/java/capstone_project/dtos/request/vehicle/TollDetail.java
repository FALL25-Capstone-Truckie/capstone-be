package capstone_project.dtos.request.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TollDetail(
        String name,
        String address,
        String type,
        BigDecimal amount
) {}
