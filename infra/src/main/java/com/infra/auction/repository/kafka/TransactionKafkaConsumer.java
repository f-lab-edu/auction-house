package com.infra.auction.repository.kafka;

import com.domain.alert.AlertStatus;
import com.domain.auction.AuctionHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.alert.kafka.dto.AuctionAlertResponseMessage;
import com.infra.auction.repository.AuctionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
 * TODO
 *  알림 전송 실패 시, 후처리 방법 고안 필요
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionKafkaConsumer {
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final ObjectMapper objectMapper;
    @Transactional
    @KafkaListener(topics = "auction-transaction-topic", groupId = "auction-group-id")
    public void consumeAlertTransactionResultEvent(String alertResponseMessage) throws JsonProcessingException {
        AuctionAlertResponseMessage auctionAlertResponseMessage = objectMapper.readValue(alertResponseMessage, AuctionAlertResponseMessage.class);
        AuctionHistory auctionHistory = auctionHistoryRepository.findById(auctionAlertResponseMessage.auctionHistoryId())
                .orElseThrow();
        if (auctionAlertResponseMessage.alertStatus() == AlertStatus.SUCCESS) {
            auctionHistory.alertSendSuccess();
        } else {
            auctionHistory.alertSendFailed();
        }
    }
}
