package capstone_project.dtos.request.room;

import capstone_project.common.enums.RoomEnum;
import capstone_project.common.enums.enumValidator.EnumValidator;
import jakarta.validation.constraints.NotBlank;

public record GetRoomRequest(
        @NotBlank
        String orderId,
        @EnumValidator(enumClass = RoomEnum.class, message = "Invalid room type")
        String roomType
) {
}
