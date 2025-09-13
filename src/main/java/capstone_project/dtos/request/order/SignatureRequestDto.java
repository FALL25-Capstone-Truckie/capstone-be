package capstone_project.dtos.request.order;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record SignatureRequestDto (
        UUID orderId,
        UUID userId,
        String notes
){
}
