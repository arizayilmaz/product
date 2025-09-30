package com.aryil.productapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;
}
