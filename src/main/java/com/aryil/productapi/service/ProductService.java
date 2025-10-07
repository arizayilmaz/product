package com.aryil.productapi.service;

import com.aryil.productapi.dto.request.ProductCreateRequest;
import com.aryil.productapi.entity.Category;
import com.aryil.productapi.entity.Product;
import com.aryil.productapi.messaging.MessageProducer;
import com.aryil.productapi.repository.CategoryRepository;
import com.aryil.productapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MessageProducer messageProducer;

    @Transactional
    public Product createProduct(ProductCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setSku(request.getSku());

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            product.setCategories(categories);
        }

        Product saved = productRepository.save(product);

        messageProducer.sendProductMessage("New product created: " + saved.getName());

        return saved;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(UUID id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SELLER"))) {
            if (product.getCreatedBy() == null || !product.getCreatedBy().getUsername().equals(currentUsername)) {
                throw new AccessDeniedException("Kendi ürünlerinizi güncelleyebilirsiniz!");
            }
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setSku(request.getSku());

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            product.setCategories(categories);
        }

        Product updated = productRepository.save(product);

        messageProducer.sendProductMessage("Product updated: " + updated.getName());

        return updated;
    }

    @Transactional
    public Product updateProductCategories(UUID id, Set<UUID> categoryIds) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        product.setCategories(categories);

        Product updated = productRepository.save(product);

        messageProducer.sendProductMessage("Product categories updated: " + updated.getName());

        return updated;
    }

    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SELLER"))) {
            if (product.getCreatedBy() == null || !product.getCreatedBy().getUsername().equals(currentUsername)) {
                throw new AccessDeniedException("Kendi ürünlerinizi silebilirsiniz!");
            }
        }

        productRepository.delete(product);

        messageProducer.sendProductMessage("Product deleted: " + product.getName());
    }
}
