package capstone_project.dtos.response.chat;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRoomResponse(
        @NotNull(message = "OrderId is required")
        String orderId,
        @NotNull(message = "Participants is required")
        List<String> participants
) {
}
