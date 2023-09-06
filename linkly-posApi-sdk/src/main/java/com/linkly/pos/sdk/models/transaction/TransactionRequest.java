package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.IPurchaseAnalysisDataConverter;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.linkly.pos.sdk.models.transaction.surcharge.SurchargeOptions;

/**
 * Base request used for all transactions.
 */
public class TransactionRequest extends PosApiRequest {

    protected TxnType txnType;
    private String currencyCode;
    private boolean enableTip;
    private boolean trainingMode;
    private String txnRef;
    private int authCode;
    private PanSource panSource = PanSource.PinPad;
    private String pan;
    private String dateExpiry;
    private String track2;
    private AccountType accountType = AccountType.Default;
    private String rrn;
    private int cvv;

    /**
     * Set tip options to present to the cardholder. The options are different tip percentages for the
     * terminal to present. When set, the terminal is to display each of the options along with providing
     * the ability for a cardholder to enter their own dollar amount. This setter updates
     * {@link PosApiRequest.PurchaseAnalysisData}
     * 
     * @param tippingOptions The TippingOptions value.
     */
    public void setTipOptions(TippingOptions tippingOptions) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.TPO,
            ((IPurchaseAnalysisDataConverter) tippingOptions).toPadString());
    }

    /**
     * Set the tip amount (in cents) to apply on top of the transaction amount. The tip will be
     * included on the receipt. This setter updates {@link PosApiRequest#getPurchaseAnalysisData}.
     * 
     * @param tipAmount The int value of tipAmount.
     */
    public void setTipAmount(int tipAmount) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.TIP, String.valueOf(
            tipAmount));
    }

    /**
     * Set the product level blocking flag, indicating whether restricted item(s) are present in the sale.
     * This flag is optional unless the merchant is participating in the DSS PLB program, in which case it
     * is mandatory. Setting this property to true indicates restricted item(s) are present in the sale
     * and product level blocking is required. If false no restricted items are present in the sale and
     * product level blocking is not required. This setter updates
     * {@link PosApiRequest#getPurchaseAnalysisData}
     * 
     * @param productLevelBlock The boolean value of productLevelBlock.
     */
    public void setProductLevelBlock(boolean productLevelBlock) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.PLB, productLevelBlock ? "1"
            : "0");
    }

    /**
     * Set the surcharges rules to apply based on the card bin. This setter updates
     * #see PosApiRequest.PurchaseAnalysisData
     * 
     * @param surchargeOptions The SurchargeOptions value.
     */
    public void setSurchargeOptions(SurchargeOptions surchargeOptions) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.SC2,
            ((IPurchaseAnalysisDataConverter) surchargeOptions).toPadString());
    }

    /**
     * Type of transaction to perform. This field is set by the specific request class and cannot
     * 
     * @return TxnType
     */
    public TxnType getTxnType() {
        return txnType;
    }

    /**
     * Sets the txnType.
     * 
     * @param txnType The TxnType value.
     */
    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    /**
     * The currency code for this transaction (e.g. AUD). A 3 digit ISO currency code.
     * 
     * @return String
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currencyCode.
     * 
     * @param currencyCode The String value of currencyCode.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Indicates if the transaction supports tipping.. Set to TRUE if tipping is to be enabled
     * for this transaction.
     * 
     * @return boolean
     */
    public boolean isEnableTip() {
        return enableTip;
    }

    /**
     * Sets the enableTip.
     * 
     * @param enableTip The boolean value of enableTip.
     */
    public void setEnableTip(boolean enableTip) {
        this.enableTip = enableTip;
    }

    /**
     * Determines if the transaction is a training mode transaction. Set to TRUE if the transaction is to
     * be performed in training mode. The default is false.
     * 
     * @return boolean
     */
    public boolean isTrainingMode() {
        return trainingMode;
    }

    /**
     * Sets the trainingMode.
     * 
     * @param trainingMode The boolean value of trainingMode.
     */
    public void setTrainingMode(boolean trainingMode) {
        this.trainingMode = trainingMode;
    }

    /**
     * The reference number to attach to the transaction. This will appear on the receipt.
     * 
     * @return String
     */
    public String getTxnRef() {
        return txnRef;
    }

    /**
     * Sets the txnRef.
     * 
     * @param txnRef The String value of txnRef.
     */
    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    /**
     * The authorisation number for the transaction.
     * 
     * @return int
     */
    public int getAuthCode() {
        return authCode;
    }

    /**
     * Sets the authCode.
     * 
     * @param authCode The int value of authCode.
     */
    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }

    /**
     * Indicates the source of the customer card details. The default is
     * {@link Models.PanSource.PinPad}
     * 
     * @return PanSource
     */
    public PanSource getPanSource() {
        return panSource;
    }

    /**
     * Sets the panSource.
     * 
     * @param panSource The PanSource value.
     */
    public void setPanSource(PanSource panSource) {
        this.panSource = panSource;
    }

    /**
     * The card number to use when pan source of POS keyed is used. Use this property in conjunction with
     * {@link PanSource}
     * 
     * @return String
     */
    public String getPan() {
        return pan;
    }

    /**
     * Sets the pan.
     * 
     * @param pan The String value of pan.
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * The expiry date of the card when of POS keyed is used. In MMYY format. Use this property in
     * conjunction with {@link PanSource} when passing the card expiry date to Linkly.
     * 
     * @return String
     */
    public String getDateExpiry() {
        return dateExpiry;
    }

    /**
     * Sets the dateExpiry.
     * 
     * @param dateExpiry The String value of dateExpiry.
     */
    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    /**
     * The track 2 to use when of POS swiped is used. Use this property in conjunction with
     * {@link PanSource}
     * 
     * @return
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
     * The account to use for this transaction. Defaults to {@link AccountType#Default}
     * 
     * @return AccountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the accountType.
     * 
     * @param accountType The AccountType value.
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * The retrieval reference number for the transaction. Only required for some transaction
     * 
     * @return String
     */
    public String getRrn() {
        return rrn;
    }

    /**
     * Sets the rrn.
     * 
     * @param rrn The String value of rrn.
     */
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    /**
     * Card verification value.
     * 
     * @return int
     */
    public int getCvv() {
        return cvv;
    }

    /**
     * Sets the cvv.
     * 
     * @param cvv The int value of cvv.
     */
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.length(this.currencyCode, "currencyCode", 3),
            ValidatorUtil.notEmpty(this.txnRef, "txnRef"),
            ValidatorUtil.maxLength(this.txnRef, "txnRef", 32),
            ValidatorUtil.isInEnum(PanSource.class, this.panSource, "panSource"),
            ValidatorUtil.length(this.pan, "pan", 20),
            ValidatorUtil.expiryDate(this.dateExpiry, "dateExpiry"),
            ValidatorUtil.length(this.track2, "track2", 40),
            ValidatorUtil.isInEnum(AccountType.class, this.accountType, "accountType"),
            ValidatorUtil.length(this.rrn, "rrn", 12))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (this.panSource == PanSource.PosKeyed) {
            String panError = ValidatorUtil.notEmpty(this.pan, "pan");
            String dateExpiryError = ValidatorUtil.notEmpty(this.dateExpiry, "dateExpiry");
            if (panError != null) {
                validationErrors.add(panError);
            }
            if (dateExpiryError != null) {
                validationErrors.add(dateExpiryError);
            }
        }
        if (this.panSource == PanSource.PosSwiped) {
            String track2 = ValidatorUtil.notEmpty(this.track2, "track2");
            if (track2 != null) {
                validationErrors.add(track2);
            }
        }

        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }
}
