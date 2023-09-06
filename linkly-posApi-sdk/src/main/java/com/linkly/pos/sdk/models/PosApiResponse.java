package com.linkly.pos.sdk.models;

import java.util.Map;

import com.linkly.pos.sdk.models.enums.ResponseType;
import com.squareup.moshi.Json;

/**
 * Common model for POS API responses.
 */
public class PosApiResponse {

    @Json(name = "ResponseType")
    protected ResponseType responseType;

    @Json(name = "PurchaseAnalysisData")
    protected Map<String, String> purchaseAnalysisData;

    /**
     * Type of API response.
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Sets the type of API response.
     * 
     * @param responseType The ResponseType value of API response.
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    /**
     * Additional data to be sent or received directly from the PIN pad.
     */
    public Map<String, String> getPurchaseAnalysisData() {
        return purchaseAnalysisData;
    }

    /**
     * Sets the purchaseAnalysisData.
     * 
     * @param purchaseAnalysisData TheMap<String,String> value of purchaseAnalysisData.
     */
    public void setPurchaseAnalysisData(Map<String, String> purchaseAnalysisData) {
        this.purchaseAnalysisData = purchaseAnalysisData;
    }
}
