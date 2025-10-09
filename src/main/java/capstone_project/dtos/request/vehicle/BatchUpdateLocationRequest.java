package capstone_project.dtos.request.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpdateLocationRequest {
    @NotEmpty
    private List<VehicleLocationUpdate> updates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleLocationUpdate {
        @NotNull
        private UUID vehicleId;

        @NotNull
        private BigDecimal latitude;

        @NotNull
        private BigDecimal longitude;
    }
}
