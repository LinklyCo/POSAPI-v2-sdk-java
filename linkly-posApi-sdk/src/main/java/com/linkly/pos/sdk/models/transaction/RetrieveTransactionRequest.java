package com.linkly.pos.sdk.models.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.IValidatable;
import com.linkly.pos.sdk.models.enums.ReferenceType;

/**
 * Retrieve a historical transaction result.
 */
public class RetrieveTransactionRequest implements IBaseRequest, IValidatable {

    private ReferenceType referenceType = ReferenceType.ReferenceNo;
    private String reference;

    /**
     * Identifier which should be used to search for historical transactions.
     * 
     * @return ReferenceType
     */
    public ReferenceType getReferenceType() {
        return referenceType;
    }

    /**
     * Sets the referenceType
     * 
     * @param referenceType The ReferenceType value.
     */
    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Reference identifier to search for.
     * 
     * @return String
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     * 
     * @param reference The String value of reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(ReferenceType.class, this.referenceType, "referenceType"),
            ValidatorUtil.notEmpty(this.reference, "reference"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new IllegalArgumentException(String.join(", ", validationErrors));
        }
    }
}