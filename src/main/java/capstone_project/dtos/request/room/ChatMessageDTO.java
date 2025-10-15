package capstone_project.dtos.request.room;


import com.google.cloud.Timestamp;

public record ChatMessageDTO(
        String id,
        String senderId,
        String senderType,
        String content,
        Timestamp createAt,
        String type
) {
}
