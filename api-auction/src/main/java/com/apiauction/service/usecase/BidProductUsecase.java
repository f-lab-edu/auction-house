package com.apiauction.service.usecase;

import com.domain.auction.AuctionHistory;
import lombok.Builder;

@Builder
public record BidProductUsecase(
        Long bidAmount,
        Long productId,
        Long bidderId
) {
    public AuctionHistory toEntity() {
        return AuctionHistory
                .builder()
                .bidAmount(bidAmount)
                .productId(productId)
                .bidderId(bidderId)
                .build();
    }
}
