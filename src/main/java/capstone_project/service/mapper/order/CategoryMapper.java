package capstone_project.service.mapper.order;

import capstone_project.dtos.request.order.CategoryRequest;
import capstone_project.dtos.response.order.CategoryResponse;
import capstone_project.entity.order.order.CategoryEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(final CategoryEntity categoryEntity);

    CategoryEntity mapRequestToEntity(final CategoryRequest categoryRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCategoryEntity(CategoryRequest request,@MappingTarget CategoryEntity entity);
}
