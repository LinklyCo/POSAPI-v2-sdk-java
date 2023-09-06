package com.linkly.pos.demoPos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.enums.ReceiptAutoPrint;

public class SessionData {

    private ReceiptAutoPrint receiptAutoPrint = ReceiptAutoPrint.POS;
    private boolean cutReceipt;
    private UUID appGuid;
    private List<Pad> pads = new ArrayList<>();
    private List<SurchargeRates> surchargeRates = new ArrayList<>();
    private List<Lane> lanes = new ArrayList<>();

    public ReceiptAutoPrint getReceiptAutoPrint() {
        return receiptAutoPrint;
    }

    public void setReceiptAutoPrint(ReceiptAutoPrint receiptAutoPrint) {
        this.receiptAutoPrint = receiptAutoPrint;
    }

    public boolean isCutReceipt() {
        return cutReceipt;
    }

    public void setCutReceipt(boolean cutReceipt) {
        this.cutReceipt = cutReceipt;
    }

    public UUID getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(UUID appGuid) {
        this.appGuid = appGuid;
    }

    public List<Pad> getPads() {
        return pads;
    }

    public void setPads(List<Pad> pads) {
        this.pads = pads;
    }

    public List<SurchargeRates> getSurchargeRates() {
        return surchargeRates;
    }

    public void setSurchargeRates(List<SurchargeRates> surchargeRates) {
        this.surchargeRates = surchargeRates;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public static class Pad {
        private boolean use;
        private String tag;
        private String value;

        public Pad() {
            super();
        }

        public Pad(boolean use, String tag, String value) {
            super();
            this.use = use;
            this.tag = tag;
            this.value = value;
        }

        public boolean isUse() {
            return use;
        }

        public void setUse(boolean use) {
            this.use = use;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SurchargeRates {

        private String type;
        private String bin;
        private String value;

        public SurchargeRates() {
        }

        public SurchargeRates(String type, String bin, String value) {
            super();
            this.type = type;
            this.bin = bin;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBin() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin = bin;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Lane {

        private String username;
        private String secret;
        private String authEndpoint;
        private String posEndpoint;
        private boolean lastActive = true;
        private List<TransactionSessions> trasactions = new ArrayList<>();

        public Lane() {
            super();
        }

        public Lane(String username, String secret, String authEndpoint, String posEndpoint,
            boolean lastActive,
            List<TransactionSessions> trasactions) {
            super();
            this.username = username;
            this.secret = secret;
            this.authEndpoint = authEndpoint;
            this.posEndpoint = posEndpoint;
            this.lastActive = lastActive;
            this.trasactions = trasactions;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getAuthEndpoint() {
            return authEndpoint;
        }

        public void setAuthEndpoint(String authEndpoint) {
            this.authEndpoint = authEndpoint;
        }

        public String getPosEndpoint() {
            return posEndpoint;
        }

        public void setPosEndpoint(String posEndpoint) {
            this.posEndpoint = posEndpoint;
        }

        public boolean isLastActive() {
            return lastActive;
        }

        public void setLastActive(boolean lastActive) {
            this.lastActive = lastActive;
        }

        public List<TransactionSessions> getTrasactions() {
            return trasactions;
        }

        public void setTrasactions(List<TransactionSessions> trasactions) {
            this.trasactions = trasactions;
        }
    }

    public static class TransactionSessions {

        private String type;
        private UUID sessionId;
        private String requestTimestamp;
        private String responseTimestamp;
        private Object request;
        private Object response;
        private ErrorResponse error;

        public TransactionSessions() {
            super();
        }

        public TransactionSessions(String type, UUID sessionId, String requestTimestamp,
            String responseTimestamp, Object request, PosApiResponse response,
            ErrorResponse error) {
            super();
            this.type = type;
            this.sessionId = sessionId;
            this.requestTimestamp = requestTimestamp;
            this.responseTimestamp = responseTimestamp;
            this.request = request;
            this.response = response;
            this.error = error;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public UUID getSessionId() {
            return sessionId;
        }

        public void setSessionId(UUID sessionId) {
            this.sessionId = sessionId;
        }

        public String getRequestTimestamp() {
            return requestTimestamp;
        }

        public void setRequestTimestamp(String requestTimestamp) {
            this.requestTimestamp = requestTimestamp;
        }

        public String getResponseTimestamp() {
            return responseTimestamp;
        }

        public void setResponseTimestamp(String responseTimestamp) {
            this.responseTimestamp = responseTimestamp;
        }

        public Object getRequest() {
            return request;
        }

        public void setRequest(Object request) {
            this.request = request;
        }

        public Object getResponse() {
            return response;
        }

        public void setResponse(Object response) {
            this.response = response;
        }

        public ErrorResponse getError() {
            return error;
        }

        public void setError(ErrorResponse error) {
            this.error = error;
        }
    }
}
