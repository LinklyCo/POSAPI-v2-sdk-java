package com.linkly.pos.sdk.models.settlement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.SettlementType;

/**
 * Get the last settlement details.
 */
public class SettlementRequest extends PosApiRequest {

    private SettlementType settlementType = SettlementType.Settlement;
    private boolean resetTotals;

    /**
     * EFT settlement type. Defaults to {@link SettlementType#Settlement}
     * 
     * @return SettlementType
     */
    public SettlementType getSettlementType() {
        return settlementType;
    }

    /**
     * Sets the settlementType.
     * 
     * @param settlementType The SettlementType value.
     */
    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    /**
     * Reset totals after settlement. Only used for settlement type
     * {@link SettlementType#SubShiftTotals}
     * 
     * @return
     */
    public boolean isResetTotals() {
        return resetTotals;
    }

    /**
     * Sets the resetTotals.
     * 
     * @param resetTotals The boolean value of resetTotals.
     */
    public void setResetTotals(boolean resetTotals) {
        this.resetTotals = resetTotals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(SettlementType.class, this.settlementType, "settlementType"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new IllegalArgumentException(String.join(", ", validationErrors));
        }
    }
}