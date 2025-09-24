package com.usps.evs.vo;

import java.math.BigDecimal;
import java.util.Vector;

public class PaymentExtractDetailVO {
	private String recordType = "D1";	
	private String picCode;
	private String classOfMail;
	private String processingCategory;
	private double weight;
	private double dimensionalWeight;
	private double length;
	private double width;
	private double height;
	private String destinationZip;
	private String destinationZipPlus4;
	private String destinationRateIndicator;
	private String rateIndicator;
	private String zone;
	private String routingBarcode;
	private String discountType;
	private double discountAmount;
	private String surchargeType;
	private BigDecimal surchargeAmount = new BigDecimal(0.00);
	private String customerInternalReference;
	private String priceType;
	private BigDecimal postage = new BigDecimal(0.00);
	private BigDecimal calculatedTotalPostage = new BigDecimal(0.00);
	private String paymentStatus;
	private String capsTransactionID;
	private Vector servicesList = new Vector();	
	private String tpbFlag;
	//SRS 228 Payment Extract - International - Version 2.0
	private String customerInternalReference2;
	private String countryCode;
	private String postalCode;
	
	public String getPicCode() {
		return picCode;
	}
	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}
	public String getClassOfMail() {
		return classOfMail;
	}
	public void setClassOfMail(String classOfMail) {
		this.classOfMail = classOfMail;
	}
	public String getProcessingCategory() {
		return processingCategory;
	}
	public void setProcessingCategory(String processingCategory) {
		this.processingCategory = processingCategory;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getDimensionalWeight() {
		return dimensionalWeight;
	}
	public void setDimensionalWeight(double dimensionalWeight) {
		this.dimensionalWeight = dimensionalWeight;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getDestinationZip() {
		return destinationZip;
	}
	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}
	public String getDestinationZipPlus4() {
		return destinationZipPlus4;
	}
	public void setDestinationZipPlus4(String destinationZipPlus4) {
		this.destinationZipPlus4 = destinationZipPlus4;
	}
	public String getDestinationRateIndicator() {
		return destinationRateIndicator;
	}
	public void setDestinationRateIndicator(String destinationRateIndicator) {
		this.destinationRateIndicator = destinationRateIndicator;
	}
	public String getRateIndicator() {
		return rateIndicator;
	}
	public void setRateIndicator(String rateIndicator) {
		this.rateIndicator = rateIndicator;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRoutingBarcode() {
		return routingBarcode;
	}
	public void setRoutingBarcode(String routingBarcode) {
		this.routingBarcode = routingBarcode;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getSurchargeType() {
		return surchargeType;
	}
	public void setSurchargeType(String surchargeType) {
		this.surchargeType = surchargeType;
	}
	public BigDecimal getSurchargeAmount() {
		return surchargeAmount;
	}
	public void setSurchargeAmount(BigDecimal surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}
	public String getCustomerInternalReference() {
		return customerInternalReference;
	}
	public void setCustomerInternalReference(String customerInternalReference) {
		this.customerInternalReference = customerInternalReference;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public BigDecimal getCalculatedTotalPostage() {
		return calculatedTotalPostage;
	}
	public void setCalculatedTotalPostage(BigDecimal calculatedTotalPostage) {
		this.calculatedTotalPostage = calculatedTotalPostage;
	}
	public Vector getServicesList() {
		return servicesList;
	}
	public void setServicesList(Vector servicesList) {
		this.servicesList = servicesList;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCapsTransactionID() {
		return capsTransactionID;
	}
	public void setCapsTransactionID(String capsTransactionID) {
		this.capsTransactionID = capsTransactionID;
	}
	/**
	 * @return the tpbFlag
	 */
	public String getTpbFlag() {
		return tpbFlag;
	}
	/**
	 * @param tpbFlag the tpbFlag to set
	 */
	public void setTpbFlag(String tpbFlag) {
		this.tpbFlag = tpbFlag;
	}
  //SRS 228 Payment Extract - International - Version 2.0
	public String getCustomerInternalReference2() {
		return customerInternalReference2;
	}
	public void setCustomerInternalReference2(String customerInternalReference2) {
		this.customerInternalReference2 = customerInternalReference2;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
