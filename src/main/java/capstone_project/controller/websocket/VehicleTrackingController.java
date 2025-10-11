package capstone_project.controller.websocket;

import capstone_project.dtos.websocket.VehicleLocationMessage;
import capstone_project.service.websocket.VehicleLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller for handling vehicle tracking WebSocket communications
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class VehicleTrackingController {

    private final SimpMessagingTemplate messagingTemplate;
    private final VehicleLocationService vehicleLocationService;

    /**
     * Handler for client subscription - allows clients to subscribe to tracking updates
     * Client subscribes to: /topic/vehicles/{vehicleId}
     */
    @MessageMapping("/track/{vehicleId}")
    public void trackVehicle(@DestinationVariable String vehicleId) {
        // This method is triggered when a client subscribes to a vehicle
        // You might want to send initial location data or track subscription count
        log.info("New client subscribed to track vehicle: {}", vehicleId);

        // Optional: Send a confirmation message back to the subscriber
        messagingTemplate.convertAndSend("/topic/vehicles/" + vehicleId + "/status", "Tracking started");
    }
}
