package com.aryil.productapi.mapper;

import com.aryil.productapi.dto.CategoryDTO;
import com.aryil.productapi.entity.Category;
import com.aryil.productapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    CategoryDTO toDto(Category category);

    default Set<String> mapProducts(Set<Product> products) {
        if (products == null) return Set.of();
        return products.stream().map(Product::getName).collect(Collectors.toSet());
    }
}
