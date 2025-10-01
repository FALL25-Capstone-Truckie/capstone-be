package capstone_project.service.mapper.setting;

import capstone_project.dtos.request.setting.CarrierSettingRequest;
import capstone_project.dtos.response.setting.CarrierSettingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import capstone_project.entity.setting.CarrierSettingEntity;

@Mapper(componentModel = "spring")
public interface CarrierSettingMapper {
    CarrierSettingEntity toEntity(CarrierSettingRequest request);
    CarrierSettingResponse toResponse(CarrierSettingEntity entity);
    void updateEntityFromRequest(CarrierSettingRequest request, @MappingTarget CarrierSettingEntity entity);
}
