package capstone_project.service.services.notification.impl;

import capstone_project.dtos.request.notification.NotificationRequest;
import capstone_project.service.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Send a notification to all users
     * @param notification The notification to send
     */
    public void sendGlobalNotification(NotificationRequest notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    /**
     * Send a notification to a specific user
     * @param userId The ID of the user to send the notification to
     * @param notification The notification to send
     */
    public void sendUserNotification(UUID userId, NotificationRequest notification) {
        messagingTemplate.convertAndSend("/topic/user/" + userId, notification);
    }

    /**
     * Send a notification related to a specific entity (order, contract, etc.)
     * @param entityType The type of entity (order, contract, etc.)
     * @param entityId The ID of the entity
     * @param notification The notification to send
     */
    public void sendEntityNotification(String entityType, UUID entityId, NotificationRequest notification) {
        messagingTemplate.convertAndSend("/topic/entity/" + entityType + "/" + entityId, notification);
    }

}
