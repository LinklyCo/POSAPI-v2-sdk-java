package com.linkly.pos.demoPos.service.impl;

import static com.linkly.pos.sdk.common.StringUtil.isNullOrWhiteSpace;
import static com.linkly.pos.sdk.models.enums.ResponseType.ConfigureMerchant;
import static com.linkly.pos.sdk.models.enums.ResponseType.Display;
import static com.linkly.pos.sdk.models.enums.ResponseType.Logon;
import static com.linkly.pos.sdk.models.enums.ResponseType.QueryCard;
import static com.linkly.pos.sdk.models.enums.ResponseType.ReprintReceipt;
import static com.linkly.pos.sdk.models.enums.ResponseType.Settlement;
import static com.linkly.pos.sdk.models.enums.ResponseType.Status;
import static com.linkly.pos.sdk.models.enums.ResponseType.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.linkly.pos.demoPos.DemoPosTransactionRequest;
import com.linkly.pos.demoPos.SimplifiedResponse;
import com.linkly.pos.demoPos.service.DemoPosApiService;
import com.linkly.pos.demoPos.utils.DataManager;
import com.linkly.pos.demoPos.utils.DemoLogger;
import com.linkly.pos.demoPos.utils.NumberUtils;
import com.linkly.pos.demoPos.utils.SessionData.Lane;
import com.linkly.pos.demoPos.utils.SessionData.Pad;
import com.linkly.pos.demoPos.utils.SessionData.SurchargeRates;
import com.linkly.pos.sdk.common.StringUtil;
import com.linkly.pos.sdk.models.ApiServiceEndpoint;
import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.PosApiServiceOptions;
import com.linkly.pos.sdk.models.PosVendorDetails;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantResponse;
import com.linkly.pos.sdk.models.display.DisplayResponse;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.logon.LogonResponse;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardResponse;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptResponse;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.settlement.SettlementResponse;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.status.StatusResponse;
import com.linkly.pos.sdk.models.transaction.CashRequest;
import com.linkly.pos.sdk.models.transaction.DepositRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthCancelRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthCompletionRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthExtendRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthInquiryRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthPartialCancelRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthSummaryRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthTopUpRequest;
import com.linkly.pos.sdk.models.transaction.PurchaseRequest;
import com.linkly.pos.sdk.models.transaction.RefundRequest;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TippingOptions;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionResponse;
import com.linkly.pos.sdk.models.transaction.VoidRequest;
import com.linkly.pos.sdk.models.transaction.surcharge.FixedSurcharge;
import com.linkly.pos.sdk.models.transaction.surcharge.PercentageSurcharge;
import com.linkly.pos.sdk.models.transaction.surcharge.SurchargeOptions;
import com.linkly.pos.sdk.models.transaction.surcharge.SurchargeRule;
import com.linkly.pos.sdk.service.IPosApiEventListener;
import com.linkly.pos.sdk.service.IPosApiService;
import com.linkly.pos.sdk.service.impl.PosApiService;

@Service
public class DemoPosApiServiceImpl implements DemoPosApiService, IPosApiEventListener {

    private List<SimplifiedResponse> asyncResponses = new ArrayList<>();
    private static final String RECEIPT_RESPONSE_KEY = "receipt";
    private static final String ERROR_RESPONSE_KEY = "ERROR";
    private static final String PAIRING_RESPONSE_KEY = "PAIRING";
    private static final String FIXED = "Fixed";
    private static final String PERCENTAGE = "Percentage";

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
        posVendorDetails = new PosVendorDetails(posName, posVersion, UUID.fromString(posId), UUID
            .fromString(posId));
        ApiServiceEndpoint serviceEndpoint = new ApiServiceEndpoint(authUri, posUri);
        service = new PosApiService(this, null, posVendorDetails, new PosApiServiceOptions(),
            serviceEndpoint, logger);

        dataManager = new DataManager(posVendorDetails.getPosId());
        String secret = dataManager.getCurrentLane().getSecret();
        if (!StringUtil.isNullOrWhiteSpace(secret)) {
            service.setPairSecret(secret);
        }
    }

    @Override
    public void pairingRequest(PairingRequest request) {
        service.pairingRequest(request);
    }

    @Override
    public UUID statusRequest(StatusRequest request) {
        UUID uuid = service.statusRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        return uuid;
    }

    @Override
    public UUID logonRequest(LogonRequest request) {
        UUID uuid = service.logonRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        if (request.getPurchaseAnalysisData().size() > 0) {
            dataManager.savePads(request.getPurchaseAnalysisData());
        }
        return uuid;
    }

    @Override
    public UUID settlementRequest(SettlementRequest request) {
        UUID uuid = service.settlementRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        if (request.getPurchaseAnalysisData().size() > 0) {
            dataManager.savePads(request.getPurchaseAnalysisData());
        }
        return uuid;
    }

    @Override
    public UUID queryCardRequest(QueryCardRequest request) {
        UUID uuid = service.queryCardRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        if (request.getPurchaseAnalysisData().size() > 0) {
            dataManager.savePads(request.getPurchaseAnalysisData());
        }
        return uuid;
    }

    @Override
    public UUID configureMerchantRequest(ConfigureMerchantRequest request) {
        UUID uuid = service.configureMerchantRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        return uuid;
    }

    @Override
    public UUID reprintReceiptRequest(ReprintReceiptRequest request) {
        UUID uuid = service.reprintReceiptRequest(request);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, request.getClass().getSimpleName(), request, null,
                null);
        }
        return uuid;
    }

    @Override
    public void sendKeyRequest(SendKeyRequest request) {
        service.sendKeyRequest(request);
    }

    @Override
    public void resultRequest(ResultRequest request) {
        service.resultRequest(request);
        dataManager.setLastSessionInterrupted(false);
        dataManager.setLastInterruptedSessionId(null);
    }

    @Override
    public void retrieveTransactionRequest(RetrieveTransactionRequest request) {
        service.retrieveTransactionRequest(request);
    }

    @Override
    public UUID transactionRequest(DemoPosTransactionRequest request) {

        var options = surchargeOptions(request);
        var transactionRequest = transactionRequestFactory(request);
        if (!isNullOrWhiteSpace(request.getPanSourceTemp())) {
            transactionRequest.setPanSource(PanSource.valueOf(request.getPanSourceTemp()));
        }
        if (!isNullOrWhiteSpace(request.getAccountTypeTemp())) {
            transactionRequest.setAccountType(AccountType.valueOf(request.getAccountTypeTemp()));
        }
        transactionRequest.setTxnRef(request.getTxnRef());
        transactionRequest.setCurrencyCode(request.getCurrencyCode());
        transactionRequest.setTrainingMode(request.isTrainingMode());
        transactionRequest.setAuthCode(request.getAuthCode());
        transactionRequest.setDateExpiry(request.getDateExpiry());
        transactionRequest.setProductLevelBlock(request.isPlb());
        transactionRequest.setRrn(request.getRrn());
        transactionRequest.setMerchant(request.getMerchant());
        transactionRequest.setReceiptAutoPrint(request.getReceiptAutoPrint());
        transactionRequest.setCutReceipt(request.isCutReceipt());
        transactionRequest.getPurchaseAnalysisData().putAll(request.getPurchaseAnalysisData());

        if (options.isNotEmpty()) {
            transactionRequest.setSurchargeOptions(options);
        }
        setTippingOptions(request, transactionRequest);
        updatePanSource(request, transactionRequest);

        UUID uuid = service.transactionRequest(transactionRequest);
        if (uuid != null) {
            dataManager.saveTransaction(uuid, transactionRequest.getClass().getSimpleName(),
                request, null, null);
        }
        if (request.getPurchaseAnalysisData().size() > 0) {
            dataManager.savePads(request.getPurchaseAnalysisData());
        }
        return uuid;
    }

    @Override
    public void receipt(UUID sessionId, PosApiRequest request, ReceiptResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(),
            RECEIPT_RESPONSE_KEY, response, sessionId.toString()));
    }

    @Override
    public void display(UUID sessionId, PosApiRequest request, DisplayResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), Display
            .name(), response, sessionId.toString()));
    }

    @Override
    public void error(UUID sessionId, IBaseRequest request, ErrorResponse error) {
        var uuid = sessionId != null ? sessionId.toString() : "";
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(),
            ERROR_RESPONSE_KEY, error, uuid));
        if (sessionId != null) {
            dataManager.saveTransaction(sessionId, null, null, null, error);
        }
    }

    @Override
    public void pairingComplete(PairingRequest request, PairingResponse response) {
        var lane = new Lane();
        lane.setAuthEndpoint(authUri);
        lane.setLastActive(true);
        lane.setPosEndpoint(posUri);
        lane.setUsername(request.getUsername());
        lane.setSecret(response.getSecret());
        dataManager.saveCurrent(lane);

        service.setPairSecret(response.getSecret());
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(),
            PAIRING_RESPONSE_KEY, response, null));
    }

    @Override
    public void transactionComplete(UUID sessionId, TransactionRequest request,
        TransactionResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), Transaction
            .name(), response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void statusComplete(UUID sessionId, StatusRequest request, StatusResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), Status.name(),
            response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void logonComplete(UUID sessionId, LogonRequest request, LogonResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), Logon.name(),
            response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void settlementComplete(UUID sessionId, SettlementRequest request,
        SettlementResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), Settlement
            .name(), response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void queryCardComplete(UUID sessionId, QueryCardRequest request,
        QueryCardResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), QueryCard
            .name(), response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void configureMerchantComplete(UUID sessionId, ConfigureMerchantRequest request,
        ConfigureMerchantResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(),
            ConfigureMerchant.name(), response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void reprintReceiptComplete(UUID sessionId, ReprintReceiptRequest request,
        ReprintReceiptResponse response) {
        asyncResponses.add(new SimplifiedResponse(request.getClass().getSimpleName(), ReprintReceipt
            .name(), response, sessionId.toString()));
        dataManager.saveTransaction(sessionId, request.getClass().getSimpleName(), request,
            response, null);
    }

    @Override
    public void resultComplete(ResultRequest request, List<PosApiResponse> responses) {
        responses.forEach(r -> {
            dataManager.saveTransaction(request.getSessionId(), null, null, r, null);
            asyncResponses
                .add(new SimplifiedResponse(request.getClass().getSimpleName(), r.getResponseType()
                    .name(), r, request.getSessionId().toString()));
        });
    }

    @Override
    public void retrieveTransactionComplete(RetrieveTransactionRequest request,
        List<TransactionResponse> responses) {
        // No implementation
    }

    @Override
    public void refreshResponses() {
        asyncResponses.clear();
    }

    @Override
    public List<SimplifiedResponse> getResponses() {
        boolean refreshResponse = false;
        for (SimplifiedResponse response : asyncResponses) {
            var responseType = response.getResponseType();
            if (ERROR_RESPONSE_KEY.equals(responseType) || PAIRING_RESPONSE_KEY.equals(
                responseType)) {
                refreshResponse = true;
            }
            else if (!RECEIPT_RESPONSE_KEY.equals(responseType)) {
                List<String> completedResponseTypes = Arrays.asList(Logon.name(), Status.name(),
                    Display.name(),
                    ConfigureMerchant.name(), QueryCard.name(), ReprintReceipt.name(), Transaction
                        .name(), Settlement.name());
                if (completedResponseTypes.contains(responseType)) {
                    refreshResponse = true;
                }
            }
        }
        var responses = new ArrayList<>(asyncResponses);
        if (refreshResponse) {
            refreshResponses();
        }
        return responses;
    }

    @Override
    public List<String> users() {
        return dataManager.getLanes().stream().map(l -> l.getUsername()).filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public String[] endpoints() {
        return new String[] { authUri, posUri };
    }

    public PosVendorDetails getPosVendorDetails() {
        return posVendorDetails;
    }

    @Override
    public List<String> sessions() {
        var transactions = dataManager.getCurrentLane().getTrasactions();
        if (!CollectionUtils.isEmpty(transactions)) {
            return transactions.stream().map(t -> t.getSessionId().toString())
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public String lastTransactionInterrupted() {
        if (dataManager.isLastSessionInterrupted()) {
            return dataManager.getLastInterruptedSessionId().toString();
        }
        return null;
    }

    @Override
    public String currentUser() {
        String currentUsername = dataManager.getCurrentLane().getUsername();
        if (StringUtil.isNullOrWhiteSpace(currentUsername)) {
            return "";
        }
        return currentUsername;
    }

    private SurchargeOptions surchargeOptions(DemoPosTransactionRequest request) {
        List<SurchargeRates> rates = new ArrayList<>();
        var options = new SurchargeOptions();
        boolean isBinAndValueNotEmpty1 = !isNullOrWhiteSpace(request.getSurchargeBin1())
            && !isNullOrWhiteSpace(request.getSurchargeValue1());
        boolean isBinAndValueNotEmpty2 = !isNullOrWhiteSpace(request.getSurchargeBin2())
            && !isNullOrWhiteSpace(request.getSurchargeValue2());

        if (FIXED.equals(request.getSurchargeType1()) && isBinAndValueNotEmpty1) {
            SurchargeRule rule = new FixedSurcharge(request.getSurchargeBin1(),
                NumberUtils.toInt(request.getSurchargeValue1()));
            options.add(rule);
            rates.add(new SurchargeRates(request.getSurchargeType1(), request.getSurchargeBin1(),
                request.getSurchargeValue1()));
        }
        if (PERCENTAGE.equals(request.getSurchargeType1()) && isBinAndValueNotEmpty1) {
            SurchargeRule rule = new PercentageSurcharge(request.getSurchargeBin1(),
                NumberUtils.toInt(request.getSurchargeValue1()));
            options.add(rule);
            rates.add(new SurchargeRates(request.getSurchargeType1(), request.getSurchargeBin1(),
                request.getSurchargeValue1()));
        }
        if (FIXED.equals(request.getSurchargeType2()) && isBinAndValueNotEmpty2) {
            SurchargeRule rule = new FixedSurcharge(request.getSurchargeBin2(),
                NumberUtils.toInt(request.getSurchargeValue2()));
            options.add(rule);
            rates.add(new SurchargeRates(request.getSurchargeType2(), request.getSurchargeBin2(),
                request.getSurchargeValue2()));
        }
        if (PERCENTAGE.equals(request.getSurchargeType2()) && isBinAndValueNotEmpty2) {
            SurchargeRule rule = new PercentageSurcharge(request.getSurchargeBin2(),
                NumberUtils.toInt(request.getSurchargeValue2()));
            options.add(rule);
            rates.add(new SurchargeRates(request.getSurchargeType2(), request.getSurchargeBin2(),
                request.getSurchargeValue2()));
        }
        dataManager.saveSurcharges(rates);
        return options;
    }

    private TransactionRequest createPreAuthInquiryRequest(DemoPosTransactionRequest request) {
        if (isNullOrWhiteSpace(request.getRfn())) {
            PreAuthSummaryRequest psr = new PreAuthSummaryRequest();
            Integer pai = request.getPai() == null ? 0 : Integer.parseInt(request.getPai());
            if (pai > 0) {
                psr.setPreAuthIndex(pai);
            }
            return psr;
        }
        else {
            PreAuthInquiryRequest pair = new PreAuthInquiryRequest(request.getRfn());
            pair.setRfn(request.getRfn());
            return pair;
        }
    }

    private void updatePanSource(DemoPosTransactionRequest request,
        TransactionRequest transactionRequest) {
        if (!isNullOrWhiteSpace(request.getPanSourceTemp())) {
            PanSource panSource = PanSource.valueOf(request.getPanSourceTemp());
            if (PanSource.PinPad != panSource) {
                if (isNullOrWhiteSpace(request.getAccountTypeTemp())) {
                    transactionRequest.setAccountType(AccountType.valueOf(request
                        .getAccountTypeTemp()));
                }
                if (PanSource.PosSwiped != panSource) {
                    transactionRequest.setPan(request.getPan());
                }
                else {
                    transactionRequest.setTrack2(request.getTrack2());
                }
            }
        }
    }

    private void setTippingOptions(DemoPosTransactionRequest request,
        TransactionRequest transactionRequest) {
        if (!"none".equalsIgnoreCase(request.getTipOption())) {
            transactionRequest.setEnableTip(true);
        }
        if ("tipfixed".equalsIgnoreCase(request.getTipOption())) {
            int fixedAmount = NumberUtils.toInt(request.getFixedOptionAmount());
            transactionRequest.setTipAmount(fixedAmount);
        }
        else if ("tipoptions".equalsIgnoreCase(request.getTipOption())) {
            Integer o1 = isNullOrWhiteSpace(request.getTipOptions1()) ? 0
                : Integer.valueOf(request.getTipOptions1());
            Integer o2 = isNullOrWhiteSpace(request.getTipOptions2()) ? 0
                : Integer.valueOf(request.getTipOptions2());
            Integer o3 = isNullOrWhiteSpace(request.getTipOptions3()) ? 0
                : Integer.valueOf(request.getTipOptions3());
            transactionRequest.setTipOptions(new TippingOptions(new byte[] { o1.byteValue(), o2
                .byteValue(), o3.byteValue() }));
        }
    }

    private TransactionRequest transactionRequestFactory(DemoPosTransactionRequest request) {
        int amount = NumberUtils.toInt(request.getAmount());
        int amountCash = NumberUtils.toInt(request.getAmountCash());
        int totalCheque = isNullOrWhiteSpace(request.getTotalCheques())
            ? 0
            : Integer.parseInt(request.getTotalCheques());
        switch (request.getTxnType()) {
            case Purchase:
                return new PurchaseRequest(amount, amountCash);
            case Cash:
                return new CashRequest(amountCash);
            case Refund:
                return new RefundRequest(amount, request.getRfn());
            case Deposit:
                return new DepositRequest(amountCash, amount, totalCheque);
            case PreAuth:
                return new PreAuthRequest(amount);
            case PreAuthExtend:
                return new PreAuthExtendRequest(request.getRfn());
            case PreAuthTopUp:
                return new PreAuthTopUpRequest(amount, request.getRfn());
            case PreAuthCancel:
                return new PreAuthCancelRequest(request.getRfn());
            case PreAuthPartialCancel:
                return new PreAuthPartialCancelRequest(amount, request.getRfn());
            case PreAuthComplete:
                return new PreAuthCompletionRequest(amount, request.getRfn());
            case PreAuthInquiry:
                return createPreAuthInquiryRequest(request);
            case Void:
                return new VoidRequest(amount);
            default:
                throw new IllegalArgumentException("Transaction type invalid");
        }
    }

    @Override
    public List<Pad> purchaseAnalysisData() {
        var pads = dataManager.getSessions().getPads();
        var limit = 4 - pads.size();
        for (int ctr = 0; ctr < limit; ctr++) {
            pads.add(new Pad(false, "", ""));
        }
        return pads;
    }

    @Override
    public List<SurchargeRates> surchargeRates() {
        var percentageKey = "Percentage";
        var fixedKey = "Fixed";
        var rates = dataManager.getSessions().getSurchargeRates();
        if (rates.size() == 1) {
            String key = rates.get(0).getType().equalsIgnoreCase(fixedKey) ? fixedKey
                : percentageKey;
            rates.add(new SurchargeRates(key, "", ""));
        }
        if (rates.size() == 0) {
            rates.add(new SurchargeRates(percentageKey, "", ""));
            rates.add(new SurchargeRates(fixedKey, "", ""));
        }

        return dataManager.getSessions().getSurchargeRates();
    }

    @Override
    public List<String> changeLane(String username) {
        dataManager.changeLane(username);
        String secret = dataManager.getCurrentLane().getSecret();
        service.setPairSecret(secret);
        return this.sessions();
    }

    @Override
    public List<String> getLogs() {
        var demoLogger = (DemoLogger) logger;
        return demoLogger.getLogs();
    }
}