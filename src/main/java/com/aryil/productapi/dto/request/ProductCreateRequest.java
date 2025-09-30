package com.aryil.productapi.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String sku;
    private Set<UUID> categoryIds;
    private UUID createdByUserId;
}

