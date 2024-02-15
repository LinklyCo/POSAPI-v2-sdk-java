package com.linkly.pos.sdk.service.impl;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantResponse;
import com.linkly.pos.sdk.models.display.DisplayResponse;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.logon.LogonResponse;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardResponse;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptResponse;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.settlement.SettlementResponse;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.status.StatusResponse;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionResponse;
import com.linkly.pos.sdk.service.IPosApiEventListener;
import com.squareup.moshi.Types;

public class MockEventListener implements IPosApiEventListener {

    private final List<String> responseContents = new ArrayList<>();
    
    public String getResponseContent(String responseType, int secondsDelay) {
    	Instant start = Instant.now();
        long timeElapsed = Duration.between(start, start).toSeconds();
        while (timeElapsed < secondsDelay) {
        	List<String> synchedResponseContents = Collections.synchronizedList(
        			new ArrayList<>(responseContents));
    		for (String content : synchedResponseContents) {
                if (content.contains(responseType)) {
                    return content;
                }
            }
            timeElapsed = Duration.between(start, Instant.now()).toSeconds();
        }
        return String.join(", ", responseContents);
    }

    public List<String> getResponseContents(int expectedNumberOfResponse, int seconds) {
    	Instant start = Instant.now();
        long timeElapsed = Duration.between(start, start).toSeconds();

        while (timeElapsed < seconds) {
        	if(responseContents.size() == expectedNumberOfResponse) {
        		break;
        	}
            timeElapsed = Duration.between(start, Instant.now()).toSeconds();
        }
        return responseContents;
    }

    @Override
    public void receipt(UUID sessionId, PosApiRequest request, ReceiptResponse response) {
        responseContents.add(MoshiUtil.getAdapter(ReceiptResponse.class).toJson(response));
    }

    @Override
    public void display(UUID sessionId, PosApiRequest request, DisplayResponse response) {
        responseContents.add(MoshiUtil.getAdapter(DisplayResponse.class).toJson(response));
    }

    @Override
    public void error(UUID sessionId, IBaseRequest request, ErrorResponse error) {
        responseContents.add(MoshiUtil.getAdapter(ErrorResponse.class).toJson(error));
    }

    @Override
    public void pairingComplete(PairingRequest request, PairingResponse response) {
        responseContents.add(MoshiUtil.getAdapter(PairingResponse.class)
            .toJson(response));
    }

    @Override
    public void transactionComplete(UUID sessionId, TransactionRequest request,
        TransactionResponse response) {
        responseContents.add(MoshiUtil.getAdapter(TransactionResponse.class).toJson(response));
    }

    @Override
    public void statusComplete(UUID sessionId, StatusRequest request, StatusResponse response) {
        responseContents.add(MoshiUtil.getAdapter(StatusResponse.class).toJson(response));
    }

    @Override
    public void logonComplete(UUID sessionId, LogonRequest request, LogonResponse response) {
        responseContents.add(MoshiUtil.getAdapter(LogonResponse.class).toJson(response));
    }

    @Override
    public void settlementComplete(UUID sessionId, SettlementRequest request,
        SettlementResponse response) {
        responseContents.add(MoshiUtil.getAdapter(SettlementResponse.class).toJson(response));
    }

    @Override
    public void queryCardComplete(UUID sessionId, QueryCardRequest request,
        QueryCardResponse response) {
        responseContents.add(MoshiUtil.getAdapter(QueryCardResponse.class).toJson(response));
    }

    @Override
    public void configureMerchantComplete(UUID sessionId, ConfigureMerchantRequest request,
        ConfigureMerchantResponse response) {
        responseContents.add(MoshiUtil.getAdapter(ConfigureMerchantResponse.class).toJson(
            response));
    }

    @Override
    public void reprintReceiptComplete(UUID sessionId, ReprintReceiptRequest request,
        ReprintReceiptResponse response) {
        responseContents.add(MoshiUtil.getAdapter(ReprintReceiptResponse.class).toJson(response));

    }

    @Override
    public void resultComplete(ResultRequest request, List<PosApiResponse> responses) {
        Type type = Types.newParameterizedType(List.class, PosApiResponse.class);
        responseContents.add(MoshiUtil.getAdapter(type).toJson(responses));
    }

    @Override
    public void retrieveTransactionComplete(RetrieveTransactionRequest request,
        List<TransactionResponse> responses) {
        responseContents.add("No Implementation Yet!");
    }

    public void clear() {
        responseContents.clear();
    }
}