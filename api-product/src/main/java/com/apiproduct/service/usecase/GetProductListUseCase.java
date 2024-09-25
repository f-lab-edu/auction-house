package com.apiproduct.service.usecase;

import com.domain.product.Product;
import lombok.Builder;

import java.time.Instant;

@Builder
public record GetProductListUseCase(
        Long productId,
        String name,
        Long minimumAmount,
        Instant auctionPeriod,
        Long bidAmount,
        Long sellerId,
        Instant createdAt,
        Instant updatedAt
) {
    public static GetProductListUseCase fromEntity(Product product) {
        return GetProductListUseCase.builder()
                .productId(product.getId())
                .name(product.getName())
                .minimumAmount(product.getMinimumAmount())
                .auctionPeriod(product.getAuctionPeriod())
                .bidAmount(product.getBidAmount())
                .sellerId(product.getSellerId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
