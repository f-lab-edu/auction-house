package com.infra.alert.kafka;

import com.domain.alert.AlertStatus;
import com.domain.alert.AuctionAlert;
import com.infra.alert.kafka.dto.AuctionAlertResponseMessage;
import com.infra.alert.kafka.message.AuctionAlertMessage;
import com.infra.alert.repository.AuctionAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertReceiver {
    public static final String KAFKA_TOPIC = "auction-topic";
    public static final String KAFKA_GROUP_ID = "auctio-group-id";

    private final AuctionAlertRepository alertRepository;
    private final TransactionKafkaProducer transactionKafkaProducer;

    @RetryableTopic(
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            listenerContainerFactory = "retryConcurrentFactory",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            kafkaTemplate = "alertKafkaTemplate")
    @KafkaListener(
            topics = KAFKA_TOPIC,
            groupId = KAFKA_GROUP_ID,
            containerFactory = "alertListenerContainerFactory"
    )
    public void listen(AuctionAlertMessage alertMessage) {
        Long auctionHistoryId = alertMessage.getAuctionHistoryId();
        try {
            AuctionAlert alert = alertMessage.toEntity();
            alertRepository.save(alert);

            AuctionAlertResponseMessage successMessage = new AuctionAlertResponseMessage(auctionHistoryId, AlertStatus.SUCCESS);
            transactionKafkaProducer.sendTransactionResultMessage(successMessage);
        } catch (RuntimeException e) {
            AuctionAlertResponseMessage failMessage = new AuctionAlertResponseMessage(auctionHistoryId, AlertStatus.FAILED);
            transactionKafkaProducer.sendTransactionResultMessage(failMessage);
        }

    }

    /**
     * TODO
     * Dlt에 메세지 쌓일 때 실패 로그 쌓음
     */
    @DltHandler
    public void dltHandler(ConsumerRecord<String, String> record,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.RECEIVED_PARTITION) int partitionId,
                           @Header(KafkaHeaders.OFFSET) Long offset,
                           @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
        log.error("received message='{}' with partitionId='{}', offset='{}', topic='{}'", record.value(), offset,
                partitionId, topic);
        // kafkaConsumerService.saveFailedMessage(topic, partitionId, offset, record.value(), errorMessage);
    }
}
