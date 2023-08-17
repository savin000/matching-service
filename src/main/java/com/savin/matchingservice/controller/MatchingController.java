package com.savin.matchingservice.controller;

import com.savin.matchingservice.constants.ControllerConstants;
import com.savin.matchingservice.kafka.producer.MatchResultProducerService;
import com.savin.matchingservice.model.Match;
import com.savin.matchingservice.service.MatchingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.ROOT_PATH + "match")
public class MatchingController {
    private final MatchResultProducerService matchResultProducerService;
    private final MatchingService matchingService;

    public MatchingController(MatchResultProducerService matchResultProducerService,
                              MatchingService matchingService) {
        this.matchResultProducerService = matchResultProducerService;
        this.matchingService = matchingService;
    }

    @PostMapping(produces = "application/json")
    public void findMatch(@RequestParam String matchingUserInternalId) {
        Match match = matchingService.findMatchByMatchingUserInternalId(matchingUserInternalId);
        if (match != null) {
            matchResultProducerService.send(match);
        }
    }
}
