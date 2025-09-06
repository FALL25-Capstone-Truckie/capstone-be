package capstone_project.repository.entityServices.user;

import capstone_project.entity.user.address.AddressEntity;
import capstone_project.repository.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressEntityService extends BaseEntityService<AddressEntity, UUID> {

    List<AddressEntity> findByCustomerId(UUID customerId);

    Optional<AddressEntity> findSenderAddressByCustomerId(UUID customerId);

}

