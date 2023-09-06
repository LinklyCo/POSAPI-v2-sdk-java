package com.linkly.pos.sdk.models.receipt;

import java.util.ArrayList;
import java.util.List;

import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.enums.ReceiptType;
import com.squareup.moshi.Json;

/**
 * This message is returned asynchronously when a receipt event occurs on the PIN pad. To handle
 * receipt events refer to {@link IPosApiEventListener#receipt}
 */
public class ReceiptResponse extends PosApiResponse {

    @Json(name = "ReceiptType")
    private ReceiptType receiptType;

    @Json(name = "ReceiptText")
    private List<String> receiptText = new ArrayList<>();

    @Json(name = "IsPrePrint")
    private boolean isPrePrint;

    /**
     * The receipt type.
     * 
     * @return ReceiptType
     */
    public ReceiptType getReceiptType() {
        return receiptType;
    }

    /**
     * Sets the receiptType.
     * 
     * @param receiptType The ReceiptType value.
     */
    public void setReceiptType(ReceiptType receiptType) {
        this.receiptType = receiptType;
    }

    /**
     * Receipt text to be printed.
     * 
     * @return List<String>
     */
    public List<String> getReceiptText() {
        return receiptText;
    }

    /**
     * Sets the receiptText.
     * 
     * @param receiptText the List<String> value of receiptText.
     */
    public void setReceiptText(List<String> receiptText) {
        this.receiptText = receiptText;
    }

    /**
     * Receipt response is a pre-print.
     * 
     * @return boolean
     */
    public boolean isPrePrint() {
        return isPrePrint;
    }

    /**
     * Sets the isPrePrint.
     * 
     * @param isPrePrint The boolean value of isPrePrint.
     */
    public void setPrePrint(boolean isPrePrint) {
        this.isPrePrint = isPrePrint;
    }
}
