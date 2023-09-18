package com.linkly.pos.sdk.models;

import static com.linkly.pos.sdk.common.StringUtil.isNullOrWhiteSpace;

import java.util.UUID;

/**
 * Used to identify the POS vendor with Linkly systems.
 */
public class PosVendorDetails {

    protected UUID posId;
    protected String posName;
    protected String posVersion;
    protected UUID posVendorId;

    /**
     * Initialise a new instance.
     * 
     * @param posName
     *            Name of the POS vendor.
     * @param posVersion
     *            POS software version.
     * @param posId
     *            Persistent and unique POS instance identifier. Must differ across separate
     *            deployments of the same POS application.
     * @param posVendorId
     *            Persistent and unique POS product identifier. All instances of the
     *            POS application should provide the same UUID.
     */
    public PosVendorDetails(String posName, String posVersion, UUID posId, UUID posVendorId) {
        super();
        if (isNullOrWhiteSpace(posName)) {
            throw new IllegalArgumentException("posName is required");
        }
        if (isNullOrWhiteSpace(posVersion)) {
            throw new IllegalArgumentException("posVersion is required");
        }
        this.posName = posName;
        this.posVersion = posVersion;
        this.posId = posId;
        this.posVendorId = posVendorId;
    }

    /**
     * Returns the configured unique POS identifier.
     * 
     * @return UUID
     */
    public UUID getPosId() {
        return posId;
    }

    /**
     * Returns the configured name of the POS vendor.
     * 
     * @return String
     */
    public String getPosName() {
        return posName;
    }

    /**
     * Returns the configured version of the POS Software.
     * 
     * @return String
     */
    public String getPosVersion() {
        return posVersion;
    }

    /**
     * Returns the configured unique POS vendor identifier.
     * 
     * @return UUID
     */
    public UUID getPosVendorId() {
        return posVendorId;
    }

}
