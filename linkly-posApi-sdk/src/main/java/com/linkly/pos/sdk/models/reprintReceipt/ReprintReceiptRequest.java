package com.linkly.pos.sdk.models.reprintReceipt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.ReprintType;

/**
 * Re-print receipt.
 */
public class ReprintReceiptRequest extends PosApiRequest {

    private ReprintType reprintType = ReprintType.GetLast;

    /**
     * Indicates whether the receipt should be returned or reprinted.
     * 
     * @return ReprintType
     */
    public ReprintType getReprintType() {
        return reprintType;
    }

    /**
     * Sets the reprintType.
     * 
     * @param reprintType The ReprintType value.
     */
    public void setReprintType(ReprintType reprintType) {
        this.reprintType = reprintType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(ReprintType.class, this.reprintType, "reprintType"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
