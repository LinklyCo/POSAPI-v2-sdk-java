package com.linkly.pos.sdk.models.transaction;

import com.linkly.pos.sdk.common.IPurchaseAnalysisDataConverter;
import com.linkly.pos.sdk.exception.InvalidArgumentException;

/**
 * Provides up to three tipping options to present to the customer as a percentage of the
 * total purchase amount.
 */
public class TippingOptions implements IPurchaseAnalysisDataConverter {

    private final byte[] tipPercentages;

    /**
     * Provide at least one, and up to three tipping percentage options.
     * 
     * @param tipPercentages The byte[] value of tipPercentages.
     */
    public TippingOptions(byte[] tipPercentages) {
        if (tipPercentages.length < 1) {
            throw new InvalidArgumentException("Must provide at least one tipping percentages");
        }
        if (tipPercentages.length > 3)
            throw new InvalidArgumentException("Must provide at most three tipping percentages");

        for (int percentage : tipPercentages) {
            if (percentage < 1) {
                throw new InvalidArgumentException("Tipping percentage must be greater than 1.");
            }
            if (percentage > 99) {
                throw new InvalidArgumentException("Tipping percentage must be less than 99.");
            }
        }
        this.tipPercentages = tipPercentages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toPadString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int ctr = 0; ctr < tipPercentages.length; ctr++) {
            stringBuilder.append(String.format("%02d", tipPercentages[ctr]));
            if (ctr < tipPercentages.length - 1) {
                stringBuilder.append(",");
            }
        }
        return "[" + stringBuilder.toString() + "]";
    }
}
