package com.infra.config.kafka;

import com.infra.alert.kafka.message.AuctionAlertMessage;
import com.infra.config.util.KafkaConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ListenerConfiguration {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, AuctionAlertMessage> alertListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AuctionAlertMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(alertConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AuctionAlertMessage> alertConsumerFactory() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.KAFKA_BROKER);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstant.GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // consumer transaction 설정
        // default : true
        configurations.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configurations.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 이부분
        // read_committed: 커밋된 데이터만 읽는다.
        // default : read_uncommitted
        configurations.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        return new DefaultKafkaConsumerFactory<>(configurations, new StringDeserializer(),
                new JsonDeserializer<>(AuctionAlertMessage.class));
    }

}

