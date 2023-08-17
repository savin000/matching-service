package com.savin.matchingservice.kafka.consumer;

import com.savin.matchingservice.model.MatchingUser;
import com.savin.matchingservice.service.MatchingUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchRequestConsumerService {
    private final MatchingUserService matchingUserService;

    @Autowired
    public MatchRequestConsumerService(MatchingUserService matchingUserService) {
        this.matchingUserService = matchingUserService;
    }

    @KafkaListener(topics = "${kafka.consumer.topic-name}", groupId = "group_id", containerFactory = "matchingUserKafkaListener")
    public void listen(MatchingUser matchingUser) {
        log.info("Received matching user: {}", matchingUser.toString());

        matchingUserService.addMatchingUser(matchingUser);
    }
}
