package com.linkly.pos.sdk.models.transaction;

import java.util.List;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Perform an inquiry or verify the amount of a {@link PreAuthRequest}.
 */
public class PreAuthInquiryRequest extends FollowUpTransactionRequest {

	/**
	 * Initialise a new pre auth inquiry request.
	 * 
	 * @param rfn The String value of rfn.
	 */
    public PreAuthInquiryRequest(String rfn) {
        setTxnType(TxnType.PreAuthInquiry);
        setRfn(rfn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        return super.validate();
    }
}