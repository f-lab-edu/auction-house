package com.infra.config.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConstant {
    public static final String KAFKA_TOPIC = "auction";
    public static final String GROUP_ID = "foo";
    public static final String KAFKA_BROKER = "localhost:9092";
}
