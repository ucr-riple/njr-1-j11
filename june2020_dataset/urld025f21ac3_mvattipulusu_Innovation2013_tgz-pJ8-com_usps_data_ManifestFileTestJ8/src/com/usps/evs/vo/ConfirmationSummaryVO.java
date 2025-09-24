/*
 * ConfirmationSummaryVO.java
 *
 * Author: bmyrr0
 *
 * Confirmation Summary record.
 *
 * Release: 31
 */

package com.usps.evs.vo;
import java.sql.Date;

public class ConfirmationSummaryVO
{
	private String recordType;
	private String payeeAccount;
	private String mailingAcctRef;
	private String capsTransactionNumber;
	private Date transactionDate;
	private String transactionType;
	private String apsFileNumber;
	private String subFileNumber;
	private double uspsStmtAmt;
	private int uspsStmtPieces;
	private double mailerClaimedAmt;
	private int mailerClaimedPieces;
	private String reasonCode;
	private String reasonText;
	private String caseControlId;
	private String month;
	private String monthlyFiscalYear;
	private String metricsSeqNo;
	private String mID;
	private int ccfHeaderSeqNo;
	private int ccfSummarySeqNo;

	public int getUspsStmtPieces() {
		return uspsStmtPieces;
	}
	public void setUspsStmtPieces(int uspsStmtPieces) {
		this.uspsStmtPieces = uspsStmtPieces;
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
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	public String getMailingAcctRef() {
		return mailingAcctRef;
	}
	public void setMailingAcctRef(String mailingAcctRef) {
		this.mailingAcctRef = mailingAcctRef;
	}
	public int getMailerClaimedPieces() {
		return mailerClaimedPieces;
	}
	public double getUspsStmtAmt() {
		return uspsStmtAmt;
	}
	public void setUspsStmtAmt(double uspsStmtAmt) {
		this.uspsStmtAmt = uspsStmtAmt;
	}
	public void setMailerClaimedPieces(int mailerClaimedPieces) {
		this.mailerClaimedPieces = mailerClaimedPieces;
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
	public String getApsFileNumber() {
		return apsFileNumber;
	}
	public void setApsFileNumber(String apsFileNumber) {
		this.apsFileNumber = apsFileNumber;
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
	public String getMetricsSeqNo() {
		return metricsSeqNo;
	}
	public void setMetricsSeqNo(String metricsSeqNo) {
		this.metricsSeqNo = metricsSeqNo;
	}
	public String getMID() {
		return mID;
	}
	public void setMID(String mid) {
		mID = mid;
	}
	public String getCapsTransactionNumber() {
		return capsTransactionNumber;
	}
	public void setCapsTransactionNumber(String capsTransactionNumber) {
		this.capsTransactionNumber = capsTransactionNumber;
	}
	public String getSubFileNumber() {
		return subFileNumber;
	}
	public void setSubFileNumber(String subFileNumber) {
		this.subFileNumber = subFileNumber;
	}

}
