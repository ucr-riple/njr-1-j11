/*
 * ManifestDetail.java
 *
 * Author: Nat Meo
 * 
 *  Change Log:
 * 		03/01/2007 - Jose Rudel R. de Castro 
 * 		Updated for R-2006 Rate Case
 *      Updated to handle new manifest file format.
 *
 *			09/02/22007 - Ivan Sutanto
 * 			* This is an Edit Rule for 
 *						Remedy# = 2347406. 
 *						CR# = 20973,20974
 *						Adding more field to store the original values in new fileds while the modified values will be stored in the base.
 *
 *			04/04/2008 Rel 16.1 Add more details in toStirng()
 *
 *			05/15/2008 REL 17.1, Discount Rebate Initiated
 *			
 *			01/14/2009 REL19.0.0, change toString
 *
 * Manifest detail record.
 */
/*
 * $Workfile:   ManifestDetail.java  $
 * $Revision:   1.26  $
 * =================================== 
 * $Log:   P:/databases/PostalOne!/archives/PostalOne!/P1_DEV/e-VS/EvsServer/com/usps/evs/vo/ManifestDetail.java-arc  $
 * 
 *    Rev 1.26   Jun 26 2009 14:33:16   sutantod
 * Add more info in toString such as warning value and message
 * 
 *    Rev 1.25   Jun 10 2009 20:57:28   sutantod
 * Add warning type, value and message to be inserted to dc_detail01
 * 
 */
 
package com.usps.evs.vo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;

// Jose Rudel R. de Castro - Updated for R-2006 Rate Case
public class ManifestDetail implements CommonAttributes, DetailRateI, ContractPaymentInfoI 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2411417598154618205L;
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
    //private String processingCategory;
    //private String destinationRateIndicator;
    //private String rateIndicator;
    //private String zone;
    private String poBoxIndicator;
    private String waiverOfSignature;
    //private String noWeekendHolidayDelivery;
    private double articleValue;
    //private double codAmountDueSender;
    private BigDecimal handlingCharge = new BigDecimal(0.00);;
    private Vector<SpecialServiceVO> servicesList;
    private String duns;
    private String customerInternalReference;
    private String surchargeType;
    private BigDecimal surchargeAmount = new BigDecimal(0.00);;
    //private String nonIncidentalEnclosureRateIndicator;
    //private String nonIncidentalEnclosureClass;
    //private BigDecimal nonIncidentalEnclosurePostage = new BigDecimal(0.00);;
    //private double nonIncidentalEnclosureWeight;
    //private int customsDesignatedNumber;
    private String corpDuns;
    private String dcDunsPkgidDzip;
    private String dcDuns;
    private String dcPkgid;
    private Calendar mailingDate;
	private Calendar originalMailingDate;  // 17.0.0
    private String accountPeriod;
    private String fiscalYear;
    //private String statusFlag;
    private String month;
    private String monthlyFiscalYear;
    private String entryFacilityZip;
    private int filteredType;
    private String serviceTypeCode;
    private int systemType;
    private boolean invalidDuns;
    private long postageStatementSequenceNumber;
    //private String errorDescription;
    //Jose de Castro - Added length, width, height, and dimensionalWeight per R-2006 Rate Case
    private double length;
    private double width;
    private double height;
    private double dimensionalWeight;
    //Jose de Castro - Added postalRoutingBarcodeIndicator per R-2006 Rate Case
    private String postalRoutingBarcodeIndicator;
    private BigDecimal calculatedPostage = new BigDecimal(0.00);;
	private BigDecimal calculatedTotalPostage = new BigDecimal(0.00);
    private BigDecimal calculatedSurcharge = new BigDecimal(0.00);;
    private BigDecimal postageDifference = new BigDecimal(0.00);;
    private Vector<SpecialServiceVO> calculatedServicesList;
    // 09-02-2007 - Ivan Sutanto CR 20973, 20974
    private String mailerMailClass;
    private String mailerDestRateInd;
    private String mailerRateInd;
    private String mailerProcessCat;
    private String mailerZone;
    private String mailerRoutingBarcode;
    private String mailerSurchargeType;
    private String rateIngredModInd;
    private boolean isDimWeightUsed = false;
    // End Of Change
    // REL 17.1
    private BigDecimal publishedPostage = new BigDecimal(0.00);;
    private BigDecimal publishedTotalPostage = new BigDecimal(0.00);;
    //private ProductVO productVO;
    //private CustomDetail customDetail = new CustomDetail();
    private double mailerDimWeight;
    private double mailerWeight;
    //private String rateSchedule;
    // REL18.0.0-DiscountSurcharge
	private String evsWwsDiscountSurchargeType = Constants.DEFAULT_VALUE_EVS_DISCOUNT_SURCHARGE;
    //REL 18.1.0 CommercialPlus changes
    private String priceType;
    //REL 19.0.0
    private String filteredMessage="";
    //REL21.0
    //private int warningType;
    //private String warningValue;
    //private String warningMessage="";
	//REL23.0
	private String corpPriceType;
	private boolean roundUpHalfPound = true;
	private String tier = "*"; // REL23.0 Default value
	
	/* Beginning Of REL 24.0 Additional Attributes */
	private double eFileVersionNumber;
	private String detailRecId;
	private String stc;
	private String barcodeConstruct;
	public String getBarcodeConstruct() {
		return barcodeConstruct;
	}


	public void setBarcodeConstruct(String barcodeConstruct) {
		this.barcodeConstruct = barcodeConstruct;
	}


	private String destinationFacilityType;
	private String postalCode;
	//private String carrierRoute;
	private String logisticsManagerMailer;
	private String mailerOwnerMailerId;
	//private String containerId1;
	//private String containerType1;
	//private String containerId2;
	//private String containerType2;
	//private String containerId3;
	//private String containerType3;
	private String mailerOwnerCRID;
	//private String fastReservationNumber;
	//private int fastScheduledInductionDate;
	//private int fastScheduledInducationTime;
	private int paymentAccountNumber;
	private int paymentMethod;
	private String postOfficeOfAccountZipCode;
	private String meterSerialNumber;
	private String chargeBackCode;
	private String postageType;
	//private String CSSCNumber;
	//private String CSSCProductId;
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
	
	// additional attribute from header
	private String financeNumber;
	private String referrer = "upload";  // REL 25.0.0
	
	/* End Of REL 24.0 Additional Attributes */
    
	private int psGroupNo = 0; // REL 25.0.0
	private int lineNumber = 0; // REL 25.0.0
	private String destinationDeliveryPoint = "0";  // REL 26.0.0
	
	private String formType; /* REL 26.0 */
	private String wwsProcessingCategory; /* REL 26.0 */
   // private BigDecimal calcFeeEDA = new BigDecimal(0.0000);  /* REL 29.0 */
    private BigDecimal calcFeeFuel = new BigDecimal(0.0000); /* REL 29.0 */
    private BigDecimal calcFeePPI = new BigDecimal(0.0000);  /* REL 29.0 */
    //boolean zipEDA = false;  /* REL 29.0 */
    //boolean fuelFeeEnabled = false;  /* REL 29.0 */
    private String tpbFlag = "N";   /* REL 30 */
    private String intlMailInd = "N"; /*REL 31 */

    //private String deliveryOptionIndicator="";  /* SRS 228 */
    //private String removalIndicator="";  /* SRS 228 */
    //private String overlabelIndicator="";  /* SRS 228 */
    //private String overlabelBarcodeConstructCode="";  /* SRS 228 */
    //private String overlabelNumber="";  /* SRS 228 */
    private String customerReferenceNumber2="";  /* SRS 228 */
    //private String recipientName="";  /* SRS 228 */
    private String deliveryAddress="";  /* SRS 228 */
    //private String ancillaryServiceEndorsement="";  /* SRS 228 */
    //private String addressServiceParticipantCode="";  /* SRS 228 */
    private String keyLine="";  /* SRS 228 */
    private String returnAddress="";  /* SRS 228 */
    private String returnAddressCity="";  /* SRS 228 */
    private String returnAddressState="";  /* SRS 228 */
    private String returnAddressZip="";  /* SRS 228 */
    //private String logisticMailingFacilityCRID="";  /* SRS 228 */
    //private String altPic=""; /* SRS 228 */
    //private String altDuns=""; /* SRS 228 */
    //private String altPkgId=""; /* SRS 228 */
    //private String altBarConstCode=""; /* SRS 228 */
    //private String altSource=""; /* SRS 228 */
    //private String altDunsPkgIdZip=""; /* SRS 228 */
    private String internationalProcessStatus=""; /* SRS 228 */
    //private String serialNumber=""; /* SRS 228 */
    private String applicationIdentifier=""; /* SRS 228 */
    private String originCountryCode=""; /* SRS 228 */
    private int priceGroup; /* SRS 228 */
    //private String apsStatusIndicator;
    //private boolean updateOverlabel = false;	
    //Release 34.0
    //private int convRateMapSeqNum;
    private ArrayList<ByForVO> byForList;
    
    
	/** Creates a new instance of ManifestDetail */
    public ManifestDetail() {
    }


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
    /**
     * Added by Jose Rudel R. de Castro
     * This method sets the length for the 
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @param length The length to set.
     */
    public void setLength(double length){
    	this.length = length;
    }
    /**
     * Added by Jose Rudel R. de Castro
     * This method gets the length for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @return The length.
     */
    public double getLength(){
    	return this.length;
    }
    /**
     * Added by Jose Rudel R. de Castro
     * This method sets the width for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @param width The width to set.
     */
    public void setWidth(double width){
    	this.width = width;
    }
    /**
     * Added by Jose Rudel R. de Cstro
     * This method gets the width for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @return The width.
     */
    public double getWidth(){
    	return this.width;
    }
    /**
     * Added by Jose Rudel R. de Castro
     * This method sets the height for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @param height The height to set.
     */
    public void setHeight(double height){
    	this.height = height;
    }
    /**
     * Added by Jose Rudel R. de Castro
     * This method gets the height for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @return The height.
     */
    public double getHeight(){
    	return this.height;
    }
    /**
     * Added by Jose Rudel R. de Castro
     * This method gets the dimensional weight for the
     * Priority Mail Dimensional Weight Pricing Criteria per
     * R-2006 Rate Case.
     * @param dimensionalWeight The dimensional weight to set.
     */
    public void setDimensionalWeight(double dimensionalWeight){
    	this.dimensionalWeight = dimensionalWeight;
    }
	/**
	 * Added by Jose Rudel R. de Castro
	 * This method gets the dimensional weight for the
	 * Priority Mail Dimensional Weight Pricing Criteria per
	 * R-2006 Rate Case.
	 * @return The dimensional weight.
	 */
    public double getDimensionalWeight(){
    	return this.dimensionalWeight;
    }
	/**
	 * Added by Jose Rudel R. de Castro
	 * This method gets the postal rounting barcode indicator per R-2006 Rate Case.
	 * @param postalRoutingBarcodeIndicator The postalRoutingBarcodeIndicator to set.
	 */
    public void setPostalRoutingBarcodeIndicator(String postalRoutingBarcodeIndicator){
    	this.postalRoutingBarcodeIndicator = postalRoutingBarcodeIndicator;
    }
	/**
     * Added by Jose Rudel R. de Castro
	 * This method gets the postal rounting barcode indicator per R-2006 Rate Case.
	 * @return The postalRoutingBarcodeIndicator.
	 */
    public String getPostalRoutingBarcodeIndicator(){
    	return this.postalRoutingBarcodeIndicator;
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

  
    public double getArticleValue() {
        return articleValue;
    }

    public void setArticleValue(double articleValue) {
        this.articleValue = articleValue;
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

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public boolean isInvalidDuns()
    {
        return invalidDuns;
    }

    public void setInvalidDuns(boolean invalidDuns)
    {
        this.invalidDuns = invalidDuns;
    }

    public Vector<SpecialServiceVO> getCalculatedServicesList()
    {
        return calculatedServicesList;
    }
    public void setCalculatedServicesList(Vector<SpecialServiceVO> servicesList)
    {
        this.calculatedServicesList = servicesList;
    }

	public Vector<SpecialServiceVO> getServicesList() {
		return servicesList;
	}

	public void setServicesList(Vector<SpecialServiceVO> servicesList) {
		this.servicesList = servicesList;
	}
	
	

	public BigDecimal getCalculatedServicesCost() {
		
		BigDecimal cost = new BigDecimal(0);
		
		if (calculatedServicesList == null) return cost;
		
		for (int i = 0; i < calculatedServicesList.size(); i++) {
			cost = cost.add(new BigDecimal(((SpecialServiceVO) calculatedServicesList.get(i)).getRate()));
		}
		return DataTypes.format(cost);
	}

	

	public long getPostageStatementSequenceNumber() {
		return postageStatementSequenceNumber;
	}

	public void setPostageStatementSequenceNumber(
			long postageStatementSequenceNumber) {
		this.postageStatementSequenceNumber = postageStatementSequenceNumber;
	}

	public BigDecimal getCalculatedPostage() {
		return calculatedPostage;
	}

	public void setCalculatedPostage(BigDecimal calculatedPostage) {
		this.calculatedPostage = calculatedPostage;
	}

	public BigDecimal getCalculatedSurcharge() {
		return calculatedSurcharge;
	}

	public void setCalculatedSurcharge(BigDecimal calculatedSurcharge) {
		this.calculatedSurcharge = calculatedSurcharge;
	}

	public BigDecimal getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public BigDecimal getPostageDifference() {
		return postageDifference;
	}

	public void setPostageDifference(BigDecimal postageDifference) {
		this.postageDifference = postageDifference;
	}

	public BigDecimal getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(BigDecimal surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public BigDecimal getTotalPostage() {
		return totalPostage;
	}

	public void setTotalPostage(BigDecimal totalPostage) {
		this.totalPostage = totalPostage;
	}
	/**
	 * @return
	 */
	public String getMailerDestRateInd() {
		return mailerDestRateInd;
	}

	/**
	 * @return
	 */
	public String getMailerMailClass() {
		return mailerMailClass;
	}

	/**
	 * @return
	 */
	public String getMailerProcessCat() {
		return mailerProcessCat;
	}

	/**
	 * @return
	 */
	public String getMailerRateInd() {
		return mailerRateInd;
	}

	/**
	 * @return
	 */
	public String getMailerRoutingBarcode() {
		return mailerRoutingBarcode;
	}

	/**
	 * @return
	 */
	public String getMailerSurchargeType() {
		return mailerSurchargeType;
	}

	/**
	 * @return
	 */
	public String getMailerZone() {
		return mailerZone;
	}

	/**
	 * @return
	 */
	public String getRateIngredModInd() {
		return rateIngredModInd;
	}

	/**
	 * @param string
	 */
	public void setMailerDestRateInd(String string) {
		mailerDestRateInd = string;
	}

	/**
	 * @param string
	 */
	public void setMailerMailClass(String string) {
		mailerMailClass = string;
	}

	/**
	 * @param string
	 */
	public void setMailerProcessCat(String string) {
		mailerProcessCat = string;
	}

	/**
	 * @param string
	 */
	public void setMailerRateInd(String string) {
		mailerRateInd = string;
	}

	/**
	 * @param string
	 */
	public void setMailerRoutingBarcode(String string) {
		mailerRoutingBarcode = string;
	}

	/**
	 * @param string
	 */
	public void setMailerSurchargeType(String string) {
		mailerSurchargeType = string;
	}

	/**
	 * @param string
	 */
	public void setMailerZone(String string) {
		mailerZone = string;
	}

	/**
	 * @param string
	 */
	public void setRateIngredModInd(String string) {
		rateIngredModInd = string;
	}

	/**
	 * @return
	 */
	public boolean isDimWeightUsed() {
		return isDimWeightUsed;
	}

	/**
	 * @param b
	 */
	public void setDimWeightUsed(boolean b) {
		isDimWeightUsed = b;
	}

	// 17.1.0
	
//	/**
//	 * @return
//	 */
//	public CustomDetail getCustomDetail() {
//		return customDetail;
//	}
//
//	/**
//	 * @param detail
//	 */
//	public void setCustomDetail(CustomDetail detail) {
//		customDetail = detail;
//	}

	/**
	 * @return
	 */
	public Calendar getOriginalMailingDate() {
		return originalMailingDate;
	}

	/**
	 * @param calendar
	 */
	public void setOriginalMailingDate(Calendar calendar) {
		originalMailingDate = calendar;
	}

	// 17.1.0
	
	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#getMailClass()
	 */
	public String getMailClass() {
		return classOfMail;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#getRoutingBarcode()
	 */
	public String getRoutingBarcode() {
		return postalRoutingBarcodeIndicator;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#getDiscountSurchargeType()
	 */
	public String getDiscountSurchargeType() {
		return surchargeType;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#setMailClass(java.lang.String)
	 */
	public void setMailClass(String s) {
		classOfMail = s;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#setRoutingBarcode(java.lang.String)
	 */
	public void setRoutingBarcode(String s) {
		postalRoutingBarcodeIndicator = s;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailI#setDiscountSurchargeType(java.lang.String)
	 */
	public void setDiscountSurchargeType(String s) {
		surchargeType = s;
	}

	/**
	 * @return
	 */
	public double getMailerDimWeight() {
		return mailerDimWeight;
	}

		/**
	 * @param d
	 */
	public void setMailerDimWeight(double d) {
		mailerDimWeight = d;
	}

	
	/**
	 * @return
	 */
	public double getMailerWeight() {
		return mailerWeight;
	}

	/**
	 * @param d
	 */
	public void setMailerWeight(double d) {
		mailerWeight = d;
	}

	

	/**
	 * @return
	 */
	public BigDecimal getPublishedPostage() {
		return publishedPostage;
	}

	/**
	 * @param decimal
	 */
	public void setPublishedPostage(BigDecimal decimal) {
		publishedPostage = decimal;
	}

	/**
	 * @return
	 */
	public String getEvsWwsDiscountSurchargeType() {
		return evsWwsDiscountSurchargeType;
	}

	/**
	 * @param string
	 */
	public void setEvsWwsDiscountSurchargeType(String string) {
		evsWwsDiscountSurchargeType = string;
	}

	/**
	 * @return
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * @param string
	 */
	public void setPriceType(String string) {
		priceType = string;
	}

	/**
	 * @return
	 */
	public String getFilteredMessage() {
		return filteredMessage;
	}

	/**
	 * @param string
	 */
	public void setFilteredMessage(String string) {
		filteredMessage = string;
	}


	public String getDunsPkgid() { //REL22.0.X
		return dcDunsPkgidDzip;
	}

	/**
	 * @return
	 */
	public String getCorpPriceType() {
		return corpPriceType;
	}

	/**
	 * @param string
	 */
	public void setCorpPriceType(String string) {
		corpPriceType = string;
	}

	/**
	 * REL23.0
	 * @return
	 */
	public boolean isRoundUpHalfPound() {
		return roundUpHalfPound;
	}

	/**
	 * @param b
	 */
	public void setRoundUpHalfPound(boolean b) {
		roundUpHalfPound = b;
	}

	/**
	 * REL 26.0.0 SRS 417 REQ 4.2.4
	 * 
	 * @return
	 * 
	 */
	public double getCubicSize() { 
		BigDecimal lengthNearestQ = DataTypes.roundDownToNearestQuater(length); /* REL 26.0.0 SRS 417 REQ 4.2.4 */
		BigDecimal heightNearestQ = DataTypes.roundDownToNearestQuater(height);
		BigDecimal widthNearestQ = DataTypes.roundDownToNearestQuater(width);
		BigDecimal vol = (lengthNearestQ.multiply(heightNearestQ)).multiply(widthNearestQ);
		BigDecimal cubic = vol.divide(new BigDecimal(Double.toString(Constants.INCHES_TO_CUBIC_FEET_DIVIDER)), 2, BigDecimal.ROUND_HALF_UP);
		return cubic.doubleValue();
	}
	
	/**
	 * @return
	 */
	public String getTier() {
		return tier;
	}

	/**
	 * @param string
	 */
	public void setTier(String string) {
		tier = string;
	}

	/**
	 * @return
	 */
	public double getEFileVersionNumber() {
		return eFileVersionNumber;
	}

	/**
	 * @param d
	 */
	public void setEFileVersionNumber(double d) {
		eFileVersionNumber = d;
	}

	/* Beginning Of REL24.0 Methods */
	
	

	/**
	 * @return
	 */
	public String getChargeBackCode() {
		return chargeBackCode;
	}





	/**
	 * @return
	 */
	public String getDestinationFacilityType() {
		return destinationFacilityType;
	}

	/**
	 * @return
	 */
	public String getDetailRecId() {
		return detailRecId;
	}

	/**
	 * @return
	 */
	public double getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @return
	 */
	public String getDiscountType() {
		return discountType;
	}


	/**
	 * @return
	 */
	public String getFiller() {
		return filler;
	}

	/**
	 * @return
	 */
	public String getLogisticsManagerMailer() {
		return logisticsManagerMailer;
	}

	/**
	 * @return
	 */
	public String getMailerOwnerCRID() {
		return mailerOwnerCRID;
	}

	/**
	 * @return
	 */
	public String getMailerOwnerMailerId() {
		return mailerOwnerMailerId;
	}

	/**
	 * @return
	 */
	public String getMeterSerialNumber() {
		return meterSerialNumber;
	}

	/**
	 * @return
	 */
	public int getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @return
	 */
	public String getNonIncidentalEnclosureProcessCat() {
		return nonIncidentalEnclosureProcessCat;
	}

	/**
	 * @return
	 */
	public String getOpenDistributedContentsInd() {
		return openDistributedContentsInd;
	}

	/**
	 * @return
	 */
	public int getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	/**
	 * @return
	 */
	public String getPostageType() {
		return postageType;
	}

	/**
	 * @return
	 */
	public String getPostalCode() {
		return postalCode;
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
	public String getStc() {
		return stc;
	}

	
	/**
	 * @param string
	 */
	public void setChargeBackCode(String string) {
		chargeBackCode = string;
	}

	

	/**
	 * @param string
	 */
	public void setDestinationFacilityType(String string) {
		destinationFacilityType = string;
	}

	/**
	 * @param string
	 */
	public void setDetailRecId(String string) {
		detailRecId = string;
	}

	/**
	 * @param d
	 */
	public void setDiscountAmount(double d) {
		discountAmount = d;
	}

	/**
	 * @param string
	 */
	public void setDiscountType(String string) {
		discountType = string;
	}


	/**
	 * @param string
	 */
	public void setFiller(String string) {
		filler = string;
	}

	/**
	 * @param string
	 */
	public void setLogisticsManagerMailer(String string) {
		logisticsManagerMailer = string;
	}

	/**
	 * @param string
	 */
	public void setMailerOwnerCRID(String string) {
		mailerOwnerCRID = string;
	}

	/**
	 * @param string
	 */
	public void setMailerOwnerMailerId(String string) {
		mailerOwnerMailerId = string;
	}

	/**
	 * @param string
	 */
	public void setMeterSerialNumber(String string) {
		meterSerialNumber = string;
	}

	/**
	 * @param i
	 */
	public void setPaymentMethod(int i) {
		paymentMethod = i;
	}

	/**
	 * @param string
	 */
	public void setNonIncidentalEnclosureProcessCat(String string) {
		nonIncidentalEnclosureProcessCat = string;
	}

	/**
	 * @param string
	 */
	public void setOpenDistributedContentsInd(String string) {
		openDistributedContentsInd = string;
	}

	/**
	 * @param i
	 */
	public void setPaymentAccountNumber(int i) {
		paymentAccountNumber = i;
	}

	/**
	 * @param string
	 */
	public void setPostageType(String string) {
		postageType = string;
	}

	/**
	 * @param string
	 */
	public void setPostalCode(String string) {
		postalCode = string;
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
	public void setStc(String string) {
		stc = string;
	}
	/* End Of REL24.0 Methods */
	/**
	 * @return
	 */
	public String getFinanceNumber() {
		return financeNumber;
	}

	/**
	 * @param string
	 */
	public void setFinanceNumber(String string) {
		financeNumber = string;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.ContractPaymentInfoI#getMailingDateTime()
	 */
	public Calendar getMailingDateTime() {
		return mailingDate;
	}

	/**
	 * @return
	 */
	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}

	/**
	 * @param i
	 */
	public void setMetricsSeqNo(int i) {
		metricsSeqNo = i;
	}

	/**
	 * @return
	 */
	public double getManifestedSurchargeAmount() {
		return manifestedSurchargeAmount;
	}

	/**
	 * @return
	 */
	public String getManifestedSurchargeType() {
		return manifestedSurchargeType;
	}

	/**
	 * @param d
	 */
	public void setManifestedSurchargeAmount(double d) {
		manifestedSurchargeAmount = d;
	}

	/**
	 * @param string
	 */
	public void setManifestedSurchargeType(String string) {
		manifestedSurchargeType = string;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailRateI#getCalculatedTotalPostage()
	 */
	public BigDecimal getCalculatedTotalPostage() {
		return calculatedTotalPostage;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.DetailRateI#setCalculatedTotalPostage(java.math.BigDecimal)
	 */
	public void setCalculatedTotalPostage(BigDecimal calculatedTotalPostage) {
		this.calculatedTotalPostage = calculatedTotalPostage;
	}

	/**
	 * @return the psGroupNo
	 */
	public int getPsGroupNo() {
		return psGroupNo;
	}

	/**
	 * @param psGroupNo the psGroupNo to set
	 */
	public void setPsGroupNo(int psGroupNo) {
		this.psGroupNo = psGroupNo;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getReferrer() {
		return referrer;
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

	public boolean isLHWValidForCPC() {
		return ((length <= 18.0) && (height <= 18.0) && (width <= 18.0));  // REL 26.1.0 correct 26.0, it has to be AND instead of OR
	}	

	public boolean isOpenDistribute() {
		return (Constants.CLASS_PRIORITY_MAIL.equals(getMailClass()) || Constants.CLASS_EXPRESS_MAIL.equals(getMailClass())) 
				&& Constants.PROCESSING_CATEGORY_OD.equals(getProcessingCategory());
	}

	/*public BigDecimal getCalcFeeEDA() {
		return calcFeeEDA;
	}

	public void setCalcFeeEDA(BigDecimal calcFeeEDA) {
		this.calcFeeEDA = calcFeeEDA;
	}*/

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

	
	public BigDecimal getPublishedTotalPostage() {
		return publishedTotalPostage;
	}

	public void setPublishedTotalPostage(BigDecimal publishedTotalPostage) {
		this.publishedTotalPostage = publishedTotalPostage;
	}
	
	public void setValidPermit(boolean validPermit) {
		this.validPermit = validPermit;
	}

	public boolean getValidPermit() {
		return validPermit;
	}

	public void setDuplicateFlag(boolean duplicateFlag){
		this.duplicateFlag = duplicateFlag;
	}

	public boolean getDuplicateFlag(){
		return duplicateFlag;
	}
	
	public String getTpbPoDuns() {
		return duns;
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
	
   

   

    public String getCustomerReferenceNumber2() {
    	return customerReferenceNumber2;
    }

    public void setCustomerReferenceNumber2(String customerReferenceNumber2) {
    	this.customerReferenceNumber2 = customerReferenceNumber2;
    }

 

    public String getDeliveryAddress() {
    	return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
    	this.deliveryAddress = deliveryAddress;
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
    	this.returnAddressZip =  returnAddressZip;
    }

  

	
    public void setInternationalProcessStatus(String internationalProcessStatus) {
    	this.internationalProcessStatus = internationalProcessStatus;
    }
    
    public String getInternationalProcessStatus() {
    	return internationalProcessStatus;
    }
    
  
    
    public String getApplicationIdentifier() {
    	return applicationIdentifier;
    }
    
    public void setApplicationIdentifier(String applicationIdentifier) {
    	this.applicationIdentifier = applicationIdentifier;
    }
    
    public void setOriginCountryCode(String originCountryCode) {
    	this.originCountryCode = originCountryCode;
    }
    
    public String getOriginCountryCode() {
    	return originCountryCode;
    }

	public int getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(int priceGroup) {
		this.priceGroup = priceGroup;
	}


	
    public List<String> getExtraServiceCodesList() {
    	List<String> retList = new ArrayList<String>();
    	if(null != this.getServicesList() && this.getServicesList().size()>0) {
    		for(SpecialServiceVO service : this.getServicesList()) {
    			if(StringUtils.isNotBlank(service.getServiceCode())) {
    				retList.add(service.getServiceCode());
    			}
    		}
    	}
		return retList;
    }

	public void setByForList(ArrayList<ByForVO> byForList) {
		this.byForList = byForList;
	}

	public ArrayList<ByForVO> getByForList() {
		return byForList;
	}


	@Override
	public String getProcessingCategory() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getDestinationRateIndicator() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRateIndicator() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getZone() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ProductVO getProductVO() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRateSchedule() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setProcessingCategory(String s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setDestinationRateIndicator(String s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRateIndicator(String s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setZone(String s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setProductVO(ProductVO productVO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRateSchedule(String d) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isZipEDA() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setZipEDA(boolean zipEDA) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isFuelFeeEnabled() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setFuelFeeEnabled(boolean fuelFeeEnabled) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getCodAmountDueSender() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String toString() {
		return "ManifestDetail [sequenceNumber=" + sequenceNumber
				+ ", picCode=" + picCode + ", fileNumber=" + fileNumber
				+ ", subFileNumber=" + subFileNumber + ", classOfMail="
				+ classOfMail + ", destinationZip=" + destinationZip
				+ ", destinationZipPlus4=" + destinationZipPlus4
				+ ", countryCode=" + countryCode + ", postage=" + postage
				+ ", totalPostage=" + totalPostage + ", uomCode=" + uomCode
				+ ", weight=" + weight + ", poBoxIndicator=" + poBoxIndicator
				+ ", waiverOfSignature=" + waiverOfSignature
				+ ", articleValue=" + articleValue + ", handlingCharge="
				+ handlingCharge + ", servicesList=" + servicesList + ", duns="
				+ duns + ", customerInternalReference="
				+ customerInternalReference + ", surchargeType="
				+ surchargeType + ", surchargeAmount=" + surchargeAmount
				+ ", corpDuns=" + corpDuns + ", dcDunsPkgidDzip="
				+ dcDunsPkgidDzip + ", dcDuns=" + dcDuns + ", dcPkgid="
				+ dcPkgid + ", mailingDate=" + mailingDate
				+ ", originalMailingDate=" + originalMailingDate
				+ ", accountPeriod=" + accountPeriod + ", fiscalYear="
				+ fiscalYear + ", month=" + month + ", monthlyFiscalYear="
				+ monthlyFiscalYear + ", entryFacilityZip=" + entryFacilityZip
				+ ", filteredType=" + filteredType + ", serviceTypeCode="
				+ serviceTypeCode + ", systemType=" + systemType
				+ ", invalidDuns=" + invalidDuns
				+ ", postageStatementSequenceNumber="
				+ postageStatementSequenceNumber + ", length=" + length
				+ ", width=" + width + ", height=" + height
				+ ", dimensionalWeight=" + dimensionalWeight
				+ ", postalRoutingBarcodeIndicator="
				+ postalRoutingBarcodeIndicator + ", calculatedPostage="
				+ calculatedPostage + ", calculatedTotalPostage="
				+ calculatedTotalPostage + ", calculatedSurcharge="
				+ calculatedSurcharge + ", postageDifference="
				+ postageDifference + ", calculatedServicesList="
				+ calculatedServicesList + ", mailerMailClass="
				+ mailerMailClass + ", mailerDestRateInd=" + mailerDestRateInd
				+ ", mailerRateInd=" + mailerRateInd + ", mailerProcessCat="
				+ mailerProcessCat + ", mailerZone=" + mailerZone
				+ ", mailerRoutingBarcode=" + mailerRoutingBarcode
				+ ", mailerSurchargeType=" + mailerSurchargeType
				+ ", rateIngredModInd=" + rateIngredModInd
				+ ", isDimWeightUsed=" + isDimWeightUsed
				+ ", publishedPostage=" + publishedPostage
				+ ", publishedTotalPostage=" + publishedTotalPostage
				+ ", mailerDimWeight=" + mailerDimWeight + ", mailerWeight="
				+ mailerWeight + ", evsWwsDiscountSurchargeType="
				+ evsWwsDiscountSurchargeType + ", priceType=" + priceType
				+ ", filteredMessage=" + filteredMessage + ", corpPriceType="
				+ corpPriceType + ", roundUpHalfPound=" + roundUpHalfPound
				+ ", tier=" + tier + ", eFileVersionNumber="
				+ eFileVersionNumber + ", detailRecId=" + detailRecId
				+ ", stc=" + stc + ", barcodeConstruct=" + barcodeConstruct
				+ ", destinationFacilityType=" + destinationFacilityType
				+ ", postalCode=" + postalCode + ", logisticsManagerMailer="
				+ logisticsManagerMailer + ", mailerOwnerMailerId="
				+ mailerOwnerMailerId + ", mailerOwnerCRID=" + mailerOwnerCRID
				+ ", paymentAccountNumber=" + paymentAccountNumber
				+ ", paymentMethod=" + paymentMethod
				+ ", postOfficeOfAccountZipCode=" + postOfficeOfAccountZipCode
				+ ", meterSerialNumber=" + meterSerialNumber
				+ ", chargeBackCode=" + chargeBackCode + ", postageType="
				+ postageType + ", discountType=" + discountType
				+ ", discountAmount=" + discountAmount
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
				+ ", tpbFlag=" + tpbFlag + ", intlMailInd=" + intlMailInd
				+ ", customerReferenceNumber2=" + customerReferenceNumber2
				+ ", deliveryAddress=" + deliveryAddress + ", keyLine="
				+ keyLine + ", returnAddress=" + returnAddress
				+ ", returnAddressCity=" + returnAddressCity
				+ ", returnAddressState=" + returnAddressState
				+ ", returnAddressZip=" + returnAddressZip
				+ ", internationalProcessStatus=" + internationalProcessStatus
				+ ", applicationIdentifier=" + applicationIdentifier
				+ ", originCountryCode=" + originCountryCode + ", priceGroup="
				+ priceGroup + ", byForList=" + byForList + "]";
	}
	
	
}
