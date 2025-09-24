/*
 * Created on Dec 11, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import org.apache.commons.lang.StringUtils;

import com.evs.data.util.Constants;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManifestDetailRawLineV15Vo extends ManifestDetailRawLineVo {
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
	private String fastScheduledInductionDate;
	private String fastScheduledInducationTime;
	private String paymentAccountNumber;
	private String methodPayment;
	private String postOfficeAccountZip;
	private String meterSerialNumber;
	private String chargeBackCode;
	private String postageType;
	private String CSSCNumber;
	private String CSSCProductId;
	private String specialServiceCode4;
	private String specialServiceFee4;
	private String specialServiceCode5;
	private String specialServiceFee5;
	private String discountType;
	private String discountAmount;
	private String nonIncidentalEnclosureProcessCat;
	private String openDistributedContentsInd;
	private String filler;	
	private PicCodeVO picCodeVo;
	private String manifestedSurchargeType;
	private String manifestedSurchargeAmount;
		

	/**
	 * 
	 */
	public ManifestDetailRawLineV15Vo() {
	}


	public int parseLine(String line) {

		setEFileVersionNumber("1.5");

		checkMinimumLength(line); 		
		setDetailRecId(line.substring(0,2));
		
		if (getFilteredType() != Constants.FILTERED_NONE) {
			return getFilteredType();
		}
		
		setPicCode(line.substring(2, 36)); 
		setClassOfMail(line.substring(36, 38));
		setStc(line.substring(38, 42));
		setBarcodeConstruct(line.substring(42, 46));
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
				
		this.setNonIncidentalEnclosureRateIndicator(line.substring(478, 480));
		setNonIncidentalEnclosureClass(line.substring(480, 482));
		setNonIncidentalEnclosurePostage(line.substring(482, 489));
		setNonIncidentalEnclosureWeight(line.substring(489, 498));
		setNonIncidentalEnclosureProcessCat(line.substring(498, 499));
		setPostalRoutingBarcodeIndicator(line.substring(499, 500));
		setOpenDistributedContentsInd(line.substring(500, 502));
		if (line.length() > 502)
			setFiller(line.substring(502));
				
		 return getFilteredType(); 
	}

	private void checkMinimumLength(String line) {
		if (line.length() < 502) {
			if (line.length() >= 46) {
				setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
				setPicCode(line.substring(3, 36));
				setClassOfMail(line.substring(36, 38).trim());
				setStc(line.substring(38, 42));
				setBarcodeConstruct(line.substring(42, 46));				
				setFilteredMessage("Filtered because detail line is less then 502.  Minimum is 502");
			} else {
				setFilteredType(Constants.FILTERED_DETAIL_TOO_SHORT);
				if (line.length() >= 38) {
					setPicCode(line.substring(3, 36));
					setClassOfMail(line.substring(36,38));
					setStc(" ");
					setBarcodeConstruct("INV"); // Prevent null				
				} else {
					if (line.length() >= 3) {
						setClassOfMail("XX");
						setPicCode(line.substring(3));
						setStc(" ");
						setBarcodeConstruct("INV"); // Prevent null
					} else {				
						setClassOfMail("XX");
						setPicCode("0");
						setStc(" ");
						setBarcodeConstruct("INV"); // Prevent null
					}
				}
				setFilteredMessage("Filtered for Bad Record because detail line is less then 46.");
			}
			// Add other NOT_NULL Constraint value, Other will be set later
			setPostage("0");
			setDuns("000000000");
			setDestinationZip("00000");				
			logger.info(getFilteredMessage()+" Length="+line.length()+", PicCode="+getPicCode());
		}
	}


	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public String getDetailRecId() {
		return detailRecId;
	}

	/**
	 * @param string
	 */
	public void setDetailRecId(String string) {
		detailRecId = string;
	}


	/**
	 * @return
	 */
	public String getBarcodeConstruct() {
		return barcodeConstruct;
	}

	/**
	 * @return
	 */
	public String getCarrierRoute() {
		return carrierRoute;
	}

	/**
	 * @return
	 */
	public String getChargeBackCode() {
		return chargeBackCode;
	}

	/**
	 * @return
	 */
	public String getContainerId1() {
		return containerId1;
	}

	/**
	 * @return
	 */
	public String getContainerId2() {
		return containerId2;
	}

	/**
	 * @return
	 */
	public String getContainerId3() {
		return containerId3;
	}

	/**
	 * @return
	 */
	public String getContainerType1() {
		return containerType1;
	}

	/**
	 * @return
	 */
	public String getContainerType2() {
		return containerType2;
	}

	/**
	 * @return
	 */
	public String getContainerType3() {
		return containerType3;
	}

	/**
	 * @return
	 */
	public String getCSSCNumber() {
		return CSSCNumber;
	}

	/**
	 * @return
	 */
	public String getCSSCProductId() {
		return CSSCProductId;
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
	public String getDiscountType() {
		return discountType;
	}

	/**
	 * @return
	 */
	public String getFastReservationNumber() {
		return fastReservationNumber;
	}

	/**
	 * @return
	 */
	public String getFastScheduledInducationTime() {
		return fastScheduledInducationTime;
	}

	/**
	 * @return
	 */
	public String getFastScheduledInductionDate() {
		return fastScheduledInductionDate;
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
	public String getMethodPayment() {
		return methodPayment;
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
	public String getPaymentAccountNumber() {
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
	public String getPostOfficeAccountZip() {
		return postOfficeAccountZip;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceCode4() {
		return specialServiceCode4;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceCode5() {
		return specialServiceCode5;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceFee4() {
		return specialServiceFee4;
	}

	/**
	 * @return
	 */
	public String getSpecialServiceFee5() {
		return specialServiceFee5;
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
	public void setBarcodeConstruct(String string) {
		barcodeConstruct = string;
	}

	/**
	 * @param string
	 */
	public void setCarrierRoute(String string) {
		carrierRoute = string;
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
	public void setContainerId1(String string) {
		containerId1 = string;
	}

	/**
	 * @param string
	 */
	public void setContainerId2(String string) {
		containerId2 = string;
	}

	/**
	 * @param string
	 */
	public void setContainerId3(String string) {
		containerId3 = string;
	}

	/**
	 * @param string
	 */
	public void setContainerType1(String string) {
		containerType1 = string;
	}

	/**
	 * @param string
	 */
	public void setContainerType2(String string) {
		containerType2 = string;
	}

	/**
	 * @param string
	 */
	public void setContainerType3(String string) {
		containerType3 = string;
	}

	/**
	 * @param string
	 */
	public void setCSSCNumber(String string) {
		CSSCNumber = string;
	}

	/**
	 * @param string
	 */
	public void setCSSCProductId(String string) {
		CSSCProductId = string;
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
	public void setDiscountType(String string) {
		discountType = string;
	}

	/**
	 * @param string
	 */
	public void setFastReservationNumber(String string) {
		fastReservationNumber = string;
	}

	/**
	 * @param string
	 */
	public void setFastScheduledInducationTime(String string) {
		fastScheduledInducationTime = string;
	}

	/**
	 * @param string
	 */
	public void setFastScheduledInductionDate(String string) {
		fastScheduledInductionDate = string;
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
	 * @param string
	 */
	public void setMethodPayment(String string) {
		methodPayment = string;
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
	 * @param string
	 */
	public void setPaymentAccountNumber(String string) {
		paymentAccountNumber = string;
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
	public void setPostOfficeAccountZip(String string) {
		postOfficeAccountZip = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceCode4(String string) {
		specialServiceCode4 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceCode5(String string) {
		specialServiceCode5 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceFee4(String string) {
		specialServiceFee4 = string;
	}

	/**
	 * @param string
	 */
	public void setSpecialServiceFee5(String string) {
		specialServiceFee5 = string;
	}

	/**
	 * @param string
	 */
	public void setStc(String string) {
		stc = string;
	}

	/**
	 * @return
	 */
	public String getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param string
	 */
	public void setDiscountAmount(String string) {
		discountAmount = string;
	}

	/**
	 * @return
	 */
	public PicCodeVO getPicCodeVo() {
		return picCodeVo;
	}

	/**
	 * @param codeVO
	 */
	public void setPicCodeVo(PicCodeVO codeVO) {
		picCodeVo = codeVO;
	}

	/**
	 * @return
	 */
	public String getManifestedSurchargeAmount() {
		return manifestedSurchargeAmount;
	}

	/**
	 * @return
	 */
	public String getManifestedSurchargeType() {
		return manifestedSurchargeType;
	}

	/**
	 * @param string
	 */
	public void setManifestedSurchargeAmount(String string) {
		manifestedSurchargeAmount = string;
	}

	/**
	 * @param string
	 */
	public void setManifestedSurchargeType(String string) {
		manifestedSurchargeType = string;
	}

}
