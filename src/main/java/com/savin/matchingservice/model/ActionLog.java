package com.savin.matchingservice.model;

import com.savin.matchingservice.enums.ActionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "action-logs")
public class ActionLog extends AbstractEntity {
    private Timestamp timestamp;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String entityName;
}
