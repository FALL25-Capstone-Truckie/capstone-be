package capstone_project.controller.order;

import capstone_project.dtos.request.order.CreatePackingProofImageRequest;
import capstone_project.dtos.request.order.UpdatePackingProofImageRequest;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.order.PackingProofImageResponse;
import capstone_project.service.services.order.order.PackingProofImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${packing-proof-image.api.base-path}")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PackingProofImageController {
    private final PackingProofImageService packingProofImageService;

    /**
     * Upload và lưu ảnh đóng hàng
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PackingProofImageResponse>> uploadAndSaveImage(
            @RequestPart("file") MultipartFile file,
            @RequestPart("request") CreatePackingProofImageRequest request) throws IOException {
        final var result = packingProofImageService.uploadAndSaveImage(file, request);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * Cập nhật mô tả ảnh
     */
    @PutMapping
    public ResponseEntity<ApiResponse<PackingProofImageResponse>> updateImage(
            @RequestBody UpdatePackingProofImageRequest request) {
        final var result = packingProofImageService.updateImage(request);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * Lấy ảnh theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PackingProofImageResponse>> getImage(@PathVariable UUID id) {
        final var result = packingProofImageService.getImage(id);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * Lấy tất cả ảnh
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PackingProofImageResponse>>> getAllImages() {
        final var result = packingProofImageService.getAllImages();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * Lấy ảnh theo vehicle assignment ID
     */
    @GetMapping("/vehicle-assignment/{vehicleAssignmentId}")
    public ResponseEntity<ApiResponse<List<PackingProofImageResponse>>> getByVehicleAssignmentId(
            @PathVariable UUID vehicleAssignmentId) {
        final var result = packingProofImageService.getByVehicleAssignmentId(vehicleAssignmentId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
