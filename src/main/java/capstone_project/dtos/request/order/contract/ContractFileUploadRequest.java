package capstone_project.dtos.request.order.contract;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ContractFileUploadRequest (
        MultipartFile file,
        UUID contractId
) {}