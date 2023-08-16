package com.savin.matchingservice.repository;

import com.savin.matchingservice.model.MatchingUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingUserRepository extends ElasticsearchRepository<MatchingUser, String> {
    Optional<MatchingUser> getMatchingUserByInternalId(String matchingUserInternalId);

    void deleteMatchingUserByInternalId(String matchingUserInternalId);
}
