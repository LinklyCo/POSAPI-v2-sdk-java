package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.Json;

/**
 * Purchase transaction.
 */
public class PurchaseRequest extends TransactionRequest {

    @Json(name = "AmtPurchase")
    private int amount;

    @Json(name = "AmtCash")
    private int amountCash;

    /**
     * Initialise a new purchase request.
     * 
     * @param amount The int value of amount.
     * @param amountCash The int value of amountCash.
     */
    public PurchaseRequest(int amount, int amountCash) {
        setTxnType(TxnType.Purchase);
        this.amount = amount;
        this.amountCash = amountCash;
    }

    /**
     * The purchase amount (in cents) of the transaction.
     * 
     * @return int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * The cash out amount (in cents) of the transaction. Defaults to 0
     * 
     * @return int
     */
    public int getAmountCash() {
        return amountCash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.inclusiveBetween(this.amount, "amount", 1,
                Constants.Validation.MAX_AMOUNT),
            ValidatorUtil.inclusiveBetween(this.amountCash, "amountCash", 0,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
