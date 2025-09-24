/*
 *
 */

package com.usps.evs.vo;
import java.util.Calendar;
import java.util.logging.Logger;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;
import com.evs.data.util.PipeParser;

public class ManifestHeaderRawLineVoV20 extends ManifestHeaderRawLineVo{
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
	private int filteredType = Constants.FILTERED_NONE;
	private String filteredField;
	private String filteredValue;
	private String filteredMessage = "";
	private String month;
	private String monthlyFiscalYear;
	private String applicationIdentifier;
	private String stc;
	private String entryFacilityType;
	private String entryFacilityZipPlus4;
	private String countryCode;
	private String shipmentFeeCode;
	private String shipmentFee;
	private String filler1;
	private String filler2;
	private String mailerId;



	/** Creates a new instance of ManifestDetail */
	public ManifestHeaderRawLineVoV20() {
	}

	public ManifestHeader parseLine(String line) {
		ManifestHeader header = new ManifestHeader();
		
		int fieldCount=0;
		String field = "";
		PipeParser pp = new PipeParser(line);

		fieldCount = pp.countTokens();
		if (fieldCount < 18) {
			this.setFilteredType(Constants.FILTERED_HEADER_FORMAT);
			this.setFilteredMessage("Insufficient field values");
			this.setFilteredValue("" + fieldCount);
			
			return header;
		}
		
		field = pp.nextToken();
		field = pp.nextToken();
		
		if (field.startsWith("92750") || field.startsWith("93750")) { 
				this.setFileNumber(field.trim());
				this.setApplicationIdentifier(field.substring(0,2).trim());
				this.setStc(field.substring(2,5).trim());			
				if (this.getApplicationIdentifier().startsWith("92")) {
					this.setCorpDuns(field.substring(5,14).trim());
				} else {
					this.setCorpDuns(field.substring(5,12).trim()); 
				}
		} else {
				this.setFileNumber("0");
				this.setCorpDuns("0");
				this.setFilteredType(Constants.FILTERED_HEADER_FORMAT);
				this.setFilteredMessage("Invalid EFN");
				this.setFilteredValue(line.substring(0, 34));
		}
		
		if (Constants.FILTERED_HEADER_FORMAT == this.getFilteredType()) {
			return header;
		}
				
		this.setFileType(pp.nextToken());
		this.setMailingDate(pp.nextToken());
		this.setMailingTime(pp.nextToken());
		this.setMailingDateTime(getMailingDate() + getMailingTime());	
		this.setEntryFacilityType(pp.nextToken());
		this.setEntryFacility(pp.nextToken());
		this.setEntryFacilityZipPlus4(pp.nextToken());
		this.setCountryCode(pp.nextToken());
		this.setShipmentFeeCode(pp.nextToken());
		this.setShipmentFee(pp.nextToken());
		this.setFiller1(pp.nextToken());
		this.setEFileVersionNumber(pp.nextToken());
		this.setTransactionID(pp.nextToken());
		this.setDevIdCode(pp.nextToken());
		this.setProductVersionNumber(pp.nextToken());
		this.setFileRecordCount(pp.nextToken());
		this.setMailerId(pp.nextToken());

		Calendar today = Calendar.getInstance();
		this.setMonthlyFiscalYear(DataTypes.toFiscalYear(today)); 
		this.setMonth(DataTypes.toFiscalMonth(today));
		

		
		header.setFileNumber(this.getFileNumber());
		if (this.getFilteredType() == Constants.FILTERED_NONE) {
				header.setCorpDuns(this.getCorpDuns());
				header.setOriginalCorpDuns(this.corpDuns);
				header.setFileType(this.getFileType());
				header.setFileNumber(this.getFileNumber());
				header.setMailingDateTime(DataTypes.toCalendar(this.getMailingDateTime(), DataTypes.MANIFEST));
				header.setEntryFacility(this.getEntryFacility());
				//header.setPaymentAccountNumber(DataTypes.toInteger(this.getPaymentAccountNumber()));
				//header.setPaymentMethod(DataTypes.toInteger(this.getPaymentMethod()));
				//header.setPostOfficeOfAccountZipCode(this.getPostOfficeOfAccountZipCode());
				//header.setDsasReservationNumber(this.getDsasReservationNumber());
				//header.setPickupRequestedIndicator(this.getPickupRequestedIndicator());
				header.setEFileVersionNumber(DataTypes.toDouble(this.getEFileVersionNumber(), 1));
				header.setDevIdCode(this.getDevIdCode());
				header.setProductVersionNumber(this.getProductVersionNumber());
				header.setFileRecordCount(DataTypes.toInteger(this.getFileRecordCount()));
				header.setTransactionID(this.getTransactionID());
				//header.setFedAgencyCostCode(this.getFedAgencyCostCode());		
				header.setEntryFacilityType(this.getEntryFacilityType());
				header.setEntryFacilityZipPlus4(this.entryFacilityZipPlus4);
				header.setCountryCode(this.countryCode);
				header.setShipmentFeeCode(this.shipmentFeeCode);
				header.setShipmentFee(DataTypes.toDouble(this.shipmentFee,2));
				header.setFiller1(this.filler1);
				header.setFiller2(this.filler2);
				header.setWarningMessage(this.getWarningMessage());
				header.setMailerId(this.getMailerId());
		} else {
				header.setSkipManifestReason(this.getFilteredMessage()+this.getFilteredValue());
		}

		header.setHeaderRawLine(this);
		
		return header;
	}

	private boolean checkMinimumLength(String line, int i) {
		if (line.length() < i) {
			this.setFilteredType(Constants.FILTERED_HEADER_FORMAT);
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
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @return
	 */
	public String getEntryFacilityType() {
		return entryFacilityType;
	}

	/**
	 * @return
	 */
	public String getEntryFacilityZipPlus4() {
		return entryFacilityZipPlus4;
	}

	/**
	 * @return
	 */
	public String getFiller1() {
		return filler1;
	}

	/**
	 * @return
	 */
	public String getFiller2() {
		return filler2;
	}



	/**
	 * @return
	 */
	public String getShipmentFee() {
		return shipmentFee;
	}

	/**
	 * @return
	 */
	public String getShipmentFeeCode() {
		return shipmentFeeCode;
	}

	/**
	 * @param string
	 */
	public void setCountryCode(String string) {
		countryCode = string;
	}

	/**
	 * @param string
	 */
	public void setEntryFacilityType(String string) {
		entryFacilityType = string;
	}

	/**
	 * @param string
	 */
	public void setEntryFacilityZipPlus4(String string) {
		entryFacilityZipPlus4 = string;
	}

	/**
	 * @param string
	 */
	public void setFiller1(String string) {
		filler1 = string;
	}

	/**
	 * @param string
	 */
	public void setFiller2(String string) {
		filler2 = string;
	}


	/**
	 * @param string
	 */
	public void setShipmentFee(String string) {
		shipmentFee = string;
	}

	/**
	 * @param string
	 */
	public void setShipmentFeeCode(String string) {
		shipmentFeeCode = string;
	}

	/**
	 * @return
	 */
	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	/**
	 * @return
	 */
	public String getStc() {
		return stc;
	}

	/**
	 * @param string
	 */
	public void setApplicationIdentifier(String string) {
		applicationIdentifier = string;
	}

	/**
	 * @param string
	 */
	public void setStc(String string) {
		stc = string;
	}

	public void setMailerId(String mailerId){
		this.mailerId = mailerId;
	}
	
	public String getMailerId(){
		return mailerId;
	}
}
