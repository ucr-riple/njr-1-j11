package com.usps.evs.vo;


public class ReconExtractFileVO extends ExtractFileVO {
	private int adjustmentType;
	private String extractType;
	private String mailClass;
	private String oldExtractStatus;
	private String newExtractStatus;
	private String reportType;
	private int adjustmentSeqNo;
	
	public int getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(int adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public String getExtractType() {
		return extractType;
	}
	public void setExtractType(String extractType) {
		this.extractType = extractType;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public String getOldExtractStatus() {
		return oldExtractStatus;
	}
	public void setOldExtractStatus(String oldExtractStatus) {
		this.oldExtractStatus = oldExtractStatus;
	}
	public String getNewExtractStatus() {
		return newExtractStatus;
	}
	public void setNewExtractStatus(String newExtractStatus) {
		this.newExtractStatus = newExtractStatus;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public int getAdjustmentSeqNo() {
		return adjustmentSeqNo;
	}
	public void setAdjustmentSeqNo(int adjustmentSeqNo) {
		this.adjustmentSeqNo = adjustmentSeqNo;
	}	
	
}
