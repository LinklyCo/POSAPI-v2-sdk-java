package com.linkly.pos.sdk.models.transaction.surcharge;

import static com.linkly.pos.sdk.common.StringUtil.isNullOrWhiteSpace;

import com.linkly.pos.sdk.exception.InvalidArgumentException;

public abstract class SurchargeRule {

    protected final String bin;

    protected SurchargeRule(String bin) {
        if (isNullOrWhiteSpace(bin)) {
            throw new InvalidArgumentException("Surcharge Card bin is required" + bin);
        }
        if (bin.length() < 2 || bin.length() > 6) {
            throw new InvalidArgumentException(
                "Surcharge Card bin should be between 2 and 6 digits" + bin);
        }
        this.bin = bin;
    }

    public abstract String toPadSurchargeString();
}
