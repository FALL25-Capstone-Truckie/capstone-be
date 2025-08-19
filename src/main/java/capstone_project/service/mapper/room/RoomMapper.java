package capstone_project.service.mapper.room;

import capstone_project.dtos.response.chat.CreateRoomResponse;
import capstone_project.dtos.response.chat.ParticipantResponse;
import capstone_project.dtos.response.user.CustomerResponse;
import capstone_project.entity.chat.ParticipantInfo;
import capstone_project.entity.chat.RoomEntity;
import capstone_project.entity.user.customer.CustomerEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    CreateRoomResponse toCreateRoomResponse(RoomEntity roomEntity);

    List<CreateRoomResponse> toCreateRoomResponseList(List<RoomEntity> roomEntities);

    ParticipantResponse toParticipantResponse(ParticipantInfo participantInfo);
}
