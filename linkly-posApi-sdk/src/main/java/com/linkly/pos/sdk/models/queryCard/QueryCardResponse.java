package com.linkly.pos.sdk.models.queryCard;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;
import com.linkly.pos.sdk.models.enums.AccountType;

/**
 * Response to a {@link QueryCardRequest}.
 */
public class QueryCardResponse extends PosApiResponseWithResult {

    private String merchant;
    private boolean isTrack1Available;
    private boolean isTrack2Available;
    private boolean isTrack3Available;
    private String track1;
    private String track2;
    private String track3;
    private String cardName;
    private AccountType accountType;

    /**
     * Two digit merchant code. Example: 00
     * 
     * @return String
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * Sets the merchant.
     * 
     * @param merchant The String value of merchant.
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * Indicates if track 1 was read.
     * 
     * @return boolean
     */
    public boolean isTrack1Available() {
        return isTrack1Available;
    }

    /**
     * Sets the isTrack1Available.
     * 
     * @param isTrack1Available The boolean value of isTrack1Available.
     */
    public void setTrack1Available(boolean isTrack1Available) {
        this.isTrack1Available = isTrack1Available;
    }

    /**
     * Indicates if track 2 was read.
     * 
     * @return boolean
     */
    public boolean isTrack2Available() {
        return isTrack2Available;
    }

    /**
     * Sets the isTrack2Available.
     * 
     * @param isTrack2Available The boolean value of isTrack2Available.
     */
    public void setTrack2Available(boolean isTrack2Available) {
        this.isTrack2Available = isTrack2Available;
    }

    /**
     * Indicates if track 3 was read.
     * 
     * @return boolean
     */
    public boolean isTrack3Available() {
        return isTrack3Available;
    }

    /**
     * Sets the isTrack3Available.
     * 
     * @param isTrack3Available The boolean value of isTrack3Available.
     */
    public void setTrack3Available(boolean isTrack3Available) {
        this.isTrack3Available = isTrack3Available;
    }

    /**
     * Data encoded on Track1 of the card.
     * 
     * @return String
     */
    public String getTrack1() {
        return track1;
    }

    /**
     * Sets the track1.
     * 
     * @param track1 The String value of track1.
     */
    public void setTrack1(String track1) {
        this.track1 = track1;
    }

    /**
     * Data encoded on Track2 of the card.
     * 
     * @return String
     */
    public String getTrack2() {
        return track2;
    }

    /**
     * Sets the track2.
     * 
     * @param track2 The String value of track2.
     */
    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    /**
     * Data encoded on Track3 of the card.
     * 
     * @return String
     */
    public String getTrack3() {
        return track3;
    }

    /**
     * Sets the track3.
     * 
     * @param track3 The String value of track3.
     */
    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    /**
     * BIN number of the card. For a complete list refer to the API documentation.
     * 
     * @return String
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets the cardName.
     * 
     * @param cardName The String valus of cardName.
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * Account type selected.
     * 
     * @return AccountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the accountType
     * 
     * @param accountType The AccountType value.
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
