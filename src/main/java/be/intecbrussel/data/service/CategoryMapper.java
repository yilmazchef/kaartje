package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.CategoryDto;
import be.intecbrussel.data.entity.Category;
import org.mapstruct.*;

@Mapper ( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring" )
public interface CategoryMapper {
    @Mapping ( source = "categoryId", target = "id" )
    Category categoryDtoToCategory ( CategoryDto categoryDto );

    @Mapping ( source = "id", target = "categoryId" )
    CategoryDto categoryToCategoryDto ( Category category );

    @Mapping ( source = "categoryId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Category updateCategoryFromCategoryDto ( CategoryDto categoryDto, @MappingTarget Category category );
}
