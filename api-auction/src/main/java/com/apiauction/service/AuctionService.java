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
public class AuctionService {

    private final ProductRepository productRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final ProductValidator productValidator;
    private final UserValidator userValidator;
    private final AlertSender alertSender;

    @Transactional
    public void bid(BidProductUsecase usecase) {
        userValidator.valid(usecase.bidderId());
        Product product = productRepository.findByIdForUpdate(usecase.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + usecase.productId()));
        productValidator.isAvailableToBid(product, usecase.bidAmount());
        product.updateBidAmount(usecase.bidAmount());
        AuctionHistory auctionHistory = auctionHistoryRepository.save(usecase.toEntity(AlertStatus.IN_PROGRESS));
        alertSender.send(AuctionAlertMessage.createBidAlertMessage(auctionHistory.getId(), product.getSellerId()));
    }
}
