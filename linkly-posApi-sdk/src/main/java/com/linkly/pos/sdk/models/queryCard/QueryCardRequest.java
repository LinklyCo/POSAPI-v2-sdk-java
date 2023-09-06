package com.linkly.pos.sdk.models.queryCard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.enums.QueryCardType;

/**
 * Query the details of a card.
 */
public class QueryCardRequest extends PosApiRequest {

    private QueryCardType queryCardType = QueryCardType.ReadCard;

    /**
     * Type of querycard to perform.
     * 
     * @return QueryCardType
     */
    public QueryCardType getQueryCardType() {
        return queryCardType;
    }

    /**
     * Sets the queryCardType
     * 
     * @param queryCardType The QueryCardType value.
     */
    public void setQueryCardType(QueryCardType queryCardType) {
        this.queryCardType = queryCardType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validate() {
        List<String> parentValidationErrors = super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.isInEnum(QueryCardType.class, this.queryCardType, "queryCardType"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        validationErrors.addAll(parentValidationErrors);
        return validationErrors;
    }
}