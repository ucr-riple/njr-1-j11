/*
 * CewHeader.java
 *
 * Author: Nat Meo
 *
 * CEW header record.
 */

package com.usps.evs.vo;

import java.util.*;

public class CewHeader extends CewRecord {
	private Calendar receiptDateTime;
	
	private String receiptDateTimeStr;

	private String entryFacilityZip;

	private Calendar mailingDate;
	
	private String mailingDateStr;

	private int recordsRead;

	private int recordsRejected;

	private int recordsAccepted;

	private int recordsAcceptedD1;

	private int recordsAcceptedD2;

	private String summaryMessage;

	/** Creates a new instance of CewHeader */
	public CewHeader() {
	}

	public Calendar getReceiptDateTime() {
		return receiptDateTime;
	}

	public void setReceiptDateTime(Calendar receiptDateTime) {
		this.receiptDateTime = receiptDateTime;
	}

	public String getEntryFacilityZip() {
		return entryFacilityZip;
	}

	public void setEntryFacilityZip(String entryFacilityZip) {
		this.entryFacilityZip = entryFacilityZip;
	}

	public Calendar getMailingDate() {
		return mailingDate;
	}

	public void setMailingDate(Calendar mailingDate) {
		this.mailingDate = mailingDate;
	}

	public int getRecordsRead() {
		return recordsRead;
	}

	public void setRecordsRead(int recordsRead) {
		this.recordsRead = recordsRead;
	}

	public int getRecordsRejected() {
		return recordsRejected;
	}

	public void setRecordsRejected(int recordsRejected) {
		this.recordsRejected = recordsRejected;
	}

	public int getRecordsAccepted() {
		return recordsAccepted;
	}

	public void setRecordsAccepted(int recordsAccepted) {
		this.recordsAccepted = recordsAccepted;
	}

	public int getRecordsAcceptedD1() {
		return recordsAcceptedD1;
	}

	public void setRecordsAcceptedD1(int recordsAcceptedD1) {
		this.recordsAcceptedD1 = recordsAcceptedD1;
	}

	public int getRecordsAcceptedD2() {
		return recordsAcceptedD2;
	}

	public void setRecordsAcceptedD2(int recordsAcceptedD2) {
		this.recordsAcceptedD2 = recordsAcceptedD2;
	}

	public String getSummaryMessage() {
		return summaryMessage;
	}

	public void setSummaryMessage(String summaryMessage) {
		this.summaryMessage = summaryMessage;
	}

	public String getReceiptDateTimeStr() {
		return receiptDateTimeStr;
	}

	public void setReceiptDateTimeStr(String receiptDateTimeStr) {
		this.receiptDateTimeStr = receiptDateTimeStr;
	}

	public String getMailingDateStr() {
		return mailingDateStr;
	}

	public void setMailingDateStr(String mailingDateStr) {
		this.mailingDateStr = mailingDateStr;
	}	
}
