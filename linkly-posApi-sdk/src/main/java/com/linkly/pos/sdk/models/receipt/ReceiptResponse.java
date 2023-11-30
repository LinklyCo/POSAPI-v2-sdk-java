package com.linkly.pos.sdk.models.receipt;

import java.util.List;

import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.enums.ReceiptType;
import com.linkly.pos.sdk.service.IPosApiEventListener;

/**
 * This message is returned asynchronously when a receipt event occurs on the PIN pad. To handle
 * receipt events refer to
 * {@link IPosApiEventListener#receipt(java.util.UUID, com.linkly.pos.sdk.models.PosApiRequest, ReceiptResponse)}
 */
public class ReceiptResponse extends PosApiResponse {

    private ReceiptType type;
    private List<String> receiptText;
    private boolean isPrePrint;

    /**
     * The receipt type.
     * 
     * @return ReceiptType
     */
    public ReceiptType getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            The ReceiptType value.
     */
    public void setType(ReceiptType type) {
        this.type = type;
    }

    /**
     * Receipt text to be printed.
     * 
     * @return List of string
     */
    public List<String> getReceiptText() {
        return receiptText;
    }

    /**
     * Sets the receiptText.
     * 
     * @param receiptText
     *            {@link List} of {@link String} value of receiptText.
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
     * @param isPrePrint
     *            The boolean value of isPrePrint.
     */
    public void setPrePrint(boolean isPrePrint) {
        this.isPrePrint = isPrePrint;
    }
}
