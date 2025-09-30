package com.aryil.productapi.specification;

import com.aryil.productapi.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction(); // filtre uygulanmaz
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasPriceGreaterThanOrEqualTo(BigDecimal minPrice) {
        return (root, query, cb) -> {
            if (minPrice == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Product> hasPriceLessThanOrEqualTo(BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    public static Specification<Product> hasCategory(String categoryName) {
        return (root, query, cb) -> {
            if (categoryName == null || categoryName.isBlank()) {
                return cb.conjunction();
            }
            Join<Object, Object> categories = root.join("categories");
            return cb.equal(cb.lower(categories.get("name")), categoryName.toLowerCase());
        };
    }
}
