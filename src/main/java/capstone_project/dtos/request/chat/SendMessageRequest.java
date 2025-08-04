package capstone_project.dtos.request.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendMessageRequest(
        @NotBlank(message = "Content is required")
        String content,
        @NotBlank(message = "SenderId is required")
        String senderId,
        @NotBlank(message = "Type is required")
        String type
) {
}
