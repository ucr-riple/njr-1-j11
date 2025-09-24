package com.usps.evs.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ByForDetailPostageLineVO implements Cloneable, Serializable
{	
	private static final long serialVersionUID = -3437760046556616801L;
	private BigDecimal cccDiscountTotal;
	private long cccProductSeqNo;
	private long evsPostageLineSeqNo;
	private String fileNumber;
	private String lineNumber;
	private long maCrid;
	private String maNoCridMid;
	private String month;
	private String monthlyFiscalYear;
	private long moCrid;
	private String moNoCridMid;
	private char pendingMidCridLookUp;
	private long pieces;
	private BigDecimal postage;
	private long psGroupNo;
	private BigDecimal publishedPostage;
	private char section;
	private long tranSeqNo;
	private String vipCode;
	private BigDecimal weight;
	private String postageType;
	private BigDecimal feeAmount;
	
	public BigDecimal getCccDiscountTotal() {
		return cccDiscountTotal;
	}
	public void setCccDiscountTotal(BigDecimal cccDiscountTotal) {
		this.cccDiscountTotal = cccDiscountTotal;
	}
	public long getCccProductSeqNo() {
		return cccProductSeqNo;
	}
	public void setCccProductSeqNo(long cccProductSeqNo) {
		this.cccProductSeqNo = cccProductSeqNo;
	}
	public long getEvsPostageLineSeqNo() {
		return evsPostageLineSeqNo;
	}
	public void setEvsPostageLineSeqNo(long evsPostageLineSeqNo) {
		this.evsPostageLineSeqNo = evsPostageLineSeqNo;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public long getMaCrid() {
		return maCrid;
	}
	public void setMaCrid(long maCrid) {
		this.maCrid = maCrid;
	}
	public String getMaNoCridMid() {
		return maNoCridMid;
	}
	public void setMaNoCridMid(String maNoCridMid) {
		this.maNoCridMid = maNoCridMid;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthlyFiscalYear() {
		return monthlyFiscalYear;
	}
	public void setMonthlyFiscalYear(String monthlyFiscalYear) {
		this.monthlyFiscalYear = monthlyFiscalYear;
	}
	public long getMoCrid() {
		return moCrid;
	}
	public void setMoCrid(long moCrid) {
		this.moCrid = moCrid;
	}
	public String getMoNoCridMid() {
		return moNoCridMid;
	}
	public void setMoNoCridMid(String moNoCridMid) {
		this.moNoCridMid = moNoCridMid;
	}
	public char getPendingMidCridLookUp() {
		return pendingMidCridLookUp;
	}
	public void setPendingMidCridLookUp(char pendingMidCridLookUp) {
		this.pendingMidCridLookUp = pendingMidCridLookUp;
	}
	public long getPieces() {
		return pieces;
	}
	public void setPieces(long pieces) {
		this.pieces = pieces;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public long getPsGroupNo() {
		return psGroupNo;
	}
	public void setPsGroupNo(long psGroupNo) {
		this.psGroupNo = psGroupNo;
	}
	public BigDecimal getPublishedPostage() {
		return publishedPostage;
	}
	public void setPublishedPostage(BigDecimal publishedPostage) {
		this.publishedPostage = publishedPostage;
	}
	public char getSection() {
		return section;
	}
	public void setSection(char section) {
		this.section = section;
	}
	public long getTranSeqNo() {
		return tranSeqNo;
	}
	public void setTranSeqNo(long tranSeqNo) {
		this.tranSeqNo = tranSeqNo;
	}
	public String getVipCode() {
		return vipCode;
	}
	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setPostageType(String postageType) {
		this.postageType = postageType;
	}
	public String getPostageType() {
		return postageType;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	public String toString()
	{
		return "ByForDetailPostageLineVO attributes: cccDiscountTotal --> " + cccDiscountTotal + " | cccProductSeqNo --> " + cccProductSeqNo + 
			   " | evsPostageLineSeqNo --> " + evsPostageLineSeqNo + " | fileNumber " + fileNumber + " | lineNumber --> " + lineNumber + 
			   " | maCrid --> " + maCrid + " | maNoCridMid --> " + maNoCridMid + " | month --> " + month + " | monthlyFiscalYear --> " + monthlyFiscalYear +
			   " | moCrid --> " + moCrid + " | moNoCridMid --> " + moNoCridMid + " | pendingMidCridLookUp --> " + pendingMidCridLookUp + " | pieces --> " + pieces +
			   " | postage --> " + postage + " | psGroupNo --> " + psGroupNo + " | publishedPostage --> " + publishedPostage + " | section --> " + section + 
			   " | tranSeqNo --> " + tranSeqNo + " | vipCode --> " + vipCode + " | weight --> " + weight + " | postageType --> " + postageType + 
			   " | feeAmount --> " + feeAmount;
			   
	}
}
