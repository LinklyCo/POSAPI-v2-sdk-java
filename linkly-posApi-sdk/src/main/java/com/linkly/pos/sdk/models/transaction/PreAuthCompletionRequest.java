package com.linkly.pos.sdk.models.transaction;

import java.util.List;

import com.linkly.pos.sdk.models.enums.TxnType;

/**
 * Complete a {@link PreAuthRequest} for an amount of
 * {@link PreAuthManipulationRequest#amount}
 */
public class PreAuthCompletionRequest extends PreAuthManipulationRequest {

	/**
	 * Initialise a new pre auth completion request.
	 * 
	 * @param amount The int value of amount.
	 * @param rfn The String value of rfn.
	 */
    public PreAuthCompletionRequest(int amount, String rfn) {
        setTxnType(TxnType.PreAuthComplete);
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