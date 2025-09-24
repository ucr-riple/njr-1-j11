/*
 * ConfirmationDetailVO.java
 *
 * Author: bmyrr0
 *
 * Confirmation Detail record.
 * 
 * 
 *
 */

package com.usps.evs.vo;
import java.sql.Date;

public class ConfirmationDetailVO {
    
	private String recordType;
	private Date transactionDate;
	private String transactionType;
	private String picCode;
	private String mailClassCode;
	private double uspsStmtAmt;
	private double mailerClaimedAmt;
	private String reasonCode;
	private String reasonText;
	private String caseControlId;
	private String apsFileNumber;
    private long pDcDetailSeq; 
    private String mID;
    private String capsTransactionNumber;
	private int ccfHeaderSeqNo;
	private int ccfSummarySeqNo;
	private String dcDunsPkgIdZip;
    private String month;
    private String monthlyFiscalYear;

	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCapsTransactionNumber() {
		return capsTransactionNumber;
	}
	public void setCapsTransactionNumber(String capsTransactionNumber) {
		this.capsTransactionNumber = capsTransactionNumber;
	}
	public String getMonthlyFiscalYear() {
		return monthlyFiscalYear;
	}

	public void setMonthlyFiscalYear(String monthlyFiscalYear) {
		this.monthlyFiscalYear = monthlyFiscalYear;
	}

	public String getDcDunsPkgIdZip() {
		return dcDunsPkgIdZip;
	}

	public void setDcDunsPkgIdZip(String dcDunsPkgIdZip) {
		this.dcDunsPkgIdZip = dcDunsPkgIdZip;
	}

	public long getPDcDetailSeq() {
		return pDcDetailSeq;
	}

	public void setPDcDetailSeq(long dcDetailSeq) {
		pDcDetailSeq = dcDetailSeq;
	}
	public String getApsFileNumber() {
		return apsFileNumber;
	}
	public void setApsFileNumber(String apsFileNumber) {
		this.apsFileNumber = apsFileNumber;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}

	public String getMailClassCode() {
		return mailClassCode;
	}

	public void setMailClassCode(String mailClassCode) {
		this.mailClassCode = mailClassCode;
	}

	public double getUspsStmtAmt() {
		return uspsStmtAmt;
	}

	public void setUspsStmtAmt(double uspsStmtAmt) {
		this.uspsStmtAmt = uspsStmtAmt;
	}

	public double getMailerClaimedAmt() {
		return mailerClaimedAmt;
	}

	public void setMailerClaimedAmt(double mailerClaimedAmt) {
		this.mailerClaimedAmt = mailerClaimedAmt;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getCaseControlId() {
		return caseControlId;
	}

	public void setCaseControlId(String caseControlId) {
		this.caseControlId = caseControlId;
	}
	public String getMID() {
		return mID;
	}

	public void setMID(String mid) {
		mID = mid;
	}


	public int getCcfHeaderSeqNo() {
		return ccfHeaderSeqNo;
	}
	public void setCcfHeaderSeqNo(int ccfHeaderSeqNo) {
		this.ccfHeaderSeqNo = ccfHeaderSeqNo;
	}
	public int getCcfSummarySeqNo() {
		return ccfSummarySeqNo;
	}
	public void setCcfSummarySeqNo(int ccfSummarySeqNo) {
		this.ccfSummarySeqNo = ccfSummarySeqNo;
	}
}
