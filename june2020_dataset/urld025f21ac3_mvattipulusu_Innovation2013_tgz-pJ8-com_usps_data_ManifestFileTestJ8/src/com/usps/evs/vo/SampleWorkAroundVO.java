package com.usps.evs.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class SampleWorkAroundVO {
	
	private Date activityDate;
	private Date startDate;
	private Date endDate;
	private String mailClass;
	private String processingCategory;
	private String destRateIndicator;
	private String rateIndicator;
	private String zone;
	private String routingBarcode;
	private double minWeight;
	private double maxWeight;
	private String convertMailClass;
	private String convertProcessingCategory;
	private String convertDestRateIndicator;
	private String convertRateIndicator;
	private String convertZone;
	private String convertRoutingBarcode;
	private String convertDiscountSurcharge;
	private double convertMinWeight;
	private double convertMaxWeight;
	private String serviceTypeCodeReqd;
	private String extraServiceCodereqd;
	private String discountCodeReqd;
	private String surchargeCodereqd;
	private String prohibitedSTC;
	private String prohibitedExtraServiceCode;
	private String prohibitedDiscountCode;
	private String prohibitedSurchargeCode;

	private Date exstErrProcessdate;
	private int  evsConvertRateMapSeqno;//EVS_CONV_RATE_MAP_SEQ_NO
	
	public int getEvsConvertRateMapSeqno() {
		return evsConvertRateMapSeqno;
	}

	public void setEvsConvertRateMapSeqno(int evsConvertRateMapSeqno) {
		this.evsConvertRateMapSeqno = evsConvertRateMapSeqno;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getProcessingCategory() {
		return processingCategory;
	}

	public void setProcessingCategory(String processingCategory) {
		this.processingCategory = processingCategory;
	}

	public String getDestRateIndicator() {
		return destRateIndicator;
	}

	public void setDestRateIndicator(String destRateIndicator) {
		this.destRateIndicator = destRateIndicator;
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


	public double getMinWeight() {
		return minWeight;
	}


	public void setMinWeight(double minWeight) {
		this.minWeight = minWeight;
	}


	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public String getConvertMailClass() {
		return convertMailClass;
	}

	public void setConvertMailClass(String convertMailClass) {
		this.convertMailClass = convertMailClass;
	}

	public String getConvertProcessingCategory() {
		return convertProcessingCategory;
	}

	public void setConvertProcessingCategory(String convertProcessingCategory) {
		this.convertProcessingCategory = convertProcessingCategory;
	}

	public String getConvertDestRateIndicator() {
		return convertDestRateIndicator;
	}

	public void setConvertDestRateIndicator(String convertDestRateIndicator) {
		this.convertDestRateIndicator = convertDestRateIndicator;
	}

	public String getConvertRateIndicator() {
		return convertRateIndicator;
	}

	public void setConvertRateIndicator(String convertRateIndicator) {
		this.convertRateIndicator = convertRateIndicator;
	}

	public String getConvertZone() {
		return convertZone;
	}

	public void setConvertZone(String convertZone) {
		this.convertZone = convertZone;
	}

	public String getConvertRoutingBarcode() {
		return convertRoutingBarcode;
	}

	public void setConvertRoutingBarcode(String convertRoutingBarcode) {
		this.convertRoutingBarcode = convertRoutingBarcode;
	}

	public double getConvertMinWeight() {
		return convertMinWeight;
	}

	public void setConvertMinWeight(double convertMinWeight) {
		this.convertMinWeight = convertMinWeight;
	}

	public double getConvertMaxWeight() {
		return convertMaxWeight;
	}

	public void setConvertMaxWeight(double convertMaxWeight) {
		this.convertMaxWeight = convertMaxWeight;
	}

	public String getServiceTypeCodeReqd() {
		return serviceTypeCodeReqd;
	}

	public void setServiceTypeCodeReqd(String serviceTypeCodeReqd) {
		this.serviceTypeCodeReqd = serviceTypeCodeReqd;
	}

	public String getExtraServiceCodereqd() {
		return extraServiceCodereqd;
	}

	public void setExtraServiceCodereqd(String extraServiceCodereqd) {
		this.extraServiceCodereqd = extraServiceCodereqd;
	}

	public String getProhibitedSTC() {
		return prohibitedSTC;
	}

	public void setProhibitedSTC(String prohibitedSTC) {
		this.prohibitedSTC = prohibitedSTC;
	}

	public String getProhibitedExtraServiceCode() {
		return prohibitedExtraServiceCode;
	}

	public void setProhibitedExtraServiceCode(String prohibitedExtraServiceCode) {
		this.prohibitedExtraServiceCode = prohibitedExtraServiceCode;
	}

	public String getProhibitedDiscountCode() {
		return prohibitedDiscountCode;
	}

	public void setProhibitedDiscountCode(String prohibitedDiscountCode) {
		this.prohibitedDiscountCode = prohibitedDiscountCode;
	}

	public String getProhibitedSurchargeCode() {
		return prohibitedSurchargeCode;
	}

	public void setProhibitedSurchargeCode(String prohibitedSurchargeCode) {
		this.prohibitedSurchargeCode = prohibitedSurchargeCode;
	}

	public String getDiscountCodeReqd() {
		return discountCodeReqd;
	}

	public void setDiscountCodeReqd(String discountCodeReqd) {
		this.discountCodeReqd = discountCodeReqd;
	}

	public String getSurchargeCodereqd() {
		return surchargeCodereqd;
	}

	public void setSurchargeCodereqd(String surchargeCodereqd) {
		this.surchargeCodereqd = surchargeCodereqd;
	}

	public Date getExstErrProcessdate() {
		return exstErrProcessdate;
	}

	public void setExstErrProcessdate(Date exstErrProcessdate) {
		this.exstErrProcessdate = exstErrProcessdate;
	}
	
	public String getConvertDiscountSurcharge() {
		return convertDiscountSurcharge;
	}

	public void setConvertDiscountSurcharge(String convertDiscountSurcharge) {
		this.convertDiscountSurcharge = convertDiscountSurcharge;
	}


	
	@Override
	public String toString() {
		
		return "SampleWorkAroundVO: "+
		
				", activityDate="+activityDate+
				", startDate="+startDate+
				", endDate="+endDate+
				", mailClass="+mailClass+
				", processingCategory="+processingCategory+
				", destRateIndicator="+destRateIndicator+
				", rateIndicator="+rateIndicator+
				", zone="+zone+
				", routingBarcode="+routingBarcode+
				", minWeight="+minWeight+
				", maxWeight="+maxWeight+
				", convertMailClass="+convertMailClass+
				", convertProcessingCategory="+convertProcessingCategory+
				", convertDestRateIndicator="+convertDestRateIndicator+
				", convertRateIndicator="+convertRateIndicator+
				", convertZone="+convertZone+
				", convertRoutingBarcode="+convertRoutingBarcode+
				", convertDiscountSurcharge="+convertDiscountSurcharge+
				", convertMinWeight="+convertMinWeight+
				", convertMaxWeight="+convertMaxWeight+
				", convertZone="+convertZone+
				", serviceTypeCodeReqd="+serviceTypeCodeReqd+
				", extraServiceCodereqd="+extraServiceCodereqd+
				", prohibitedSTC="+prohibitedSTC+
				", prohibitedExtraServiceCode="+prohibitedExtraServiceCode+
				", prohibitedDiscountCode="+prohibitedDiscountCode+
				", prohibitedSurchargeCode="+prohibitedSurchargeCode+
				", discountCodeReqd="+discountCodeReqd+
				", surchargeCodereqd="+surchargeCodereqd+
				", exstErrProcessdate="+exstErrProcessdate;
		
	}
	
}
