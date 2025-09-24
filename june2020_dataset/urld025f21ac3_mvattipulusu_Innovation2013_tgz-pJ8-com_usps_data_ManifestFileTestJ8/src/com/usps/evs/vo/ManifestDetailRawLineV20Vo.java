/*
 * Created on Dec 11, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;



import org.apache.log4j.Logger;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;
import com.evs.data.util.PipeParser;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManifestDetailRawLineV20Vo extends ManifestDetailRawLineV17Vo {
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
    
	private Logger logger = Logger.getLogger("ManifestDetailRawLineV20Vo");
	
	
	
	/**
	 * 
	 */
	public ManifestDetailRawLineV20Vo() {
	}


	public int parseLine(String line) {

		setEFileVersionNumber("2.0");

		setOriginCountryCode("");
		setOverlabelIndicator("");
		setOverlabelBarcodeConstructCode("");
		
		int fieldCount=0;
		String field = "";
		PipeParser pp = new PipeParser(line);
		fieldCount = pp.countTokens();

		// if these remain null this will cause problems in PicCodeParserManifestV20
		setOverlabelIndicator("");
		setOverlabelBarcodeConstructCode("");
		
		// if this is null it will cause problems in ConvertV20.parseLine
		setDestinationZip("00000");
				
		if (fieldCount < 86) {
			logger.error("insufficient number of fields, received " + fieldCount);
			System.out.println("insufficient number of fields, received " + fieldCount);

			this.setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
			this.setFilteredMessage("Insufficient field values");
			this.setFilteredValue("" + fieldCount);
			
			setClassOfMail("XX");
			setPicCode("0");
			setStc(" ");
			setBarcodeConstruct("INV"); // Prevent null
			
			return getFilteredType();
		}

		
		field = pp.nextToken();
		
		if (field.length() > 2)
			setDetailRecId(line.substring(0,2));
		
		if (getFilteredType() != Constants.FILTERED_NONE) {
			return getFilteredType();
		}
		
		setPicCode(pp.nextToken().trim());
	    if (getPicCode().length() > 34) {
	    	setPicCode(getPicCode().substring(0,34));
	    }
	    	    
		setClassOfMail(pp.nextToken());
		if (getClassOfMail().length() > 2) {
			setClassOfMail(getClassOfMail().substring(0,2));
		}
		setStc(pp.nextToken());
		if (getStc().length() > 4)
		{
			setStc(getStc().substring(0,4));
		}		
		setBarcodeConstruct(pp.nextToken());
		if (getBarcodeConstruct().length() > 3) {
			setBarcodeConstruct(getBarcodeConstruct().substring(0,3));
		}
		if ("C01".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(12, 14));
		} else if ("C02".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(8, 10));
		} else if ("C03".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(8, 10));
		} else if ("C04".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0, 2));
		} else if ("C05".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(12, 14));
		} else if ("C06".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(8, 10));
		} else if ("C07".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(8, 10));
		} else if ("C08".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0, 2));
		} else if ("C09".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0, 2));
		} else if ("C10".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0, 2));
		} else if ("L01".equals(getBarcodeConstruct())) { /* Old format Pic Code in a new format file V1.5 */
			setApplicationIdentifier(getPicCode().substring(0, 2));
		}else if ("I01".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0,2));
		} else if ("G01".equals(getBarcodeConstruct())) {
			setApplicationIdentifier(getPicCode().substring(0,2));
		}	 else  if ("".equals(getBarcodeConstruct())) { /* Competitor */
			// because we do not know how to parse IMpb to its attribute and some attibutes are not nullable, system needs to fill out
			setApplicationIdentifier("00");
		} else setApplicationIdentifier("00");
		
		
		setSerialNumber("");
		if (getBarcodeConstruct().trim().equals("G01")) {
			setSerialNumber(getPicCode().substring(2));			
		}
		if (getBarcodeConstruct().trim().equals("I01")) {
			setSerialNumber(getPicCode().substring(2,11));			
			setOriginCountryCode(getPicCode().substring(11));
		}
		if (DataTypes.isBarcodeInternational(getBarcodeConstruct()))
			setInternationalProcessStatus("Y");
		else 
			setInternationalProcessStatus("N");
		
		setDestinationZip(pp.nextToken());
		if (getDestinationZip().length() > 5) {
			setDestinationZip(getDestinationZip().substring(0,5));
		}
		
		setDestinationZipPlus4(pp.nextToken());
		if (getDestinationZipPlus4().length() > 4) {
			setDestinationZipPlus4(getDestinationZipPlus4().substring(0,4));
		}
		
		setDestinationFacilityType(pp.nextToken());
		if (getDestinationFacilityType().length() > 1) {
			setDestinationFacilityType(getDestinationFacilityType().substring(0,1));
		}
			
		setCountryCode(pp.nextToken());
		if (getCountryCode().length() > 2) {
			setCountryCode(getCountryCode().substring(0,2));
		}
				
		setPostalCode(pp.nextToken());
		if (getPostalCode().length() > 11) {
			setPostalCode(getPostalCode().substring(0,11));
		}
		
		setCarrierRoute(pp.nextToken());
		if (getCarrierRoute().length() > 5) {
			setCarrierRoute(getCarrierRoute().substring(0,5));
		}
		
		setLogisticsManagerMailer(pp.nextToken());
		if (getLogisticsManagerMailer().length() > 9) {
			setLogisticsManagerMailer(getLogisticsManagerMailer().substring(0,9));
		}
		
		setMailerOwnerMailerId(pp.nextToken());
		if (getMailerOwnerMailerId().length() > 9) {
			setMailerOwnerMailerId(getMailerOwnerMailerId().substring(0,9));
		}
					
		setContainerId1(pp.nextToken());
		if (getContainerId1().length() > 34) {
			setContainerId1(getContainerId1().substring(0,34));
		}
		
		setContainerType1(pp.nextToken());
		if (getContainerType1().length() > 2) {
			setContainerType1(getContainerType1().substring(0,2));
		}

		setContainerId2(pp.nextToken());
		if (getContainerId2().length() > 34) {
			setContainerId2(getContainerId2().substring(0,34));
		}
		
		setContainerType2(pp.nextToken());
		if (getContainerType2().length() > 2) {
			setContainerType2(getContainerType2().substring(0,2));
		}
		
		
		setContainerId3(pp.nextToken());
		if (getContainerId3().length() > 34) {
			setContainerId3(getContainerId3().substring(0,34));
		}
		
		setContainerType3(pp.nextToken());
		if (getContainerType3().length() > 2) {
			setContainerType3(getContainerType3().substring(0,2));
		}		
		
		setMailerOwnerCRID(pp.nextToken());
		if (getMailerOwnerCRID().length() > 15) {
			setMailerOwnerCRID(getMailerOwnerCRID().substring(0,15));
		}
		
		setCustomerInternalReference(pp.nextToken());
		if (getCustomerInternalReference().length() > 40) {
			setCustomerInternalReference(getCustomerInternalReference().substring(0,40));
		}
		
		setFastReservationNumber(pp.nextToken());
		if (getFastReservationNumber().length() > 5) {
			setFastReservationNumber(getFastReservationNumber().substring(0,5));
		}
		
		setFastScheduledInductionDate(pp.nextToken());
		if (getFastScheduledInductionDate().length() >  8) {
			setFastScheduledInductionDate(getFastScheduledInductionDate().substring(0,8));
		}
		
		setFastScheduledInducationTime(pp.nextToken());
		if (getFastScheduledInducationTime().length() > 6) {
			setFastScheduledInducationTime(getFastScheduledInducationTime().substring(0,6));
		}		
		
		setPaymentAccountNumber(pp.nextToken());
		if (getPaymentAccountNumber().length() > 10) {
			setPaymentAccountNumber(getPaymentAccountNumber().substring(0,10));
		}
		
		setMethodPayment(pp.nextToken());
		if (getMethodPayment().length() > 2)
		{
			setMethodPayment(getMethodPayment().substring(0,2));
		}
		
		setPostOfficeAccountZip(pp.nextToken());
		if (getPostOfficeAccountZip().length() > 5) {
			setPostOfficeAccountZip(getPostOfficeAccountZip().substring(0,5));
		}
		
		setMeterSerialNumber(pp.nextToken());
		if (getMeterSerialNumber().length() > 20) {
			setMeterSerialNumber(getMeterSerialNumber().substring(0,20));
		}
		
		setChargeBackCode(pp.nextToken());
		if (getChargeBackCode().length() > 6) {
			setChargeBackCode(getChargeBackCode().substring(0,6));
		}
		
		setPostage(pp.nextToken()); 
		if (getPostage().length() > 7) {
			setPostage(getPostage().substring(0,7));
		}
		
		setPostageType(pp.nextToken());
		if (getPostageType().length() > 2) {
			setPostageType(getPostageType().substring(0,2));
		}
		
		setCSSCNumber(pp.nextToken());			
		if (getCSSCNumber().length() > 22) {
			setCSSCNumber(getCSSCNumber().substring(0,22));
		}
		
		setCSSCProductId(pp.nextToken());
		if (getCSSCProductId().length() > 14) {
			setCSSCProductId(getCSSCProductId().substring(0,14));
		}
		
		setUomCode(pp.nextToken());
		if (getUomCode().length() > 2) {
			setUomCode(getUomCode().substring(0,2));
		}
		
		setWeight(pp.nextToken());
		if (getWeight().length() > 9) {
			setWeight(getWeight().substring(0,9));
		}
		
		setProcessingCategory(pp.nextToken());
		if (getProcessingCategory().length() > 1) {
			setProcessingCategory(getProcessingCategory().substring(0,1));
		}
		
		setRateIndicator(pp.nextToken());
		if (getRateIndicator().length() > 2) {
			setRateIndicator(getRateIndicator().substring(0,2));
		}
		
		setDestinationRateIndicator(pp.nextToken());
		if (getDestinationRateIndicator().length() > 1) {
			setDestinationRateIndicator(getDestinationRateIndicator().substring(0,1));
		}		
		
		setZone(pp.nextToken());
		if (getZone().length() > 2) {
			setZone(getZone().substring(0,2));
		}
				
		setLength(pp.nextToken());
		if (getLength().length() > 5) {
			setLength(getLength().substring(0,5));
		}
		
		setWidth(pp.nextToken());
		if (getWidth().length() > 5) {
			setWidth(getWidth().substring(0,5));
		}
		
		setHeight(pp.nextToken());
		if (getHeight().length() > 5) {
			setHeight(getHeight().substring(0,5));
		}
		
		setDimensionalWeight(pp.nextToken());
		if (getDimensionalWeight().length() > 6) {
			setDimensionalWeight(getDimensionalWeight().substring(0,6));
		}
		
		setSpecialServiceCode1(pp.nextToken());
		if (getSpecialServiceCode1().length() > 3) {
			setSpecialServiceCode1(getSpecialServiceCode1().substring(0,3));
		}
		
		setSpecialServiceFee1(pp.nextToken());
		if (getSpecialServiceFee1().length() > 6) {
			setSpecialServiceFee1(getSpecialServiceFee1().substring(0,6));
		}
		
		setSpecialServiceCode2(pp.nextToken());
		if (getSpecialServiceCode2().length() > 3) {
			setSpecialServiceCode2(getSpecialServiceCode2().substring(0,3));
		}
		
		setSpecialServiceFee2(pp.nextToken());
		if (getSpecialServiceFee2().length() > 6) {
			setSpecialServiceFee2(getSpecialServiceFee2().substring(0,6));
		}
		
		setSpecialServiceCode3(pp.nextToken());
		if (getSpecialServiceCode3().length() > 3) {
			setSpecialServiceCode3(getSpecialServiceCode3().substring(0,3));
		}
		
		setSpecialServiceFee3(pp.nextToken());	
		if (getSpecialServiceFee3().length() > 6) {
			setSpecialServiceFee3(getSpecialServiceFee3().substring(0,6));
		}
		
		setSpecialServiceCode4(pp.nextToken());
		if (getSpecialServiceCode4().length() > 3) {
			setSpecialServiceCode4(getSpecialServiceCode4().substring(0,3));
		}
		
		setSpecialServiceFee4(pp.nextToken());	
		if (getSpecialServiceFee4().length() > 6) {
			setSpecialServiceFee4(getSpecialServiceFee4().substring(0,6));
		}
		
		setSpecialServiceCode5(pp.nextToken());
		if (getSpecialServiceCode5().length() > 3) {
			setSpecialServiceCode5(getSpecialServiceCode5().substring(0,3));
		}
		
		setSpecialServiceFee5(pp.nextToken());
		if (getSpecialServiceFee5().length() > 6) {
			setSpecialServiceFee5(getSpecialServiceFee5().substring(0,6));
		}		
		
		setArticleValue(pp.nextToken());
		if (getArticleValue().length() > 7) {
			setArticleValue(getArticleValue().substring(0,7));
		}		
		
		setCodAmountDueSender(pp.nextToken());
		if (getCodAmountDueSender().length() > 6) {
			setCodAmountDueSender(getCodAmountDueSender().substring(0,6));
		}
		
		setHandlingCharge(pp.nextToken());
		if (getHandlingCharge().length() > 4) {
			setHandlingCharge(getHandlingCharge().substring(0,4));
		}		
		
		setManifestedSurchargeType(pp.nextToken());
		if (getManifestedSurchargeType().length() > 2) {
			setManifestedSurchargeType(getManifestedSurchargeType().substring(0,2));
		}
		
		setManifestedSurchargeAmount(pp.nextToken());
		if (getManifestedSurchargeAmount().length() > 7) {
			setManifestedSurchargeAmount(getManifestedSurchargeAmount().substring(0,7));
		}
		
		setDiscountType(pp.nextToken());
		if (getDiscountType().length() > 2) {
			setDiscountType(getDiscountType().substring(0,2));
		}
		
		setDiscountAmount(pp.nextToken());
		if (getDiscountAmount().length() > 7) {
			setDiscountAmount(getDiscountAmount().substring(0,7));
		}
		
		/* Setting SurchargeType and Amount moved here REL24.0 */
		if (null!=getDiscountType()) {
			/* when discount type is not empty, even manifested sucharge type is not empty, surcharge type be set to discount type */  
			setSurchargeType(getDiscountType());
			setSurchargeAmount(getDiscountAmount());
		} else if (null!=getManifestedSurchargeType()) {
			/* when discount type is empty, but manifested sucharge type is not empty, surcharge type be set to manifested surcharge type */
			setSurchargeType(getManifestedSurchargeType());
			setSurchargeAmount(getManifestedSurchargeAmount());
		} else {
			/* when both is empty, it does not matter what to set */
			setSurchargeType(getDiscountType());
			setSurchargeAmount(getDiscountAmount());
		}	
				
		setNonIncidentalEnclosureRateIndicator(pp.nextToken());
		if (getNonIncidentalEnclosureRateIndicator().length() > 2) {
			setNonIncidentalEnclosureRateIndicator(getNonIncidentalEnclosureRateIndicator().substring(0,2));
		}			
		
		setNonIncidentalEnclosureClass(pp.nextToken());
		if (getNonIncidentalEnclosureClass().length() > 2) {
			setNonIncidentalEnclosureClass(getNonIncidentalEnclosureClass().substring(0,2));
		}
		
		setNonIncidentalEnclosurePostage(pp.nextToken());
		if (getNonIncidentalEnclosurePostage().length() > 7) {
			setNonIncidentalEnclosurePostage(getNonIncidentalEnclosurePostage().substring(0,7));
		}
		
		setNonIncidentalEnclosureWeight(pp.nextToken());
		if (getNonIncidentalEnclosureWeight().length() > 9) {
			setNonIncidentalEnclosureWeight(getNonIncidentalEnclosureWeight().substring(0,9));
		}
		
		setNonIncidentalEnclosureProcessCat(pp.nextToken());
		if (getNonIncidentalEnclosureProcessCat().length() > 1) {
			setNonIncidentalEnclosureProcessCat(getNonIncidentalEnclosureProcessCat().substring(0,1));
		}
		
		setPostalRoutingBarcodeIndicator(pp.nextToken());
		if (getPostalRoutingBarcodeIndicator().length() > 1) {
			setPostalRoutingBarcodeIndicator(getPostalRoutingBarcodeIndicator().substring(0,1));
		}
		
		setOpenDistributedContentsInd(pp.nextToken());
		if (getOpenDistributedContentsInd().length() > 2) {
			setOpenDistributedContentsInd(getOpenDistributedContentsInd().substring(0,2));
		}
				
		setPoBoxIndicator(pp.nextToken());
		if (getPoBoxIndicator().length() > 1) {
			setPoBoxIndicator(getPoBoxIndicator().substring(0,1));
		}		
		
		setWaiverOfSignature(pp.nextToken());
		if (getWaiverOfSignature().length() > 1) {
			setWaiverOfSignature(getWaiverOfSignature().substring(0,1));
		}
		
		setNoWeekendHolidayDelivery(pp.nextToken());
		if (getNoWeekendHolidayDelivery().length() > 1) {
			setNoWeekendHolidayDelivery(getNoWeekendHolidayDelivery().substring(0,1));
		}		
		
		setDestinationDeliveryPoint(pp.nextToken());
		if (getDestinationDeliveryPoint().length() > 2) {
			setDestinationDeliveryPoint(getDestinationDeliveryPoint().substring(0,2));
		}			
		
		// the following are SRS 228
		setRemovalIndicator(pp.nextToken());
		if (getRemovalIndicator().length() > 1) {
			setRemovalIndicator(getRemovalIndicator().substring(0,1));
		}
		
		setOverlabelIndicator(pp.nextToken());
	    if (getOverlabelIndicator().length() > 2)
	    {
	    	setOverlabelIndicator(getOverlabelIndicator().substring(0,2));
	    }
		
		if ("01".equals(getOverlabelIndicator().trim()))
		{
			setOverlabelBarcodeConstructCode(pp.nextToken().trim());
			setOverlabelNumber(pp.nextToken());
			if (getOverlabelNumber().length() > 34) {
				setOverlabelNumber(getOverlabelNumber().substring(0,34));
			}
			setAltPic(getOverlabelNumber());
			if (getAltPic().length() > 34) {
				setAltPic(getAltPic().substring(0,34));
			}
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
			
		setCustomerReferenceNumber2(pp.nextToken());
		if (getCustomerReferenceNumber2().length() > 30) {
			setCustomerReferenceNumber2(getCustomerReferenceNumber2().substring(0,30));
		}
		
		setRecipientName(pp.nextToken());
		if (getRecipientName().length() > 48) {
			setRecipientName(getRecipientName().substring(0,48));
		}
		
		setDeliveryAddress(pp.nextToken());
		if (getDeliveryAddress().length() > 48) {
			setDeliveryAddress(getDeliveryAddress().substring(0,48));
		}
		
		setAncillaryServiceEndorsement(pp.nextToken());
		if (getAncillaryServiceEndorsement().length() > 3) {
			setAncillaryServiceEndorsement(getAncillaryServiceEndorsement().substring(0,3));
		}
		
		setAddressServiceParticipantCode(pp.nextToken());
		if (getAddressServiceParticipantCode().length() > 9) {
			setAddressServiceParticipantCode(getAddressServiceParticipantCode().substring(0,9));
		}
		
		setKeyLine(pp.nextToken());
		if (getKeyLine().length() > 16) {
			setKeyLine(getKeyLine().substring(0,16));
		}
		
		setReturnAddress(pp.nextToken());
		if (getReturnAddress().length() > 48) {
			setReturnAddress(getReturnAddress().substring(0,48));
		}
		
		setReturnAddressCity(pp.nextToken());
		if (getReturnAddressCity().length() >  28) {
			setReturnAddressCity(getReturnAddressCity().substring(0,28));
		}
		
		setReturnAddressState(pp.nextToken());
		if (getReturnAddressState().length() > 2) {
			setReturnAddressState(getReturnAddressState().substring(0,2));
		}
		
		setReturnAddressZip(pp.nextToken());
		if (getReturnAddressZip().length() > 5) {
			setReturnAddressZip(getReturnAddressZip().substring(0,5));
		}		
		
		setLogisticMailingFacilityCRID(pp.nextToken());	
		if (getLogisticMailingFacilityCRID().length() > 15) {
			setLogisticMailingFacilityCRID(getLogisticMailingFacilityCRID().substring(0,15));
		}
				
		 return getFilteredType(); 
	}


	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
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
		this.altPic = altPic;
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
