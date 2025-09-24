package com.usps.data;

import java.math.BigDecimal;
import java.util.Calendar;


public class Detail {
	
	private int sequenceNumber;
    private String picCode;
    private String fileNumber;
    private String subFileNumber;
    private String classOfMail;
    private String destinationZip;
    private String destinationZipPlus4;
    private String countryCode;
    private BigDecimal postage = new BigDecimal(0.00);
    private BigDecimal totalPostage = new BigDecimal(0.00);
    private String uomCode;
    private double weight;
    private String processingCategory;
    private String destinationRateIndicator;
    private String rateIndicator;
    private String zone;
    private String poBoxIndicator;
    private String waiverOfSignature;
    private String noWeekendHolidayDelivery;
    private double articleValue;
    private double codAmountDueSender;
    private BigDecimal handlingCharge = new BigDecimal(0.00);;
    private String duns;
    private String customerInternalReference;
    private String surchargeType;
    private BigDecimal surchargeAmount = new BigDecimal(0.00);;
    private String nonIncidentalEnclosureRateIndicator;
    private String nonIncidentalEnclosureClass;
    private BigDecimal nonIncidentalEnclosurePostage = new BigDecimal(0.00);;
    private double nonIncidentalEnclosureWeight;
    private int customsDesignatedNumber;
    private String corpDuns;
    private String dcDunsPkgidDzip;
    private String dcDuns;
    private String dcPkgid;
    private Calendar mailingDate;
	private Calendar originalMailingDate;  // 17.0.0
    private String accountPeriod;
    private String fiscalYear;
    private String statusFlag;
    private String month;
    private String monthlyFiscalYear;
    private String entryFacilityZip;
    private int filteredType;
    private String serviceTypeCode;
    private int systemType;
    private boolean invalidDuns;
    private long postageStatementSequenceNumber;
    private String errorDescription;
    private double length;
    private double width;
    private double height;
    private double dimensionalWeight;
    private String postalRoutingBarcodeIndicator;
    private BigDecimal calculatedPostage = new BigDecimal(0.00);;
	private BigDecimal calculatedTotalPostage = new BigDecimal(0.00);
    private BigDecimal calculatedSurcharge = new BigDecimal(0.00);;
    private BigDecimal postageDifference = new BigDecimal(0.00);;
    private String mailerMailClass;
    private String mailerDestRateInd;
    private String mailerRateInd;
    private String mailerProcessCat;
    private String mailerZone;
    private String mailerRoutingBarcode;
    private String mailerSurchargeType;
    private String rateIngredModInd;
    private boolean isDimWeightUsed = false;
    private BigDecimal publishedPostage = new BigDecimal(0.00);;
    private BigDecimal publishedTotalPostage = new BigDecimal(0.00);;
    private double mailerDimWeight;
    private double mailerWeight;
    private String rateSchedule;
	private String evsWwsDiscountSurchargeType = "";
    private String priceType;
    private String filteredMessage="";
    private int warningType;
    private String warningValue;
    private String warningMessage="";
	private String corpPriceType;
	private boolean roundUpHalfPound = true;
	private String tier = "*"; 
	private double eFileVersionNumber;
	private String detailRecId;
	private String stc;
	private String barcodeConstruct;
	private String destinationFacilityType;
	private String postalCode;
	private String carrierRoute;
	private String logisticsManagerMailer;
	private String mailerOwnerMailerId;
	private String containerId1;
	private String containerType1;
	private String containerId2;
	private String containerType2;
	private String containerId3;
	private String containerType3;
	private String mailerOwnerCRID;
	private String fastReservationNumber;
	private int fastScheduledInductionDate;
	private int fastScheduledInducationTime;
	private int paymentAccountNumber;
	private int paymentMethod;
	private String postOfficeOfAccountZipCode;
	private String meterSerialNumber;
	private String chargeBackCode;
	private String postageType;
	private String CSSCNumber;
	private String CSSCProductId;
	private String discountType;
	private double discountAmount;
	private String nonIncidentalEnclosureProcessCat;
	private String openDistributedContentsInd;
	private String filler;	
	private int metricsSeqNo;
	private String manifestedSurchargeType;
	private double manifestedSurchargeAmount;
	private boolean validPermit=true;
	private boolean duplicateFlag=false;
	private String financeNumber;
	private String referrer = "upload";  
	private int psGroupNo = 0; 
	private int lineNumber = 0; 
	private String destinationDeliveryPoint = "0";  
	private String formType; 
	private String wwsProcessingCategory; 
    private BigDecimal calcFeeFuel = new BigDecimal(0.0000); 
    private BigDecimal calcFeePPI = new BigDecimal(0.0000);  
    boolean zipEDA = false;  
    boolean fuelFeeEnabled = false;  
    private String tpbFlag = "N";   
    private String intlMailInd = "N"; 
    private String deliveryOptionIndicator="";  
    private String removalIndicator="";  
    private String overlabelIndicator="";  
    private String overlabelBarcodeConstructCode="";  
    private String overlabelNumber="";  
    private String customerReferenceNumber2="";  
    private String recipientName="";  
    private String deliveryAddress="";  
    private String ancillaryServiceEndorsement="";  
    private String addressServiceParticipantCode="";  
    private String keyLine="";  
    private String returnAddress="";  
    private String returnAddressCity="";  
    private String returnAddressState="";  
    private String returnAddressZip="";  
    private String logisticMailingFacilityCRID="";  
    private String altPic=""; 
    private String altDuns=""; 
    private String altPkgId=""; 
    private String altBarConstCode=""; 
    private String altSource=""; 
    private String altDunsPkgIdZip=""; 
    private String internationalProcessStatus=""; 
    private String serialNumber=""; 
    private String applicationIdentifier=""; 
    private String originCountryCode=""; 
    private int priceGroup; 
    private String apsStatusIndicator;
    private boolean updateOverlabel = false;	
    private int convRateMapSeqNum;
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getPicCode() {
		return picCode;
	}
	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	public String getSubFileNumber() {
		return subFileNumber;
	}
	public void setSubFileNumber(String subFileNumber) {
		this.subFileNumber = subFileNumber;
	}
	public String getClassOfMail() {
		return classOfMail;
	}
	public void setClassOfMail(String classOfMail) {
		this.classOfMail = classOfMail;
	}
	public String getDestinationZip() {
		return destinationZip;
	}
	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}
	public String getDestinationZipPlus4() {
		return destinationZipPlus4;
	}
	public void setDestinationZipPlus4(String destinationZipPlus4) {
		this.destinationZipPlus4 = destinationZipPlus4;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public BigDecimal getTotalPostage() {
		return totalPostage;
	}
	public void setTotalPostage(BigDecimal totalPostage) {
		this.totalPostage = totalPostage;
	}
	public String getUomCode() {
		return uomCode;
	}
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getProcessingCategory() {
		return processingCategory;
	}
	public void setProcessingCategory(String processingCategory) {
		this.processingCategory = processingCategory;
	}
	public String getDestinationRateIndicator() {
		return destinationRateIndicator;
	}
	public void setDestinationRateIndicator(String destinationRateIndicator) {
		this.destinationRateIndicator = destinationRateIndicator;
	}
	public String getRateIndicator() {
		return rateIndicator;
	}
	public void setRateIndicator(String rateIndicator) {
		this.rateIndicator = rateIndicator;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getPoBoxIndicator() {
		return poBoxIndicator;
	}
	public void setPoBoxIndicator(String poBoxIndicator) {
		this.poBoxIndicator = poBoxIndicator;
	}
	public String getWaiverOfSignature() {
		return waiverOfSignature;
	}
	public void setWaiverOfSignature(String waiverOfSignature) {
		this.waiverOfSignature = waiverOfSignature;
	}
	public String getNoWeekendHolidayDelivery() {
		return noWeekendHolidayDelivery;
	}
	public void setNoWeekendHolidayDelivery(String noWeekendHolidayDelivery) {
		this.noWeekendHolidayDelivery = noWeekendHolidayDelivery;
	}
	public double getArticleValue() {
		return articleValue;
	}
	public void setArticleValue(double articleValue) {
		this.articleValue = articleValue;
	}
	public double getCodAmountDueSender() {
		return codAmountDueSender;
	}
	public void setCodAmountDueSender(double codAmountDueSender) {
		this.codAmountDueSender = codAmountDueSender;
	}
	public BigDecimal getHandlingCharge() {
		return handlingCharge;
	}
	public void setHandlingCharge(BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}
	public String getDuns() {
		return duns;
	}
	public void setDuns(String duns) {
		this.duns = duns;
	}
	public String getCustomerInternalReference() {
		return customerInternalReference;
	}
	public void setCustomerInternalReference(String customerInternalReference) {
		this.customerInternalReference = customerInternalReference;
	}
	public String getSurchargeType() {
		return surchargeType;
	}
	public void setSurchargeType(String surchargeType) {
		this.surchargeType = surchargeType;
	}
	public BigDecimal getSurchargeAmount() {
		return surchargeAmount;
	}
	public void setSurchargeAmount(BigDecimal surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}
	public String getNonIncidentalEnclosureRateIndicator() {
		return nonIncidentalEnclosureRateIndicator;
	}
	public void setNonIncidentalEnclosureRateIndicator(
			String nonIncidentalEnclosureRateIndicator) {
		this.nonIncidentalEnclosureRateIndicator = nonIncidentalEnclosureRateIndicator;
	}
	public String getNonIncidentalEnclosureClass() {
		return nonIncidentalEnclosureClass;
	}
	public void setNonIncidentalEnclosureClass(String nonIncidentalEnclosureClass) {
		this.nonIncidentalEnclosureClass = nonIncidentalEnclosureClass;
	}
	public BigDecimal getNonIncidentalEnclosurePostage() {
		return nonIncidentalEnclosurePostage;
	}
	public void setNonIncidentalEnclosurePostage(
			BigDecimal nonIncidentalEnclosurePostage) {
		this.nonIncidentalEnclosurePostage = nonIncidentalEnclosurePostage;
	}
	public double getNonIncidentalEnclosureWeight() {
		return nonIncidentalEnclosureWeight;
	}
	public void setNonIncidentalEnclosureWeight(double nonIncidentalEnclosureWeight) {
		this.nonIncidentalEnclosureWeight = nonIncidentalEnclosureWeight;
	}
	public int getCustomsDesignatedNumber() {
		return customsDesignatedNumber;
	}
	public void setCustomsDesignatedNumber(int customsDesignatedNumber) {
		this.customsDesignatedNumber = customsDesignatedNumber;
	}
	public String getCorpDuns() {
		return corpDuns;
	}
	public void setCorpDuns(String corpDuns) {
		this.corpDuns = corpDuns;
	}
	public String getDcDunsPkgidDzip() {
		return dcDunsPkgidDzip;
	}
	public void setDcDunsPkgidDzip(String dcDunsPkgidDzip) {
		this.dcDunsPkgidDzip = dcDunsPkgidDzip;
	}
	public String getDcDuns() {
		return dcDuns;
	}
	public void setDcDuns(String dcDuns) {
		this.dcDuns = dcDuns;
	}
	public String getDcPkgid() {
		return dcPkgid;
	}
	public void setDcPkgid(String dcPkgid) {
		this.dcPkgid = dcPkgid;
	}
	public Calendar getMailingDate() {
		return mailingDate;
	}
	public void setMailingDate(Calendar mailingDate) {
		this.mailingDate = mailingDate;
	}
	public Calendar getOriginalMailingDate() {
		return originalMailingDate;
	}
	public void setOriginalMailingDate(Calendar originalMailingDate) {
		this.originalMailingDate = originalMailingDate;
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
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
	public String getEntryFacilityZip() {
		return entryFacilityZip;
	}
	public void setEntryFacilityZip(String entryFacilityZip) {
		this.entryFacilityZip = entryFacilityZip;
	}
	public int getFilteredType() {
		return filteredType;
	}
	public void setFilteredType(int filteredType) {
		this.filteredType = filteredType;
	}
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}
	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	public int getSystemType() {
		return systemType;
	}
	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}
	public boolean isInvalidDuns() {
		return invalidDuns;
	}
	public void setInvalidDuns(boolean invalidDuns) {
		this.invalidDuns = invalidDuns;
	}
	public long getPostageStatementSequenceNumber() {
		return postageStatementSequenceNumber;
	}
	public void setPostageStatementSequenceNumber(
			long postageStatementSequenceNumber) {
		this.postageStatementSequenceNumber = postageStatementSequenceNumber;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getDimensionalWeight() {
		return dimensionalWeight;
	}
	public void setDimensionalWeight(double dimensionalWeight) {
		this.dimensionalWeight = dimensionalWeight;
	}
	public String getPostalRoutingBarcodeIndicator() {
		return postalRoutingBarcodeIndicator;
	}
	public void setPostalRoutingBarcodeIndicator(
			String postalRoutingBarcodeIndicator) {
		this.postalRoutingBarcodeIndicator = postalRoutingBarcodeIndicator;
	}
	public BigDecimal getCalculatedPostage() {
		return calculatedPostage;
	}
	public void setCalculatedPostage(BigDecimal calculatedPostage) {
		this.calculatedPostage = calculatedPostage;
	}
	public BigDecimal getCalculatedTotalPostage() {
		return calculatedTotalPostage;
	}
	public void setCalculatedTotalPostage(BigDecimal calculatedTotalPostage) {
		this.calculatedTotalPostage = calculatedTotalPostage;
	}
	public BigDecimal getCalculatedSurcharge() {
		return calculatedSurcharge;
	}
	public void setCalculatedSurcharge(BigDecimal calculatedSurcharge) {
		this.calculatedSurcharge = calculatedSurcharge;
	}
	public BigDecimal getPostageDifference() {
		return postageDifference;
	}
	public void setPostageDifference(BigDecimal postageDifference) {
		this.postageDifference = postageDifference;
	}
	public String getMailerMailClass() {
		return mailerMailClass;
	}
	public void setMailerMailClass(String mailerMailClass) {
		this.mailerMailClass = mailerMailClass;
	}
	public String getMailerDestRateInd() {
		return mailerDestRateInd;
	}
	public void setMailerDestRateInd(String mailerDestRateInd) {
		this.mailerDestRateInd = mailerDestRateInd;
	}
	public String getMailerRateInd() {
		return mailerRateInd;
	}
	public void setMailerRateInd(String mailerRateInd) {
		this.mailerRateInd = mailerRateInd;
	}
	public String getMailerProcessCat() {
		return mailerProcessCat;
	}
	public void setMailerProcessCat(String mailerProcessCat) {
		this.mailerProcessCat = mailerProcessCat;
	}
	public String getMailerZone() {
		return mailerZone;
	}
	public void setMailerZone(String mailerZone) {
		this.mailerZone = mailerZone;
	}
	public String getMailerRoutingBarcode() {
		return mailerRoutingBarcode;
	}
	public void setMailerRoutingBarcode(String mailerRoutingBarcode) {
		this.mailerRoutingBarcode = mailerRoutingBarcode;
	}
	public String getMailerSurchargeType() {
		return mailerSurchargeType;
	}
	public void setMailerSurchargeType(String mailerSurchargeType) {
		this.mailerSurchargeType = mailerSurchargeType;
	}
	public String getRateIngredModInd() {
		return rateIngredModInd;
	}
	public void setRateIngredModInd(String rateIngredModInd) {
		this.rateIngredModInd = rateIngredModInd;
	}
	public boolean isDimWeightUsed() {
		return isDimWeightUsed;
	}
	public void setDimWeightUsed(boolean isDimWeightUsed) {
		this.isDimWeightUsed = isDimWeightUsed;
	}
	public BigDecimal getPublishedPostage() {
		return publishedPostage;
	}
	public void setPublishedPostage(BigDecimal publishedPostage) {
		this.publishedPostage = publishedPostage;
	}
	public BigDecimal getPublishedTotalPostage() {
		return publishedTotalPostage;
	}
	public void setPublishedTotalPostage(BigDecimal publishedTotalPostage) {
		this.publishedTotalPostage = publishedTotalPostage;
	}
	public double getMailerDimWeight() {
		return mailerDimWeight;
	}
	public void setMailerDimWeight(double mailerDimWeight) {
		this.mailerDimWeight = mailerDimWeight;
	}
	public double getMailerWeight() {
		return mailerWeight;
	}
	public void setMailerWeight(double mailerWeight) {
		this.mailerWeight = mailerWeight;
	}
	public String getRateSchedule() {
		return rateSchedule;
	}
	public void setRateSchedule(String rateSchedule) {
		this.rateSchedule = rateSchedule;
	}
	public String getEvsWwsDiscountSurchargeType() {
		return evsWwsDiscountSurchargeType;
	}
	public void setEvsWwsDiscountSurchargeType(String evsWwsDiscountSurchargeType) {
		this.evsWwsDiscountSurchargeType = evsWwsDiscountSurchargeType;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getFilteredMessage() {
		return filteredMessage;
	}
	public void setFilteredMessage(String filteredMessage) {
		this.filteredMessage = filteredMessage;
	}
	public int getWarningType() {
		return warningType;
	}
	public void setWarningType(int warningType) {
		this.warningType = warningType;
	}
	public String getWarningValue() {
		return warningValue;
	}
	public void setWarningValue(String warningValue) {
		this.warningValue = warningValue;
	}
	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	public String getCorpPriceType() {
		return corpPriceType;
	}
	public void setCorpPriceType(String corpPriceType) {
		this.corpPriceType = corpPriceType;
	}
	public boolean isRoundUpHalfPound() {
		return roundUpHalfPound;
	}
	public void setRoundUpHalfPound(boolean roundUpHalfPound) {
		this.roundUpHalfPound = roundUpHalfPound;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public double geteFileVersionNumber() {
		return eFileVersionNumber;
	}
	public void seteFileVersionNumber(double eFileVersionNumber) {
		this.eFileVersionNumber = eFileVersionNumber;
	}
	public String getDetailRecId() {
		return detailRecId;
	}
	public void setDetailRecId(String detailRecId) {
		this.detailRecId = detailRecId;
	}
	public String getStc() {
		return stc;
	}
	public void setStc(String stc) {
		this.stc = stc;
	}
	public String getBarcodeConstruct() {
		return barcodeConstruct;
	}
	public void setBarcodeConstruct(String barcodeConstruct) {
		this.barcodeConstruct = barcodeConstruct;
	}
	public String getDestinationFacilityType() {
		return destinationFacilityType;
	}
	public void setDestinationFacilityType(String destinationFacilityType) {
		this.destinationFacilityType = destinationFacilityType;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCarrierRoute() {
		return carrierRoute;
	}
	public void setCarrierRoute(String carrierRoute) {
		this.carrierRoute = carrierRoute;
	}
	public String getLogisticsManagerMailer() {
		return logisticsManagerMailer;
	}
	public void setLogisticsManagerMailer(String logisticsManagerMailer) {
		this.logisticsManagerMailer = logisticsManagerMailer;
	}
	public String getMailerOwnerMailerId() {
		return mailerOwnerMailerId;
	}
	public void setMailerOwnerMailerId(String mailerOwnerMailerId) {
		this.mailerOwnerMailerId = mailerOwnerMailerId;
	}
	public String getContainerId1() {
		return containerId1;
	}
	public void setContainerId1(String containerId1) {
		this.containerId1 = containerId1;
	}
	public String getContainerType1() {
		return containerType1;
	}
	public void setContainerType1(String containerType1) {
		this.containerType1 = containerType1;
	}
	public String getContainerId2() {
		return containerId2;
	}
	public void setContainerId2(String containerId2) {
		this.containerId2 = containerId2;
	}
	public String getContainerType2() {
		return containerType2;
	}
	public void setContainerType2(String containerType2) {
		this.containerType2 = containerType2;
	}
	public String getContainerId3() {
		return containerId3;
	}
	public void setContainerId3(String containerId3) {
		this.containerId3 = containerId3;
	}
	public String getContainerType3() {
		return containerType3;
	}
	public void setContainerType3(String containerType3) {
		this.containerType3 = containerType3;
	}
	public String getMailerOwnerCRID() {
		return mailerOwnerCRID;
	}
	public void setMailerOwnerCRID(String mailerOwnerCRID) {
		this.mailerOwnerCRID = mailerOwnerCRID;
	}
	public String getFastReservationNumber() {
		return fastReservationNumber;
	}
	public void setFastReservationNumber(String fastReservationNumber) {
		this.fastReservationNumber = fastReservationNumber;
	}
	public int getFastScheduledInductionDate() {
		return fastScheduledInductionDate;
	}
	public void setFastScheduledInductionDate(int fastScheduledInductionDate) {
		this.fastScheduledInductionDate = fastScheduledInductionDate;
	}
	public int getFastScheduledInducationTime() {
		return fastScheduledInducationTime;
	}
	public void setFastScheduledInducationTime(int fastScheduledInducationTime) {
		this.fastScheduledInducationTime = fastScheduledInducationTime;
	}
	public int getPaymentAccountNumber() {
		return paymentAccountNumber;
	}
	public void setPaymentAccountNumber(int paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}
	public int getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPostOfficeOfAccountZipCode() {
		return postOfficeOfAccountZipCode;
	}
	public void setPostOfficeOfAccountZipCode(String postOfficeOfAccountZipCode) {
		this.postOfficeOfAccountZipCode = postOfficeOfAccountZipCode;
	}
	public String getMeterSerialNumber() {
		return meterSerialNumber;
	}
	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}
	public String getChargeBackCode() {
		return chargeBackCode;
	}
	public void setChargeBackCode(String chargeBackCode) {
		this.chargeBackCode = chargeBackCode;
	}
	public String getPostageType() {
		return postageType;
	}
	public void setPostageType(String postageType) {
		this.postageType = postageType;
	}
	public String getCSSCNumber() {
		return CSSCNumber;
	}
	public void setCSSCNumber(String cSSCNumber) {
		CSSCNumber = cSSCNumber;
	}
	public String getCSSCProductId() {
		return CSSCProductId;
	}
	public void setCSSCProductId(String cSSCProductId) {
		CSSCProductId = cSSCProductId;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getNonIncidentalEnclosureProcessCat() {
		return nonIncidentalEnclosureProcessCat;
	}
	public void setNonIncidentalEnclosureProcessCat(
			String nonIncidentalEnclosureProcessCat) {
		this.nonIncidentalEnclosureProcessCat = nonIncidentalEnclosureProcessCat;
	}
	public String getOpenDistributedContentsInd() {
		return openDistributedContentsInd;
	}
	public void setOpenDistributedContentsInd(String openDistributedContentsInd) {
		this.openDistributedContentsInd = openDistributedContentsInd;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}
	public void setMetricsSeqNo(int metricsSeqNo) {
		this.metricsSeqNo = metricsSeqNo;
	}
	public String getManifestedSurchargeType() {
		return manifestedSurchargeType;
	}
	public void setManifestedSurchargeType(String manifestedSurchargeType) {
		this.manifestedSurchargeType = manifestedSurchargeType;
	}
	public double getManifestedSurchargeAmount() {
		return manifestedSurchargeAmount;
	}
	public void setManifestedSurchargeAmount(double manifestedSurchargeAmount) {
		this.manifestedSurchargeAmount = manifestedSurchargeAmount;
	}
	public boolean isValidPermit() {
		return validPermit;
	}
	public void setValidPermit(boolean validPermit) {
		this.validPermit = validPermit;
	}
	public boolean isDuplicateFlag() {
		return duplicateFlag;
	}
	public void setDuplicateFlag(boolean duplicateFlag) {
		this.duplicateFlag = duplicateFlag;
	}
	public String getFinanceNumber() {
		return financeNumber;
	}
	public void setFinanceNumber(String financeNumber) {
		this.financeNumber = financeNumber;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public int getPsGroupNo() {
		return psGroupNo;
	}
	public void setPsGroupNo(int psGroupNo) {
		this.psGroupNo = psGroupNo;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getDestinationDeliveryPoint() {
		return destinationDeliveryPoint;
	}
	public void setDestinationDeliveryPoint(String destinationDeliveryPoint) {
		this.destinationDeliveryPoint = destinationDeliveryPoint;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getWwsProcessingCategory() {
		return wwsProcessingCategory;
	}
	public void setWwsProcessingCategory(String wwsProcessingCategory) {
		this.wwsProcessingCategory = wwsProcessingCategory;
	}
	public BigDecimal getCalcFeeFuel() {
		return calcFeeFuel;
	}
	public void setCalcFeeFuel(BigDecimal calcFeeFuel) {
		this.calcFeeFuel = calcFeeFuel;
	}
	public BigDecimal getCalcFeePPI() {
		return calcFeePPI;
	}
	public void setCalcFeePPI(BigDecimal calcFeePPI) {
		this.calcFeePPI = calcFeePPI;
	}
	public boolean isZipEDA() {
		return zipEDA;
	}
	public void setZipEDA(boolean zipEDA) {
		this.zipEDA = zipEDA;
	}
	public boolean isFuelFeeEnabled() {
		return fuelFeeEnabled;
	}
	public void setFuelFeeEnabled(boolean fuelFeeEnabled) {
		this.fuelFeeEnabled = fuelFeeEnabled;
	}
	public String getTpbFlag() {
		return tpbFlag;
	}
	public void setTpbFlag(String tpbFlag) {
		this.tpbFlag = tpbFlag;
	}
	public String getIntlMailInd() {
		return intlMailInd;
	}
	public void setIntlMailInd(String intlMailInd) {
		this.intlMailInd = intlMailInd;
	}
	public String getDeliveryOptionIndicator() {
		return deliveryOptionIndicator;
	}
	public void setDeliveryOptionIndicator(String deliveryOptionIndicator) {
		this.deliveryOptionIndicator = deliveryOptionIndicator;
	}
	public String getRemovalIndicator() {
		return removalIndicator;
	}
	public void setRemovalIndicator(String removalIndicator) {
		this.removalIndicator = removalIndicator;
	}
	public String getOverlabelIndicator() {
		return overlabelIndicator;
	}
	public void setOverlabelIndicator(String overlabelIndicator) {
		this.overlabelIndicator = overlabelIndicator;
	}
	public String getOverlabelBarcodeConstructCode() {
		return overlabelBarcodeConstructCode;
	}
	public void setOverlabelBarcodeConstructCode(
			String overlabelBarcodeConstructCode) {
		this.overlabelBarcodeConstructCode = overlabelBarcodeConstructCode;
	}
	public String getOverlabelNumber() {
		return overlabelNumber;
	}
	public void setOverlabelNumber(String overlabelNumber) {
		this.overlabelNumber = overlabelNumber;
	}
	public String getCustomerReferenceNumber2() {
		return customerReferenceNumber2;
	}
	public void setCustomerReferenceNumber2(String customerReferenceNumber2) {
		this.customerReferenceNumber2 = customerReferenceNumber2;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getAncillaryServiceEndorsement() {
		return ancillaryServiceEndorsement;
	}
	public void setAncillaryServiceEndorsement(String ancillaryServiceEndorsement) {
		this.ancillaryServiceEndorsement = ancillaryServiceEndorsement;
	}
	public String getAddressServiceParticipantCode() {
		return addressServiceParticipantCode;
	}
	public void setAddressServiceParticipantCode(
			String addressServiceParticipantCode) {
		this.addressServiceParticipantCode = addressServiceParticipantCode;
	}
	public String getKeyLine() {
		return keyLine;
	}
	public void setKeyLine(String keyLine) {
		this.keyLine = keyLine;
	}
	public String getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	public String getReturnAddressCity() {
		return returnAddressCity;
	}
	public void setReturnAddressCity(String returnAddressCity) {
		this.returnAddressCity = returnAddressCity;
	}
	public String getReturnAddressState() {
		return returnAddressState;
	}
	public void setReturnAddressState(String returnAddressState) {
		this.returnAddressState = returnAddressState;
	}
	public String getReturnAddressZip() {
		return returnAddressZip;
	}
	public void setReturnAddressZip(String returnAddressZip) {
		this.returnAddressZip = returnAddressZip;
	}
	public String getLogisticMailingFacilityCRID() {
		return logisticMailingFacilityCRID;
	}
	public void setLogisticMailingFacilityCRID(String logisticMailingFacilityCRID) {
		this.logisticMailingFacilityCRID = logisticMailingFacilityCRID;
	}
	public String getAltPic() {
		return altPic;
	}
	public void setAltPic(String altPic) {
		this.altPic = altPic;
	}
	public String getAltDuns() {
		return altDuns;
	}
	public void setAltDuns(String altDuns) {
		this.altDuns = altDuns;
	}
	public String getAltPkgId() {
		return altPkgId;
	}
	public void setAltPkgId(String altPkgId) {
		this.altPkgId = altPkgId;
	}
	public String getAltBarConstCode() {
		return altBarConstCode;
	}
	public void setAltBarConstCode(String altBarConstCode) {
		this.altBarConstCode = altBarConstCode;
	}
	public String getAltSource() {
		return altSource;
	}
	public void setAltSource(String altSource) {
		this.altSource = altSource;
	}
	public String getAltDunsPkgIdZip() {
		return altDunsPkgIdZip;
	}
	public void setAltDunsPkgIdZip(String altDunsPkgIdZip) {
		this.altDunsPkgIdZip = altDunsPkgIdZip;
	}
	public String getInternationalProcessStatus() {
		return internationalProcessStatus;
	}
	public void setInternationalProcessStatus(String internationalProcessStatus) {
		this.internationalProcessStatus = internationalProcessStatus;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}
	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}
	public String getOriginCountryCode() {
		return originCountryCode;
	}
	public void setOriginCountryCode(String originCountryCode) {
		this.originCountryCode = originCountryCode;
	}
	public int getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(int priceGroup) {
		this.priceGroup = priceGroup;
	}
	public String getApsStatusIndicator() {
		return apsStatusIndicator;
	}
	public void setApsStatusIndicator(String apsStatusIndicator) {
		this.apsStatusIndicator = apsStatusIndicator;
	}
	public boolean isUpdateOverlabel() {
		return updateOverlabel;
	}
	public void setUpdateOverlabel(boolean updateOverlabel) {
		this.updateOverlabel = updateOverlabel;
	}
	public int getConvRateMapSeqNum() {
		return convRateMapSeqNum;
	}
	public void setConvRateMapSeqNum(int convRateMapSeqNum) {
		this.convRateMapSeqNum = convRateMapSeqNum;
	}
	@Override
	public String toString() {
		return "Detail [sequenceNumber=" + sequenceNumber + ", picCode="
				+ picCode + ", fileNumber=" + fileNumber + ", subFileNumber="
				+ subFileNumber + ", classOfMail=" + classOfMail
				+ ", destinationZip=" + destinationZip
				+ ", destinationZipPlus4=" + destinationZipPlus4
				+ ", countryCode=" + countryCode + ", postage=" + postage
				+ ", totalPostage=" + totalPostage + ", uomCode=" + uomCode
				+ ", weight=" + weight + ", processingCategory="
				+ processingCategory + ", destinationRateIndicator="
				+ destinationRateIndicator + ", rateIndicator=" + rateIndicator
				+ ", zone=" + zone + ", poBoxIndicator=" + poBoxIndicator
				+ ", waiverOfSignature=" + waiverOfSignature
				+ ", noWeekendHolidayDelivery=" + noWeekendHolidayDelivery
				+ ", articleValue=" + articleValue + ", codAmountDueSender="
				+ codAmountDueSender + ", handlingCharge=" + handlingCharge
				+ ", duns=" + duns + ", customerInternalReference="
				+ customerInternalReference + ", surchargeType="
				+ surchargeType + ", surchargeAmount=" + surchargeAmount
				+ ", nonIncidentalEnclosureRateIndicator="
				+ nonIncidentalEnclosureRateIndicator
				+ ", nonIncidentalEnclosureClass="
				+ nonIncidentalEnclosureClass
				+ ", nonIncidentalEnclosurePostage="
				+ nonIncidentalEnclosurePostage
				+ ", nonIncidentalEnclosureWeight="
				+ nonIncidentalEnclosureWeight + ", customsDesignatedNumber="
				+ customsDesignatedNumber + ", corpDuns=" + corpDuns
				+ ", dcDunsPkgidDzip=" + dcDunsPkgidDzip + ", dcDuns=" + dcDuns
				+ ", dcPkgid=" + dcPkgid + ", mailingDate=" + mailingDate
				+ ", originalMailingDate=" + originalMailingDate
				+ ", accountPeriod=" + accountPeriod + ", fiscalYear="
				+ fiscalYear + ", statusFlag=" + statusFlag + ", month="
				+ month + ", monthlyFiscalYear=" + monthlyFiscalYear
				+ ", entryFacilityZip=" + entryFacilityZip + ", filteredType="
				+ filteredType + ", serviceTypeCode=" + serviceTypeCode
				+ ", systemType=" + systemType + ", invalidDuns=" + invalidDuns
				+ ", postageStatementSequenceNumber="
				+ postageStatementSequenceNumber + ", errorDescription="
				+ errorDescription + ", length=" + length + ", width=" + width
				+ ", height=" + height + ", dimensionalWeight="
				+ dimensionalWeight + ", postalRoutingBarcodeIndicator="
				+ postalRoutingBarcodeIndicator + ", calculatedPostage="
				+ calculatedPostage + ", calculatedTotalPostage="
				+ calculatedTotalPostage + ", calculatedSurcharge="
				+ calculatedSurcharge + ", postageDifference="
				+ postageDifference + ", mailerMailClass=" + mailerMailClass
				+ ", mailerDestRateInd=" + mailerDestRateInd
				+ ", mailerRateInd=" + mailerRateInd + ", mailerProcessCat="
				+ mailerProcessCat + ", mailerZone=" + mailerZone
				+ ", mailerRoutingBarcode=" + mailerRoutingBarcode
				+ ", mailerSurchargeType=" + mailerSurchargeType
				+ ", rateIngredModInd=" + rateIngredModInd
				+ ", isDimWeightUsed=" + isDimWeightUsed
				+ ", publishedPostage=" + publishedPostage
				+ ", publishedTotalPostage=" + publishedTotalPostage
				+ ", mailerDimWeight=" + mailerDimWeight + ", mailerWeight="
				+ mailerWeight + ", rateSchedule=" + rateSchedule
				+ ", evsWwsDiscountSurchargeType="
				+ evsWwsDiscountSurchargeType + ", priceType=" + priceType
				+ ", filteredMessage=" + filteredMessage + ", warningType="
				+ warningType + ", warningValue=" + warningValue
				+ ", warningMessage=" + warningMessage + ", corpPriceType="
				+ corpPriceType + ", roundUpHalfPound=" + roundUpHalfPound
				+ ", tier=" + tier + ", eFileVersionNumber="
				+ eFileVersionNumber + ", detailRecId=" + detailRecId
				+ ", stc=" + stc + ", barcodeConstruct=" + barcodeConstruct
				+ ", destinationFacilityType=" + destinationFacilityType
				+ ", postalCode=" + postalCode + ", carrierRoute="
				+ carrierRoute + ", logisticsManagerMailer="
				+ logisticsManagerMailer + ", mailerOwnerMailerId="
				+ mailerOwnerMailerId + ", containerId1=" + containerId1
				+ ", containerType1=" + containerType1 + ", containerId2="
				+ containerId2 + ", containerType2=" + containerType2
				+ ", containerId3=" + containerId3 + ", containerType3="
				+ containerType3 + ", mailerOwnerCRID=" + mailerOwnerCRID
				+ ", fastReservationNumber=" + fastReservationNumber
				+ ", fastScheduledInductionDate=" + fastScheduledInductionDate
				+ ", fastScheduledInducationTime="
				+ fastScheduledInducationTime + ", paymentAccountNumber="
				+ paymentAccountNumber + ", paymentMethod=" + paymentMethod
				+ ", postOfficeOfAccountZipCode=" + postOfficeOfAccountZipCode
				+ ", meterSerialNumber=" + meterSerialNumber
				+ ", chargeBackCode=" + chargeBackCode + ", postageType="
				+ postageType + ", CSSCNumber=" + CSSCNumber
				+ ", CSSCProductId=" + CSSCProductId + ", discountType="
				+ discountType + ", discountAmount=" + discountAmount
				+ ", nonIncidentalEnclosureProcessCat="
				+ nonIncidentalEnclosureProcessCat
				+ ", openDistributedContentsInd=" + openDistributedContentsInd
				+ ", filler=" + filler + ", metricsSeqNo=" + metricsSeqNo
				+ ", manifestedSurchargeType=" + manifestedSurchargeType
				+ ", manifestedSurchargeAmount=" + manifestedSurchargeAmount
				+ ", validPermit=" + validPermit + ", duplicateFlag="
				+ duplicateFlag + ", financeNumber=" + financeNumber
				+ ", referrer=" + referrer + ", psGroupNo=" + psGroupNo
				+ ", lineNumber=" + lineNumber + ", destinationDeliveryPoint="
				+ destinationDeliveryPoint + ", formType=" + formType
				+ ", wwsProcessingCategory=" + wwsProcessingCategory
				+ ", calcFeeFuel=" + calcFeeFuel + ", calcFeePPI=" + calcFeePPI
				+ ", zipEDA=" + zipEDA + ", fuelFeeEnabled=" + fuelFeeEnabled
				+ ", tpbFlag=" + tpbFlag + ", intlMailInd=" + intlMailInd
				+ ", deliveryOptionIndicator=" + deliveryOptionIndicator
				+ ", removalIndicator=" + removalIndicator
				+ ", overlabelIndicator=" + overlabelIndicator
				+ ", overlabelBarcodeConstructCode="
				+ overlabelBarcodeConstructCode + ", overlabelNumber="
				+ overlabelNumber + ", customerReferenceNumber2="
				+ customerReferenceNumber2 + ", recipientName=" + recipientName
				+ ", deliveryAddress=" + deliveryAddress
				+ ", ancillaryServiceEndorsement="
				+ ancillaryServiceEndorsement
				+ ", addressServiceParticipantCode="
				+ addressServiceParticipantCode + ", keyLine=" + keyLine
				+ ", returnAddress=" + returnAddress + ", returnAddressCity="
				+ returnAddressCity + ", returnAddressState="
				+ returnAddressState + ", returnAddressZip=" + returnAddressZip
				+ ", logisticMailingFacilityCRID="
				+ logisticMailingFacilityCRID + ", altPic=" + altPic
				+ ", altDuns=" + altDuns + ", altPkgId=" + altPkgId
				+ ", altBarConstCode=" + altBarConstCode + ", altSource="
				+ altSource + ", altDunsPkgIdZip=" + altDunsPkgIdZip
				+ ", internationalProcessStatus=" + internationalProcessStatus
				+ ", serialNumber=" + serialNumber + ", applicationIdentifier="
				+ applicationIdentifier + ", originCountryCode="
				+ originCountryCode + ", priceGroup=" + priceGroup
				+ ", apsStatusIndicator=" + apsStatusIndicator
				+ ", updateOverlabel=" + updateOverlabel
				+ ", convRateMapSeqNum=" + convRateMapSeqNum + "]";
	}
}
