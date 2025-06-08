package com.shopping.basket.infrastructure.config;

import com.shopping.basket.domain.model.Product;
import com.shopping.basket.domain.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
public class ProductDataLoader {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            // Delete all existing products
            productRepository.deleteAll();

            // Create sample products
            List<Product> products = Arrays.asList(
                Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("iPhone 14")
                    .price(999.99)
                    .availableQuantity(100)
                    .build(),
                Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Samsung Galaxy S23")
                    .price(899.99)
                    .availableQuantity(100)
                    .build(),
                Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("MacBook Pro")
                    .price(1499.99)
                    .availableQuantity(100)
                    .build(),
                Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("AirPods Pro")
                    .price(249.99)
                    .availableQuantity(100)
                    .build(),
                Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("iPad Air")
                    .price(599.99)
                    .availableQuantity(100)
                    .build()
            );

            // Save all products
            productRepository.saveAll(products);
        };
    }
} 