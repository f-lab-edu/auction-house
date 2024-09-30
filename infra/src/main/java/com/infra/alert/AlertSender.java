package com.infra.alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.function.BiConsumer;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertSender {
    private final KafkaTemplate<String, Alert> alertKafkaTemplate;

    public void send(String topic, Alert alert) {
        CompletableFuture<SendResult<String, Alert>> send = alertKafkaTemplate.send(topic, alert);
        send.whenComplete(callback(alert));
    }

    private BiConsumer<SendResult<String, Alert>, Throwable> callback(Alert data) {
        return (result, ex) -> {
            if (sendMessageSuccess(result)) {
            }
        };
    }

    private boolean sendMessageSuccess(SendResult<String, Alert> result) {
        return result != null;
    }
}
