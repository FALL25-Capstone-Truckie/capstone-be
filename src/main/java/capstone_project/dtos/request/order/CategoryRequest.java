package capstone_project.dtos.request.order;

import capstone_project.common.enums.CategoryEnum;
import capstone_project.common.enums.enumValidator.EnumValidator;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        @NotBlank(message = "Category name cannot be blank")
        @EnumValidator(enumClass = CategoryEnum.class, message = "Invalid category name")
        String categoryName,
        String description
) {
}
