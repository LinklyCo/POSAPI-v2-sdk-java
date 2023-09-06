package com.linkly.pos.sdk.models.transaction;

import java.util.List;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Perform an inquiry on all existing pre-authorisations for informational purposes.
 */
public class PreAuthSummaryRequest extends TransactionRequest {

	/**
	 * Initialise a new pre auth summary request.
	 */
    public PreAuthSummaryRequest() {
        setTxnType(TxnType.PreAuthInquiry);
    }

    /**
     * Sets the summary window number to request. If null then the first summary window will be
     * requested. Example: 1. This setter updates
     * {@link com.linkly.pos.sdk.models.PosApiRequest#getPurchaseAnalysisData}
     * 
     * @param value The int value.
     */
    public void setPreAuthIndex(int value) {
        getPurchaseAnalysisData().put(Constants.PurchaseAnalysisData.PAI, String.valueOf(
            value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        return super.validate();
    }
}
