package com.linkly.pos.sdk.models.authentication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.IValidatable;

/**
 * Pair a PIN pad to the POS.
 */
public class PairingRequest implements IBaseRequest, IValidatable {

    private String username;
    private String password;
    private String pairCode;

    public PairingRequest() {
    }

    /**
     * Initialise a new pairing request.
     * 
     * @param username
     *            Linkly cloud username.
     * @param password
     *            Linkly cloud password.
     * @param pairCode
     *            Pairing code as displayed on the pin-pad.
     */
    public PairingRequest(String username, String password, String pairCode) {
        super();
        this.username = username;
        this.password = password;
        this.pairCode = pairCode;
    }

    /**
     * Linkly Cloud username.
     * 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username
     *            The String value of username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Linkly Cloud password.
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            The String value of password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The pair-code as displayed on the PIN pad.
     * 
     * @return String
     */
    public String getPairCode() {
        return pairCode;
    }

    /**
     * Sets the paircode.
     * 
     * @param pairCode
     *            The String value of pairCode.
     */
    public void setPairCode(String pairCode) {
        this.pairCode = pairCode;
    }

    /**
     * Validate the model using {@link ValidatorUtil}
     * 
     * @throws IllegalArgumentException
     *             if contains validation errors
     */
    @Override
    public void validate() {
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.notEmpty(this.username, "username"),
            ValidatorUtil.notEmpty(this.password, "pairCode"),
            ValidatorUtil.notEmpty(this.pairCode, "username"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (validationErrors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", validationErrors));
        }
    }
}