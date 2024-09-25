package com.apiauction.presentation.dto;

import com.apiauction.service.usecase.BidProductUsecase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BidProductRequestDto(
        @Positive(message = "입찰 금액은 0보다 커야 합니다.")
        Long bidAmount,

        @NotNull(message = "경매 상품이 비어있으면 안됩니다.")
        Long productId,

        @NotNull(message = "입찰자가 비어있으면 안됩니다.")
        Long bidderId
) {
    public BidProductUsecase toUsecase() {
        return BidProductUsecase.builder()
                .bidAmount(bidAmount)
                .productId(productId)
                .bidderId(bidderId)
                .build();
    }
}
