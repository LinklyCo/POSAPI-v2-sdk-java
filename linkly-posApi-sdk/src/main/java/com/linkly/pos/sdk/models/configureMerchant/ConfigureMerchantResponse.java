package com.linkly.pos.sdk.models.configureMerchant;

import com.linkly.pos.sdk.models.PosApiResponseWithResult;
import com.squareup.moshi.Json;

/**
 * Response to a {@link ConfigureMerchantRequest} 
 */
public class ConfigureMerchantResponse extends PosApiResponseWithResult {

	@Json(name = "Merchant")
    private String merchant;

	/**
     * Two digit merchant code.
     * example: 00
     * 
     * @return String
     */
    public String getMerchant() {
        return merchant;
    }
    
    /**
     * Sets the merchant.
     * 
     * @param merchant The String value of merchant.
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}
