package com.linkly.pos.sdk.models.reprintReceipt;

import java.util.List;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;

/**
 * Response to a {@link ReprintReceiptRequest}.
 */
public class ReprintReceiptResponse extends PosApiResponseWithResult {

    private String merchant;
    private List<String> receiptText;
    
    /**
     * Receipt text
     * 
     * @return List of receipt text lines 
     */
    public List<String> getReceiptText() {
        return receiptText;
    }

    /**
     * Sets the receipt text.
     * 
     * @param receiptText List of strings that represents the receipt.
     */
    public void setReceiptText(List<String> receiptText) {
        this.receiptText = receiptText;
    }
    
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
}