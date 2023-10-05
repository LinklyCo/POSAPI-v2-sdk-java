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
 * Void transaction prior to settlement.
 */
public class VoidRequest extends TransactionRequest {

    @Json(name = "AmtPurchase")
    private int amount;
    
    /**
     * Initialise a new VoidRequest.
     * 
     * @param amount The int value of amount.
     */
    public VoidRequest(int amount) {
        setTxnType(TxnType.Void);
        this.amount = amount;
    }

    /**
     * Return the amount value.
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
            ValidatorUtil.inclusiveBetween(this.amount, "amount", 1,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
