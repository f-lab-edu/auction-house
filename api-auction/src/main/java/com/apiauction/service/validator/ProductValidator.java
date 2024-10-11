package com.apiauction.service.validator;

import com.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class ProductValidator {
    public void isAvailableToBid(Product product, Long bidAmount) {
        product.isAvailableToBid(bidAmount);
    }
}
