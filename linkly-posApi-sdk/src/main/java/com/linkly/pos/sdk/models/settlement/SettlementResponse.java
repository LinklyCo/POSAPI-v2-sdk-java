package com.linkly.pos.sdk.models.settlement;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;

/**
 * Response to a {@link SettlementRequest}.
 */
public class SettlementResponse extends PosApiResponseWithResult {

    private String merchant;
    private String settlementData;

     /**
     * Two digit merchant code
     * Example: 00
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
     * Settlement data
     * Example:
     * 000000002138VISA                000000100001000000100001000000100001+000000300003DEBIT
     * 000000100001000000100001000000100001+000000300003069TOTAL
     * 000000300001000000300001000000300001+000000900009
     * 
     * @return String
     */
    public String getSettlementData() {
        return settlementData;
    }

    /**
     * Sets the settlementData.
     * 
     * @param settlementData The String value of settlementData.
     */
    public void setSettlementData(String settlementData) {
        this.settlementData = settlementData;
    }

}
