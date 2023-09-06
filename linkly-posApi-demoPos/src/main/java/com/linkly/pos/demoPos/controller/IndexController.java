package com.linkly.pos.demoPos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linkly.pos.demoPos.DemoPosTransactionRequest;
import com.linkly.pos.demoPos.SimplifiedResponse;
import com.linkly.pos.demoPos.service.DemoPosApiService;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.LogonType;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.QueryCardType;
import com.linkly.pos.sdk.models.enums.ReceiptAutoPrint;
import com.linkly.pos.sdk.models.enums.SettlementType;
import com.linkly.pos.sdk.models.enums.StatusType;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;

@Controller("/")
@RequestMapping
public class IndexController {

    @Autowired
    private DemoPosApiService posService;

    @GetMapping
    public ModelAndView index(Model model) {
        Map<String, Object> predefinedValue = new HashMap<>();
        predefinedValue.put("txnTypes", TxnType.values());
        predefinedValue.put("panSources", PanSource.values());
        predefinedValue.put("accountTypes", AccountType.values());
        predefinedValue.put("logonTypes", LogonType.values());
        predefinedValue.put("settlementTypes", SettlementType.values());
        predefinedValue.put("queryCardTypes", QueryCardType.values());
        predefinedValue.put("statusTypes", StatusType.values());
        predefinedValue.put("receiptAutoPrints", ReceiptAutoPrint.values());
        predefinedValue.put("users", posService.users());
        predefinedValue.put("sessions", posService.sessions());
        predefinedValue.put("endpoints", posService.endpoints());
        predefinedValue.put("posVersion", posService.getPosVendorDetails().getPosVersion());
        predefinedValue.put("lastTransactionInterrupted", posService.lastTransactionInterrupted());
        predefinedValue.put("currentUser", posService.currentUser());
        predefinedValue.put("pads", posService.purchaseAnalysisData());
        predefinedValue.put("surchargeRates", posService.surchargeRates());

        return new ModelAndView("index", predefinedValue);
    }

    @PostMapping("/pairingRequest")
    public @ResponseBody String pairingRequest(@RequestBody PairingRequest request) {
        posService.pairingRequest(request);
        return "";
    }

    @PostMapping("/transactionRequest")
    public @ResponseBody UUID transactionRequest(@RequestBody DemoPosTransactionRequest request) {
        return posService.transactionRequest(request);
    }

    @PostMapping("/status")
    public @ResponseBody UUID statusRequest(@RequestBody StatusRequest request) {
        return posService.statusRequest(request);
    }

    @PostMapping("/logon")
    public @ResponseBody UUID logonRequest(@RequestBody LogonRequest request) {
        return posService.logonRequest(request);
    }

    @PostMapping("/settlement")
    public @ResponseBody UUID settlementRequest(@RequestBody SettlementRequest request) {
        return posService.settlementRequest(request);
    }

    @PostMapping("/queryCard")
    public @ResponseBody UUID queryCardRequest(@RequestBody QueryCardRequest request) {
        return posService.queryCardRequest(request);
    }

    @PostMapping("/configureMerchant")
    public @ResponseBody UUID configureMerchantRequest(
        @RequestBody ConfigureMerchantRequest request) {
        return posService.configureMerchantRequest(request);
    }

    @PostMapping("/reprintReceipt")
    public @ResponseBody UUID reprintReceiptRequest(@RequestBody ReprintReceiptRequest request) {
        return posService.reprintReceiptRequest(request);
    }

    @PostMapping("/sendKey")
    public @ResponseBody String sendKeyRequest(@RequestBody SendKeyRequest request) {
        posService.sendKeyRequest(request);
        return "";
    }

    @PostMapping("/result")
    public @ResponseBody String resultRequest(@RequestBody ResultRequest request) {
        posService.resultRequest(request);
        return "";
    }

    public @ResponseBody String retrieveTransactionRequest(
        @RequestBody RetrieveTransactionRequest request) {
        posService.retrieveTransactionRequest(request);
        return "";
    }

    @GetMapping("/responses")
    public @ResponseBody List<SimplifiedResponse> apiResponses() {
        return posService.getResponses();
    }

    @PutMapping("/change-lane/{username}")
    public @ResponseBody List<String> changeLane(@PathVariable String username) {
        return posService.changeLane(username);
    }

    @GetMapping("/logs")
    public @ResponseBody List<String> getLogs() {
        return posService.getLogs();
    }
}
