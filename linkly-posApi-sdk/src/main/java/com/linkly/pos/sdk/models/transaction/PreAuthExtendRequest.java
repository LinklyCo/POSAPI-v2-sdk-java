package com.linkly.pos.sdk.models.transaction;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Renew the expiry date of a {@link PreAuthRequest}.
 */
public class PreAuthExtendRequest extends FollowUpTransactionRequest {

	/**
	 * Initialise a new pre auth extend request.
	 * 
	 * @param rfn The string alue of rfn.
	 */
    public PreAuthExtendRequest(String rfn) {
        setTxnType(TxnType.PreAuthExtend);
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
