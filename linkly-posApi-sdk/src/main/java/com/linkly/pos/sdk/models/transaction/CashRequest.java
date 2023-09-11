package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.IValidatable;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.Json;

/**
 * Cash-out only transaction.
 */
public class CashRequest extends TransactionRequest implements IValidatable {

    @Json(name = "AmtCash")
    private int amountCash;

    /**
     * Initialise a new cash request.
     * 
     * @param amountCash The int value of amountCash.
     */
    public CashRequest(int amountCash) {
        setTxnType(TxnType.Cash);
        this.amountCash = amountCash;
    }

    /**
     * The cash out amount.
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
            ValidatorUtil.inclusiveBetween(this.amountCash, "amountCash", 1,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new IllegalArgumentException(String.join(", ", validationErrors));
        }
    }
}