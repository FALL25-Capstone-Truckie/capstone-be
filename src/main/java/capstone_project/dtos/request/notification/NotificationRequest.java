package capstone_project.dtos.request.notification;

import capstone_project.common.enums.CommonStatusEnum;
import capstone_project.common.enums.DatabaseTableEnum;
import capstone_project.common.enums.enumValidator.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String type;           // e.g., "ORDER_CREATED", "CONTRACT_SIGNED", etc.
    private String title;          // Brief title of the notification
    private String message;

    @EnumValidator(enumClass = DatabaseTableEnum.class, message = "Type should match with database table name")
    private String entityType;     // e.g., "ORDER", "CONTRACT", etc.
    private UUID entityId;         // ID of the related entity
    private UUID userId;           // ID of the user this notification is for (null if global)
    private LocalDateTime timestamp;
    private String status;         // e.g., "READ", "UNREAD"
    private String priority;       // e.g., "HIGH", "MEDIUM", "LOW"
    private String actionUrl;      // URL to redirect when clicked (optional)
}
