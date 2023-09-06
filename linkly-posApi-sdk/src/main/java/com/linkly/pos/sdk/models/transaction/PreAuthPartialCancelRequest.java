package com.linkly.pos.sdk.models.transaction;

import java.util.List;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Reduce a {@link PreAuthRequest} for an amount of
 * {@link PreAuthManipulationRequest#amount}
 */
public class PreAuthPartialCancelRequest extends PreAuthManipulationRequest {

	/**
	 * Initialise a new pre auth partial cancel request.
	 * 
	 * @param amount The int value of amount.
	 * @param rfn The String value of rfn.
	 */
    public PreAuthPartialCancelRequest(int amount, String rfn) {
        setTxnType(TxnType.PreAuthPartialCancel);
        setRfn(rfn);
        super.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        return super.validate();
    }
}