package com.linkly.pos.sdk.models.transaction;

import static com.linkly.pos.sdk.common.MapUtil.getValueOrDefault;

import java.time.LocalDateTime;
import java.util.List;

import com.linkly.pos.sdk.common.Constants.PurchaseAnalysisData;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.PosApiResponseWithResult;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;
import com.squareup.moshi.Json;

public class TransactionResponse extends PosApiResponseWithResult {

    private TxnType txnType;
    private String merchant;
    private String cardType;
    private String cardName;
    
    @Json(name = "RRN")
    private String rrn;
    private LocalDateTime dateSettlement;

    @Json(name = "amtCash")
    private int amountCash;

    @Json(name = "amtPurchase")
    private int amount;

    @Json(name = "amtTip")
    private int amountTip;

    private int authCode;
    private String txnRef;
    private String pan;
    private String dateExpiry;
    private String track2;
    private AccountType accountType;
    private TransactionFlags txnFlags;
    private boolean balanceReceived;
    private int availableBalance;
    private int clearedFundsBalance;
    private LocalDateTime date;

	@Json(name = "catid")
    private String catId;

    @Json(name = "caid")
    private String caId;
    
    private int stan;
    private List<ReceiptResponse> receipts;

    /**
     * The type of transaction to perform.
     * Example: P
     * 
     * @return txntype
     */
    public TxnType getTxnType() {
        return txnType;
    }

    /**
     * Sets the txntype.
     * 
     * @param txntype
     *            The TxnType value.
     */
    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    /**
     * Two digit merchant code.
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
     * @param merchant
     *            The String value of merchant.
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * Indicates the card type used for the transaction as described by the bank. Examples may
     * include:
     * 'AMEX', 'VISA', 'DEBIT'. The possible values of this field may change between acquirers and
     * PIN pad
     * versions. To identify the payment type used, reference the cardName field.
     * Example" "AMEX CARD "
     * 
     * @return String
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the cardType.
     * 
     * @param cardType
     *            The String value of cardType.
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
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
     * @param cardName
     *            The String value of cardName.
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * The retrieval reference number for the transaction.
     * 
     * @return String
     */
    public String getRrn() {
        return rrn;
    }

    /**
     * Sets the rrn.
     * Example: 123456789012
     * 
     * @param rrn
     *            The String value of rrn.
     */
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    /**
     * Indicates which settlement batch this transaction will be included in.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getDateSettlement() {
        return dateSettlement;
    }

    /**
     * Sets the dateSettlement.
     * 
     * @param dateSettlement
     *            The LocalDateTime value of dateSettlement.
     */
    public void setDateSettlement(LocalDateTime dateSettlement) {
        this.dateSettlement = dateSettlement;
    }

    /**
     * The cash amount (in cents) for the transaction.
     * 
     * @return int
     */
    public int getAmountCash() {
        return amountCash;
    }

    /**
     * Sets the amountCash.
     * Example: 0
     * 
     * @param amountCash
     *            The int value of amountCash.
     */
    public void setAmountCash(int amountCash) {
        this.amountCash = amountCash;
    }

    /**
     * The purchase amount (in cents) for the transaction.
     * Example: 2345
     * 
     * @return int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * 
     * @param amount
     *            The int value of amount.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * The tip amount (in cents) for the transaction.
     * Example: 0
     * 
     * @return int
     */
    public int getAmountTip() {
        return amountTip;
    }

    /**
     * Sets the amountTip.
     * 
     * @param amountTip
     *            The int value of amountTip.
     */
    public void setAmountTip(int amountTip) {
        this.amountTip = amountTip;
    }

    /**
     * The authorisation number for the transaction.
     * Example: 0
     * 
     * @return int
     */
    public int getAuthCode() {
        return authCode;
    }

    /**
     * Sets the authCode.
     * 
     * @param authCode
     *            The int value of autCode.
     */
    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }

    /**
     * The reference number to attach to the transaction. This will appear on the receipt.
     * Example: 1234567890
     * 
     * @return String
     */
    public String getTxnRef() {
        return txnRef;
    }

    /**
     * Sets the txnRef
     * 
     * @param txnRef
     *            The String value of txnRef.
     */
    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    /**
     * The card number to use when pan source of POS keyed is used.
     * Example: "37601234567890 "
     * 
     * @return String
     */
    public String getPan() {
        return pan;
    }

    /**
     * Sets the pan.
     * 
     * @param pan
     *            The String value of pan.
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * The expiry date of the card when of POS keyed is used. In MMYY format.
     * 
     * @return String
     */
    public String getDateExpiry() {
        return dateExpiry;
    }

    /**
     * Sets the dateExpiry.
     * 
     * @param dateExpiry
     *            The String value of dateExpiry.
     */
    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    /**
     * The track 2 to use when of POS swiped is used.
     * Example: "37601234567890=0949 "
     * 
     * @return String
     */
    public String getTrack2() {
        return track2;
    }

    /**
     * Sets the track2.
     * 
     * @param track2
     *            The String value of track2.
     */
    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    /**
     * The account to use for this transaction. Use ' ' to prompt user to enter the account type
     * Example: 2
     * 
     * @return AccountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the accountType.
     * 
     * @param accountType
     *            The AccountType value.
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Flags that indicate how the transaction was processed.
     * 
     * @return TransactionFlags
     */
    public TransactionFlags getTxnFlags() {
        return txnFlags;
    }

    /**
     * Sets the txnFlags.
     * 
     * @param txnFlags
     *            The TransactionFlags value.
     */
    public void setTxnFlags(TransactionFlags txnFlags) {
        this.txnFlags = txnFlags;
    }

    /**
     * Indicates if an available balance is present in the response.
     * 
     * @return boolean
     */
    public boolean isBalanceReceived() {
        return balanceReceived;
    }

    /**
     * Sets the balancedReceived.
     * 
     * @param balanceReceived
     *            The boolean value of balancedReceived.
     */
    public void setBalanceReceived(boolean balanceReceived) {
        this.balanceReceived = balanceReceived;
    }

    /**
     * Balance available on the processed account.
     * 
     * @return int
     */
    public int getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Sets the availableBalance.
     * 
     * @param availableBalance
     *            The int value of availableBalance.
     */
    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * Cleared balance on the processed account.
     * 
     * @return int
     */
    public int getClearedFundsBalance() {
        return clearedFundsBalance;
    }

    /**
     * Sets the clearedFundsBalance.
     * 
     * @param clearedFundsBalance
     *            The int value of clearedFundsBalance.
     */
    public void setClearedFundsBalance(int clearedFundsBalance) {
        this.clearedFundsBalance = clearedFundsBalance;
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
     * @param date
     *            The LocalDateTime value of date.
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
     * Sets the catId.
     * 
     * @param catId
     *            The String value of catId.
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
     * @param caId
     *            The String value of caId.
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
     * @param stan
     *            The int value of stan.
     */
    public void setStan(int stan) {
        this.stan = stan;
    }

    /**
     * Transaction reference number returned by the bank or acquirer, required for follow-up
     * transactions
     * such as refunds, pre-auth completions, pre-auth cancellations, etc. This value comes from
     * {@link PurchaseAnalysisData}
     * 
     * @return String
     */
    public String getRfn() {
        return getValueOrDefault(getPurchaseAnalysisData(), PurchaseAnalysisData.RFN);
    }

    /**
     * If tipping or surcharging is enabled this should return the total transaction amount (in
     * cents)
     * including tips and surcharges. This value comes from
     * {@link PurchaseAnalysisData}
     * 
     * @return total amount from {@link PosApiResponse#getPurchaseAnalysisData()}
     */
    public int getAmountTotal() {
        String amountTotalStr = getValueOrDefault(getPurchaseAnalysisData(),
            PurchaseAnalysisData.AMOUNT);
        int totalAmt = 0;
        if (amountTotalStr != null) {
            totalAmt = Integer.parseInt(amountTotalStr);
        }
        return totalAmt;
    }

    /**
     * If surcharging is enabled this should return the surcharge amount (in cents). This value
     * comes from {@link PosApiResponse#getPurchaseAnalysisData()}
     * 
     * @return int
     */
    public int getAmountSurcharge() {
        String surchargeTotalStr = getValueOrDefault(getPurchaseAnalysisData(),
            PurchaseAnalysisData.SURCHARGE);
        int totalSurcharge = 0;

        if (surchargeTotalStr != null) {
            totalSurcharge = Integer.parseInt(surchargeTotalStr);
        }
        return totalSurcharge;
    }

    /**
     * Receipts generated by the transaction.
     * 
     * @return list of {@link ReceiptResponse}
     */
    public List<ReceiptResponse> getReceipts() {
        return receipts;
    }

    /**
     * Sets the receipts.
     * 
     * @param receipts
     *            {@link List} of receipts.
     */
    public void setReceipts(List<ReceiptResponse> receipts) {
        this.receipts = receipts;
    }
}