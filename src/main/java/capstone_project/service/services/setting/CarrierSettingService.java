package capstone_project.service.services.setting;

import capstone_project.dtos.request.setting.CarrierSettingRequest;
import capstone_project.dtos.response.setting.CarrierSettingResponse;

import java.util.List;

public interface CarrierSettingService {
    List<CarrierSettingResponse> findAll();
    CarrierSettingResponse findById(Long id);
    CarrierSettingResponse create(CarrierSettingRequest request);
    CarrierSettingResponse update(Long id, CarrierSettingRequest request);
    void delete(Long id);
}
