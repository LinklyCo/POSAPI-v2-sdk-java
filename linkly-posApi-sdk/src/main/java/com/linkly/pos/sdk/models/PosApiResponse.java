package com.linkly.pos.sdk.models;

import java.util.Map;

import com.linkly.pos.sdk.models.enums.ResponseType;

/**
 * Common model for POS API responses.
 */
public class PosApiResponse {

    protected ResponseType responseType;
    protected Map<String, String> purchaseAnalysisData;

    /**
     * Type of API response.
     * 
     * @return {@link ResponseType} type of response
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Sets the type of API response.
     * 
     * @param responseType
     *            The ResponseType value of API response.
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    /**
     * Additional data to be sent or received directly from the PIN pad.
     * 
     * @return {@link Map} key: {@link String}, value: {@link String}
     */
    public Map<String, String> getPurchaseAnalysisData() {
        return purchaseAnalysisData;
    }

    /**
     * Sets the purchaseAnalysisData.
     * 
     * @param purchaseAnalysisData
     *            {@link Map} key: {@link String}, value: {@link String}
     */
    public void setPurchaseAnalysisData(Map<String, String> purchaseAnalysisData) {
        this.purchaseAnalysisData = purchaseAnalysisData;
    }
}
