package capstone_project.service.mapper.order;

import capstone_project.dtos.request.order.CategoryPricingDetailRequest;
import capstone_project.dtos.response.order.CategoryPricingDetailResponse;
import capstone_project.entity.order.order.CategoryEntity;
import capstone_project.entity.order.order.CategoryPricingDetailEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryPricingDetailMapper {

    @Mapping(source = "category", target = "categoryResponse")
    CategoryPricingDetailResponse toCategoryPricingDetailResponse(final CategoryPricingDetailEntity categoryPricingDetailEntity);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    CategoryPricingDetailEntity mapRequestToEntity(final CategoryPricingDetailRequest categoryPricingDetailRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    void toCategoryPricingDetailEntity(CategoryPricingDetailRequest request,@MappingTarget CategoryPricingDetailEntity entity);

    @Named("categoryFromId")
    default CategoryEntity mapCategoryFromId(String categoryId) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(UUID.fromString(categoryId));
        return entity;
    }

}
