package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.CreatePackingProofImageRequest;
import capstone_project.dtos.request.order.UpdatePackingProofImageRequest;
import capstone_project.dtos.response.order.PackingProofImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PackingProofImageService {
    PackingProofImageResponse uploadAndSaveImage(MultipartFile file, CreatePackingProofImageRequest request) throws IOException;
    PackingProofImageResponse updateImage(UpdatePackingProofImageRequest request);
    PackingProofImageResponse getImage(UUID id);
    List<PackingProofImageResponse> getAllImages();
    List<PackingProofImageResponse> getByVehicleAssignmentId(UUID vehicleAssignmentId);
}
