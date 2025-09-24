/*
 *
 */

package com.usps.evs.vo;
import java.util.Calendar;

import com.evs.data.util.CommonUtil;
import com.evs.data.util.DataTypes;
import com.evs.data.util.PipeParser;

public class ManifestHeaderRawLineVo {
	private String fileNumber;
	private String fileType;
	private String mailingDateTime;
	private String mailingDate;
	private String mailingTime;
	private String entryFacility;
	private String paymentAccountNumber;
	private String paymentMethod;
	private String postOfficeOfAccountZipCode;
	private String dsasReservationNumber;
	private String pickupRequestedIndicator;
	private String eFileVersionNumber;
	private String devIdCode;
	private String productVersionNumber;
	private String fileRecordCount;
	private String transactionID;
	private String fedAgencyCostCode;
	private String corpDuns;
	private int filteredType = 1;
	private String filteredField;
	private String filteredValue;
	private String filteredMessage = "";
	private String month;
	private String monthlyFiscalYear;
	private String warningMessage;


	/** Creates a new instance of ManifestDetail */
	public ManifestHeaderRawLineVo() {
	}
    
    public String getFileVersion (String line) {
		String fileVersion = "0";
    	if (line.indexOf("|") != -1)
    	{
    		int tokenCount = 0;
    		PipeParser pp = new PipeParser(line);
    		while (true) {
    			fileVersion = pp.nextToken();
    			tokenCount++;
    			if (tokenCount == 13) {
    				return fileVersion;
    			}
    			if (tokenCount == 14) {
    				return "0";
    			}
    		}    		
    	}
    	
		if (checkMinimumLength(line,77)) { 
			return line.substring(74, 77).trim();
		} else {
			return "0";
		}
    }
    
	public String getStartingStrings(String line, int length) {
		if (checkMinimumLength(line,length)) { 
			return line.substring(0, length).trim();
		} else {
			return "0";
		}
	}
    
	public ManifestHeader parseLine(String line) {
 
		if (checkMinimumLength(line,3)) { 
			this.setFileType(line.substring(2, 3).trim());
		}
		if (checkMinimumLength(line,25)) { 
			this.setFileNumber(line.substring(3, 25).trim());
			this.setCorpDuns(this.getFileNumber().substring(4, 13));
		} else {
			this.setFileNumber("0");
			this.setCorpDuns("0");
		}
		if (checkMinimumLength(line,33)) { 
			this.setMailingDate(line.substring(25, 33).trim());
		}
		if (checkMinimumLength(line,39)) { 
			this.setMailingTime(line.substring(33, 39).trim());
			this.setMailingDateTime(line.substring(25, 39).trim());	
		}
		if (checkMinimumLength(line,44)) { 
			this.setEntryFacility(line.substring(39, 44).trim());
		}
		if (checkMinimumLength(line,54)) { 
			this.setPaymentAccountNumber(line.substring(44, 54).trim());
		}
		if (checkMinimumLength(line,56)) { 
			this.setPaymentMethod(line.substring(54, 56).trim());
		}
		if (checkMinimumLength(line,61)) { 
		this.setPostOfficeOfAccountZipCode(line.substring(56, 61).trim());
		}
		if (checkMinimumLength(line,73)) { 
			this.setDsasReservationNumber(line.substring(61, 73).trim());
		}
		if (checkMinimumLength(line,74)) { 
			this.setPickupRequestedIndicator(line.substring(73, 74).trim());
		}
		if (checkMinimumLength(line,77)) { 
			this.setEFileVersionNumber(line.substring(74, 77).trim());
		}
		if (checkMinimumLength(line,80)) { 
			this.setDevIdCode(line.substring(77, 80).trim());
		}
		if (checkMinimumLength(line,88)) { 
			this.setProductVersionNumber(line.substring(80, 88).trim());
		}
		if (checkMinimumLength(line,97)) { 
			this.setFileRecordCount(line.substring(88, 97).trim());
		} 
		if (line.length() >= 109) {
			this.setTransactionID(line.substring(97, 109).trim());
		}

		//REL 18.0. If Payment method = 03, get the Federal Agency Cost Code
		if (line.length() >= 115) {
			if (this.getPaymentMethod().equals("3") || this.getPaymentMethod().equals("03")) {
				String fedCostCode = line.substring(109, 115).trim();
				//Postion No. 109 to 115
				fedCostCode =
					DataTypes.padFedAgencyCostCode(fedCostCode, true, 6);
				//True for Zero Left Padded String
				this.setFedAgencyCostCode(fedCostCode);
			}
		}
		Calendar today = Calendar.getInstance();
		this.setMonthlyFiscalYear(CommonUtil.toFiscalYear(today)); 
		this.setMonth(CommonUtil.toFiscalMonth(today));
		
			
		ManifestHeader header = new ManifestHeader();
		header.setFileNumber(this.getFileNumber());
		if (this.getFilteredType() == 1) {
				header.setCorpDuns(this.getCorpDuns());
				header.setOriginalCorpDuns(this.corpDuns);
				header.setFileType(this.getFileType());
				header.setFileNumber(this.getFileNumber());
				header.setMailingDateTime(DataTypes.toCalendar(this.getMailingDateTime(), DataTypes.MANIFEST));
				header.setEntryFacility(this.getEntryFacility());
				header.setPaymentAccountNumber(DataTypes.toInteger(this.getPaymentAccountNumber()));
				header.setPaymentMethod(DataTypes.toInteger(this.getPaymentMethod()));
				header.setPostOfficeOfAccountZipCode(this.getPostOfficeOfAccountZipCode());
				header.setDsasReservationNumber(this.getDsasReservationNumber());
				header.setPickupRequestedIndicator(this.getPickupRequestedIndicator());
				header.setEFileVersionNumber(DataTypes.toDouble(this.getEFileVersionNumber(), 1));
				header.setDevIdCode(this.getDevIdCode());
				header.setProductVersionNumber(this.getProductVersionNumber());
				header.setFileRecordCount(DataTypes.toInteger(this.getFileRecordCount()));
				header.setTransactionID(this.getTransactionID());
				header.setFedAgencyCostCode(this.getFedAgencyCostCode());		
				header.setWarningMessage(this.getWarningMessage());
		} else {
				header.setSkipManifestReason(this.getFilteredMessage()+this.getFilteredValue());
				header.setEFileVersionNumber(DataTypes.toDouble(this.getEFileVersionNumber(), 1));
		}
		
		header.setHeaderRawLine(this);
				
		return header;
	}

	private boolean checkMinimumLength(String line, int i) {
		if (line.length() < i) {
			this.setFilteredType(2);
			this.setFilteredMessage(" header too short ");
			this.setFilteredValue(String.valueOf(line.length()));
			return false;
		}
		return true;
	}


	/**
	 * @return
	 */
	public String getCorpDuns() {
		return corpDuns;
	}

	/**
	 * @return
	 */
	public String getDevIdCode() {
		return devIdCode;
	}

	/**
	 * @return
	 */
	public String getDsasReservationNumber() {
		return dsasReservationNumber;
	}

	/**
	 * @return
	 */
	public String getEFileVersionNumber() {
		return eFileVersionNumber;
	}

	/**
	 * @return
	 */
	public String getEntryFacility() {
		return entryFacility;
	}

	/**
	 * @return
	 */
	public String getFedAgencyCostCode() {
		return fedAgencyCostCode;
	}

	/**
	 * @return
	 */
	public String getFileNumber() {
		return fileNumber;
	}

	/**
	 * @return
	 */
	public String getFileRecordCount() {
		return fileRecordCount;
	}

	/**
	 * @return
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @return
	 */
	public String getFilteredField() {
		return filteredField;
	}

	/**
	 * @return
	 */
	public String getFilteredMessage() {
		return filteredMessage;
	}

	/**
	 * @return
	 */
	public int getFilteredType() {
		return filteredType;
	}

	/**
	 * @return
	 */
	public String getFilteredValue() {
		return filteredValue;
	}

	/**
	 * @return
	 */
	public String getMailingDateTime() {
		return mailingDateTime;
	}

	/**
	 * @return
	 */
	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	/**
	 * @return
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @return
	 */
	public String getPickupRequestedIndicator() {
		return pickupRequestedIndicator;
	}

	/**
	 * @return
	 */
	public String getPostOfficeOfAccountZipCode() {
		return postOfficeOfAccountZipCode;
	}

	/**
	 * @return
	 */
	public String getProductVersionNumber() {
		return productVersionNumber;
	}

	/**
	 * @return
	 */
	public String getTransactionID() {
		return transactionID;
	}



	/**
	 * @param string
	 */
	public void setCorpDuns(String string) {
		corpDuns = string;
	}

	/**
	 * @param string
	 */
	public void setDevIdCode(String string) {
		devIdCode = string;
	}

	/**
	 * @param string
	 */
	public void setDsasReservationNumber(String string) {
		dsasReservationNumber = string;
	}

	/**
	 * @param string
	 */
	public void setEFileVersionNumber(String string) {
		eFileVersionNumber = string;
	}

	/**
	 * @param string
	 */
	public void setEntryFacility(String string) {
		entryFacility = string;
	}

	/**
	 * @param string
	 */
	public void setFedAgencyCostCode(String string) {
		fedAgencyCostCode = string;
	}

	/**
	 * @param string
	 */
	public void setFileNumber(String string) {
		fileNumber = string;
	}

	/**
	 * @param string
	 */
	public void setFileRecordCount(String string) {
		fileRecordCount = string;
	}

	/**
	 * @param string
	 */
	public void setFileType(String string) {
		fileType = string;
	}

	/**
	 * @param string
	 */
	public void setFilteredField(String string) {
		filteredField = string;
	}

	/**
	 * @param string
	 */
	public void setFilteredMessage(String string) {
		filteredMessage = string;
	}

	/**
	 * @param i
	 */
	public void setFilteredType(int i) {
		filteredType = i;
	}

	/**
	 * @param string
	 */
	public void setFilteredValue(String string) {
		filteredValue = string;
	}

	/**
	 * @param string
	 */
	public void setMailingDateTime(String string) {
		mailingDateTime = string;
	}

	/**
	 * @param string
	 */
	public void setPaymentAccountNumber(String string) {
		paymentAccountNumber = string;
	}

	/**
	 * @param string
	 */
	public void setPaymentMethod(String string) {
		paymentMethod = string;
	}

	/**
	 * @param string
	 */
	public void setPickupRequestedIndicator(String string) {
		pickupRequestedIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setPostOfficeOfAccountZipCode(String string) {
		postOfficeOfAccountZipCode = string;
	}

	/**
	 * @param string
	 */
	public void setProductVersionNumber(String string) {
		productVersionNumber = string;
	}

	/**
	 * @param string
	 */
	public void setTransactionID(String string) {
		transactionID = string;
	}


	/**
	 * @return
	 */
	public String getMailingDate() {
		return mailingDate;
	}

	/**
	 * @return
	 */
	public String getMailingTime() {
		return mailingTime;
	}

	/**
	 * @param string
	 */
	public void setMailingDate(String string) {
		mailingDate = string;
	}

	/**
	 * @param string
	 */
	public void setMailingTime(String string) {
		mailingTime = string;
	}

	/**
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public String getMonthlyFiscalYear() {
		return monthlyFiscalYear;
	}

	/**
	 * @param string
	 */
	public void setMonth(String string) {
		month = string;
	}

	/**
	 * @param string
	 */
	public void setMonthlyFiscalYear(String string) {
		monthlyFiscalYear = string;
	}

	/**
	 * @return
	 */
	public String getWarningMessage() {
		return warningMessage;
	}

	/**
	 * @param string
	 */
	public void setWarningMessage(String string) {
		warningMessage = string;
	}
}
