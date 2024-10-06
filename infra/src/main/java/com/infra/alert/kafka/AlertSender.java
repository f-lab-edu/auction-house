package com.infra.alert.kafka;

import com.infra.alert.kafka.message.AuctionAlertMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertSender {
    private final KafkaTemplate<String, AuctionAlertMessage> alertKafkaTemplate;
    public static final String KAFKA_TOPIC = "auction";

    public void send(AuctionAlertMessage alert) {
        alertKafkaTemplate.send(KAFKA_TOPIC, alert);
    }

}
