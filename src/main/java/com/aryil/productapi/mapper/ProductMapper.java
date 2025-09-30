package com.aryil.productapi.mapper;

import com.aryil.productapi.dto.ProductDTO;
import com.aryil.productapi.entity.Category;
import com.aryil.productapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", expression = "java(mapCategories(product.getCategories()))")
    ProductDTO toDto(Product product);

    List<ProductDTO> toDtoList(List<Product> products);

    default Set<String> mapCategories(Set<Category> categories) {
        if (categories == null) return Collections.emptySet();
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
}
