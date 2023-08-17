package com.savin.matchingservice.controller;

import com.savin.matchingservice.constants.ControllerConstants;
import com.savin.matchingservice.model.MatchingUser;
import com.savin.matchingservice.service.MatchingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.ROOT_PATH + "matching-users")
public class MatchingUserController {
    private final MatchingUserService matchingUserService;

    @Autowired
    public MatchingUserController(MatchingUserService matchingUserService) {
        this.matchingUserService = matchingUserService;
    }

    @GetMapping(produces = "application/json")
    public List<MatchingUser> getMatchingUsers() {
        return this.matchingUserService.getMatchingUsers();
    }

    @GetMapping(value = "/{matchingUserId}", produces = "application/json")
    public MatchingUser getMatchingUser(@PathVariable String matchingUserId) {
        return this.matchingUserService.getMatchingUser(matchingUserId);
    }

    @PostMapping()
    public void addMatchingUser(@RequestBody MatchingUser matchingUser) {
        this.matchingUserService.addMatchingUser(matchingUser);
    }

    @PutMapping(value = "/{matchingUserId}", produces = "application/json")
    public MatchingUser updateMatchingUser(@PathVariable String matchingUserId,
                                           @RequestBody MatchingUser matchingUser) {
        return this.matchingUserService.updateMatchingUser(matchingUserId, matchingUser);
    }

    @DeleteMapping(value = "/{matchingUserId}", produces = "application/json")
    public void deleteMatchingUser(@PathVariable String matchingUserId) {
        this.matchingUserService.deleteMatchingUserById(matchingUserId);
    }
}
