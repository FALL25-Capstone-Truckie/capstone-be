package capstone_project.service.services.room;

import capstone_project.dtos.request.chat.CreateRoomRequest;
import capstone_project.dtos.response.chat.CreateRoomResponse;

public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);
}
