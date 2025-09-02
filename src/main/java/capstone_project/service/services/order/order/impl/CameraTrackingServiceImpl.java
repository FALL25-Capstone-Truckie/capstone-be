package capstone_project.service.services.order.order.impl;

import capstone_project.dtos.request.order.CameraTrackingRequest;
import capstone_project.dtos.response.order.CameraTrackingResponse;
import capstone_project.entity.device.CameraTrackingEntity;
import capstone_project.service.entityServices.device.CameraTrackingEntityService;
import capstone_project.service.entityServices.device.DeviceEntityService;
import capstone_project.service.entityServices.vehicle.VehicleAssignmentEntityService;
import capstone_project.service.mapper.order.CameraTrackingMapper;
import capstone_project.service.services.cloudinary.CloudinaryService;
import capstone_project.service.services.order.order.CameraTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraTrackingServiceImpl implements CameraTrackingService {
    private final CameraTrackingEntityService cameraTrackingEntityService;
    private final VehicleAssignmentEntityService vehicleAssignmentEntityService;
    private final DeviceEntityService deviceEntityService;
    private final CameraTrackingMapper cameraTrackingMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public CameraTrackingResponse saveTracking(CameraTrackingRequest request) throws IOException {
        // Upload to Cloudinary
        Map<String, Object> uploadResult = cloudinaryService.uploadFile(
                request.file(),
                request.fileName(),
                request.folder()
        );

        String videoUrl = (String) uploadResult.get("secure_url");

        // Build entity
        CameraTrackingEntity entity = CameraTrackingEntity.builder()
                .videoUrl(videoUrl)
                .trackingAt(LocalDateTime.now())
                .status(request.status())
                .vehicleAssignmentEntity(vehicleAssignmentEntityService.findContractRuleEntitiesById(request.vehicleAssignmentId()).get())
                .deviceEntity(deviceEntityService.findContractRuleEntitiesById(request.deviceId()).get())
                .build();

        CameraTrackingEntity saved = cameraTrackingEntityService.save(entity);

        return cameraTrackingMapper.toResponse(saved);
    }

    @Override
    public List<CameraTrackingResponse> getAllTracking() {
        return cameraTrackingEntityService.findAll()
                .stream()
                .map(cameraTrackingMapper::toResponse)
                .toList();
    }

    @Override
    public List<CameraTrackingResponse> getByVehicleAssignment(UUID vehicleAssignmentId) {
        return cameraTrackingEntityService.findByVehicleAssignmentEntity_Id(vehicleAssignmentId)
                .stream()
                .map(cameraTrackingMapper::toResponse)
                .toList();
    }

    @Override
    public List<CameraTrackingResponse> getByDevice(UUID deviceId) {
        return cameraTrackingEntityService.findByDeviceEntity_Id(deviceId)
                .stream()
                .map(cameraTrackingMapper::toResponse)
                .toList();
    }
}
