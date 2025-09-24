/*
 * Created on Dec 11, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import org.apache.commons.lang.StringUtils;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManifestDetailRawLineV17Vo extends ManifestDetailRawLineV15Vo {
	private String destinationDeliveryPoint = "0";	
	private String deliveryOptionIndicator;
	private String overlabelIndicator;
	private String overlabelBarcodeConstructCode="";
	private String overlabelNumber;
	private String customerReferenceNumber2;
	private String recipientName;
	private String deliveryAddress;
	private String ancillaryServiceEndorsement;
	private String addressServiceParticipantCode;
	private String keyLine;
	private String returnAddress;
	private String returnAddressCity;
	private String returnAddressState;
	private String returnAddressZip;
	private String logisticMailingFacilityCRID;
	private String removalIndicator;
	private String applicationIdentifier;
	private String serialNumber;
	private String originCountryCode;
	private String altPic;
    private String altDuns; /* SRS 228 */
    private String altPkgId; /* SRS 228 */
    private String altBarConstCode; /* SRS 228 */
    private String altSource; /* SRS 228 */
    private String internationalProcessStatus;
	
	
	
	/**
	 * 
	 */
	public ManifestDetailRawLineV17Vo() {
	}


	public int parseLine(String line) {

		setEFileVersionNumber("1.7");

		checkMinimumLength(line);
		if (line.length() > 2)
			setDetailRecId(line.substring(0,2));
		
		// if these remain null this will cause problems in PicCodeParserManifestV17
		setOverlabelIndicator("");
		setOverlabelBarcodeConstructCode("");
		setOriginCountryCode("");
		
		// if this is null it will cause problems in ConvertV17.parseLine
		setDestinationZip("00000");
		
		if (getFilteredType() != Constants.FILTERED_NONE) {
			return getFilteredType();
		}
		
		setPicCode(line.substring(2, 36));
		setApplicationIdentifier(line.substring(2,4));
		setClassOfMail(line.substring(36, 38));
		setStc(line.substring(38, 42));
		setBarcodeConstruct(line.substring(42, 46));
		if (getBarcodeConstruct().trim().equals("G01")) {
			setSerialNumber(getPicCode().substring(2,10));			
		}
		if (getBarcodeConstruct().trim().equals("I01")) {
			setSerialNumber(getPicCode().substring(2,11));
			setOriginCountryCode(getPicCode().substring(12,14));
		}
		if (DataTypes.isBarcodeInternational(getBarcodeConstruct()))
			setInternationalProcessStatus("Y");
		else 
			setInternationalProcessStatus("N");
		setDestinationZip(line.substring(46, 51));
		setDestinationZipPlus4(line.substring(51, 55));
		setDestinationFacilityType(line.substring(55, 56));
		setCountryCode(line.substring(56, 58));
		setPostalCode(line.substring(58, 69));
		setCarrierRoute(line.substring(69, 74));
		setLogisticsManagerMailer(line.substring(74, 83));
		setMailerOwnerMailerId(line.substring(83, 92));
		setContainerId1(line.substring(92, 126));
		setContainerType1(line.substring(126, 128));		
		setContainerId2(line.substring(128, 162));
		setContainerType2(line.substring(162, 164));		
		setContainerId3(line.substring(164, 198));
		setContainerType3(line.substring(198, 200));
		setMailerOwnerCRID(line.substring(200, 215));
		setCustomerInternalReference(line.substring(215, 245));
		setFastReservationNumber(line.substring(245, 260));
		setFastScheduledInductionDate(line.substring(260, 268));
		setFastScheduledInducationTime(line.substring(268, 274));
		setPaymentAccountNumber(line.substring(274, 284));
		setMethodPayment(line.substring(284, 286));
		setPostOfficeAccountZip(line.substring(286, 291));
		setMeterSerialNumber(line.substring(291, 311));
		setChargeBackCode(line.substring(311, 317));
		setPostage(line.substring(317, 324)); 
		setPostageType(line.substring(324, 325));
		setCSSCNumber(line.substring(325, 347));				
		setCSSCProductId(line.substring(347, 361));
		setUomCode(line.substring(361, 362));
		setWeight(line.substring(362, 371));
		setProcessingCategory(line.substring(371, 372));
		setRateIndicator(line.substring(372, 374));
		setDestinationRateIndicator(line.substring(374, 375));
		setZone(line.substring(375, 377));
		setLength(line.substring(377, 382));
		setWidth(line.substring(382, 387));
		setHeight(line.substring(387, 392));
		setDimensionalWeight(line.substring(392, 398));
		setSpecialServiceCode1(line.substring(398, 401));
		setSpecialServiceFee1(line.substring(401, 407));				
		setSpecialServiceCode2(line.substring(407, 410));
		setSpecialServiceFee2(line.substring(410, 416));				
		setSpecialServiceCode3(line.substring(416, 419));
		setSpecialServiceFee3(line.substring(419, 425));				
		setSpecialServiceCode4(line.substring(425, 428));
		setSpecialServiceFee4(line.substring(428, 434));				
		setSpecialServiceCode5(line.substring(434, 437));
		setSpecialServiceFee5(line.substring(437, 443));
		setArticleValue(line.substring(443, 450));
		setCodAmountDueSender(line.substring(450, 456));
		setHandlingCharge(line.substring(456, 460));
		setManifestedSurchargeType(line.substring(460, 462));
		setManifestedSurchargeAmount(line.substring(462, 469));
		setDiscountType(line.substring(469, 471));
		setDiscountAmount(line.substring(471, 478));
		/* Setting SurchargeType and Amount moved here REL24.0 */
		if (! StringUtils.isEmpty(StringUtils.strip(getDiscountType()))) {
			/* when discount type is not empty, even manifested sucharge type is not empty, surcharge type be set to discount type */  
			setSurchargeType(getDiscountType());
			setSurchargeAmount(getDiscountAmount());
		} else if (! StringUtils.isEmpty(StringUtils.strip(getManifestedSurchargeType()))) {
			/* when discount type is empty, but manifested sucharge type is not empty, surcharge type be set to manifested surcharge type */
			setSurchargeType(getManifestedSurchargeType());
			setSurchargeAmount(getManifestedSurchargeAmount());
		} else {
			/* when both is empty, it does not matter what to set */
			setSurchargeType(getDiscountType());
			setSurchargeAmount(getDiscountAmount());
		}	
				
		setNonIncidentalEnclosureRateIndicator(line.substring(478, 480));
		setNonIncidentalEnclosureClass(line.substring(480, 482));
		setNonIncidentalEnclosurePostage(line.substring(482, 489));
		setNonIncidentalEnclosureWeight(line.substring(489, 498));
		setNonIncidentalEnclosureProcessCat(line.substring(498, 499));
		setPostalRoutingBarcodeIndicator(line.substring(499, 500));
		setOpenDistributedContentsInd(line.substring(500, 502));
		setPoBoxIndicator(line.substring(502, 503));
		setWaiverOfSignature(line.substring(503, 504));
		setNoWeekendHolidayDelivery(line.substring(504, 505));
		setDestinationDeliveryPoint(line.substring(505, 507));
		// the following are SRS 228
		setRemovalIndicator(line.substring(507,508));
		setOverlabelIndicator(line.substring(508,510));
		if ("01".equals(getOverlabelIndicator().trim()))
		{
			setOverlabelBarcodeConstructCode(line.substring(510,514).trim());
			setOverlabelNumber(line.substring(514,548));
			setAltPic(getOverlabelNumber());
		// altSource will later bet set to value that will indicate whether the main pic code or the alternate pic code should be used
			setAltSource("");
			setAltBarConstCode(getOverlabelBarcodeConstructCode());
		}
		else {
			setOverlabelBarcodeConstructCode("");
			setOverlabelNumber("");
			setAltPic("");
		// altSource will later bet set to value that will indicate whether the main pic code or the alternate pic code should be used
			setAltSource("");
			setAltBarConstCode("");			
		}
			
		setCustomerReferenceNumber2(line.substring(548,578));
		setRecipientName(line.substring(578,626));
		setDeliveryAddress(line.substring(626,674));
		setAncillaryServiceEndorsement(line.substring(674,677));
		setAddressServiceParticipantCode(line.substring(677,686));
		setKeyLine(line.substring(686,702));
		setReturnAddress(line.substring(702,750));
		setReturnAddressCity(line.substring(750,778));
		setReturnAddressState(line.substring(778,780));
		setReturnAddressZip(line.substring(780,785));
		setLogisticMailingFacilityCRID(line.substring(785,800));
		
		if (line.length() > 800)
			setFiller(line.substring(800));
				
		 return getFilteredType(); 
	}

	private void checkMinimumLength(String line) {
		int l = line.length();
		if (line.length() < 888) {
			setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
			setFilteredMessage("Filtered because detail line is too short, less than 888");
			logger.info(getFilteredMessage()+" Length="+line.length());		
		
		if (line.length() >= 46) {
				setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
				setPicCode(line.substring(2, 36));
				setClassOfMail(line.substring(36, 38).trim());
				setStc(line.substring(38, 42));
				setBarcodeConstruct(line.substring(42, 46));				
			} 
		else if (line.length() >= 38) {
			setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
					setPicCode(line.substring(2, 36));
					setClassOfMail(line.substring(36,38));
					setStc(" ");
					setBarcodeConstruct("INV"); // Prevent null				
					setFilteredMessage("Filtered for Bad Record because detail line is less then 46.");
		} else {
			setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
			if (line.length() >= 3) {
				setClassOfMail("XX");
				setPicCode(line.substring(2));
				setStc(" ");
				setBarcodeConstruct("INV"); // Prevent null
				setFilteredMessage("Filtered for Bad Record because detail line is less then 46.");
			} else {				
						setClassOfMail("XX");
						setPicCode("0");
						setStc(" ");
						setBarcodeConstruct("INV"); // Prevent null
						setFilteredMessage("Filtered for Bad Record because detail line is less then 46.");
					}
		}
		
		// Add other NOT_NULL Constraint value, Other will be set later
		setPostage("0");
		setDuns("000000000");
		setDestinationZip("00000");				
		logger.info(getFilteredMessage()+" Length="+line.length()+", PicCode="+getPicCode());
		
		}
	}





	public String getDestinationDeliveryPoint() {
		return destinationDeliveryPoint;
	}


	public void setDestinationDeliveryPoint(String destinationDeliveryPoint) {
		this.destinationDeliveryPoint = destinationDeliveryPoint;
	}
	
	public void setDeliveryOptionIndicator(String deliveryOptionIndicator) {
		this.deliveryOptionIndicator = deliveryOptionIndicator;
	}

	public String getDeliveryOptionIndicator() {
		return deliveryOptionIndicator;
	}

	public void setOverlabelIndicator(String overlabelIndicator) {
		this.overlabelIndicator = overlabelIndicator;
	}

	public String getOverlabelIndicator() {
		return overlabelIndicator;
	}

	public void setOverlabelNumber(String overlabelNumber) {
		this.overlabelNumber = overlabelNumber;
	}

	public String getOverlabelNumber() {
		return overlabelNumber;
	}

	public void setOverlabelBarcodeConstructCode(String overlabelBarcodeConstructCode) {
		this.overlabelBarcodeConstructCode = overlabelBarcodeConstructCode;
	}

	public String getOverlabelBarcodeConstructCode() {
		return overlabelBarcodeConstructCode;
	}

	public void setCustomerReferenceNumber2(String customerReferenceNumber2) {
		this.customerReferenceNumber2 = customerReferenceNumber2;
	}

	public String getCustomerReferenceNumber2() {
		return customerReferenceNumber2;
	}

	public void setRecipientName(String recepientName) {
		this.recipientName = recepientName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setAncillaryServiceEndorsement(String ancillaryServiceEndorsement) {
		this.ancillaryServiceEndorsement = ancillaryServiceEndorsement;
	}

	public String getAncillaryServiceEndorsement() {
		return ancillaryServiceEndorsement;
	}

	public void setAddressServiceParticipantCode(String addressServiceParticipantCode) {
		this.addressServiceParticipantCode = addressServiceParticipantCode;
	}

	public String getAddressServiceParticipantCode() {
		return addressServiceParticipantCode;
	}

	public void setKeyLine(String keyLine) {
		this.keyLine = keyLine;
	}

	public String getKeyLine() {
		return keyLine;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}

	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddressCity(String returnAddressCity) {
		this.returnAddressCity = returnAddressCity;
	}

	public String getReturnAddressCity() {
		return returnAddressCity;
	}

	public void setReturnAddressState(String returnAddressState) {
		this.returnAddressState = returnAddressState;
	}

	public String getReturnAddressState() {
		return returnAddressState;
	}

	public void setLogisticMailingFacilityCRID(String logisticMailingFacilityCRID) {
		this.logisticMailingFacilityCRID = logisticMailingFacilityCRID;
	}

	public String getLogisticMailingFacilityCRID() {
		return logisticMailingFacilityCRID;
	}

	public void setReturnAddressZip(String returnAddressZip) {
		this.returnAddressZip = returnAddressZip;
	}

	public String getReturnAddressZip() {
		return returnAddressZip;
	}

	public void setRemovalIndicator(String removalIndicator) {
		this.removalIndicator = removalIndicator;
	}

	public String getRemovalIndicator() {
		return removalIndicator;
	}
	
	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}

	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setOriginCountryCode(String originCountryCode) {
		this.originCountryCode = originCountryCode;
	}

	public String getOriginCountryCode() {
		return originCountryCode;
	}
	
	public void setAltPic(String altPic) {
		
	}
	
	public String getAltPic() {
		return altPic;
	}

    private void setAltDuns(String altDuns) {
    	this.altDuns = altDuns;
    }
    
    public String getAltDuns() {
    	return altDuns;
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
    
    public void setAltSource(String altSource){
    	this.altSource = altSource;
    }
    
    public void setInternationalProcessStatus(String internationalProcessStatus) {
    	this.internationalProcessStatus = internationalProcessStatus;
    }
    
    public String getInternationalProcessStatus() {
    	return internationalProcessStatus;
    }
    
}
