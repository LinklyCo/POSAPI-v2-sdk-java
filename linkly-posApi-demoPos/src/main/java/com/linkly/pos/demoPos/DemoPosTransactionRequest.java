package com.linkly.pos.demoPos;

import com.linkly.pos.sdk.models.transaction.TransactionRequest;

public class DemoPosTransactionRequest extends TransactionRequest {

	private String tipOption;
	private String tipOptions1;
	private String tipOptions2;
	private String tipOptions3;
	private String fixedOptionAmount;
	
	private String surchargeType1;
	private String surchargeBin1;
	private String surchargeValue1;
	private String surchargeType2;
	private String surchargeBin2;
	private String surchargeValue2;
	
	private String pai;
	private String rfn;
	private String amount;
	private String amountCash;
	private String totalCheques;
	private boolean plb;
	private String panSourceTemp;
	private String accountTypeTemp;
		
	public String getTotalCheques() {
		return totalCheques;
	}
	public void setTotalCheques(String totalCheques) {
		this.totalCheques = totalCheques;
	}
	public String getAccountTypeTemp() {
		return accountTypeTemp;
	}
	public void setAccountTypeTemp(String accountTypeTemp) {
		this.accountTypeTemp = accountTypeTemp;
	}
	public String getPanSourceTemp() {
		return panSourceTemp;
	}
	public void setPanSourceTemp(String panSourceTemp) {
		this.panSourceTemp = panSourceTemp;
	}
	public String getTipOption() {
		return tipOption;
	}
	public void setTipOption(String tipOption) {
		this.tipOption = tipOption;
	}
	public String getTipOptions1() {
		return tipOptions1;
	}
	public void setTipOptions1(String tipOptions1) {
		this.tipOptions1 = tipOptions1;
	}
	public String getTipOptions2() {
		return tipOptions2;
	}
	public void setTipOptions2(String tipOptions2) {
		this.tipOptions2 = tipOptions2;
	}
	public String getTipOptions3() {
		return tipOptions3;
	}
	public void setTipOptions3(String tipOptions3) {
		this.tipOptions3 = tipOptions3;
	}
	public String getFixedOptionAmount() {
		return fixedOptionAmount;
	}
	public void setFixedOptionAmount(String fixedOptionAmount) {
		this.fixedOptionAmount = fixedOptionAmount;
	}
	public String getSurchargeType1() {
		return surchargeType1;
	}
	public void setSurchargeType1(String surchargeType1) {
		this.surchargeType1 = surchargeType1;
	}
	public String getSurchargeBin1() {
		return surchargeBin1;
	}
	public void setSurchargeBin1(String surchargeBin1) {
		this.surchargeBin1 = surchargeBin1;
	}
	public String getSurchargeValue1() {
		return surchargeValue1;
	}
	public void setSurchargeValue1(String surchargeValue1) {
		this.surchargeValue1 = surchargeValue1;
	}
	public String getSurchargeType2() {
		return surchargeType2;
	}
	public void setSurchargeType2(String surchargeType2) {
		this.surchargeType2 = surchargeType2;
	}
	public String getSurchargeBin2() {
		return surchargeBin2;
	}
	public void setSurchargeBin2(String surchargeBin2) {
		this.surchargeBin2 = surchargeBin2;
	}
	public String getSurchargeValue2() {
		return surchargeValue2;
	}
	public void setSurchargeValue2(String surchargeValue2) {
		this.surchargeValue2 = surchargeValue2;
	}
	public String getPai() {
		return pai;
	}
	public void setPai(String pai) {
		this.pai = pai;
	}
	public String getRfn() {
		return rfn;
	}
	public void setRfn(String rfn) {
		this.rfn = rfn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountCash() {
		return amountCash;
	}
	public void setAmountCash(String amountCash) {
		this.amountCash = amountCash;
	}
	public boolean isPlb() {
		return plb;
	}
	public void setPlb(boolean plb) {
		this.plb = plb;
	}
	

}
