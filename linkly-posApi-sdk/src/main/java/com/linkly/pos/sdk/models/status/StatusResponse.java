package com.linkly.pos.sdk.models.status;

import java.time.LocalDateTime;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;
import com.linkly.pos.sdk.models.enums.EftTerminalType;
import com.linkly.pos.sdk.models.enums.KeyHandlingType;
import com.linkly.pos.sdk.models.enums.NetworkType;
import com.linkly.pos.sdk.models.enums.TerminalCommsType;
import com.squareup.moshi.Json;

/**
 * Response to a {@link StatusRequest}.
 */
public class StatusResponse extends PosApiResponseWithResult {

    private String merchant;
    private String aiic;
    private int nii;

    @Json(name = "catid")
    private String catId;

    @Json(name = "caid")
    private String caId;
    private int timeout;
    private boolean loggedOn;
    private String pinPadSerialNumber;
    private String pinPadVersion;
    private String bankCode;
    private String bankDescription;
    private String kvc;
    private int safCount;
    private NetworkType networkType;
    private String hardwareSerial;
    private String retailerName;
    private PinPadOptionFlags optionsFlags;
    private int safCreditLimit;
    private int safDebitLimit;

    @Json(name = "maxSAF")
    private int maxSaf;

    private KeyHandlingType keyHandlingScheme;
    private int cashOutLimit;
    private int refundLimit;
    private String cpatVersion;
    private String nameTableVersion;
    private TerminalCommsType terminalCommsType;
    private int cardMisreadCount;
    private int totalMemoryInTerminal;
    private int freeMemoryInTerminal;
    private EftTerminalType eftTerminalType;
    private int numAppsInTerminal;
    private int numLinesOnDisplay;
    private LocalDateTime hardwareInceptionDate;

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
     * The AIIC that is configured in the terminal.
     * Example: 444777
     * 
     * @return String
     */
    public String getAiic() {
        return aiic;
    }

    /**
     * Sets the aiic.
     * 
     * @param aiic The String value of aiic.
     */
    public void setAiic(String aiic) {
        this.aiic = aiic;
    }

    /**
     * The NII that is configured in the terminal.
     * Example: 0
     * 
     * @return int
     */
    public int getNii() {
        return nii;
    }

    /**
     * Sets the nii.
     * 
     * @param nii The int value of nii.
     */
    public void setNii(int nii) {
        this.nii = nii;
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
     * @param caId The String value of caId.
     */
    public void setCaId(String caId) {
        this.caId = caId;
    }

    /**
     * The bank response timeout that is configured in the terminal.
     * 
     * @return int
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeOut.
     * 
     * @param timeOut The int value of timeOut.
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Indicates if the PIN pad is currently logged on.
     * 
     * @return boolean
     */
    public boolean isLoggedOn() {
        return loggedOn;
    }

    /**
     * Sets the loggedOn.
     * 
     * @param loggedOn The boolean value of loggedOn.
     */
    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    /**
     * The serial number of the terminal.
     * Example: 1234567890ABCDEF
     * 
     * @return String
     */
    public String getPinPadSerialNumber() {
        return pinPadSerialNumber;
    }

    /**
     * Sets the pinPadSerialNumber.
     * 
     * @param pinPadSerialNumber The String value of pinPadSerialNumber.
     */
    public void setPinPadSerialNumber(String pinPadSerialNumber) {
        this.pinPadSerialNumber = pinPadSerialNumber;
    }

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
     * Sets the pinPadVersion.
     * 
     * @param pinPadVersion The String value of pinPadVersion.
     */
    public void setPinPadVersion(String pinPadVersion) {
        this.pinPadVersion = pinPadVersion;
    }

    /**
     * The bank acquirer code.
     * Example: " "
     * 
     * @return String
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets bankCode.
     * 
     * @param bankCode The String value of bankCode.
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * The bank description.
     * Example: " "
     * 
     * @return String.
     */
    public String getBankDescription() {
        return bankDescription;
    }

    /**
     * Sets the bankDescription.
     * 
     * @param bankDescription The String value of bankDescription.
     */
    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    /**
     * Key verification code.
     * 
     * @return String
     */
    public String getKvc() {
        return kvc;
    }

    /**
     * Sets the kvc.
     * 
     * @param kvc The String value of kvc.
     */
    public void setKvc(String kvc) {
        this.kvc = kvc;
    }

    /**
     * Current number of stored transactions.
     * 
     * @return int
     */
    public int getSafCount() {
        return safCount;
    }

    /**
     * Sets the safCount.
     * 
     * @param safCount The int value of safCount.
     */
    public void setSafCount(int safCount) {
        this.safCount = safCount;
    }

    /**
     * PIN pad terminal network option.
     * 
     * @return NetworkType
     */
    public NetworkType getNetworkType() {
        return networkType;
    }

    /**
     * Sets the networkType
     * 
     * @param networkType The NetworkType value.
     */
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    /**
     * The hardware serial number.
     * Example: ABCDEF1234567890
     * 
     * @return String
     */
    public String getHardwareSerial() {
        return hardwareSerial;
    }

    /**
     * Sets the hardwareSerial.
     * 
     * @param hardwareSerial The String value of hardwareSerial.
     */
    public void setHardwareSerial(String hardwareSerial) {
        this.hardwareSerial = hardwareSerial;
    }

    /**
     * The merchant retailer name.
     * Example: Example Retailer
     * 
     * @return String
     */
    public String getRetailerName() {
        return retailerName;
    }

    /**
     * Sets the retailerName.
     * 
     * @param retailerName The String value of retailerName.
     */
    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    /**
     * PIN pad terminal supported options flags.
     * 
     * @return PinPadOptionFlags
     */
    public PinPadOptionFlags getOptionsFlags() {
        return optionsFlags;
    }

    /**
     * Sets the optionFlags.
     * 
     * @param optionFlags The PinPadOptionFlags value.
     */
    public void setOptionsFlags(PinPadOptionFlags optionsFlags) {
        this.optionsFlags = optionsFlags;
    }

    /**
     * Store-and forward credit limit.
     * 
     * @return int
     */
    public int getSafCreditLimit() {
        return safCreditLimit;
    }

    /**
     * Sets the safCreditLimit.
     * 
     * @param safCreditLimit The int value of safCreditLimit.
     */
    public void setSafCreditLimit(int safCreditLimit) {
        this.safCreditLimit = safCreditLimit;
    }

    /**
     * Store-and-forward debit limit.
     * 
     * @return int
     */
    public int getSafDebitLimit() {
        return safDebitLimit;
    }

    /**
     * Sets the safDebitLimit.
     * 
     * @param safDebitLimit The int value of safDebitLimit.
     */
    public void setSafDebitLimit(int safDebitLimit) {
        this.safDebitLimit = safDebitLimit;
    }

    /**
     * The maximum number of store transactions.
     * 
     * @return int
     */
    public int getMaxSaf() {
        return maxSaf;
    }

    /**
     * Sets the value of maxSef.
     * 
     * @param maxSaf The int value of maxSef.
     */
    public void setMaxSaf(int maxSaf) {
        this.maxSaf = maxSaf;
    }

    /**
     * The terminal key handling scheme.
     * 
     * @return KeyHandlingType
     */
    public KeyHandlingType getKeyHandlingScheme() {
        return keyHandlingScheme;
    }

    /**
     * Sets the keyHandlingScheme.
     * 
     * @param keyHandlingScheme The KeyHandlingType value.
     */
    public void setKeyHandlingScheme(KeyHandlingType keyHandlingScheme) {
        this.keyHandlingScheme = keyHandlingScheme;
    }

    /**
     * The maximum cash out limit.
     * 
     * @return int
     */
    public int getCashOutLimit() {
        return cashOutLimit;
    }

    /**
     * Sets the cashOutLimit.
     * 
     * @param cashOutLimit The int value of cashOutLimit.
     */
    public void setCashOutLimit(int cashOutLimit) {
        this.cashOutLimit = cashOutLimit;
    }

    /**
     * The maximum refund limit.
     * 
     * @return int
     */
    public int getRefundLimit() {
        return refundLimit;
    }

    /**
     * Sets the refundLimit.
     * 
     * @param refundLimit The int value of refundLimit.
     */
    public void setRefundLimit(int refundLimit) {
        this.refundLimit = refundLimit;
    }

    /**
     * Card prefix table version.
     * 
     * @return String
     */
    public String getCpatVersion() {
        return cpatVersion;
    }

    /**
     * Sets the cpatVersion.
     * 
     * @param cpatVersion The String value of cpatVersion.
     */
    public void setCpatVersion(String cpatVersion) {
        this.cpatVersion = cpatVersion;
    }

    /**
     * Card name table version.
     * 
     * @return String
     */
    public String getNameTableVersion() {
        return nameTableVersion;
    }

    /**
     * Sets the nameTableVersion.
     * 
     * @param nameTableVersion The String value of nameTableVersion.
     */
    public void setNameTableVersion(String nameTableVersion) {
        this.nameTableVersion = nameTableVersion;
    }

    /**
     * The terminal to PC communication type.
     * 
     * @return TerminalCommsType
     */
    public TerminalCommsType getTerminalCommsType() {
        return terminalCommsType;
    }

    /**
     * Sets the terminalCommsType.
     * 
     * @param terminalCommsType The TerminalCommsType value.
     */
    public void setTerminalCommsType(TerminalCommsType terminalCommsType) {
        this.terminalCommsType = terminalCommsType;
    }

    /**
     * Number of card mis-reads.
     * 
     * @return int
     */
    public int getCardMisreadCount() {
        return cardMisreadCount;
    }

    /**
     * Sets the cardMisreadCount.
     * 
     * @param cardMisreadCount The int value of cardMisreadCount.
     */
    public void setCardMisreadCount(int cardMisreadCount) {
        this.cardMisreadCount = cardMisreadCount;
    }

    /**
     * Number of memory pages in the PIN pad terminal.
     * 
     * @return int
     */
    public int getTotalMemoryInTerminal() {
        return totalMemoryInTerminal;
    }

    /**
     * Sets the totalMemoryInTerminal
     * 
     * @param totalMemoryInTerminal The int value of totalMemoryInTerminal.
     */
    public void setTotalMemoryInTerminal(int totalMemoryInTerminal) {
        this.totalMemoryInTerminal = totalMemoryInTerminal;
    }

    /**
     * Number of free memory pages in the PIN pad terminal.
     * 
     * @return int
     */
    public int getFreeMemoryInTerminal() {
        return freeMemoryInTerminal;
    }

    /**
     * Sets the freeMemoryInTerminal.
     * 
     * @param freeMemoryInTerminal The int value of freeMemoryInTerminal.
     */
    public void setFreeMemoryInTerminal(int freeMemoryInTerminal) {
        this.freeMemoryInTerminal = freeMemoryInTerminal;
    }

    /**
     * The type of PIN pad terminal.
     * 
     * @return EftTerminalType
     */
    public EftTerminalType getEftTerminalType() {
        return eftTerminalType;
    }

    /**
     * Sets the eftTerminalType.
     * 
     * @param eftTerminalType The EftTerminalType value.
     */
    public void setEftTerminalType(EftTerminalType eftTerminalType) {
        this.eftTerminalType = eftTerminalType;
    }

    /**
     * Number of applications in the terminal.
     * 
     * @return int
     */
    public int getNumAppsInTerminal() {
        return numAppsInTerminal;
    }

    /**
     * Sets the numAppsInTerminal.
     * 
     * @param numAppsInTerminal The int value of numAppsInTerminal.
     */
    public void setNumAppsInTerminal(int numAppsInTerminal) {
        this.numAppsInTerminal = numAppsInTerminal;
    }

    /**
     * Number of available display line on the terminal.
     * 
     * @return int
     */
    public int getNumLinesOnDisplay() {
        return numLinesOnDisplay;
    }

    /**
     * Sets the numLinesOnDisplay.
     * 
     * @param numLinesOnDisplay The int value of numLinesOnDisplay.
     */
    public void setNumLinesOnDisplay(int numLinesOnDisplay) {
        this.numLinesOnDisplay = numLinesOnDisplay;
    }

    /**
     * Hardware inception date.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getHardwareInceptionDate() {
        return hardwareInceptionDate;
    }

    /**
     * Sets the hardwareInceptionDate
     * 
     * @param hardwareInceptionDate The LocalDateTime value of hardwareInceptionDate.
     */
    public void setHardwareInceptionDate(LocalDateTime hardwareInceptionDate) {
        this.hardwareInceptionDate = hardwareInceptionDate;
    }
}