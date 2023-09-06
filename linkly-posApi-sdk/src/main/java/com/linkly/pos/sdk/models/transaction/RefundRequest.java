package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.Json;

/**
 * Refund transaction.
 */
public class RefundRequest extends FollowUpTransactionRequest {

    @Json(name = "AmtPurchase")
    private int amount;

    /**
     * Initialise a new refund request.
     * 
     * @param amount The int value of amount.
     * @param rfn The String value of rfn.
     */
    public RefundRequest(int amount, String rfn) {
        setTxnType(TxnType.Refund);
        setRfn(rfn);
        this.amount = amount;
    }

    /**
     * The refund amount (in cents) for the transaction.
     * 
     * @return int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * 
     * @param amount The int value of amount.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.exclusiveBetween(this.amount, "amount", 0,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }

}
