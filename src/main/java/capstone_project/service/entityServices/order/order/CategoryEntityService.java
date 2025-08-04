package capstone_project.service.entityServices.order.order;

import capstone_project.entity.order.order.CategoryEntity;
import capstone_project.service.entityServices.common.BaseEntityService;

import java.util.Optional;
import java.util.UUID;

public interface CategoryEntityService extends BaseEntityService<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByCategoryName(String categoryName);
}
