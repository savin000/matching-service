package com.savin.matchingservice.kafka.producer;

import com.savin.matchingservice.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchResultProducerService {
    @Value("${kafka.producer.topic-name}")
    private String producerTopicName;

    private final KafkaTemplate<String, Match> matchKafkaProducerTemplate;

    @Autowired
    public MatchResultProducerService(KafkaTemplate<String, Match> matchKafkaProducerTemplate) {
        this.matchKafkaProducerTemplate = matchKafkaProducerTemplate;
    }

    public void send(Match match) {
        matchKafkaProducerTemplate.send(producerTopicName, match);
        log.info("Sent matches: {}", match.toString());
    }
}
