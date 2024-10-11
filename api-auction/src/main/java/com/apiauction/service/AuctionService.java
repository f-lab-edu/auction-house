package com.apiauction.service;

import com.apiauction.service.usecase.BidProductUsecase;
import com.apiauction.service.validator.ProductValidator;
import com.apiauction.service.validator.UserValidator;
import com.domain.alert.AlertStatus;
import com.domain.auction.AuctionHistory;
import com.domain.product.Product;
import com.infra.alert.kafka.message.AuctionAlertMessage;
import com.infra.alert.kafka.AlertSender;
import com.infra.auction.repository.AuctionHistoryRepository;
import com.infra.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final ProductRepository productRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final ProductValidator productValidator;
    private final UserValidator userValidator;
    private final AlertSender alertSender;

    @Transactional
    public void bid(BidProductUsecase usecase) {
        Product product = productRepository.findById(usecase.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + usecase.productId()));
        productValidator.isAvailableToBid(product, usecase.bidAmount());
        userValidator.valid(usecase.bidderId());
        AuctionHistory auctionHistory = auctionHistoryRepository.save(usecase.toEntity(AlertStatus.IN_PROGRESS));
        alertSender.send(AuctionAlertMessage.createBidAlertMessage(auctionHistory.getId(), product.getSellerId()));
    }
}
