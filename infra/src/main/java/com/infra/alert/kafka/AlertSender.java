package com.infra.alert.kafka;

import com.infra.alert.kafka.message.AuctionAlertMessage;
import com.infra.common.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertSender {
    private final KafkaTemplate<String, Message> alertKafkaTemplate;
    public static final String KAFKA_TOPIC = "auction-topic";

    @Transactional
    public void send(AuctionAlertMessage alert) {
        alertKafkaTemplate.send(KAFKA_TOPIC, alert);
    }

}
