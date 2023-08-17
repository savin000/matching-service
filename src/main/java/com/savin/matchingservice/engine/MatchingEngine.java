package com.savin.matchingservice.engine;

import com.savin.matchingservice.kafka.producer.MatchResultProducerService;
import com.savin.matchingservice.model.Match;
import com.savin.matchingservice.service.MatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchingEngine {
    private final MatchResultProducerService matchResultProducerService;
    private final MatchingService matchingService;

    public MatchingEngine(MatchResultProducerService matchResultProducerService,
                          MatchingService matchingService) {
        this.matchResultProducerService = matchResultProducerService;
        this.matchingService = matchingService;
    }

    @Scheduled(fixedDelay = 1000)
    public void runMatching() {
        Match match = matchingService.findMatch();
        if (match != null) {
            matchResultProducerService.send(match);
        }
    }
}
