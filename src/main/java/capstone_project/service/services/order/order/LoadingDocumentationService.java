package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.LoadingDocumentationRequest;
import capstone_project.dtos.response.order.LoadingDocumentationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for handling loading documentation operations
 * Combines both packing proof images and seal image management
 */
public interface LoadingDocumentationService {

    /**
     * Document the loading process with both packing proof images and a seal image
     *
     * @param packingProofImages List of packing proof images
     * @param sealImage Image of the seal
     * @param request Request containing vehicleAssignmentId and descriptions
     * @return Combined response with both packing proof images and seal information
     * @throws IOException If there's an issue with image processing
     */
    LoadingDocumentationResponse documentLoading(
        List<MultipartFile> packingProofImages,
        MultipartFile sealImage,
        LoadingDocumentationRequest request
    ) throws IOException;

    /**
     * Get all loading documentation for a specific vehicle assignment
     *
     * @param vehicleAssignmentId The ID of the vehicle assignment
     * @return Combined response with both packing proof images and seal information
     */
    LoadingDocumentationResponse getLoadingDocumentation(UUID vehicleAssignmentId);
}
