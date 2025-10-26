package capstone_project.service.services.notification;


import capstone_project.dtos.request.notification.NotificationRequest;

import java.util.UUID;

public interface NotificationService {
    void sendGlobalNotification(NotificationRequest notification);
    void sendUserNotification(UUID userId, NotificationRequest notification);
    void sendEntityNotification(String entityType, UUID entityId, NotificationRequest notification);


}
