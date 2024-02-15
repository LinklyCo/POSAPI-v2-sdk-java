package com.linkly.pos.sdk.models.logon;

import java.time.LocalDateTime;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;
import com.squareup.moshi.Json;

/**
 * Response to a {@link LogonRequest}.
 */
public class LogonResponse extends PosApiResponseWithResult {

    private String pinPadVersion;
    private LocalDateTime date;
    @Json(name = "catid")
    private String catId;
    @Json(name = "caid")
    private String caId;
    private int stan;

    /**
     * PIN pad software version.
     * Example: "100800          "
     * 
     * @return String
     */
    public String getPinPadVersion() {
        return pinPadVersion;
    }

    /**
     * Sets the pinPadVersion
     * 
     * @param pinPadVersion The String value of pinPadVersion.
     */
    public void setPinPadVersion(String pinPadVersion) {
        this.pinPadVersion = pinPadVersion;
    }

    /**
     * Date and time of the response returned by the bank.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date.
     * 
     * @param date The LocalDateTime value of date.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Terminal ID configured in the PIN pad.
     * Example: 12345678
     * 
     * @return String
     */
    public String getCatId() {
        return catId;
    }

    /**
     * Sets the catId
     * 
     * @param catId The String value of catId.
     */
    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * Merchant ID configured in the PIN pad.
     * Example: 0123456789ABCDEF
     * 
     * @return String
     */
    public String getCaId() {
        return caId;
    }

    /**
     * Sets the caId.
     * 
     * @param caId the String value of caId.
     */
    public void setCaId(String caId) {
        this.caId = caId;
    }

    /**
     * System Trace Audit Number
     * 
     * @return int
     */
    public int getStan() {
        return stan;
    }

    /**
     * Sets the stan.
     * 
     * @param stan The int value of stan.
     */
    public void setStan(int stan) {
        this.stan = stan;
    }

}
