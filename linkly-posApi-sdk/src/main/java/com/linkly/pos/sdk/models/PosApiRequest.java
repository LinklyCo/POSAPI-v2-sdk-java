package com.linkly.pos.sdk.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.adapters.BoolToBitString;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.ReceiptAutoPrint;
import com.linkly.pos.sdk.service.IPosApiService;

/**
 * Common model for POS API requests.
 */
public class PosApiRequest implements IBaseRequest, IValidatable {

    protected String merchant = "00";
    protected String application = "00";
    protected ReceiptAutoPrint receiptAutoPrint = ReceiptAutoPrint.POS;

    @BoolToBitString
    protected boolean cutReceipt;
    protected Map<String, String> purchaseAnalysisData = new HashMap<>();
    protected String posName;
    protected String posVersion;
    protected UUID posId;

    /**
     * Two digit merchant code. Defaults to "00" (EFTPOS).
     * example: 00
     * 
     * @return String
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * Sets the merchant code.
     * 
     * @param merchant
     *            The String value of merchant.
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * Indicates where the request is to be sent to. Defaults to "00" (EFTPOS).
     * example: 00
     * 
     * @return String
     */
    public String getApplication() {
        return application;
    }

    /**
     * Sets the application.
     * 
     * @param application
     *            The String value of application.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Specify how the receipt should be printed.
     * 
     * @return ReceiptAutoPrint
     */
    public ReceiptAutoPrint getReceiptAutoPrint() {
        return receiptAutoPrint;
    }

    /**
     * Sets the receiptAutoPrint.
     * 
     * @param receiptAutoPrint
     *            The ReceiptAutoPrint value.
     */
    public void setReceiptAutoPrint(ReceiptAutoPrint receiptAutoPrint) {
        this.receiptAutoPrint = receiptAutoPrint;
    }

    /**
     * Indicates whether EFTPOS should cut receipts.
     * 
     * @return boolean
     */
    public boolean isCutReceipt() {
        return cutReceipt;
    }

    /**
     * Sets the cutReceipt.
     * 
     * @param cutReceipt
     *            The boolean value of cutReceipt.
     */
    public void setCutReceipt(boolean cutReceipt) {
        this.cutReceipt = cutReceipt;
    }

    /**
     * Additional data to be sent or received directly from the PIN pad.
     * 
     * @return {@link Map} of key: {@link String}, value: {@link String}
     */
    public Map<String, String> getPurchaseAnalysisData() {
        return purchaseAnalysisData;
    }

    /**
     * Sets the purchaseAnalysisData.
     * 
     * @param purchaseAnalysisData
     *            {@link Map} value of purchaseAnalysisData.
     */
    public void setPurchaseAnalysisData(Map<String, String> purchaseAnalysisData) {
        this.purchaseAnalysisData = purchaseAnalysisData;
    }

    /**
     * POS name. Set by the {@link IPosApiService}
     * 
     * @return String
     */
    public String getPosName() {
        return posName;
    }

    /**
     * Sets the posName.
     * 
     * @param posName
     *            The String value of posName.
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }

    /**
     * POS version. Set by the {@link IPosApiService}
     * 
     * @return String
     */
    public String getPosVersion() {
        return posVersion;
    }

    /**
     * Sets the posVersion.
     * 
     * @param posVersion
     *            The String value of posVersion.
     */
    public void setPosVersion(String posVersion) {
        this.posVersion = posVersion;
    }

    /**
     * POS Id. Set by the PosApiService with details for the POS Vendor details
     * 
     * @return String
     */
    public UUID getPosId() {
        return posId;
    }

    /**
     * Sets the posId.
     * 
     * @param posId
     *            The UUID value of posId.
     */
    public void setPosId(UUID posId) {
        this.posId = posId;
    }

    /**
     * Validate the model using {@link ValidatorUtil}
     * 
     * @throws InvalidArgumentException
     *             throw if has validation errors
     */
    @Override
    public void validate() {
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.notEmpty(this.merchant, "merchant"),
            ValidatorUtil.notEmpty(this.application, "application"),
            ValidatorUtil.isInEnum(ReceiptAutoPrint.class, this.receiptAutoPrint,
                "receiptAutoPrint"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (validationErrors.size() > 0) {
            throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
