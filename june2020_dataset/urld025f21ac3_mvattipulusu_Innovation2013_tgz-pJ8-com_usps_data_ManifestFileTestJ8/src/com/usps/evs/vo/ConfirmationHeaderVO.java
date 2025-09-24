/*
 * ConfirmationHeaderVO.java
 *
 * Author: bmyrr0
 *
 * Confirmation header record.
 * 
 * Release: 31
 *
 */

package com.usps.evs.vo;
import java.sql.Date;

public class ConfirmationHeaderVO
{
	private String recordType;
	private int ccfHeaderSeqNo;
	private String transactionCode;
	private double totalAmount;
	private String paymentType;
	private String paymentMethodCode;
	private Date paymentDate;
	private String traceId;
	private String payerName;
	private String payeeName;
	private String ccfFileNumber;
	private String ccfFileName;
	private Date ccfCreateDate;
	private String capsTransactionNumber;
	private String mID;
	

	public String getCapsTransactionNumber() {
		return capsTransactionNumber;
	}
	public void setCapsTransactionNumber(String capsTransactionNumber) {
		this.capsTransactionNumber = capsTransactionNumber;
	}
	public Date getCcfCreateDate() {
		return ccfCreateDate;
	}
	public void setCcfCreateDate(Date ccfCreateDate) {
		this.ccfCreateDate = ccfCreateDate;
	}
	public String getCcfFileName() {
		return ccfFileName;
	}
	public void setCcfFileName(String ccfFileName) {
		this.ccfFileName = ccfFileName;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getCcfFileNumber() {
		return ccfFileNumber;
	}
	public void setCcfFileNumber(String ccfFileNumber) {
		this.ccfFileNumber = ccfFileNumber;
	}
	public int getCcfHeaderSeqNo() {
		return ccfHeaderSeqNo;
	}
	public void setCcfHeaderSeqNo(int ccfHeaderSeqNo) {
		this.ccfHeaderSeqNo = ccfHeaderSeqNo;
	}
	public String getMID() {
		return mID;
	}
	public void setMID(String mid) {
		mID = mid;
	}


}
