package capstone_project.service.mapper.order;

import capstone_project.dtos.response.order.CameraTrackingResponse;
import capstone_project.entity.device.CameraTrackingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CameraTrackingMapper {
    CameraTrackingResponse toResponse(CameraTrackingEntity entity);

    List<CameraTrackingEntity> toEntities(List<CameraTrackingResponse> responses);
}
