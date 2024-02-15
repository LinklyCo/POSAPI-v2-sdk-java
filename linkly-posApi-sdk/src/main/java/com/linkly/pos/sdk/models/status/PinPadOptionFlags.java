package com.linkly.pos.sdk.models.status;

/**
 * PIN pad terminal supported options.
 */
public class PinPadOptionFlags {
    private boolean tipping;
    private boolean preAuth;
    private boolean completions;
    private boolean cashOut;
    private boolean refund;
    private boolean balance;
    private boolean deposit;
    private boolean voucher;
    private boolean moto;
    private boolean autoCompletion;
    private boolean efb;
    private boolean emv;
    private boolean training;
    private boolean withdrawal;
    private boolean transfer;
    private boolean startCash;

    /**
     * Tipping enabled flag.
     * 
     * @return boolean
     */
    public boolean isTipping() {
        return tipping;
    }

    /**
     * Sets tipping.
     * 
     * @param tipping
     *            The boolean value of tipping.
     */
    public void setTipping(boolean tipping) {
        this.tipping = tipping;
    }

    /**
     * Pre-authorisation enabled flag.
     * 
     * @return boolean
     */
    public boolean isPreAuth() {
        return preAuth;
    }

    /**
     * Sets preAuth
     * 
     * @param preAuth
     *            The boolean value of preAuth.
     */
    public void setPreAuth(boolean preAuth) {
        this.preAuth = preAuth;
    }

    /**
     * Completions enabled flag.
     * 
     * @return boolean
     */
    public boolean isCompletions() {
        return completions;
    }

    /**
     * Sets completions
     * 
     * @param completions
     *            The boolean value of completions.
     */
    public void setCompletions(boolean completions) {
        this.completions = completions;
    }

    /**
     * Cash-out enabled flag.
     * 
     * @return boolean
     */
    public boolean isCashOut() {
        return cashOut;
    }

    /**
     * Sets cashOut.
     * 
     * @param cashOut
     *            The boolean value of cashOut.
     */
    public void setCashOut(boolean cashOut) {
        this.cashOut = cashOut;
    }

    /**
     * Refund enabled flag.
     * 
     * @return boolean
     */
    public boolean isRefund() {
        return refund;
    }

    /**
     * Sets refund.
     * 
     * @param refund
     *            The boolean value of refund.
     */
    public void setRefund(boolean refund) {
        this.refund = refund;
    }

    /**
     * Balance enquiry enabled flag.
     * 
     * @return boolean
     */
    public boolean isBalance() {
        return balance;
    }

    /**
     * Sets balance.
     * 
     * @param balance
     *            The boolean value of balance.
     */
    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    /**
     * Deposit enabled flag.
     * 
     * @return boolean
     */
    public boolean isDeposit() {
        return deposit;
    }

    /**
     * Sets deposit.
     * 
     * @param deposit
     *            The boolean value of deposit.
     */
    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    /**
     * Manual voucher enabled flag.
     * 
     * @return boolean
     */
    public boolean isVoucher() {
        return voucher;
    }

    /**
     * Sets voucher.
     * 
     * @param voucher
     *            The boolean value of voucher.
     */
    public void setVoucher(boolean voucher) {
        this.voucher = voucher;
    }

    /**
     * Mail-order/Telephone-order enabled flag.
     * 
     * @return boolean
     */
    public boolean isMoto() {
        return moto;
    }

    /**
     * Sets moto.
     * 
     * @param moto
     *            The boolean value of moto.
     */
    public void setMoto(boolean moto) {
        this.moto = moto;
    }

    /**
     * Auto-completions enabled flag.
     * 
     * @return boolean
     */
    public boolean isAutoCompletion() {
        return autoCompletion;
    }

    /**
     * Sets the autoCompletion.
     * 
     * @param autoCompletion
     *            The boolean value of autoCompletion.
     */
    public void setAutoCompletion(boolean autoCompletion) {
        this.autoCompletion = autoCompletion;
    }

    /**
     * Electronic Fallback enabled flag.
     * 
     * @return boolean
     */
    public boolean isEfb() {
        return efb;
    }

    /**
     * Sets efb.
     * 
     * @param efb
     *            The boolean value of efb.
     */
    public void setEfb(boolean efb) {
        this.efb = efb;
    }

    /**
     * EMV enabled flag.
     * 
     * @return boolean
     */
    public boolean isEmv() {
        return emv;
    }

    /**
     * Sets the emv.
     * 
     * @param emv
     *            The boolean value of emv.
     */
    public void setEmv(boolean emv) {
        this.emv = emv;
    }

    /**
     * Training mode enabled flag.
     * 
     * @return boolean
     */
    public boolean isTraining() {
        return training;
    }

    /**
     * Sets the training.
     * 
     * @param training
     *            The boolean value of training.
     */
    public void setTraining(boolean training) {
        this.training = training;
    }

    /**
     * Withdrawal enabled flag.
     * 
     * @return boolean
     */
    public boolean isWithdrawal() {
        return withdrawal;
    }

    /**
     * Sets the withdrawal.
     * 
     * @param withdrawal
     *            The boolean value of withdrawal.
     */
    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    /**
     * Training mode enabled flag.
     * 
     * @return boolean
     */
    public boolean isTransfer() {
        return transfer;
    }

    /**
     * Sets the transfer.
     * 
     * @param transfer
     *            The boolean value of transfer.
     */
    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    /**
     * Start cash enabled flag.
     * 
     * @return boolean
     */
    public boolean isStartCash() {
        return startCash;
    }

    /**
     * Sets the startCash
     * 
     * @param startCash
     *            The boolean value of startCash.
     */
    public void setStartCash(boolean startCash) {
        this.startCash = startCash;
    }
}