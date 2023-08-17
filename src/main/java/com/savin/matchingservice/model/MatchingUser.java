package com.savin.matchingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(indexName = "matching-user")
public class MatchingUser extends AbstractEntity {
    @Field(type = FieldType.Text, name = "internalId")
    private String internalId;

    @Field(type = FieldType.Integer, name = "age")
    private int age;

    @Field(type = FieldType.Integer, name = "rating")
    private int rating;
}
