package com.infra.auction.repository.kafka;

import com.domain.alert.AlertStatus;
import com.domain.auction.AuctionHistory;
import com.infra.alert.kafka.dto.AlertResponseMessage;
import com.infra.auction.repository.AuctionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/*
* TODO
*  알림 전송 실패 시, 후처리 방법 고안 필요
* */
@Component
@RequiredArgsConstructor
public class TransactionKafkaConsumer {
    private final AuctionHistoryRepository auctionHistoryRepository;

    @KafkaListener(topics = "alert-transaction-result", groupId = "alert-transaction-result-group")
    public void consumeAlertTransactionResultEvent(AlertResponseMessage alertResponseMessage) {
        if(alertResponseMessage.alertStatus() == AlertStatus.FAILED) {

        }
        AuctionHistory auctionHistory = auctionHistoryRepository.findById(alertResponseMessage.auctionHistoryId())
                .orElseThrow();
        auctionHistoryRepository.save(auctionHistory);
    }
}
