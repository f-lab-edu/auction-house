package com.infra.alert.kafka;

import com.infra.alert.kafka.dto.AuctionAlertResponseMessage;
import com.infra.common.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionKafkaProducer {
    private static final String ALERT_TRANSACTION_RESULT_TOPIC = "alert-transaction-result";

    private final KafkaTemplate<String, Message> alertKafkaTemplate;

    public void sendTransactionResultMessage(AuctionAlertResponseMessage message) {
        alertKafkaTemplate.send(ALERT_TRANSACTION_RESULT_TOPIC, message);
    }
}
