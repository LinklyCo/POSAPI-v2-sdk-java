package com.linkly.pos.sdk.models.transaction.surcharge;

import com.linkly.pos.sdk.exception.InvalidArgumentException;

public class PercentageSurcharge extends SurchargeRule {

    private int basisPoints;

    public PercentageSurcharge(String bin, int basisPoints) {
        this(bin);
        if (basisPoints < 1 || basisPoints > 9999) {
            throw new InvalidArgumentException(
                "Surcharge percentage should be between 1 and 9999 basisPoints");
        }
        this.basisPoints = basisPoints;
    }

    protected PercentageSurcharge(String bin) {
        super(bin);
    }

    @Override
    public String toPadSurchargeString() {
        return "{\"b\":\"" + super.bin + "\",\"v\":" + this.basisPoints + "}";
    }

}
