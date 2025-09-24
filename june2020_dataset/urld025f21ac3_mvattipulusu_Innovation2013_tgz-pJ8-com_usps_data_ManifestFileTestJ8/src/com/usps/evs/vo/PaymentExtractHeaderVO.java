package com.usps.evs.vo;

public class PaymentExtractHeaderVO {
	private String recordType = "H1";
	//SRS 228 Payment Extract - International - Version 2.0
	private String fileVersionNumber;
	private String mailingDateTimeStr;
	private String entryFacilityZipPlus4;	
	private String corpDuns;
	private int metricsSeqNo;
	private String fiscalYear;
	private String fiscalMonth;	
	private String transactionID;
	private String mainFileNumber;
	private String subFileNumber; 
	private String receiptDateTimeStr;
	private String entryFacilityZip;
	private int recordsAccepted;
	private String tpbFlag;

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

	public String getEntryFacilityZipPlus4() {
		return entryFacilityZipPlus4;
	}

	public void setEntryFacilityZipPlus4(String entryFacilityZipPlus4) {
		this.entryFacilityZipPlus4 = entryFacilityZipPlus4;
	}

	public String getCorpDuns() {
		return corpDuns;
	}

	public void setCorpDuns(String corpDuns) {
		this.corpDuns = corpDuns;
	}

	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}

	public void setMetricsSeqNo(int metricsSeqNo) {
		this.metricsSeqNo = metricsSeqNo;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getFiscalMonth() {
		return fiscalMonth;
	}

	public void setFiscalMonth(String fiscalMonth) {
		this.fiscalMonth = fiscalMonth;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getMainFileNumber() {
		return mainFileNumber;
	}

	public void setMainFileNumber(String mainFileNumber) {
		this.mainFileNumber = mainFileNumber;
	}

	public String getSubFileNumber() {
		return subFileNumber;
	}

	public void setSubFileNumber(String subFileNumber) {
		this.subFileNumber = subFileNumber;
	}

	public String getReceiptDateTimeStr() {
		return receiptDateTimeStr;
	}

	public void setReceiptDateTimeStr(String receiptDateTimeStr) {
		this.receiptDateTimeStr = receiptDateTimeStr;
	}

	public String getEntryFacilityZip() {
		return entryFacilityZip;
	}

	public void setEntryFacilityZip(String entryFacilityZip) {
		this.entryFacilityZip = entryFacilityZip;
	}

	public int getRecordsAccepted() {
		return recordsAccepted;
	}

	public void setRecordsAccepted(int recordsAccepted) {
		this.recordsAccepted = recordsAccepted;
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

	
		
	
}
