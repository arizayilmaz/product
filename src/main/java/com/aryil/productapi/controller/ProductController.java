package com.aryil.productapi.controller;

import com.aryil.productapi.dto.ProductDTO;
import com.aryil.productapi.dto.request.ProductCreateRequest;
import com.aryil.productapi.dto.response.ApiResponse;
import com.aryil.productapi.entity.Product;
import com.aryil.productapi.mapper.ProductMapper;
import com.aryil.productapi.messaging.MessageProducer;
import com.aryil.productapi.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final MessageSource messageSource;
    private final MessageProducer messageProducer;

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAll(Locale locale) {
        List<ProductDTO> products = productMapper.toDtoList(productService.getAll());
        String msg = messageSource.getMessage("success.product.list", null, locale);
        return ResponseEntity.ok(new ApiResponse<>(true, msg, products));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(
            @RequestBody ProductCreateRequest request,
            Locale locale) {
        Product created = productService.createProduct(request);
        messageProducer.sendProductMessage("Ürün oluşturuldu: " + created.getName());
        String msg = messageSource.getMessage("success.product.created", null, locale);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, msg, productMapper.toDto(created)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductCreateRequest request,
            Locale locale) {
        try {
            Product updated = productService.updateProduct(id, request);
            String msg = messageSource.getMessage("success.product.updated", null, locale);
            return ResponseEntity.ok(new ApiResponse<>(true, msg, productMapper.toDto(updated)));
        } catch (EntityNotFoundException e) {
            String msg = messageSource.getMessage("error.product.notfound", null, locale);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
        }
    }

    @PutMapping("/{id}/categories")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProductCategories(
            @PathVariable UUID id,
            @RequestBody Set<UUID> categoryIds,
            Locale locale) {
        try {
            Product updated = productService.updateProductCategories(id, categoryIds);
            String msg = messageSource.getMessage("success.product.updated", null, locale);
            return ResponseEntity.ok(new ApiResponse<>(true, msg, productMapper.toDto(updated)));
        } catch (EntityNotFoundException e) {
            String msg = messageSource.getMessage("error.product.notfound", null, locale);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id, Locale locale) {
        try {
            productService.deleteProduct(id);
            String msg = messageSource.getMessage("success.product.deleted", null, locale);
            return ResponseEntity.ok(new ApiResponse<>(true, msg, null));

        } catch (EntityNotFoundException e) {
            String msg = messageSource.getMessage("error.product.notfound", null, locale);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, msg, null));
        }
    }
}
