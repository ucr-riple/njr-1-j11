package com.usps.evs.vo;

import java.util.Calendar;

public class StatusExtractHeaderOption2 extends CewHeader {
	private String recordType = "H1";
	private String fileVersionNumber = "1.1";
	private String mailingDateTimeStr;
	private String statusCode;
	
	private int ptsAcceptedNoWarningD1;  
	private int ptsAcceptedwithWarningD1;    
	private int ptsRejectedD1;   
	private int ptsAcceptedNoWarningD2;  
	private int ptsAcceptedwithWarningD2;    
	private int ptsRejectedD2; 

	private String eVSProcessingDateTimeStr;
	private int evsRecordsRead;
	private int evsAcceptedNoWarningD1;  
	private int evsAcceptedwithWarningD1;    
	private int evsRejectedD1;   
	private int evsAcceptedNoWarningD2;  
	private int evsAcceptedwithWarningD2;    
	private int evsRejectedD2;

	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getFileVersionNumber() {
		return fileVersionNumber;
	}
	public void setFileVersionNumber(String fileVersionNumber) {
		this.fileVersionNumber = fileVersionNumber;
	}
	public String getMailingDateTimeStr() {
		return mailingDateTimeStr;
	}
	public void setMailingDateTimeStr(String mailingDateTimeStr) {
		this.mailingDateTimeStr = mailingDateTimeStr;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public int getPtsAcceptedNoWarningD1() {
		return ptsAcceptedNoWarningD1;
	}
	public void setPtsAcceptedNoWarningD1(int ptsAcceptedNoWarningD1) {
		this.ptsAcceptedNoWarningD1 = ptsAcceptedNoWarningD1;
	}
	public int getPtsAcceptedwithWarningD1() {
		return ptsAcceptedwithWarningD1;
	}
	public void setPtsAcceptedwithWarningD1(int ptsAcceptedwithWarningD1) {
		this.ptsAcceptedwithWarningD1 = ptsAcceptedwithWarningD1;
	}
	public int getPtsRejectedD1() {
		return ptsRejectedD1;
	}
	public void setPtsRejectedD1(int ptsRejectedD1) {
		this.ptsRejectedD1 = ptsRejectedD1;
	}
	public int getPtsAcceptedNoWarningD2() {
		return ptsAcceptedNoWarningD2;
	}
	public void setPtsAcceptedNoWarningD2(int ptsAcceptedNoWarningD2) {
		this.ptsAcceptedNoWarningD2 = ptsAcceptedNoWarningD2;
	}
	public int getPtsAcceptedwithWarningD2() {
		return ptsAcceptedwithWarningD2;
	}
	public void setPtsAcceptedwithWarningD2(int ptsAcceptedwithWarningD2) {
		this.ptsAcceptedwithWarningD2 = ptsAcceptedwithWarningD2;
	}
	public int getPtsRejectedD2() {
		return ptsRejectedD2;
	}
	public void setPtsRejectedD2(int ptsRejectedD2) {
		this.ptsRejectedD2 = ptsRejectedD2;
	}
	public String getEVSProcessingDateTimeStr() {
		return eVSProcessingDateTimeStr;
	}
	public void setEVSProcessingDateTimeStr(String processingDateTime) {
		eVSProcessingDateTimeStr = processingDateTime;
	}
	public int getEvsRecordsRead() {
		return evsRecordsRead;
	}
	public void setEvsRecordsRead(int evsRecordsRead) {
		this.evsRecordsRead = evsRecordsRead;
	}
	public int getEvsAcceptedNoWarningD1() {
		return evsAcceptedNoWarningD1;
	}
	public void setEvsAcceptedNoWarningD1(int evsAcceptedNoWarningD1) {
		this.evsAcceptedNoWarningD1 = evsAcceptedNoWarningD1;
	}
	public int getEvsAcceptedwithWarningD1() {
		return evsAcceptedwithWarningD1;
	}
	public void setEvsAcceptedwithWarningD1(int evsAcceptedwithWarningD1) {
		this.evsAcceptedwithWarningD1 = evsAcceptedwithWarningD1;
	}
	public int getEvsRejectedD1() {
		return evsRejectedD1;
	}
	public void setEvsRejectedD1(int evsRejectedD1) {
		this.evsRejectedD1 = evsRejectedD1;
	}
	public int getEvsAcceptedNoWarningD2() {
		return evsAcceptedNoWarningD2;
	}
	public void setEvsAcceptedNoWarningD2(int evsAcceptedNoWarningD2) {
		this.evsAcceptedNoWarningD2 = evsAcceptedNoWarningD2;
	}
	public int getEvsAcceptedwithWarningD2() {
		return evsAcceptedwithWarningD2;
	}
	public void setEvsAcceptedwithWarningD2(int evsAcceptedwithWarningD2) {
		this.evsAcceptedwithWarningD2 = evsAcceptedwithWarningD2;
	}
	public int getEvsRejectedD2() {
		return evsRejectedD2;
	}
	public void setEvsRejectedD2(int evsRejectedD2) {
		this.evsRejectedD2 = evsRejectedD2;
	} 

}
