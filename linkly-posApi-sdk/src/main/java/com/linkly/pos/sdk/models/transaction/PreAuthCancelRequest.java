package com.linkly.pos.sdk.models.transaction;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Cancel a {@link PreAuthRequest}.
 */
public class PreAuthCancelRequest extends FollowUpTransactionRequest {

	/**
	 * Initialise a new pre auth cancel request.
	 * 
	 * @param rfn The String value of rfn.
	 */
    public PreAuthCancelRequest(String rfn) {
        setTxnType(TxnType.PreAuthCancel);
        setRfn(rfn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
    }

}
