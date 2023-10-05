package com.linkly.pos.sdk.models.sendKey;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.PosApiRequest;

/**
 * Send a key to the PIN pad. This request does not have a response.
 */
public class SendKeyRequest extends PosApiRequest {

    private UUID sessionId;
    private String key;
    private String data;

    /**
     * Session ID to send the key to.
     * 
     * @return UUID
     */
    public UUID getSessionId() {
        return sessionId;
    }

    /**
     * Sets the sessionId
     * 
     * @param sessionId The UUID value of sessionId.
     */
    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * The key to send to the PIN pad.
     * 
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key The String value of key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Entry data collected by POS. Maximum length of 60 characters.
     * 
     * @return String
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data.
     * 
     * @param data The String value of data.
     */
    public void setData(String data) {
        this.data = data;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.notEmpty(this.sessionId, "sessionId"),
            ValidatorUtil.notEmpty(this.key, "key"),
            ValidatorUtil.maxLength(this.data, "data", 60))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }

}
