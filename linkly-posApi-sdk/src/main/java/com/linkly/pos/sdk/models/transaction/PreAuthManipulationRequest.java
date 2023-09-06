package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.squareup.moshi.Json;

/**
 * Abstract base class for pre-authorisation requests which perform some action on an
 * existing pre-authorisation.
 */
public abstract class PreAuthManipulationRequest extends FollowUpTransactionRequest {

    @Json(name = "AmtPurchase")
    protected int amount;

    /**
     * The amount purchase for request.
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
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.inclusiveBetween(this.amount, "amount", 1,
                Constants.Validation.MAX_AMOUNT))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }

}
