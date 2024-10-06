package com.infra.alert.kafka;

import com.infra.alert.kafka.dto.AuctionAlertResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionKafkaProducer {
    private static final String ALERT_TRANSACTION_RESULT_TOPIC = "alert-transaction-result";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTransactionResultMessage(AuctionAlertResponseMessage message) {
        kafkaTemplate.send(ALERT_TRANSACTION_RESULT_TOPIC, message);
    }
}
