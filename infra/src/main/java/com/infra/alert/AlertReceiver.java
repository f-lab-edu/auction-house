package com.infra.alert;

import com.infra.config.util.KafkaConstant;
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
    @RetryableTopic(
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            listenerContainerFactory = "retryConcurrentFactory",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            kafkaTemplate = "entryKafkaTemplate")
    @KafkaListener(
            topics = "bee-chat2",
            groupId = KafkaConstant.GROUP_ID,
            containerFactory = "kafkaEntryListenerContainerFactory"
    )
    public void listen(Alert alert) {
        log.debug("sending enty via kafka listener..");
    }

    /**
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
