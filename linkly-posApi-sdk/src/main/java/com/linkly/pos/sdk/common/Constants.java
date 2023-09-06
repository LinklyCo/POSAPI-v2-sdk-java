package com.linkly.pos.sdk.common;

/**
 * Container class for storing constant values used throughout the pos api sdk.
 */
public class Constants {

    public static final int REGEX_TIMEOUT = 100;

    /**
     * Constant values used during validations.
     */
    public static class Validation {
        /**
         * Maximum amount the API supports in cents.
         */
        public static final int MAX_AMOUNT = 999999999;
    }

    /**
     * Tag names in the PurchaseAnalysisData dictionary
     */
    public static class PurchaseAnalysisData {
        /**
         * Transaction reference number to be provided to follow-up transaction requests and returned
         * in the transaction response for some initial request types, eg: purchase.
         */
        public static final String RFN = "RFN";
        
        /**
         * Full transaction amount after tipping and surcharges, returned in a transaction response.
         */
        public static final String AMOUNT = "AMT";
        
        /**
         * Surcharge amount (in cents), returned in a transaction response.
         */
        public static final String SURCHARGE = "SUR";
        
        /**
         * Pre-auth index to request, for use in a pre-auth summary request.
         */
        public static final String PAI = "PAI";
        
        /**
         * Cardholder tipping options, for use in a transaction request.
         */
        public static final String TPO = "TPO";
        
        /**
         * Tip amount (in cents) to apply on top of the transaction amount, for use in a transaction
         * request.
         */
        public static final String TIP = "TIP";
        
        /**
         * Surcharge options to apply based on card bin, for use in a transaction request.
         */
        public static final String SC2 = "SC2";
        
        /**
         * Indicate whether a restricted item is present in the sale, for use in a transaction request.
         */
        public static final String PLB = "PLB";
    }

    /**
     * Constant values used to determine the response type received.
     */
    public static class ResponseType {
        public static final String LOGON = "logon";
        public static final String STATUS = "status";
        public static final String DISPLAY = "display";
        public static final String RECEIPT = "receipt";
        public static final String CONFIGUREMERCHANT = "configuremerchant";
        public static final String QUERYCARD = "querycard";
        public static final String REPRINTRECEIPT = "reprintreceipt";
        public static final String TRANSACTION = "transaction";
        public static final String SETTLEMENT = "settlement";
    }
}
