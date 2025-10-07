package com.aryil.productapi.controller;

import com.aryil.productapi.dto.CategoryDTO;
import com.aryil.productapi.dto.request.CategoryRequest;
import com.aryil.productapi.dto.response.ApiResponse;
import com.aryil.productapi.entity.Category;
import com.aryil.productapi.mapper.CategoryMapper;
import com.aryil.productapi.messaging.MessageProducer;
import com.aryil.productapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final MessageSource messageSource;
    private final MessageProducer messageProducer;

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(
            @RequestBody CategoryRequest request, Locale locale) {
        Category category = categoryService.createCategory(request.getName(), request.getDescription());
        messageProducer.sendCategoryMessage("Kategori oluşturuldu: " + category.getName());
        String msg = messageSource.getMessage("success.category.created", null, locale);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, msg, categoryMapper.toDto(category)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories(Locale locale) {
        List<CategoryDTO> categories = categoryService.getAllCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        String msg = messageSource.getMessage("success.category.listed", null, locale);
        return ResponseEntity.ok(new ApiResponse<>(true, msg, categories));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable UUID id, Locale locale) {
        return categoryService.getCategoryById(id)
                .map(category -> {
                    String msg = messageSource.getMessage("success.category.found", null, locale);
                    return ResponseEntity.ok(new ApiResponse<>(true, msg, categoryMapper.toDto(category)));
                })
                .orElseGet(() -> {
                    String msg = messageSource.getMessage("error.category.notfound", null, locale);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
                });
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryByName(@RequestParam String name, Locale locale) {
        return categoryService.getCategoryByName(name)
                .map(category -> {
                    String msg = messageSource.getMessage("success.category.found", null, locale);
                    return ResponseEntity.ok(new ApiResponse<>(true, msg, categoryMapper.toDto(category)));
                })
                .orElseGet(() -> {
                    String msg = messageSource.getMessage("error.category.notfound", null, locale);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
                });
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable UUID id, @RequestBody CategoryRequest request, Locale locale) {
        return categoryService.updateCategory(id, request.getName(), request.getDescription())
                .map(category -> {
                    String msg = messageSource.getMessage("success.category.updated", null, locale);
                    return ResponseEntity.ok(new ApiResponse<>(true, msg, categoryMapper.toDto(category)));
                })
                .orElseGet(() -> {
                    String msg = messageSource.getMessage("error.category.notfound", null, locale);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id, Locale locale) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            String msg = messageSource.getMessage("success.category.deleted", null, locale);
            return ResponseEntity.ok(new ApiResponse<>(true, msg, null));
        } else {
            String msg = messageSource.getMessage("error.category.notfound", null, locale);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
        }
    }
}
