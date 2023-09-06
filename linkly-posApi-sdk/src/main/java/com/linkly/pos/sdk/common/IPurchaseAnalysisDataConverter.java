package com.linkly.pos.sdk.common;

/**
 * Interface for classes which need to be able to serialise into a PAD string.
 */
public interface IPurchaseAnalysisDataConverter {

    /**
     * Convert the model to a purchase analysis data string conforming to the expected format.
     * 
     * @return PAD string.
     */
    String toPadString();
}