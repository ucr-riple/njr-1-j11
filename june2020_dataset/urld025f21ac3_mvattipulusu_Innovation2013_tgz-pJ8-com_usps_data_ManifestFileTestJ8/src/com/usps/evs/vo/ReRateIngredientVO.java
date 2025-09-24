package com.usps.evs.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class ReRateIngredientVO {
	
	private Date activityDate;
	private Date startDate;
	private Date endDate;
	private String mailClass;
	private String processingCategory;
	private String destRateIndicator;
	private String rateIndicator;
	private String zone;
	private String routingBarcode;
	private Double minWeight;
	private Double maxWeight;
	private String convertMailClass;
	private String convertProcessingCategory;
	private String convertDestRateIndicator;
	private String convertRateIndicator;
	private String convertZone;
	private String convertRoutingBarcode;
	private String convertDiscountSurcharge;
//	private double convertMinWeight;
//	private double convertMaxWeight;
	
	private String prohibitedSTC;
	private String prohibitedExtraServiceCode;
	private String prohibitedDiscountCode;
	private String prohibitedSurchargeCode;
	private Date moddate;
	private int userSeqNbr;
	private Date deletionDate;
	private String deletionReason;
	private int deletionUserSeqNbr;
	private String comments;
	private String requiredSTC;
	private String requiredExtraServiceCode;
	private String requiredDiscountCode;
	private String requiredSurchargeCode;
	private String inExtFlag;

	private Date exstErrProcessdate;
	private int  evsConvertRateMapSeqno;//EVS_CONV_RATE_MAP_SEQ_NO
	
	String mailingType;
	
	public String getMailingType() {
		return mailingType;
	}

	public void setMailingType(String mailingType) {
		this.mailingType = mailingType;
	}

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

	public Double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
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

//	public double getConvertMinWeight() {
//		return convertMinWeight;
//	}
//
//	public void setConvertMinWeight(double convertMinWeight) {
//		this.convertMinWeight = convertMinWeight;
//	}
//
//	public double getConvertMaxWeight() {
//		return convertMaxWeight;
//	}
//
//	public void setConvertMaxWeight(double convertMaxWeight) {
//		this.convertMaxWeight = convertMaxWeight;
//	}

	

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

	public Date getModdate() {
		return moddate;
	}

	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}
	
	public int getUserSeqNbr() {
		return userSeqNbr;
	}

	public void setUserSeqNbr(int userSeqNbr) {
		this.userSeqNbr = userSeqNbr;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	public String getDeletionReason() {
		return deletionReason;
	}

	public void setDeletionReason(String deletionReason) {
		this.deletionReason = deletionReason;
	}

	public int getDeletionUserSeqNbr() {
		return deletionUserSeqNbr;
	}

	public void setDeletionUserSeqNbr(int deletionUserSeqNbr) {
		this.deletionUserSeqNbr = deletionUserSeqNbr;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getRequiredSTC() {
		return requiredSTC;
	}

	public void setRequiredSTC(String requiredSTC) {
		this.requiredSTC = requiredSTC;
	}

	public String getRequiredExtraServiceCode() {
		return requiredExtraServiceCode;
	}

	public void setRequiredExtraServiceCode(String requiredExtraServiceCode) {
		this.requiredExtraServiceCode = requiredExtraServiceCode;
	}

	public String getRequiredDiscountCode() {
		return requiredDiscountCode;
	}

	public void setRequiredDiscountCode(String requiredDiscountCode) {
		this.requiredDiscountCode = requiredDiscountCode;
	}

	public String getRequiredSurchargeCode() {
		return requiredSurchargeCode;
	}

	public void setRequiredSurchargeCode(String requiredSurchargeCode) {
		this.requiredSurchargeCode = requiredSurchargeCode;
	}
	
	public String getInExtFlag() {
		return inExtFlag;
	}

	public void setInExtFlag(String inExtFlag) {
		this.inExtFlag = inExtFlag;
	}

	@Override
	public String toString() {
		
		return "ReRateIngredientVO: "+
		
				", activityDate="+activityDate+
				", startDate="+startDate+
				", endDate="+endDate+
				", mailClass="+mailClass+
				", mailingType="+mailingType+
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
//				", convertMinWeight="+convertMinWeight+
//				", convertMaxWeight="+convertMaxWeight+
				", convertZone="+convertZone+
				", requiredSTC="+requiredSTC+
				", requiredExtraServiceCode="+requiredExtraServiceCode+
				", prohibitedSTC="+prohibitedSTC+
				", prohibitedExtraServiceCode="+prohibitedExtraServiceCode+
				", prohibitedDiscountCode="+prohibitedDiscountCode+
				", prohibitedSurchargeCode="+prohibitedSurchargeCode+
				", requiredDiscountCode="+requiredDiscountCode+
				", requiredSurchargeCode="+requiredSurchargeCode+
				", exstErrProcessdate="+exstErrProcessdate;
		
	}
	
}
