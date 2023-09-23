package com.linkly.pos.demoPos.utils;

import static com.linkly.pos.sdk.common.StringUtil.isNullOrWhiteSpace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkly.pos.demoPos.utils.SessionData.Lane;
import com.linkly.pos.demoPos.utils.SessionData.Pad;
import com.linkly.pos.demoPos.utils.SessionData.SurchargeRates;
import com.linkly.pos.demoPos.utils.SessionData.TransactionSessions;
import com.linkly.pos.sdk.common.StringUtil;
import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.PosApiResponse;

public class DataManager {

    private final static String FILE_NAME = "sessions.json";
    private final static ObjectMapper MAPPER = new ObjectMapper();

    private SessionData sessions = new SessionData();
    private Lane currentLane;
    private boolean lastSessionInterrupted;
    private UUID lastInterruptedSessionId;

    public DataManager(UUID appGuid) {
        load();
        sessions.setAppGuid(appGuid);
        MAPPER.registerModule(new JavaTimeModule());
    }

    public void savePads(Map<String, String> purchaseAnalysisData) {
        List<Pad> pads = purchaseAnalysisData.entrySet()
            .stream()
            .filter(e -> e.getValue() != null && e.getValue().length() > 0)
            .map(e -> {
                Pad pad = new Pad();
                pad.setUse(true);
                pad.setTag(e.getKey());
                pad.setValue(e.getValue());
                return pad;
            }).collect(Collectors.toList());
        sessions.setPads(pads);
        save();
    }

    public void saveSurcharges(List<SurchargeRates> rates) {
        rates.removeIf(e -> e.getBin() == null || e.getBin().length() == 0
            || e.getValue() == null || e.getValue().length() == 0);
        sessions.setSurchargeRates(rates);
        save();
    }

    public List<Lane> getLanes() {
        return sessions.getLanes();
    }

    public void changeLane(String username) {
        Lane newCurrentLane = sessions.getLanes()
            .stream()
            .filter(l -> l.getUsername().equals(username))
            .findFirst()
            .get();
        currentLane = newCurrentLane;
    }

    public void saveCurrent(Lane lane) {
        if (lane != null) {
            List<Lane> lanes = sessions.getLanes();
            lanes.forEach(l -> {
                if (l.getUsername() != null && !l.getUsername().equals(lane.getUsername())) {
                    l.setLastActive(false);
                }
            });
            boolean anyMatch = false;
            if (lanes.size() == 1 && isNullOrWhiteSpace(lanes.get(0).getSecret())) {
                lanes.set(0, lane);
                anyMatch = true;
            }
            for (Lane l : lanes) {
                if (l.getUsername() != null && l.getUsername().equals(lane.getUsername())) {
                    l.setSecret(lane.getSecret());
                    anyMatch = true;
                }
            }

            lane.setLastActive(true);
            currentLane = lane;
            if (!anyMatch) {
                sessions.getLanes().add(lane);
            }
            save();
        }
    }

    public void load() {
        try {
            String content = null;
            var file = new File(FILE_NAME);
            if (file.exists()) {
                content = FileCopyUtils.copyToString(new FileReader(file));
            }
            if (!StringUtil.isNullOrWhiteSpace(content)) {
                var fileSessions = MAPPER.readValue(content, SessionData.class);
                if (sessions != null) {
                    sessions = fileSessions;
                }
                if (sessions.getLanes().size() == 0) {
                    sessions.getLanes().add(new Lane());
                }
            }
            else {
                sessions.getLanes().add(new Lane());
            }
            currentLane = sessions.getLanes().stream().filter(l -> l.isLastActive()).findFirst()
                .orElse(sessions.getLanes().get(0));
            if (!CollectionUtils.isEmpty(currentLane.getTrasactions())) {
                List<TransactionSessions> foundTransactions = currentLane.getTrasactions()
                    .stream().filter(t -> t.getType() != null)
                    .collect(Collectors.toList());
                TransactionSessions lastTransaction = CollectionUtils.lastElement(
                    foundTransactions);
                if (lastTransaction != null && lastTransaction.getResponse() == null
                    && lastTransaction.getError() == null) {
                    lastSessionInterrupted = true;
                    lastInterruptedSessionId = lastTransaction.getSessionId();
                }
            }
        }
        catch (IOException e) {
            // happening in the background and not user-facing
        }
    }

    public void saveTransaction(UUID sessionId, String type, IBaseRequest request,
        PosApiResponse response, ErrorResponse errorResponse) {
        if (currentLane != null) {
            TransactionSessions transaction = currentLane.getTrasactions().stream()
                .filter(l -> l.getSessionId().equals(sessionId)).findFirst().orElse(null);
            if (transaction == null) {
                var newTransaction = new TransactionSessions(type, sessionId, type, type, request,
                    response,
                    errorResponse);
                currentLane.getTrasactions().add(newTransaction);
            }
            else {
                transaction.setResponseTimestamp(LocalDateTime.now().toString());
                if (response != null) {
                    transaction.setResponse(response);
                }
                if (errorResponse != null) {
                    transaction.setError(errorResponse);
                }
            }
            save();
        }
    }

    private void save() {
        try {
            var json = MAPPER.writeValueAsString(sessions);
            var writer = new BufferedWriter(new FileWriter(FILE_NAME));
            writer.write(json);
            writer.close();
        }
        catch (IOException e) {
            // happening in the background and not user-facing
            e.printStackTrace();
        }
    }

    public Lane getCurrentLane() {
        return currentLane;
    }

    public void setCurrentLane(Lane currentLane) {
        this.currentLane = currentLane;
    }

    public boolean isLastSessionInterrupted() {
        return lastSessionInterrupted;
    }

    public void setLastSessionInterrupted(boolean lastSessionInterrupted) {
        this.lastSessionInterrupted = lastSessionInterrupted;
    }

    public UUID getLastInterruptedSessionId() {
        return lastInterruptedSessionId;
    }

    public void setLastInterruptedSessionId(UUID lastInterruptedSessionId) {
        this.lastInterruptedSessionId = lastInterruptedSessionId;
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public SessionData getSessions() {
        return sessions;
    }
}