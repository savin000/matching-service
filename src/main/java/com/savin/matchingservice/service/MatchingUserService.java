package com.savin.matchingservice.service;

import com.savin.matchingservice.model.MatchingUser;
import com.savin.matchingservice.repository.MatchingUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingUserService {
    private static final String NO_MATCHING_USER_FOUND = "No matching user found";

    private final MatchingUserRepository matchingUserRepository;

    @Autowired
    public MatchingUserService(MatchingUserRepository matchingUserRepository) {
        this.matchingUserRepository = matchingUserRepository;
    }

    public List<MatchingUser> getMatchingUsers() {
        return IterableUtils.toList(this.matchingUserRepository.findAll());
    }

    public MatchingUser getMatchingUser(String matchingUserId) {
        return this.matchingUserRepository.findById(matchingUserId)
                .orElseThrow(() -> new EntityNotFoundException(NO_MATCHING_USER_FOUND));
    }

    public MatchingUser getMatchingUserByInternalId(String matchingUserInternalId) {
        return this.matchingUserRepository.getMatchingUserByInternalId(matchingUserInternalId)
                .orElseThrow(() -> new EntityNotFoundException(NO_MATCHING_USER_FOUND));
    }

    public void addMatchingUser(MatchingUser matchingUser) {
        this.matchingUserRepository.save(matchingUser);
    }

    public MatchingUser updateMatchingUser(String matchingUserId, MatchingUser matchingUser) {
        MatchingUser existingMatchingUser = this.getMatchingUser(matchingUserId);
        if (existingMatchingUser != null) {
            BeanUtils.copyProperties(matchingUser, existingMatchingUser,"id");
            this.matchingUserRepository.save(existingMatchingUser);
        }
        return existingMatchingUser;
    }

    public void deleteMatchingUserById(String matchingUserId) {
        if (this.getMatchingUser(matchingUserId) != null) {
            this.matchingUserRepository.deleteById(matchingUserId);
        }
    }

    public void deleteMatchingUserByInternalId(String matchingUserInternalId) {
        if (this.getMatchingUserByInternalId(matchingUserInternalId) != null) {
            this.matchingUserRepository.deleteMatchingUserByInternalId(matchingUserInternalId);
        }
    }

    public void deleteMatchedUsers(MatchingUser matchingUser1, MatchingUser matchingUser2) {
        deleteMatchingUserByInternalId(matchingUser1.getInternalId());
        deleteMatchingUserByInternalId(matchingUser2.getInternalId());
    }
}
