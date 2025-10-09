package capstone_project.service.websocket;

import capstone_project.dtos.websocket.VehicleLocationMessage;
import capstone_project.entity.vehicle.VehicleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleLocationService {

    private final SimpMessagingTemplate messagingTemplate;

    private static final String TOPIC_ALL_VEHICLES = "/topic/vehicles/locations";
    private static final String TOPIC_VEHICLE_PREFIX = "/topic/vehicles/";
    private static final String TOPIC_ASSIGNMENT_PREFIX = "/topic/assignments/";

    /**
     * Broadcast updated vehicle location to all subscribers
     */
    public void broadcastVehicleLocation(VehicleEntity vehicle) {
        if (vehicle == null || vehicle.getCurrentLatitude() == null || vehicle.getCurrentLongitude() == null) {
            return;
        }

        VehicleLocationMessage message = VehicleLocationMessage.builder()
                .vehicleId(vehicle.getId())
                .latitude(vehicle.getCurrentLatitude())
                .longitude(vehicle.getCurrentLongitude())
                .timestamp(vehicle.getLastUpdated() != null ? vehicle.getLastUpdated() : LocalDateTime.now())
                .licensePlateNumber(vehicle.getLicensePlateNumber())
                .build();

        broadcastVehicleLocation(message);
    }

    /**
     * Broadcast updated vehicle location to all subscribers using the message DTO
     */
    public void broadcastVehicleLocation(VehicleLocationMessage message) {
        // Broadcast to all vehicles topic
        messagingTemplate.convertAndSend(TOPIC_ALL_VEHICLES, message);

        // Broadcast to specific vehicle topic
        messagingTemplate.convertAndSend(TOPIC_VEHICLE_PREFIX + message.getVehicleId(), message);

        // If assignment is known, broadcast to assignment topic
        if (message.getAssignmentId() != null) {
            messagingTemplate.convertAndSend(TOPIC_ASSIGNMENT_PREFIX + message.getAssignmentId(), message);
        }

        log.debug("Broadcast vehicle location: vehicleId={}, lat={}, lng={}",
                message.getVehicleId(), message.getLatitude(), message.getLongitude());
    }

    /**
     * Broadcast vehicle location update
     */
    public void broadcastVehicleLocation(UUID vehicleId, BigDecimal latitude, BigDecimal longitude, String licensePlateNumber) {
        VehicleLocationMessage message = VehicleLocationMessage.builder()
                .vehicleId(vehicleId)
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(LocalDateTime.now())
                .licensePlateNumber(licensePlateNumber)
                .build();

        broadcastVehicleLocation(message);
    }

    /**
     * Broadcast vehicle location update with assignment
     */
    public void broadcastVehicleLocation(UUID vehicleId, BigDecimal latitude, BigDecimal longitude,
                                         String licensePlateNumber, UUID assignmentId) {
        VehicleLocationMessage message = VehicleLocationMessage.builder()
                .vehicleId(vehicleId)
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(LocalDateTime.now())
                .licensePlateNumber(licensePlateNumber)
                .assignmentId(assignmentId)
                .build();

        broadcastVehicleLocation(message);
    }
}
