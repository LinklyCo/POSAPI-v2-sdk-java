package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ReferenceType;
import com.linkly.pos.sdk.models.enums.ResponseType;
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
import com.linkly.pos.sdk.models.transaction.TransactionResponse;
import com.linkly.pos.sdk.models.transaction.VoidRequest;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;

public class TransactionMock {

    private static final JsonAdapter<CashRequest> cashRequestAdapter = MoshiUtil.getAdapter(
        CashRequest.class);

    public static CashRequest cashRequest() {
        CashRequest request = new CashRequest(100);
        request.setTxnRef("123456");
        return request;
    }

    public static final String cashRequestContent(CashRequest request) {
        return cashRequestAdapter.toJson(request);
    }

    public static DepositRequest depositRequest() {
        DepositRequest request = new DepositRequest(100, 0, 0);
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthCancelRequest preAuthCancelRequest() {
        PreAuthCancelRequest request = new PreAuthCancelRequest("test rfn");
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthCompletionRequest preAuthCompletionRequest() {
        PreAuthCompletionRequest request = new PreAuthCompletionRequest(1, "test rfn");
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthExtendRequest preAuthExtendRequest() {
        PreAuthExtendRequest request = new PreAuthExtendRequest("test rfn");
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthInquiryRequest preAuthInquiryRequest() {
        PreAuthInquiryRequest request = new PreAuthInquiryRequest("rfn");
        request.setTxnRef("123456");
        request.setRfn("test rfn");
        return request;
    }

    public static PreAuthPartialCancelRequest preAuthPartialCancelRequest() {
        PreAuthPartialCancelRequest request = new PreAuthPartialCancelRequest(1, "test rfn");
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthRequest preAuthRequest() {
        PreAuthRequest request = new PreAuthRequest(1);
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthTopUpRequest preAuthTopUpRequest() {
        PreAuthTopUpRequest request = new PreAuthTopUpRequest(1, "test rfn");
        request.setTxnRef("123456");
        return request;
    }

    public static PurchaseRequest purchaseRequest() {
        PurchaseRequest request = new PurchaseRequest(1, 0);
        request.setTxnRef("123456");
        return request;
    }

    public static RefundRequest refundRequest() {
        RefundRequest request = new RefundRequest(1, "rfn");
        request.setTxnRef("123456");
        request.setRfn("test rfn");
        return request;
    }

    public static VoidRequest voidRequest() {
        VoidRequest request = new VoidRequest(1);
        request.setTxnRef("123456");
        return request;
    }

    public static PreAuthSummaryRequest preAuthSummaryRequest() {
        PreAuthSummaryRequest request = new PreAuthSummaryRequest();
        request.setTxnRef("123456");
        return request;
    }

    public static RetrieveTransactionRequest retrieveTransactionRequest() {
        RetrieveTransactionRequest request = new RetrieveTransactionRequest();
        request.setReference("123457");
        request.setReferenceType(ReferenceType.ReferenceNo);
        return request;
    }

    public static String transactionResponseContent() {
        TransactionResponse response = new TransactionResponse();
        response.setResponseType(ResponseType.Transaction);
        response.setResponseText("Transaction Done");

        List<TransactionResponse> responses = Arrays.asList(response);
        Type type = Types.newParameterizedType(List.class, TransactionResponse.class);

        return MoshiUtil.getAdapter(type).toJson(responses);
    }
}
