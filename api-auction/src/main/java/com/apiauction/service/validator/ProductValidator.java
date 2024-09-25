package com.apiauction.service.validator;

import com.domain.product.Product;
import com.infra.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class ProductValidator {
    private final ProductRepository productRepository;

    public Product valid(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + productId));
    }

    public void isAvailableToBid(Product product, Long bidAmount) {
        product.isAvailableToBid(bidAmount);
    }
}
