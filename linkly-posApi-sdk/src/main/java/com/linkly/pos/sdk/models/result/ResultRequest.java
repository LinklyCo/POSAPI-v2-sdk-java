package com.linkly.pos.sdk.models.result;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.IValidatable;

/**
 * Send a request to the API to retrieve the responses of a session.
 */
public class ResultRequest implements IBaseRequest, IValidatable {

    private UUID sessionId;

    public ResultRequest() {
    }

    public ResultRequest(UUID sessionId) {
        super();
        this.sessionId = sessionId;
    }

    /**
     * Session ID for which to retrieve the responses.
     * 
     * @return UUID
     */
    public UUID getSessionId() {
        return sessionId;
    }

    /**
     * Sets the sessionId.
     * 
     * @param sessionId
     *            The UUID value of sessionId.
     */
    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Validate the model using {@link ValidatorUtil}
     * 
     * @throws InvalidArgumentException
     *             if contains validation errors
     */
    @Override
    public void validate() {
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.notEmpty(sessionId, "sessionId"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }

}