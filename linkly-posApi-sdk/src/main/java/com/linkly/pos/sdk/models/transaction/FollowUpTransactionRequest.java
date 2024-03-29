package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;

/**
 * Transaction request which is linked to an original transaction via a reference number.
 */
public abstract class FollowUpTransactionRequest extends TransactionRequest {

	/**
	 * Sets the reference number of the original transaction on a subsequent transaction.
	 * 
	 * @param value The String value of rfn.
	 */
    public void setRfn(String value) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.RFN, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.hasValue(getPurchaseAnalysisData(), Constants.PurchaseAnalysisData.RFN))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
        	throw new InvalidArgumentException(String.join(", ", validationErrors));
        }
    }
}
