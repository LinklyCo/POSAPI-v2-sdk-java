package com.linkly.pos.sdk.models.authentication;

import java.util.UUID;

import com.linkly.pos.sdk.models.IBaseRequest;

/**
 * Obtain a Secret token which needs to be used by subsequent API requests. Used
 * internally for automatic token generation.
 */
public class TokenRequest implements IBaseRequest {

    private String secret;
    private String posName;
    private String posVersion;
    private UUID posId;
    private UUID posVendorId;

    /**
     * Initialise a new token request.
     * 
     * @param secret Secret generated after sending a pairing request.
     * @param posName Name of the POS requesting for a token.
     * @param posVersion Version of the POS requesting for a token.
     * @param posId Generated POS ID via the POS deployment settings. Registers under the same merchant would have unique Id's
     * @param posVendorId ID that uniquely identifies a POS Product.
     */
    public TokenRequest(String secret, String posName, String posVersion, UUID posId,
        UUID posVendorId) {
        super();
        this.secret = secret;
        this.posName = posName;
        this.posVersion = posVersion;
        this.posId = posId;
        this.posVendorId = posVendorId;
    }

    /**
     * Secret generated by sending a {@link PairingRequest}
     * 
     * @return String
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret.
     * 
     * @param secret The String value of secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * The name of the POS requesting the token.
     * 
     * @return String
     */
    public String getPosName() {
        return posName;
    }

    /**
     * Sets the posName.
     * 
     * @param posName The String value of posName.
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }

    /**
     * The version of the POS requesting the token.
     * 
     * @return String
     */
    public String getPosVersion() {
        return posVersion;
    }

    /**
     * Sets the posVersion.
     * 
     * @param posVersion The String value of posVersion.
     */
    public void setPosVersion(String posVersion) {
        this.posVersion = posVersion;
    }

    /**
     * A unique UUID v4 which identifies the POS instance. This value is generated by the POS as a part of
     * the POS deployment settings. e.g. Two registers at the same merchant should supply two different
     * PosId values.
     * 
     * @return UUID
     */
    public UUID getPosId() {
        return posId;
    }

    /**
     * Sets the posId.
     * 
     * @param posId The UUID value of posId.
     */
    public void setPosId(UUID posId) {
        this.posId = posId;
    }

    /**
     * A unique UUID v4 which identifies the POS product. This value can be hard coded into the build of
     * the POS. e.g. All merchants using the same POS product should supply the same value.
     * 
     * @return UUID
     */
    public UUID getPosVendorId() {
        return posVendorId;
    }

    /**
     * Sets the posVendorId.
     * 
     * @param posVendorId The UUID value of posVendorId.
     */
    public void setPosVendorId(UUID posVendorId) {
        this.posVendorId = posVendorId;
    }
}