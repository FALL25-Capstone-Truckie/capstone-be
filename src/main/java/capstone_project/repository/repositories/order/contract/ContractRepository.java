package capstone_project.repository.repositories.order.contract;

import capstone_project.entity.order.contract.ContractEntity;
import capstone_project.repository.repositories.common.BaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends BaseRepository<ContractEntity> {
    Optional<ContractEntity> findContractEntityByOrderEntity_Id(UUID orderEntityId);

    void deleteByOrderEntityId(UUID orderId);

    // Find contracts with expired signing deadline (CONTRACT_DRAFT status and signing_deadline < now)
    List<ContractEntity> findByStatusAndSigningDeadlineBefore(String status, LocalDateTime deadline);

    // Find contracts with expired deposit payment deadline (CONTRACT_SIGNED status and deposit_payment_deadline < now)
    List<ContractEntity> findByStatusAndDepositPaymentDeadlineBefore(String status, LocalDateTime deadline);

    // Find contracts with expired full payment deadline (DEPOSITED status and full_payment_deadline < now)
    List<ContractEntity> findByStatusAndFullPaymentDeadlineBefore(String status, LocalDateTime deadline);
}
