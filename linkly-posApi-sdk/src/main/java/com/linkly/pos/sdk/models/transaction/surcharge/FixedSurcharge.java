package com.linkly.pos.sdk.models.transaction.surcharge;

import static com.linkly.pos.sdk.common.Constants.Validation.MAX_AMOUNT;

import com.linkly.pos.sdk.exception.InvalidArgumentException;

public class FixedSurcharge extends SurchargeRule {

    private final int amountInCents;

    public FixedSurcharge(String bin, int amountInCents) {
        super(bin);
        if (amountInCents < 1 || amountInCents > MAX_AMOUNT) {
            throw new InvalidArgumentException("Amount should be between 1 and "
                + MAX_AMOUNT);
        }
        this.amountInCents = amountInCents;
    }

    @Override
    public String toPadSurchargeString() {
        return "{\"b\":\"" + super.bin + "\",\"t\":\"$\",\"v\":" + amountInCents + "}";
    }

}
