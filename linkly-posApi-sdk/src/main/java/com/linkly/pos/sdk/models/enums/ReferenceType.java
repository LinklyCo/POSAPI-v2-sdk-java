package com.linkly.pos.sdk.models.enums;

/**
 * Used by {@link RetrieveTransactionRequest} to control which identifier should be
 * used to search for historical transactions.
 */
public enum ReferenceType {

    /**
     * Use {@link TransactionRequest#txnRef}.
     */
    ReferenceNo,
    
    /**
     * Use {@link TransactionRequest#rrn}.
     */
    RRN
}
