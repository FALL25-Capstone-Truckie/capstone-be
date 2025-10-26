package capstone_project.controller.notification;


import capstone_project.dtos.request.notification.NotificationRequest;
import capstone_project.service.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications")
    public NotificationRequest sendMessage(NotificationRequest notification) {
        notification.setTimestamp(LocalDateTime.now());
        System.out.println("Received notification message: " + notification);
        return notification;
    }

    @MessageMapping("/user/{userId}")
    public void sendToUser(@DestinationVariable UUID userId, NotificationRequest notification) {
        notification.setTimestamp(LocalDateTime.now());
        notificationService.sendUserNotification(userId, notification);
    }

    // REST endpoint to send notifications from other services
    @PostMapping("/send")
    @ResponseBody
    public NotificationRequest sendNotification(@RequestBody NotificationRequest notification) {
        notification.setTimestamp(LocalDateTime.now());

        if (notification.getUserId() != null) {
            notificationService.sendUserNotification(notification.getUserId(), notification);
        } else if (notification.getEntityType() != null && notification.getEntityId() != null) {
            notificationService.sendEntityNotification(
                    notification.getEntityType(),
                    notification.getEntityId(),
                    notification
            );
        } else {
            notificationService.sendGlobalNotification(notification);
        }

        return notification;
    }
}