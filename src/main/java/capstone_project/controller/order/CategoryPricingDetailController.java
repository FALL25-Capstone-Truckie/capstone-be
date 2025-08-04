package capstone_project.controller.order;

import capstone_project.dtos.request.order.CategoryPricingDetailRequest;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.order.CategoryPricingDetailResponse;
import capstone_project.service.services.order.category.CategoryPricingDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${category-pricing-detail.api.base-path}")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CategoryPricingDetailController {
    private final CategoryPricingDetailService categoryPricingDetailService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryPricingDetailResponse>>> getAllCategoryPricingDetails() {
        final var result = categoryPricingDetailService.getAllCategoryPricingDetails();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryPricingDetailResponse>> getCategoryPricingDetailById(@PathVariable("id") UUID id) {
        final var result = categoryPricingDetailService.getCategoryPricingDetailById(id);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CategoryPricingDetailResponse>> createCategoryPricingDetail(@RequestBody @Valid CategoryPricingDetailRequest categoryPricingDetailRequest) {
        final var result = categoryPricingDetailService.createCategoryPricingDetail(categoryPricingDetailRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryPricingDetailResponse>> updateCategoryPricingDetail(
            @PathVariable("id") UUID id,
            @RequestBody @Valid CategoryPricingDetailRequest categoryPricingDetailRequest) {
        final var result = categoryPricingDetailService.updateCategoryPricingDetail(id, categoryPricingDetailRequest);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
