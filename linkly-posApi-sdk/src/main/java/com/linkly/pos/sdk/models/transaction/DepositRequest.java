package com.linkly.pos.sdk.models.transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.IValidatable;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.Json;

/**
 * Deposit cash and/or cheques.
 */
public class DepositRequest extends TransactionRequest implements IValidatable {

    @Json(name = "AmtCash")
    private int amountCash;

    @Json(name = "AmtPurchase")
    private int amountCheque;

    @Json(name = "TotalPurchaseCount")
    private int totalCheques;

    /**
     * Initialise a new deposit request.
     * 
     * @param amountCash The int value of amountCash.
     * @param amountCheque The int value of amountCheque.
     * @param totalCheques The int value of totalCheques.
     */
    public DepositRequest(int amountCash, int amountCheque, int totalCheques) {
        setTxnType(TxnType.Deposit);
        this.amountCash = amountCash;
        this.amountCheque = amountCheque;
        this.totalCheques = totalCheques;
    }

    /**
     * The cash deposit amount.
     * 
     * @return int
     */
    public int getAmountCash() {
        return amountCash;
    }

    /**
     * The chequedeposit amount.
     * 
     * @return int
     */
    public int getAmountCheque() {
        return amountCheque;
    }

    /**
     * Total number of cheques to deposit.
     * 
     * @return int
     */
    public int getTotalCheques() {
        return totalCheques;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = new ArrayList<>();

        if (this.amountCheque == 0) {
            validationErrors.addAll(
                Arrays.asList(
                    ValidatorUtil.exclusiveBetween(this.amountCash, "amountCash", 1,
                        Constants.Validation.MAX_AMOUNT))
                    .stream()
                    .filter(m -> m != null)
                    .collect(Collectors.toList()));
        }
        if (this.amountCash == 0) {
            validationErrors.addAll(
                Arrays.asList(
                    ValidatorUtil.exclusiveBetween(this.amountCheque, "amountCheque", 1,
                        Constants.Validation.MAX_AMOUNT))
                    .stream()
                    .filter(m -> m != null)
                    .collect(Collectors.toList()));
        }
        String totalChequesError = ValidatorUtil.greaterThanOrEqual(this.totalCheques,
            "totalCheques", 0);
        if (totalChequesError != null) {
            validationErrors.add(totalChequesError);
        }
        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }

}
