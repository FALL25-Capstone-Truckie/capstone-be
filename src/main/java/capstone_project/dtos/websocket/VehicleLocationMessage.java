package capstone_project.dtos.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLocationMessage {
    private UUID vehicleId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String licensePlateNumber; // Optional: để client dễ hiển thị
}
