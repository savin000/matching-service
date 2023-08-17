package com.savin.matchingservice.utils;

import com.savin.matchingservice.model.MatchingUser;
import org.opensearch.search.SearchHit;

public class ConversionUtils {
    public static MatchingUser searchHitToMatchingUser(SearchHit searchHit) {
        MatchingUser matchingUser = new MatchingUser();
        matchingUser.setInternalId(searchHit.getSourceAsMap().get("internalId").toString());
        matchingUser.setAge((int) searchHit.getSourceAsMap().get("age"));
        matchingUser.setRating((int) searchHit.getSourceAsMap().get("rating"));

        return matchingUser;
    }
}
