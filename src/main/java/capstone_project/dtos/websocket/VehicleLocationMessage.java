package capstone_project.dtos.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLocationMessage {
    private UUID vehicleId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime timestamp;
    private UUID assignmentId; // Optional: Có thể null nếu không liên quan đến assignment cụ thể
    private String licensePlateNumber; // Optional: Thêm thông tin để client dễ hiển thị
}
