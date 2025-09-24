package com.usps.evs.vo;

public class StatusExtractDetailOption2 extends CewDetail {
	private String recordType = "D1";

	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	private int errorCode;

}
