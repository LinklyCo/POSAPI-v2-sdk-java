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
 * Create a new pre-authorisation. Can also be used for account verification. See Amount
 */
public class PreAuthRequest extends TransactionRequest {

    @Json(name = "AmtPurchase")
    private int amount;

    /**
     * Initialise a new pre auth request.
     * 
     * @param amount The int value of amount.
     */
    public PreAuthRequest(int amount) {
        setTxnType(TxnType.PreAuth);
        this.amount = amount;
    }

    /**
     * Pre-authorisation amount (in cents) or zero to perform an account verification.
     * 
     * @return int
     */
    public int getAmount() {
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.inclusiveBetween(this.amount, "amount", 0,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
