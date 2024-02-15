package com.linkly.pos.sdk.models.enums;

import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;

/**
 * Used by {@link RetrieveTransactionRequest} to control which identifier should be
 * used to search for historical transactions.
 */
public enum ReferenceType {

    /**
     * Use {@link TransactionRequest#getTxnRef()}.
     */
    ReferenceNo,

    /**
     * Use {@link TransactionRequest#getRrn()}.
     */
    RRN
}
