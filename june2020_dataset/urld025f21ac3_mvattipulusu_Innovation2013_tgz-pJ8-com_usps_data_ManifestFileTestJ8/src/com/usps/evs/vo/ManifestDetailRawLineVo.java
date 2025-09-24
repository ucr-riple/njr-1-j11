/*
 *
 */

package com.usps.evs.vo;
import org.apache.log4j.Logger;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;

public class ManifestDetailRawLineVo 
{
    private String picCode;
    private String classOfMail;
    private String destinationZip;
    private String destinationZipPlus4;
    private String countryCode;
    private String postage;
    private String uomCode;
    private String weight;
    private String processingCategory;
    private String destinationRateIndicator;
    private String rateIndicator;
    private String zone;
    private String poBoxIndicator;
    private String waiverOfSignature;
    private String noWeekendHolidayDelivery;
    private String articleValue;
    private String codAmountDueSender;
    private String handlingCharge;
	private String specialServiceCode1;
	private String specialServiceFee1;
	private String specialServiceCode2;
	private String specialServiceFee2;
	private String specialServiceCode3;
	private String specialServiceFee3;
	private String length;
	private String width;
	private String height;
	private String dimensionalWeight;
	private String duns;
    private String customerInternalReference;
    private String surchargeType;
    private String surchargeAmount;
    private String nonIncidentalEnclosureRateIndicator;
    private String nonIncidentalEnclosureClass;
    private String nonIncidentalEnclosurePostage;
    private String nonIncidentalEnclosureWeight;
    private String customsDesignatedNumber;
    private String postalRoutingBarcodeIndicator;
    private int filteredType = Constants.FILTERED_NONE;
    private String filteredField;
    private String filteredValue;
    private String filteredMessage="";
	//REL21.0
	private int warningType;
	private String warningField;
	private String warningValue="";
	private String warningMessage="";
	
	private String eFileVersionNumber = "1.4"; // default
    
    Logger logger =  Logger.getLogger("ManifestDetailRawLineVo");
    
    
	public ManifestDetailRawLineVo() {
	}
	 

	public int parseLine(String line) {

		setEFileVersionNumber("1.4");
		
		checkMinimumLength(line);
		
		if (filteredType != Constants.FILTERED_NONE) {
			return filteredType;
		}
		
		setClassOfMail(line.substring(2, 4).trim());
		setPicCode(line.substring(4, 26));  // REL21.0
		
		String trim = line.substring(26, 31).trim();
		 if ("".equals(trim)) {
			 setDestinationZip("00000");
		 } else {
        	
			 setDestinationZip(trim);
		 }
		 setDestinationZipPlus4(line.substring(31, 35).trim());
		 setCountryCode(line.substring(35, 37).trim());
		 setPostage(line.substring(37, 44).trim());
		 setUomCode(line.substring(44, 45).trim());
		 setWeight(line.substring(45, 54).trim());

		 String processingCategory = line.substring(54, 55).trim();
		 setProcessingCategory(DataTypes.massageProcessingCategory(processingCategory));
		 setDestinationRateIndicator(line.substring(55, 56).trim());
		 setRateIndicator(line.substring(56, 58).trim());
		 setZone(line.substring(58, 60).trim());
		 setPoBoxIndicator(line.substring(60, 61).trim());
		 setWaiverOfSignature(line.substring(61, 62).trim());
		 setNoWeekendHolidayDelivery(line.substring(62, 63).trim());
		 setArticleValue(line.substring(63, 70).trim());
		 setCodAmountDueSender(line.substring(70, 75).trim());
		 setHandlingCharge(line.substring(75, 79).trim());
        
		setSpecialServiceCode1(line.substring(79, 81));
		setSpecialServiceFee1(line.substring(81, 86));
		setSpecialServiceCode2(line.substring(86, 88));
		setSpecialServiceFee2(line.substring(88, 93));
		setSpecialServiceCode3(line.substring(93, 95));
		setSpecialServiceFee3(line.substring(95, 100));

		 setLength(line.substring(100, 105).trim()); 
		 setWidth(line.substring(105, 110).trim()); 
		 setHeight(line.substring(110, 115).trim());
		 setDimensionalWeight(line.substring(115, 121).trim());
        
		 setDuns(line.substring(121, 130).trim());
		 setCustomerInternalReference(line.substring(130, 160).trim());
		 setSurchargeType(line.substring(160, 162).trim());
		 setSurchargeAmount(line.substring(162, 169).trim());
		 setNonIncidentalEnclosureClass(line.substring(169, 171).trim());
		 setNonIncidentalEnclosureRateIndicator(line.substring(171, 173));
		 setNonIncidentalEnclosurePostage(line.substring(173, 180).trim());
		 setNonIncidentalEnclosureWeight(line.substring(180, 189).trim());
		 setCustomsDesignatedNumber(line.substring(189, 198).trim());
		 setPostalRoutingBarcodeIndicator(line.substring(198, 199).trim());
		 
		 return filteredType; 
	}

	/**
	 * @return
	 */
	public String getArticleValue() {
		return articleValue;
	}

	/**
	 * @return
	 */
	public String getClassOfMail() {
		return classOfMail;
	}

	/**
	 * @return
	 */
	public String getCodAmountDueSender() {
		return codAmountDueSender;
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
	public String getCustomerInternalReference() {
		return customerInternalReference;
	}

	/**
	 * @return
	 */
	public String getCustomsDesignatedNumber() {
		return customsDesignatedNumber;
	}

	/**
	 * @return
	 */
	public String getDestinationRateIndicator() {
		return destinationRateIndicator;
	}

	/**
	 * @return
	 */
	public String getDestinationZip() {
		return destinationZip;
	}

	/**
	 * @return
	 */
	public String getDestinationZipPlus4() {
		return destinationZipPlus4;
	}

	/**
	 * @return
	 */
	public String getDimensionalWeight() {
		return dimensionalWeight;
	}

	/**
	 * @return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * @return
	 */
	public String getHandlingCharge() {
		return handlingCharge;
	}

	/**
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @return
	 */
	public String getNonIncidentalEnclosureClass() {
		return nonIncidentalEnclosureClass;
	}

	/**
	 * @return
	 */
	public String getNonIncidentalEnclosurePostage() {
		return nonIncidentalEnclosurePostage;
	}

	/**
	 * @return
	 */
	public String getNonIncidentalEnclosureRateIndicator() {
		return nonIncidentalEnclosureRateIndicator;
	}

	/**
	 * @return
	 */
	public String getNonIncidentalEnclosureWeight() {
		return nonIncidentalEnclosureWeight;
	}

	/**
	 * @return
	 */
	public String getNoWeekendHolidayDelivery() {
		return noWeekendHolidayDelivery;
	}

	/**
	 * @return
	 */
	public String getPicCode() {
		return picCode;
	}

	/**
	 * @return
	 */
	public String getPoBoxIndicator() {
		return poBoxIndicator;
	}

	/**
	 * @return
	 */
	public String getPostage() {
		return postage;
	}

	/**
	 * @return
	 */
	public String getPostalRoutingBarcodeIndicator() {
		return postalRoutingBarcodeIndicator;
	}

	/**
	 * @return
	 */
	public String getProcessingCategory() {
		return processingCategory;
	}

	/**
	 * @return
	 */
	public String getRateIndicator() {
		return rateIndicator;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceCode1() {
		return specialServiceCode1;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceCode2() {
		return specialServiceCode2;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceCode3() {
		return specialServiceCode3;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceFee1() {
		return specialServiceFee1;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceFee2() {
		return specialServiceFee2;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceFee3() {
		return specialServiceFee3;
	}

	/**
	 * @return
	 */
	public String getSurchargeAmount() {
		return surchargeAmount;
	}

	/**
	 * @return
	 */
	public String getSurchargeType() {
		return surchargeType;
	}

	/**
	 * @return
	 */
	public String getUomCode() {
		return uomCode;
	}

	/**
	 * @return
	 */
	public String getWaiverOfSignature() {
		return waiverOfSignature;
	}

	/**
	 * @return
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @return
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param string
	 */
	public void setArticleValue(String string) {
		articleValue = string;
	}

	/**
	 * @param string
	 */
	public void setClassOfMail(String string) {
		classOfMail = string;
	}

	/**
	 * @param string
	 */
	public void setCodAmountDueSender(String string) {
		codAmountDueSender = string;
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
	public void setCustomerInternalReference(String string) {
		customerInternalReference = string;
	}

	/**
	 * @param i
	 */
	public void setCustomsDesignatedNumber(String string) {
		customsDesignatedNumber = string;
	}

	/**
	 * @param string
	 */
	public void setDestinationRateIndicator(String string) {
		destinationRateIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setDestinationZip(String string) {
		destinationZip = string;
	}

	/**
	 * @param string
	 */
	public void setDestinationZipPlus4(String string) {
		destinationZipPlus4 = string;
	}

	/**
	 * @param string
	 */
	public void setDimensionalWeight(String string) {
		dimensionalWeight = string;
	}

	/**
	 * @param string
	 */
	public void setDuns(String string) {
		duns = string;
	}

	/**
	 * @param string
	 */
	public void setHandlingCharge(String string) {
		handlingCharge = string;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/**
	 * @param string
	 */
	public void setLength(String string) {
		length = string;
	}

	/**
	 * @param string
	 */
	public void setNonIncidentalEnclosureClass(String string) {
		nonIncidentalEnclosureClass = string;
	}

	/**
	 * @param string
	 */
	public void setNonIncidentalEnclosurePostage(String string) {
		nonIncidentalEnclosurePostage = string;
	}

	/**
	 * @param string
	 */
	public void setNonIncidentalEnclosureRateIndicator(String string) {
		nonIncidentalEnclosureRateIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setNonIncidentalEnclosureWeight(String string) {
		nonIncidentalEnclosureWeight = string;
	}

	/**
	 * @param string
	 */
	public void setNoWeekendHolidayDelivery(String string) {
		noWeekendHolidayDelivery = string;
	}

	/**
	 * @param string
	 */
	public void setPicCode(String string) {
		picCode = string;
	}

	/**
	 * @param string
	 */
	public void setPoBoxIndicator(String string) {
		poBoxIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setPostage(String string) {
		postage = string;
	}

	/**
	 * @param string
	 */
	public void setPostalRoutingBarcodeIndicator(String string) {
		postalRoutingBarcodeIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setProcessingCategory(String string) {
		processingCategory = string;
	}

	/**
	 * @param string
	 */
	public void setRateIndicator(String string) {
		rateIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceCode1(String string) {
		specialServiceCode1 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceCode2(String string) {
		specialServiceCode2 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceCode3(String string) {
		specialServiceCode3 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceFee1(String string) {
		specialServiceFee1 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceFee2(String string) {
		specialServiceFee2 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceFee3(String string) {
		specialServiceFee3 = string;
	}

	/**
	 * @param string
	 */
	public void setSurchargeAmount(String string) {
		surchargeAmount = string;
	}

	/**
	 * @param string
	 */
	public void setSurchargeType(String string) {
		surchargeType = string;
	}

	/**
	 * @param string
	 */
	public void setUomCode(String string) {
		uomCode = string;
	}

	/**
	 * @param string
	 */
	public void setWaiverOfSignature(String string) {
		waiverOfSignature = string;
	}

	/**
	 * @param string
	 */
	public void setWeight(String string) {
		weight = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @param string
	 */
	public void setZone(String string) {
		zone = string;
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
	 * @param string
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

	private void checkMinimumLength(String line) {
		if (line.length() < 199) {
			if (line.length() >= 26) {
				setFilteredType(Constants.FILTERED_RATE);
				setClassOfMail(line.substring(2, 4).trim());
				setPicCode(line.substring(4, 26));
				setFilteredMessage("Filtered because detail line is less then 199.  Minimum is 199");
			} else {
				setFilteredType(9); // Bad less than 26
				if (line.length() > 4) {
					setClassOfMail(line.substring(2, 4).trim());
					setPicCode("0000000000000000000000");
				} else {
					setClassOfMail("XX");
					setPicCode("0000000000000000000000");
				}
				setFilteredMessage("Filtered for Bad Record because detail line is less then 26.");
			}
			// Add other NOT_NULL Constraint value, Other will be set later
			setPostage("0");
			setDuns("000000000");
			setDestinationZip("00000");				
			logger.info(getFilteredMessage()+" Length="+line.length()+", PicCode="+picCode);
		}
	}


	/**
	 * @return
	 */
	public String getWarningField() {
		return warningField;
	}

	/**
	 * @return
	 */
	public String getWarningMessage() {
		return warningMessage;
	}

	/**
	 * @return
	 */
	public String getWarningValue() {
		return warningValue;
	}

	/**
	 * @param string
	 */
	public void setWarningField(String string) {
		warningField = string;
	}

	/**
	 * @param string
	 */
	public void setWarningMessage(String string) {
		warningMessage = string;
	}

	/**
	 * @param string
	 */
	public void setWarningValue(String string) {
		warningValue = string;
	}

	/**
	 * @return
	 */
	public int getWarningType() {
		return warningType;
	}

	/**
	 * @param i
	 */
	public void setWarningType(int i) {
		warningType = i;
	}

	/**
	 * @return
	 */
	public String getEFileVersionNumber() {
		return eFileVersionNumber;
	}

	/**
	 * @param string
	 */
	public void setEFileVersionNumber(String string) {
		eFileVersionNumber = string;
	}

	public void setError(int filterType, String message) {
		if (Constants.FILTERED_NONE == getFilteredType()) {
			setFilteredType(filterType);
			setFilteredMessage(message);
			logger.debug(getFilteredMessage()+", PicCode="+getPicCode());
		}
	}
	
	public void setWarning(int warningType, String message, String warnedValue) {
		if ((Constants.FILTERED_NONE == getFilteredType()) && (Constants.FILTERED_NONE == getWarningType())) {
			setWarningType(warningType);
			setWarningValue(warnedValue);
			setWarningMessage(message+" "+getPicCode());
			logger.debug("Warning.."+getWarningMessage());
		}
	}
}
