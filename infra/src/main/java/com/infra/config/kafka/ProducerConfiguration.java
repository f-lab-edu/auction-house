package com.infra.config.kafka;

import com.infra.alert.Alert;
import com.infra.config.util.KafkaConstant;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@EnableTransactionManagement
public class ProducerConfiguration {
    @Bean
    public ProducerFactory<String, Alert> messageProducerFactory() {
        DefaultKafkaProducerFactory<String, Alert> factory = new DefaultKafkaProducerFactory<>(
                messageProducerConfigurations());
        factory.setTransactionIdPrefix("tx-");
        return factory;
    }

    @Bean
    public Map<String, Object> messageProducerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConstant.KAFKA_BROKER);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return configurations;
    }
}
