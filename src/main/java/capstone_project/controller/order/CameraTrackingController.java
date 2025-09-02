package capstone_project.controller.order;

import capstone_project.dtos.request.order.CameraTrackingRequest;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.order.CameraTrackingResponse;
import capstone_project.service.services.order.order.CameraTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${camera-tracking.api.base-path}")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CameraTrackingController {
    private final CameraTrackingService cameraTrackingService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<CameraTrackingResponse>> uploadTracking(
            @RequestBody CameraTrackingRequest request) throws IOException {
        final var result = cameraTrackingService.saveTracking(request);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<CameraTrackingResponse>>> getAllTrackings() throws IOException {
        final var result = cameraTrackingService.getAllTracking();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/vehicle/{vehicleAssignmentId}")
    public ResponseEntity<ApiResponse<List<CameraTrackingResponse>>> getByVehicleAssignment(
            @PathVariable UUID vehicleAssignmentId) throws IOException {
        final var result = cameraTrackingService.getByVehicleAssignment(vehicleAssignmentId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<ApiResponse<List<CameraTrackingResponse>>> getByDevice(
            @PathVariable UUID deviceId) throws IOException {
        final var result = cameraTrackingService.getByDevice(deviceId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

}
