package com.linkly.pos.demoPos.service;

import java.util.List;
import java.util.UUID;

import com.linkly.pos.demoPos.DemoPosTransactionRequest;
import com.linkly.pos.demoPos.SimplifiedResponse;
import com.linkly.pos.demoPos.utils.SessionData.Pad;
import com.linkly.pos.demoPos.utils.SessionData.SurchargeRates;
import com.linkly.pos.sdk.models.PosVendorDetails;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;

public interface DemoPosApiService {

    void pairingRequest(PairingRequest request);

    UUID transactionRequest(DemoPosTransactionRequest request);

    UUID statusRequest(StatusRequest request);

    UUID logonRequest(LogonRequest request);

    UUID settlementRequest(SettlementRequest request);

    UUID queryCardRequest(QueryCardRequest request);

    UUID configureMerchantRequest(ConfigureMerchantRequest request);

    UUID reprintReceiptRequest(ReprintReceiptRequest request);

    void sendKeyRequest(SendKeyRequest request);

    void resultRequest(ResultRequest request);

    void retrieveTransactionRequest(RetrieveTransactionRequest request);

    List<SimplifiedResponse> getResponses();

    void refreshResponses();

    List<String> users();

    String[] endpoints();

    PosVendorDetails getPosVendorDetails();

    List<String> sessions();

    String lastTransactionInterrupted();

    String currentUser();

    List<Pad> purchaseAnalysisData();

    List<SurchargeRates> surchargeRates();

    List<String> changeLane(String username);

    List<String> getLogs();

    List<String> rfnList();
}
