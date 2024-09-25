package com.apiproduct.service.usecase;

import com.domain.product.Product;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreateProductUsecase(
        String name,
        Long minimumAmount,
        Instant auctionPeriod,
        Long sellerId
) {
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .minimumAmount(minimumAmount)
                .auctionPeriod(auctionPeriod)
                .sellerId(sellerId)
                .build();
    }
}
