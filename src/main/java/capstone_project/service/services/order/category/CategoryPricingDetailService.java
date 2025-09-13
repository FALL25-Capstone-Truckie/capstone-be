package capstone_project.service.services.order.category;

import capstone_project.dtos.request.order.CategoryPricingDetailRequest;
import capstone_project.dtos.response.order.CategoryPricingDetailResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryPricingDetailService {
    List<CategoryPricingDetailResponse> getAllCategoryPricingDetails();

    CategoryPricingDetailResponse getCategoryPricingDetailById(UUID id);

    CategoryPricingDetailResponse createCategoryPricingDetail(CategoryPricingDetailRequest categoryPricingDetailRequest);

    CategoryPricingDetailResponse updateCategoryPricingDetail(UUID id, CategoryPricingDetailRequest categoryPricingDetailRequest);

    void deleteCategory(UUID id);
}
