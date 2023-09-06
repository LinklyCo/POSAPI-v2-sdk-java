package com.linkly.pos.sdk.models.logon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.LogonType;

/**
 * PIN pad logon.
 */
public class LogonRequest extends PosApiRequest {

    private LogonType logonType = LogonType.Standard;
    private boolean isLogon = false;

    /**
     * Specify type of logon. Defaults to {@link LogonType#Standard}.
     * 
     * @return LogonType
     */
    public LogonType getLogonType() {
        return logonType;
    }

    public boolean isLogon() {
        return isLogon;
    }

    /**
     * Sets logonType
     * 
     * @param logonType The LogonType value.
     */
    public void setLogonType(LogonType logonType) {
        this.logonType = logonType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(LogonType.class, this.logonType, "logonType"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }
}