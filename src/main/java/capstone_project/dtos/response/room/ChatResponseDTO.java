package capstone_project.dtos.response.room;

import java.time.Instant;
import java.time.LocalDateTime;

public record ChatResponseDTO(
        String chatId,
        String roomId,
        String senderId,
        String senderType,
        String content,
        String type,
        String status,
        Instant createdAt
) {
}
