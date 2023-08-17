package com.savin.matchingservice.service;

import com.savin.matchingservice.enums.ActionType;
import com.savin.matchingservice.exception.SearchRequestException;
import com.savin.matchingservice.model.ActionLog;
import com.savin.matchingservice.model.Match;
import com.savin.matchingservice.model.MatchingUser;
import com.savin.matchingservice.utils.ConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class MatchingService {
    private static final String SEARCH_ERROR = "Error during search.";

    private final RestHighLevelClient opensearchClient;
    private final MatchingUserService matchingUserService;
    private final ActionLogService actionLogService;

    public MatchingService(RestHighLevelClient opensearchClient, MatchingUserService matchingUserService, ActionLogService actionLogService) {
        this.opensearchClient = opensearchClient;
        this.matchingUserService = matchingUserService;
        this.actionLogService = actionLogService;
    }

    public Match findMatch() {
        List<MatchingUser> usersToMatch = this.matchingUserService.getMatchingUsers();
        if (usersToMatch.size() > 0) {
            Random random = new Random();
            MatchingUser matchingUser = usersToMatch.get(random.nextInt(usersToMatch.size()));

            try {
                if (usersToMatch.size() > 1) {
                    return this.executeSearchRequest(matchingUser);
                }
            } catch (SearchRequestException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return null;
    }

    public Match findMatchByMatchingUserInternalId(String matchingUserInternalId) {
        List<MatchingUser> usersToMatch = this.matchingUserService.getMatchingUsers();
        MatchingUser matchingUser = this.matchingUserService.getMatchingUserByInternalId(matchingUserInternalId);

        try {
            if (usersToMatch.size() > 1) {
                return this.executeSearchRequest(matchingUser);
            } else {
                return null;
            }
        } catch (SearchRequestException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Match executeSearchRequest(MatchingUser matchingUser) throws SearchRequestException {
        SearchRequest searchRequest = buildSearchRequestForMatchingUser(matchingUser);

        try {
            SearchResponse searchResponse = opensearchClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchResults = searchResponse.getHits().getHits();

            if (searchResults.length > 0) {
                MatchingUser matchedUser = ConversionUtils.searchHitToMatchingUser(searchResults[0]);

                Match match = new Match(matchingUser.getInternalId(), matchedUser.getInternalId());
                this.matchingUserService.deleteMatchedUsers(matchingUser, matchedUser);

                log.info("Match created: {} - {}", matchingUser, matchedUser);
                ActionLog matchCreatedAction = ActionLog
                        .builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .actionType(ActionType.CREATED)
                        .entityName(Match.class.getName())
                        .build();
                this.actionLogService.addActionLog(matchCreatedAction);

                return match;
            }

            return null;
        } catch (IOException e) {
            throw new SearchRequestException(SEARCH_ERROR, e);
        }
    }

    private SearchRequest buildSearchRequestForMatchingUser(MatchingUser matchingUser) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("matching-user");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("age")
                        .lte(matchingUser.getAge() + 5)
                        .gte(matchingUser.getAge() - 5))
                .must(QueryBuilders.rangeQuery("rating")
                        .lte(matchingUser.getRating() + 500)
                        .gte(matchingUser.getRating() - 500))
                .mustNot(QueryBuilders.matchQuery("internalId", matchingUser.getInternalId()));

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        return searchRequest;
    }
}
