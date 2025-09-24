package com.usps.evs.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ByForVO implements Cloneable, Serializable
{	
	
	private static final long serialVersionUID = -41242226447720936L;
	
	private long lineNumber;
	private String section;
	private String vipCode;
	private String postageType;
	private BigDecimal publishedPostage;
	private BigDecimal customPostage;
	private BigDecimal feeAmount;
	
	public long getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(long lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getVipCode() {
		return vipCode;
	}
	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}
	public String getPostageType() {
		return postageType;
	}
	public void setPostageType(String postageType) {
		this.postageType = postageType;
	}
	public BigDecimal getPublishedPostage() {
		return publishedPostage;
	}
	public void setPublishedPostage(BigDecimal publishedPostage) {
		this.publishedPostage = publishedPostage;
	}
	public BigDecimal getCustomPostage() {
		return customPostage;
	}
	public void setCustomPostage(BigDecimal customPostage) {
		this.customPostage = customPostage;
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
}
