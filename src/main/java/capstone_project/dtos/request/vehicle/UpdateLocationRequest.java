package capstone_project.dtos.request.vehicle;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest {
    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
