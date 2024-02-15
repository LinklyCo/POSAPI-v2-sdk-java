package com.linkly.pos.sdk.models.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.StatusType;

/**
 * Get the terminal status.
 */
public class StatusRequest extends PosApiRequest {

    private StatusType statusType = StatusType.Standard;

    /**
     * Type of status to perform. Defaults to {@link StatusType#Standard}
     * 
     * @return StatusType
     */
    public StatusType getStatusType() {
        return statusType;
    }

    /**
     * Sets the statusType.
     * 
     * @param statusType The StatusType value.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(StatusType.class, this.statusType, "statusType"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
