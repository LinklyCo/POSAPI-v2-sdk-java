- [1. Introduction](#1-introduction)
- [2. Build and test](#2-build-and-test)
- [3. IPosApiService Interface](#3-iposapiservice-interface)
- [4. Initialising the SDK](#4-initialising-the-sdk)
- [5. Pairing](#5-pairing)
  - [5.1. Loading an existing pair secret](#51-loading-an-existing-pair-secret)
- [6. Sending POS requests and handling responses](#6-sending-pos-requests-and-handling-responses)
  - [6.1. Logon](#61-logon)
  - [6.2. Status](#62-status)
  - [6.3. Settlement](#63-settlement)
  - [6.4. Query card](#64-query-card)
  - [6.5. Configure merchant](#65-configure-merchant)
  - [6.6. Reprint receipt](#66-reprint-receipt)
  - [6.7. Send key](#67-send-key)
  - [6.8. Transaction requests](#68-transaction-requests)
    - [6.8.1. Purchase](#681-purchase)
    - [6.8.2. Refund](#682-refund)
    - [6.8.3. Void](#683-void)
    - [6.8.4. Cash out](#684-cash-out)
    - [6.8.5. Deposit](#685-deposit)
    - [6.8.6. Pre-authorisation](#686-pre-authorisation)
    - [6.8.7. Extend pre-authorisation](#687-extend-pre-authorisation)
    - [6.8.8. Top-up pre-authorisation](#688-top-up-pre-authorisation)
    - [6.8.9. Cancel pre-authorisation](#689-cancel-pre-authorisation)
    - [6.8.10. Partially cancel pre-authorisation](#6810-partially-cancel-pre-authorisation)
    - [6.8.11. Complete pre-authorisation](#6811-complete-pre-authorisation)
    - [6.8.12. Pre-authorisation inquiry](#6812-pre-authorisation-inquiry)
    - [6.8.13. Pre-authorisation summary](#6813-pre-authorisation-summary)
  - [6.9. Handling the transaction response](#69-handling-the-transaction-response)
  - [6.10. Retrieve historical transaction](#610-retrieve-historical-transaction)
  - [6.11. Handling display events](#611-handling-display-events)
  - [6.12. Handling receipt events](#612-handling-receipt-events)
  - [6.13. Handling error events](#613-handling-error-events)

# 1. Introduction
This document provides a quick start guide to using the Java POS API v2 SDK with code samples. The purpose of the sample code is to demonstrate easily comprehensible usage of the SDK given a simplistic POS interface. Some examples use hard-coded values which would not be the case with production code. For a complete SDK reference refer to in the linkly-posApi-sdk\docs and to see a Web-based demo POS application which uses the SDK, refer to the linkly-posApi-demoPos project.

The following guide assumes you have built the linkly-posApi-sdk library and included either the project or library in your POS solution.

[!Note]
Classes that are mentioned that is not in the SDK are actual classes used in the Demo App.

# 2. Build and test
The linkly-posApi-sdk solution contains the following three projects:
* linkly-posApi-sdk - The Java SDK library which can be used to implement a POS application by leveraging the POS API v2. This library uses Java 1.8 and is compatible with higher versions. If you only want to use the SDK and do not care about running the unit tests or Demo POS you can build this project only.
* linkly-posApi-test - The unit test project for linkly-posApi-sdk. This requires Java 1.8.
* linkly-posApi-demoPos - Demo POS application which uses the linkly.posApi.sdk and demonstrates usage of the majority of functionality provided in the SDK. This can be used as a reference implementation for writing a POS. Demo POS requires Java 11. Demo app uses Java 11 while Java 8 is built using Java 8. This is to show that the SDK will work using higher versions of Java. If you opted to use Java 8 for the Demo App, minor code changes must be applied.

# 3. IPosApiService interface
The `IPosApiService` interface is meant to highlight some of the functionality which may be present in an actual POS.

```java
package com.linkly.pos.sdk.service;

public interface IPosApiService {

    void setPairSecret(String pairSecret);
    void pairingRequest(PairingRequest request);
    UUID transactionRequest(TransactionRequest request);
    UUID statusRequest(StatusRequest request);
    UUID logonRequest(LogonRequest request);
    UUID settlementRequest(SettlementRequest request);
    UUID queryCardRequest(QueryCardRequest request);
    UUID configureMerchantRequest(ConfigureMerchantRequest request);
    UUID reprintReceiptRequest(ReprintReceiptRequest request);
    void sendKeyRequest(SendKeyRequest request);
    void resultRequest(ResultRequest request);
    void retrieveTransactionRequest(RetrieveTransactionRequest request);
}
```

# 4. <a name="init-sdk"></a>Initialising the SDK

The following example shows initialisation of the SDK. An implementation of `IPosApiEventListener` is required. Since the SDK is asynchronous, responses to API requests are handled by this implementation, which in the case of this project is the `DemoPosApiServiceImpl` class.  `DemoPosApiServiceImpl` is both the service class that implements the SDK and serves as the listener

The service endpoints for the Linkly Auth API and POS API endpoints must be defined and a `PosVendorDetails` object which specifies the unique identifiers of the POS and vendor should be created. Lastly the `PosApiService` can be initialised.

At `DemoPosApiServiceImpl` class there is a method `init()` and `lastTransactionInterrupted()` to check whether there is an [existing POS and PIN pad pairing](#set-pair-secret) followed by a call to `resultRequest()` which will attempt to [recover a transaction](#session-recovery) that may have been interrupted by a software or hardware failure.

<a name="samplepos-java"></a>
```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements  IPosApiEventListener {

	@Value("${pos.app.name}")
	private String posName;

	@Value("${pos.app.version}")
	private String posVersion;

	@Value("${pos.app.id}")
	private String posId;

	@Value("${pos.api.auth.uri}")
	private String authUri;

	@Value("${pos.api.pos.uri}")
	private String posUri;

	private IPosApiService service;
	private PosVendorDetails posVendorDetails;
	private DataManager dataManager;
	private Logger logger;

	@PostConstruct
	public void init() {
		logger = new DemoLogger();
		posVendorDetails = new PosVendorDetails(posName, posVersion, UUID.fromString(posId), UUID.fromString(posId));
		ApiServiceEndpoint serviceEndpoint = new ApiServiceEndpoint(authUri, posUri);
		service = new PosApiService(this, null, posVendorDetails, new PosApiServiceOptions(), serviceEndpoint, logger);
		
		dataManager = new DataManager(posVendorDetails.getPosId());
		String secret = dataManager.getCurrentLane().getSecret();
		
        if (!StringUtil.isNullOrWhiteSpace(secret)) {
			service.setPairSecret(secret);
		}
	}

    @Override
	public String lastTransactionInterrupted() {
		var transactions = dataManager.getCurrentLane().getTrasactions();
		if (!CollectionUtils.isEmpty(transactions)) {
			var transaction = transactions.get(transactions.size() - 1);
			if (transaction.getResponse() == null && transaction.getError() == null) {
				return transaction.getSessionId().toString();
			}
		}
		return null;
	}

    @Override
	public void resultRequest(ResultRequest request) {
		service.resultRequest(request);
	}
```

# 5. Pairing
The POS interacts with the PIN pad via the Linkly Cloud Gateway (if using the cloud solution). Pairing is the process of associating the PIN pad with the Cloud Gateway.

When a PIN pad is successfully paired to the Cloud Gateway a secret JWT (JSON Web Token) is returned by the SDK and this should be stored persistently by the POS to avoid the need to re-pair when the POS application is restarted.

To pair a PIN pad use the `IPosApiService.pairingRequest()` method. The request must contain valid `Username`, `PairCode` and `Password` credentials.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public void pairingRequest(PairingRequest request) {
		service.pairingRequest(request);
	}
}
```
When pairing completes, the `IPosApiEventListener.pairingComplete()` listener will be invoked, and the pairing secret (JWT) in the response can be securely saved. Next time the POS starts it can [load the secret](#set-pair-secret) to bypass pairing. The implementation to load and store the secret is out of scope of this document.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void pairingComplete(PairingRequest request, PairingResponse response) {
		service.setPairSecret(response.getSecret());
		asyncResponses.add(new SimplifiedResponse(PAIRING_RESPONSE_KEY, response, null));
	}
}
```
## 5.1. <a name="set-pair-secret"></a>Loading an existing pair secret
When the SDK is initialised it normally needs to be paired with a PIN pad, however if a pairing secret was saved when the PIN pad was last paired, that secret can be loaded into the SDK to bypass pairing. In the following example, the code checks if the pairing secret can be loaded from secure storage. If so `IPosApiService.SetPairSecret()` is called and the POS can immediately issue requests to the SDK without pairing.

Secret key is automatically loaded during project startup. It is loaded from a json file using the `DataManager` Java class

The following method is [called during initialisation of the `DemoPosApiServiceImpl`](#samplepos-java)

```java
package com.linkly.pos.demoPos.service.impl;

@Service
public class DemoPosApiServiceImpl implements IPosApiEventListener {

    @PostConstruct
	public void init() {
		dataManager = new DataManager();
		String secret = dataManager.getCurrentLane().getSecret();
		if (!StringUtil.isNullOrWhiteSpace(secret)) {
			service.setPairSecret(secret);
		}
	}
}
```

# 6. Sending POS requests and handling responses
The following sections will explain how to send the various POS requests and handle the responses. All request types may invoke the [`IPosApiEventListener.error()`](#error-listener) listener if the request fails. Some requests may invoke the [`IPosApiEventListener.display()`](#display-listener) and [`IPosApiEventListener.receipt()`](#receipt-listener) listeners and when this is the case it will be explicitly mentioned in the proceeding sections.

## 6.1. Logon
`DemoPosApiServiceImpl.logonRequest()` performs a logon of the PIN pad to the bank. The following code sends a `Standard` logon request. `DemoPosApiServiceImpl.logonComplete()` will handle the processing of the Logon success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void logonRequest(LogonRequest request) {
		service.logonRequest(request);
	}
    
    @Override
	public void logonComplete(UUID sessionId, LogonRequest request, LogonResponse response) {
		asyncResponses.add(new SimplifiedResponse(Logon.name(), response, sessionId.toString()));
	}
}
```

## 6.2. Status
`DemoPosApiServiceImpl.statusRequest()` retrieves the terminal status. The following code sends `Standard` status request. `DemoPosApiServiceImpl.statusComplete()` will handle the processing of the Status success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public UUID statusRequest(StatusRequest request) {
		service.statusRequest(request);
	}

    @Override
	public void statusComplete(UUID sessionId, StatusRequest request, StatusResponse response) {
		asyncResponses.add(new SimplifiedResponse(Status.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}
```

## 6.3. Settlement
`DemoPosApiServiceImpl.settlementRequest()` retrieves a settlement report. `DemoPosApiServiceImpl.settlementComplete()` will handle the processing of the Settlement success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
	private DataManager dataManager;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public UUID settlementRequest(SettlementRequest request) {
		UUID uuid = service.settlementRequest(request);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    @Override
	public void settlementComplete(UUID sessionId, SettlementRequest request, SettlementResponse response) {
		asyncResponses.add(new SimplifiedResponse(Settlement.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}

```

## 6.4. Query card
`DemoPosApiServiceImpl.queryCardRequest()` retrieves the details of a card. `DemoPosApiServiceImpl.queryCardComplete()` will handle the processing of the Query Card success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
	private DataManager dataManager;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public UUID queryCardRequest(QueryCardRequest request) {
		UUID uuid = service.queryCardRequest(request);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    @Override
	public void queryCardComplete(UUID sessionId, QueryCardRequest request, QueryCardResponse response) {
		asyncResponses.add(new SimplifiedResponse(QueryCard.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}

```

## 6.5. Configure merchant
`DemoPosApiServiceImpl.configureMerchantRequest()` configures the PIN pad's `CatId` and `CaId` settings with the acquiring bank.  `DemoPosApiServiceImpl.configureMerchantComplete()` will handle the processing of the Configure Merchant success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
	private DataManager dataManager;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public UUID configureMerchantRequest(ConfigureMerchantRequest request) {
		UUID uuid = service.configureMerchantRequest(request);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    @Override
	public void configureMerchantComplete(UUID sessionId, ConfigureMerchantRequest request,
			ConfigureMerchantResponse response) {
		asyncResponses.add(new SimplifiedResponse(ConfigureMerchant.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}

```

## 6.6. Reprint receipt
`DemoPosApiServiceImpl.reprintReceiptRequest()` will retrieve a previous receipt from the PIN pad. `DemoPosApiServiceImpl.reprintReceiptComplete()` will handle the processing of the Reprint Receipt success response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;
	private DataManager dataManager;
    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public UUID reprintReceiptRequest(ReprintReceiptRequest request) {
		UUID uuid = service.reprintReceiptRequest(request);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    @Override
	public void reprintReceiptComplete(UUID sessionId, ReprintReceiptRequest request, ReprintReceiptResponse response) {
		asyncResponses.add(new SimplifiedResponse(ReprintReceipt.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}

```

## 6.7. Send key
Send a key press to the PIN pad. The following example sends the '0' key to the PIN pad which is equivalent to pressing the `OK` or `CANCEL` button (depending on the context). The `IPosApiService.sendKeyRequest()` does not have an event handler and it can be assumed the request was successful if the `IPosApiEventListener.error()` event is not invoked.

Some use cases for `IPosApiService.sendKeyRequest()` is cancelling a transaction from the POS, or verifying a card signature.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public void sendKeyRequest(SendKeyRequest request) {
		service.sendKeyRequest(request);
	}
}

 ## 6.8. <a name="transaction-requests"></a>Transaction requests
The `IPosApiService.transactionRequest()` method can be used to initiate transactions from the PIN pad. This section will show examples of sending all the supported transaction requests. Note the same [`IPosApiEventListener.transactionComplete()`](#transaction-complete) listener will be invoked upon successful completion of the transaction regardless of the transaction type. Therefore a single [example of handling the `IPosApiEventLIstener.transactionComplete()` listener](#transaction-complete) has been provided.

[`IPosApiEventListener.display()`](#display-listener) and [`IPosApiEventListener.receipt()`](#receipt-listener) listeners can be invoked as part of this request. The receipt listener will be invoked when the PIN pad prints a transaction receipt. The display listener can be invoked multiple times throughout the transaction when the PIN pad is displaying prompts to the user such as `SWIPE CARD`, `ENTER PIN`, etc.

### 6.8.1. Purchase
Purchase which also facilitates cash out.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		int amountCash = NumberUtils.toInt(request.getAmountCash());
		switch (request.getTxnType()) {
		case Purchase:
			return new PurchaseRequest(amount, amountCash);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.2. Refund
Refund for a purchase.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new RefundRequest(amount, request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.3. Void
Void a purchase before settlement so no funds leave the cardholder's account. A transaction cannot be voided post settlement and will need to be refunded.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new VoidRequest(amount);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.4. Cash out
Withdraw cash from a cardholder's account.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amountCash = NumberUtils.toInt(request.getAmountCash());
		switch (request.getTxnType()) {
		case Refund:
			return new CashRequest(amountCash);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.5. Deposit
Deposit funds into a cardholder's account.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		int amountCash = NumberUtils.toInt(request.getAmountCash());
		int totalCheque = isNullOrWhiteSpace(request.getTotalCheques())
				? 0 : Integer.parseInt(request.getTotalCheques());
		switch (request.getTxnType()) {
		case Refund:
			return new DepositRequest(amountCash, amount, totalCheque);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.6. Pre-authorisation
Place a hold on the cardholder's account for `PreAuthRequest.amount` cents. The request can later be [completed](#preauth-complete) to capture that amount into the merchant's account. It is important to note that for a pre-authorisation, a RFN (reference number) will be returned by the [`IPosApiListener.transactionComplete()`](#transaction-complete) listener in the `TransactionResponse.rfn` property. This must be used to make follow up transactions on the pre-authorisation and should be stored if the POS is to support this functionality.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthRequest(amount);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.7. Extend pre-authorisation
Pre-authorisation are only valid for a fixed time period (typically 5 days). This operation allows an active pre-authorisation to be extended using the `RFN` from the original pre-authorisation response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthExtendRequest(request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.8. Top-up pre-authorisation
Increase the pre-authorisation amount using the `RFN` from the original pre-authorisation response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthTopUpRequest(amount, request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.9. Cancel pre-authorisation
Fully cancel a pre-authorisation using the `RFN` from the original pre-authorisation response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthCancelRequest(request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.10. Partially cancel pre-authorisation
Reduce the pre-authorisation amount using the `RFN` from the original pre-authorisation response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthPartialCancelRequest(amount, request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.11. <a name="preauth-complete"></a>Complete pre-authorisation
Capture the amount (specified in the request) from the cardholder's account. The amount cannot exceed the pre-authorisation amount (taking into account top-ups and partial cancellations). This request must include the `RFN` from the original pre-authorisation response.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return new PreAuthCompletionRequest(amount, request.getRfn());
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}
}
```

### 6.8.12. Pre-authorisation inquiry
Verify a pre-authorisation using the `RFN` from the original pre-authorisation response. To check the result of this request look at the `TransactionResponse.amount` property in the [`IPosApiEventListener.transactionComplete()`](#transaction-complete) listener, which should contain the amount currently pre-authorised.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return createPreAuthInquiryRequest(request);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}

    private TransactionRequest createPreAuthInquiryRequest(DemoPosTransactionRequest request) {
		if(isNullOrWhiteSpace(request.getRfn())) {
			PreAuthSummaryRequest psr = new PreAuthSummaryRequest();
			Integer pai = request.getPai() == null ? 0 : Integer.parseInt(request.getPai());
			if(pai > 0) {
				psr.setPreAuthIndex(pai);
			}
			return psr;
		} else {
			PreAuthInquiryRequest pair = new PreAuthInquiryRequest(request.getRfn());
			pair.setRfn(request.getRfn());
			return pair;
		}
	}
}
```

### 6.8.13. Pre-authorisation summary
Perform an inquiry on all existing pre-authorisations using the `RFN` from the original pre-authorisation response. To check the result of this request look at the `TransactionResponse.purchaseAnalysisData["PAS"]` key in the [`IPosApiEventListener.transactionComplete()`](#transaction-complete) listener.


```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

    private IPosApiService service;

    @Override
	public UUID transactionRequest(DemoPosTransactionRequest request) {
		var transactionRequest = transactionRequestFactory(request);

        UUID uuid = service.transactionRequest(transactionRequest);
		if(uuid != null) {
			dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null, null);
		}
		return uuid;
	}

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
		int amount = NumberUtils.toInt(request.getAmount());
		switch (request.getTxnType()) {
		case Refund:
			return createPreAuthInquiryRequest(request);
		default:
			throw new IllegalArgumentException("Transaction type invalid");
		}
	}

    private TransactionRequest createPreAuthInquiryRequest(DemoPosTransactionRequest request) {
		if(isNullOrWhiteSpace(request.getRfn())) {
			PreAuthSummaryRequest psr = new PreAuthSummaryRequest();
			Integer pai = request.getPai() == null ? 0 : Integer.parseInt(request.getPai());
			if(pai > 0) {
				psr.setPreAuthIndex(pai);
			}
			return psr;
		} else {
			PreAuthInquiryRequest pair = new PreAuthInquiryRequest(request.getRfn());
			pair.setRfn(request.getRfn());
			return pair;
		}
	}
}
```

## 6.9. <a name="transaction-complete"></a>Handling the transaction response
The `IPosApiEventListener.transactionComplete()` listener is used to retrieve the response from a transaction request. When `IPosApiEventListener.transactionComplete()`, this indicates that the transaction has been completed and there is no need to attempt [recovery](#session-recovery) if the POS hardware or software encounters a failure.

Some fields in the `TransactionResponse` such as `RFN` may need to be saved to facilitate follow-up transactions on pre-authorisations.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

	private DataManager dataManager;
	private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void transactionComplete(UUID sessionId, TransactionRequest request, TransactionResponse response) {
		asyncResponses.add(new SimplifiedResponse(Transaction.name(), response, sessionId.toString()));
		dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request, response, null);
	}
}
```

## 6.10. Retrieve historical transaction
Retrieve the transaction details of past transactions using either the `TransactionRequest.txnRef` or `TransactionRequest.rrn`. `DemoPosApiServiceImpl.retrieveTransactionComplete()` will be invoked when the request is successful. 

Display the `TransactionResponse.responseText` of all transactions which matched the criteria in the request. In a real POS implementation, The listener will likely use more of the available fields in the `TransactionResponse`.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

	private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void retrieveTransactionRequest(RetrieveTransactionRequest request) {
		service.retrieveTransactionRequest(request);
	}

    @Override
	public void retrieveTransactionComplete(RetrieveTransactionRequest request, List<TransactionResponse> responses) {
		// No implementation
        // Should display details to UI
	}

}
```

## 6.11. <a name="display-listener"></a>Handling display events
Display events are triggered when the PIN pad displays a prompt, for example `SWIPE CARD` or `ENTER PIN`. Display events may simply be echoed on the POS UI or further logic could be triggered based on the content of the message. In the following example the `DisplayResponse.displayText` is echoed in the POS UI.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

	private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void display(UUID sessionId, PosApiRequest request, DisplayResponse response) {
		asyncResponses.add(new SimplifiedResponse(Display.name(), response, sessionId.toString()));
	}
}
```

## 6.12. <a name="receipt-listener"></a>Handling receipt events
Receipt events are triggered when the PIN pad prints a receipt. In the following example the `ReceiptResponse.receiptText` is displayed in the POS UI.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

	private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void receipt(UUID sessionId, PosApiRequest request, ReceiptResponse response) {
		asyncResponses.add(new SimplifiedResponse(RECEIPT_RESPONSE_KEY, response, sessionId.toString()));
	}
}
```

## 6.13. <a name="error-listener"></a>Handling error events
The error listener is triggered when requests fail at the HTTP or application layer, such as if there is a connectivity issue to the API, or authentication fails due to invalid credentials. Errors should be logged to help debug any issues with the POS or SDK. Some errors may be displayed to the UI, such as failed authentication (which would likely indicate user error).

Error response will be handled by `DataManager.saveTransaction()` to indicate that transacton has caught an error, there is not a need for recovery, and will be tagged as completed.

```java
package com.linkly.pos.demoPos.service.impl;

public class DemoPosApiServiceImpl implements IPosApiEventListener {

	private List<SimplifiedResponse> asyncResponses = new ArrayList<>();

    @Override
	public void error(UUID sessionId, IBaseRequest request, ErrorResponse error) {
		var uuid = sessionId != null ? sessionId.toString() : "";
		asyncResponses.add(new SimplifiedResponse(ERROR_RESPONSE_KEY, error, uuid));
		if(sessionId != null) {
			dataManager.saveTransaction(sessionId, null, null, null, error);
		}
	}
}
```

# 7. <a name="session-recovery"></a>Session recovery
A session recovery has been implemented on the Demo POS App. This is handled by `DataManager` and is saved both in `SessionData` class and store in `sessions.json` file.  

Data that are necessary for session recovery are stored in `SessionData`. 

```java
public class SessionData {
	private ReceiptAutoPrint receiptAutoPrint = ReceiptAutoPrint.POS;
	private boolean cutReceipt;
	private UUID appGuid;
	private List<Pad> pads = new ArrayList<>(); 
	private List<SurchargeRates> surchargeRates = new ArrayList<>(); 
	private List<Lane> lanes = new ArrayList<>();

	public static class Pad {
		private boolean use;
		private String tag;
		private String value;
	}
	
	public static class SurchargeRates {
		private String type;
		private String bin;
		private String value;
	}
	
	public static class Lane {
		private String username;
		private String secret;
		private String authEndpoint;
		private String posEndpoint;
		private boolean lastActive = true;
		private List<TransactionSessions> trasactions = new ArrayList<>();
	}
	
	public static class TransactionSessions {
		private String type;
		private UUID sessionId;
		private String requestTimestamp;
		private String responseTimestamp;
		private Object request;
		private Object response;
		private ErrorResponse error;
	}
}

```

`DataManager` handle all the processes of session recovery.

#### Methods: 
* `DataManager.load()` invoked during startup to initialized `SessionData`.
* `DataManager.saveCurrent()` it will save paired account and all its record. This includes, purchase analysis data, surcharges, transactions, and secret key.
* `DataManager.changeLane()` if there are 2 or more paired account in a device, UI will have an option to select another paired account.
* `DataManager.saveTransaction()` this is invoked everytime a transaction has been made, an error has occured, and/or a success response has been received. A transaction without error response or success response will be tagged as interrupted and will be subject to recovery.
* `DataManager.savePads()` save purchase analysis data from the UI inputs
* `DataManager.saveSurcharges()` save surcharges from the UI inputs

```java
package com.linkly.pos.demoPos.utils;

public class DataManager {

	private final static String FILE_NAME = "sessions.json";
	private final static ObjectMapper MAPPER = new ObjectMapper();

	private SessionData sessions = new SessionData();
	private Lane currentLane;
	private boolean lastSessionInterrupted;
	private UUID lastInterruptedSessionId;

	public DataManager(UUID appGuid) {
		load();
		sessions.setAppGuid(appGuid);
		MAPPER.registerModule(new JavaTimeModule());
	}
	
    private void load() {
		try {
			String content = null;
			var file = new File(FILE_NAME);
			if (file.exists()) {
				content = FileCopyUtils.copyToString(new FileReader(file));
			}
			if (!StringUtil.isNullOrWhiteSpace(content)) {
				var fileSessions = MAPPER.readValue(content, SessionData.class);
				if (sessions != null) {
					sessions = fileSessions;
				}
				if (sessions.getLanes().size() == 0) {
					sessions.getLanes().add(new Lane());
				}
			} else {
				sessions.getLanes().add(new Lane());
			}
			currentLane = sessions.getLanes().stream().filter(l -> l.isLastActive()).findFirst()
					.orElse(sessions.getLanes().get(0));
			TransactionSessions lastTransaction = null;
			if (!CollectionUtils.isEmpty(currentLane.getTrasactions())) {
				lastTransaction = CollectionUtils.lastElement(currentLane.getTrasactions());
				if (lastTransaction != null && lastTransaction.getResponse() == null
						&& lastTransaction.getError() == null) {
					lastSessionInterrupted = true;
					lastInterruptedSessionId = lastTransaction.getSessionId();
				}
			}
		} catch (IOException e) {
			// happening in the background and not user-facing
		}
	}

	public void saveCurrent(Lane lane) {
		if (lane != null) {
			List<Lane> lanes = sessions.getLanes();
			lanes.forEach(l -> {
				if (l.getUsername() != null && !l.getUsername().equals(lane.getUsername())) {
					l.setLastActive(false);
				}
			});
			boolean anyMatch = false;
			if(lanes.size() == 1 && isNullOrWhiteSpace(lanes.get(0).getSecret())) {
				lanes.set(0, lane);
				anyMatch = true;
			}
			anyMatch = sessions.getLanes()
					.stream().anyMatch(l -> l.getUsername() != null 
						&& lane.getUsername().equals(l.getUsername()));
			
			lane.setLastActive(true);
			currentLane = lane;
			if(!anyMatch) {
				sessions.getLanes().add(lane);
			}
			save();
		}
	}
	
	public void savePads(Map<String, String> purchaseAnalysisData) {
		List<Pad> pads = purchaseAnalysisData.entrySet()
		.stream()
		.filter(e -> e.getValue() != null && e.getValue().length() > 0)
		.map(e -> {
			Pad pad = new Pad();
			pad.setUse(true);
			pad.setTag(e.getKey());
			pad.setValue(e.getValue());
			return pad;
		}).collect(Collectors.toList());
		sessions.setPads(pads);
		save();
	}
	
	public void saveSurcharges(List<SurchargeRates> rates) {
		rates.removeIf(e -> e.getBin() == null || e.getBin().length() == 0
				|| e.getValue() == null || e.getValue().length() == 0);
		sessions.setSurchargeRates(rates);
		save();
	}

	public List<Lane> getLanes() {
		return sessions.getLanes();
	}
	
	public void changeLane(String username) {
		Lane newCurrentLane = sessions.getLanes()
				.stream()
				.filter(l -> l.getUsername().equals(username))
				.findFirst()
				.get();
		currentLane = newCurrentLane;
	}

	public void saveTransaction(UUID sessionId, String type, IBaseRequest request, PosApiResponse response,
			ErrorResponse errorResponse) {
		if (currentLane != null) {
			TransactionSessions transaction = currentLane.getTrasactions().stream()
					.filter(l -> l.getSessionId().equals(sessionId)).findFirst().orElse(null);
			if (transaction == null) {
				var newTransaction = new TransactionSessions(type, sessionId, type, type, request, response,
						errorResponse);
				currentLane.getTrasactions().add(newTransaction);
			} else {
				transaction.setResponseTimestamp(LocalDateTime.now().toString());
				if (response != null) {
					transaction.setResponse(response);
				}
				if (errorResponse != null) {
					transaction.setError(errorResponse);
				}
			}
			save();
		}
	}

	private void save() {
		try {
			var json = MAPPER.writeValueAsString(sessions);
			var writer = new BufferedWriter(new FileWriter(FILE_NAME));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			// happening in the background and not user-facing
			e.printStackTrace();
		}
	}
}
```

# 8. More complex POS-PIN Pad configurations
The examples provided assume a 1:1 mapping between POS and PIN pad, however in reality a POS may need to manage multiple PIN pads, or multiple POS applications may need to share a pool of PIN pads. These configurations can be supported with some caveats which will be mentioned.

## 8.1. Pool of PIN pads shared by one POS
If a single POS needs to manage a pool of PIN pads, the POS will need to manage multiple instances of the SDK - one per PIN pad. Each SDK instance would be paired to it's own PIN pad.

## 8.2. POS pool
If a pool of POS' share access to a single PIN pad the POS' need to make sure they do not attempt to send requests to a PIN pad which is currently handling a request from another POS. Synchronisation across isolated POS processes is probably impractical so the most feasible method of dealing with resource contention is error handling.

When a request is sent to a PIN pad which is busy handling another request, the PIN pad will return a `BY` (PINPAD BUSY) `ResponseCode` in the listener's response model. The response code can be checked and an appropriate message can be displayed on the POS.

This check is only required for the following listeners: `ConfigureMerchantComplete`, `QueryCardComplete`, `ReprintReceiptComplete`, `SettlementComplete`, `StatusComplete`, `TransactionComplete`.
