package com.savin.matchingservice.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match extends AbstractEntity {
    private String matchedUserInternalId1;

    private String matchedUserInternalId2;
}
