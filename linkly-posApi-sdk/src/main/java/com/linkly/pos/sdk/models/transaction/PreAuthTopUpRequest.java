package com.linkly.pos.sdk.models.transaction;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Increase a {@link PreAuthRequest} for an amount of
 * {@link PreAuthManipulationRequest#getAmount} cents.
 */
public class PreAuthTopUpRequest extends PreAuthManipulationRequest {

	/**
	 * Initialise a new pre auth top up request.
	 * 
	 * @param amount The int value of amount.
	 * @param rfn The String value of rfn.
	 */
    public PreAuthTopUpRequest(int amount, String rfn) {
        setTxnType(TxnType.PreAuthTopUp);
        setRfn(rfn);
        super.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
    }
}
