package com.savin.matchingservice.configuration;

import com.savin.matchingservice.model.MatchingUser;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ConsumerFactory<String, MatchingUser> matchingUserConsumerFactory() {
        Map<String, Object> consumerConfiguration = new HashMap<>();

        consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        consumerConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        consumerConfiguration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfiguration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(consumerConfiguration, new StringDeserializer(), new JsonDeserializer<>(MatchingUser.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MatchingUser> matchingUserKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, MatchingUser> kafkaListenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerFactory.setConsumerFactory(matchingUserConsumerFactory());

        return kafkaListenerFactory;
    }
}
