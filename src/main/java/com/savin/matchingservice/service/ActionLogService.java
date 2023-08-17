package com.savin.matchingservice.service;

import com.savin.matchingservice.model.ActionLog;
import com.savin.matchingservice.repository.ActionLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionLogService {
    private static final String NO_ACTION_LOG_FOUND = "No action log found";

    private final ActionLogRepository actionLogRepository;

    @Autowired
    public ActionLogService(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    public ActionLog getActionLog(ActionLog actionLog) {
        return this.actionLogRepository.findById(actionLog.getId())
                .orElseThrow(() -> new EntityNotFoundException(NO_ACTION_LOG_FOUND));
    }

    public void addActionLog(ActionLog actionLog) {
        this.actionLogRepository.save(actionLog);
    }

    public void deleteActionLog(ActionLog actionLog) {
        if (this.getActionLog(actionLog) != null) {
            this.actionLogRepository.delete(actionLog);
        }
    }
}
