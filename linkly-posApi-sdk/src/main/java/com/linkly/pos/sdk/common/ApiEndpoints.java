package com.linkly.pos.sdk.common;

/*
 *	This class contains all Linkly endpoints
 */
public final class ApiEndpoints {

	/*
	 * Endpoint for authentication
	 */
    public static final String TOKENS_ENDPOINT = "/v1/tokens/cloudpos";
    
    
    /*
	 * Endpoint for pairing
	 */
    public static final String PAIR_ENDPOINT = "/v1/pairing/cloudpos";
    
    
    /*
	 * Endpoint for creating transaction
	 */
    public static final String TRANSACTION_ENDPOINT = "/v2/sessions/{0}/transaction";
    
    
    /*
	 * Endpoint for retrieving Pinpad status
	 */
    public static final String STATUS_ENDPOINT = "/v2/sessions/{0}/status";
    
    
    /*
	 * Endpoint for logon
	 */
    public static final String LOGON_ENDPOINT = "/v2/sessions/{0}/logon";
    
    
    /*
	 * Endpoint for requesting settlement
	 */
    public static final String SETTLEMENT_ENDPOINT = "/v2/sessions/{0}/settlement";
    
    
    /*
	 * Endpoint for retriving card details
	 */
    public static final String QUERYCARD_ENDPOINT = "/v2/sessions/{0}/querycard";
    
    
    /*
	 * Endpoint for configuring merchant details
	 */
    public static final String CONFIGURE_MERCHANT_ENDPOINT = "/v2/sessions/{0}/configuremerchant";
    
    
    /*
	 * Endpoint for reprinting receipt
	 */
    public static final String REPRINT_RECEIPT_ENDPOINT = "/v2/sessions/{0}/reprintreceipt";
    
    
    /*
	 * Endpoint for sending key request to Pinpad
	 */
    public static final String SEND_KEY_ENDPOINT = "/v2/sessions/{0}/sendkey";
    
    
    /*
	 * Endpoint for retrieving status of operations
	 */
    public static final String RESULT_ENDPOINT = "/v2/sessions/{0}/result?all={1}";
    
    
    /*
	 * Endpoint for retreiving transaction details
	 */
    public static final String RETRIEVE_TRANSACTION_ENDPOINT =
        "/v2/transaction?reference={0}&referenceType={1}";
}
