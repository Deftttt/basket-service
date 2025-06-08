package com.shopping.basket.domain.service;

import com.shopping.basket.domain.exception.InsufficientProductQuantityException;
import com.shopping.basket.domain.model.Product;
import com.shopping.basket.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }

    public void checkAndDecreaseQuantity(String productId, int quantity) {
        Product product = getProduct(productId);
        if (!product.hasAvailableQuantity(quantity)) {
            throw new InsufficientProductQuantityException(productId, quantity, product.getAvailableQuantity());
        }
        product.decreaseQuantity(quantity);
        productRepository.save(product);
    }

    public void increaseQuantity(String productId, int quantity) {
        Product product = getProduct(productId);
        product.increaseQuantity(quantity);
        productRepository.save(product);
    }
} 