package com.usps.evs.vo;

import java.util.ArrayList;
import java.util.Map;

public class PaymentExtractVO {

	private PaymentExtractHeaderVO paymentExtractHeader;
	private ArrayList paymentExtractDetails;
	
	public PaymentExtractHeaderVO getPaymentExtractHeader() {
		return paymentExtractHeader;
	}
	public void setPaymentExtractHeader(PaymentExtractHeaderVO paymentExtractHeader) {
		this.paymentExtractHeader = paymentExtractHeader;
	}
	public ArrayList getPaymentExtractDetails() {
		return paymentExtractDetails;
	}
	public void setPaymentExtractDetails(ArrayList paymentExtractDetails) {
		this.paymentExtractDetails = paymentExtractDetails;
	}
}
