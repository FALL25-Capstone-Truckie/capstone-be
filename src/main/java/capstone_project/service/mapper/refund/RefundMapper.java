package capstone_project.service.mapper.refund;

import capstone_project.dtos.response.refund.GetRefundResponse;
import capstone_project.entity.order.order.RefundEntity;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {
    public GetRefundResponse toRefundResponse(RefundEntity entity) {
        if (entity == null) {
            return null;
        }

        GetRefundResponse.StaffInfo staffInfo = null;
        if (entity.getProcessedByStaff() != null) {
            var staff = entity.getProcessedByStaff();
            staffInfo = new GetRefundResponse.StaffInfo(
                    staff.getId(),
                    staff.getFullName(),
                    staff.getEmail()
            );
        }

        return new GetRefundResponse(
                entity.getId(),
                entity.getRefundAmount(),
                entity.getBankTransferImage(),
                entity.getBankName(),
                entity.getAccountNumber(),
                entity.getAccountHolderName(),
                entity.getTransactionCode(),
                entity.getRefundDate(),
                entity.getNotes(),
                entity.getIssueEntity() != null ? entity.getIssueEntity().getId() : null,
                staffInfo,
                entity.getCreatedAt()
        );
    }
}
