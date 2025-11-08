package capstone_project.service.services.refund;

import capstone_project.dtos.request.refund.ProcessRefundRequest;
import capstone_project.dtos.response.refund.GetRefundResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface RefundService {
    GetRefundResponse processRefund(ProcessRefundRequest request, MultipartFile bankTransferImage);
    GetRefundResponse getRefundByIssueId(UUID issueId);
}
