package com.linkly.pos.sdk.models.configureMerchant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.ValidatorUtil;
import com.linkly.pos.sdk.models.PosApiRequest;

/**
 * Configure a merchant's PIN pad settings.
 */
public class ConfigureMerchantRequest extends PosApiRequest {

    private String catId;
    private String caId;

    /**
     * The terminal ID (CatID) to configure the terminal with.
     * example: 12345678
     * 
     * @return String
     */
    public String getCatId() {
        return catId;
    }

    /**
     * Sets the catId.
     * 
     * @param catId The String value of catId.
     */
    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * The merchant ID (CaID) to configure the terminal with
     * example: 0123456789ABCDEF
     * 
     * @return String
     */
    public String getCaId() {
        return caId;
    }

    /**
     * Sets the caId.
     * 
     * @param caId The String value of caId.
     */
    public void setCaId(String caId) {
        this.caId = caId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        List<String> validationErrors = Arrays.asList(
            ValidatorUtil.notEmpty(this.catId, "catId"),
            ValidatorUtil.notEmpty(this.caId, "caId"))
            .stream()
            .filter(m -> m != null)
            .collect(Collectors.toList());
        if(validationErrors.size() > 0) {
        	throw new IllegalArgumentException(String.join(", ", validationErrors));
        }

    }
}
