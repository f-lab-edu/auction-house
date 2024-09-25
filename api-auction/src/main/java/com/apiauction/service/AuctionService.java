package com.apiauction.service;

import com.apiauction.service.usecase.BidProductUsecase;
import com.apiauction.service.validator.ProductValidator;
import com.apiauction.service.validator.UserValidator;
import com.domain.product.Product;
import com.infra.auction.repository.AuctionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionHistoryRepository auctionHistoryRepository;
    private final ProductValidator productValidator;
    private final UserValidator userValidator;

    @Transactional
    public void bid(BidProductUsecase usecase) {
        Product product = productValidator.valid(usecase.productId());
        productValidator.isAvailableToBid(product, usecase.bidAmount());
        userValidator.getUser(usecase.bidderId());
        auctionHistoryRepository.save(usecase.toEntity());
    }
}
