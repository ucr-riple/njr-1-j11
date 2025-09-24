package com.usps.data;

import java.util.Vector;

import com.evs.data.util.Constants;
import com.evs.data.util.DataTypes;
import com.evs.data.util.PicCodeI;
import com.usps.evs.vo.ManifestDetail;
import com.usps.evs.vo.ManifestDetailRawLineV20Vo;
import com.usps.evs.vo.PicCodeParserManifestV20;
import com.usps.evs.vo.PicCodeVO;
import com.usps.evs.vo.SpecialServiceVO;

public class ManifestDetailProcessor {

	 //TODO: Move below methods to a ParseDetail class
		private static final int PRECISION = 2;
		public static final String PROCESSING_CATEGORY_EVS_PARCEL_FIRSTCLASS = "6";
		public static String massageProcessingCategory(String processingCategory) {

			if ("".equals(processingCategory)) {
				processingCategory = Constants.PROCESSING_CATEGORY_EVS_PARCEL_FIRSTCLASS;
			}
			return processingCategory;
		}
			
		 private void setPicCode(PicCodeVO picVo, ManifestDetailRawLineV20Vo detailRawLine, ManifestDetail detail) {
				PicCodeI picCode = new PicCodeParserManifestV20();	
				picCode.parse(detailRawLine, picVo);
				detailRawLine.setPicCodeVo(picVo);
				detail.setPicCode(detailRawLine.getPicCode().trim());
				detail.setDcDuns(picVo.getDuns());
				detail.setDcPkgid(picVo.getPackageID());
				detail.setServiceTypeCode(picVo.getServiceTypeCode());
				detail.setDcDunsPkgidDzip(picVo.getDunsPkgidDzip());
				//detail.setSerialNumber(picVo.getSerialNumber());
				detail.setApplicationIdentifier(picVo.getApplicationIdentifier());
				// the following needs to be set because it is referenced in ManifestDetailRawValidatorV20
//				detailRawLine.setApplicationIdentifier(picVo.getApplicationIdentifier());
				detail.setOriginCountryCode(picVo.getOriginCountryCode());
				/*detail.setAltPkgId(picVo.getAlternatePackageID());
				detail.setAltDuns(picVo.getAlternateDuns());
				detail.setAltPic(picVo.getAlternatePicCode());
				detail.setAltPkgId(picVo.getAlternatePackageID());
				detail.setAltDunsPkgIdZip(picVo.getAlternateDunsPkgidDzip());
				detail.setAltBarConstCode(picVo.getAlternateBarCodeContruct());*/
			}
		 
			/**
			 * @param serviceList
			 * @param string
			 * @param string2
			 */
			protected void addServiceList(Vector serviceList, String string, String string2, String barcodeConstruct) {
				if ("L01".equals(barcodeConstruct)) {
					if (DataTypes.isSpecialServiceCode(string.trim()) || DataTypes.isSpecialServiceCode(string.trim())) {
						double rate = DataTypes.toDouble(string2.trim(), PRECISION);  
						serviceList.add(new SpecialServiceVO(string, rate));
					}
				} else {
					if (DataTypes.isSpecialServiceCodeV15(string.trim()) || DataTypes.isSpecialServiceCode(string.trim())) {
						double rate = DataTypes.toDouble(string2.trim(), PRECISION);  
						serviceList.add(new SpecialServiceVO(string, rate));
					}
				}
			}
			
		public ManifestDetail parseDetailLine(String line){

			ManifestDetailRawLineV20Vo detailRawLine = new ManifestDetailRawLineV20Vo();
			
			ManifestDetail detail = new ManifestDetail();

			detailRawLine.parseLine(line);

			detail.setClassOfMail(detailRawLine.getClassOfMail().trim());
			
			PicCodeVO picVo = new PicCodeVO();
			setPicCode(picVo, detailRawLine, detail);

			detail.setDestinationZip(detailRawLine.getDestinationZip().trim());
			
			if (detailRawLine.getFilteredType() != Constants.FILTERED_NONE) {
				detail.setDuns(detailRawLine.getDuns()); // Required cause Not-null constraint
				detail.setFilteredType(detailRawLine.getFilteredType());
				detail.setFilteredMessage(detailRawLine.getFilteredMessage());
				return detail;
			}

			
			detail.setClassOfMail(detailRawLine.getClassOfMail().trim());
			
			//setPicCode(picVo, detailRawLine);
			
			detail.setEFileVersionNumber(Double.parseDouble(detailRawLine.getEFileVersionNumber()));
			detail.setDestinationZipPlus4(detailRawLine.getDestinationZipPlus4().trim());
			detail.setCountryCode(detailRawLine.getCountryCode().trim()); //TODO Continue
			detail.setPostage(DataTypes.toBigDecimal(detailRawLine.getPostage().trim(), 3));
			detail.setUomCode(detailRawLine.getUomCode().trim());
			detail.setWeight(DataTypes.toDouble(detailRawLine.getWeight().trim(), 4));

			String processingCategory = detailRawLine.getProcessingCategory().trim();
			detail.setProcessingCategory(this.massageProcessingCategory(processingCategory));
			detail.setDestinationRateIndicator(detailRawLine.getDestinationRateIndicator().trim());
			detail.setRateIndicator(detailRawLine.getRateIndicator().trim());
			detail.setZone(detailRawLine.getZone().trim());
//			detail.setPoBoxIndicator(detailRawLine.getPoBoxIndicator().trim());
//			detail.setWaiverOfSignature(detailRawLine.getWaiverOfSignature().trim());
			//detail.setNoWeekendHolidayDelivery(detailRawLine.getNoWeekendHolidayDelivery().trim());
			detail.setArticleValue(DataTypes.toDouble(detailRawLine.getArticleValue().trim(), 2));
			//detail.setCodAmountDueSender(DataTypes.toDouble(detailRawLine.getCodAmountDueSender().trim(), 2));
			detail.setHandlingCharge(DataTypes.toBigDecimal(detailRawLine.getHandlingCharge().trim(), 2));
	        
			// PARSE THE SERVICE LIST FROM LINE CREATING VECTOR OF SERVICELIST VOs
			//detail.setServicesList(parseServiceList(line));
			Vector serviceList = new Vector();
			addServiceList(serviceList, detailRawLine.getSpecialServiceCode1().trim(), detailRawLine.getSpecialServiceFee1(),detailRawLine.getBarcodeConstruct());
			addServiceList(serviceList, detailRawLine.getSpecialServiceCode2().trim(), detailRawLine.getSpecialServiceFee2(),detailRawLine.getBarcodeConstruct());
			addServiceList(serviceList, detailRawLine.getSpecialServiceCode3().trim(), detailRawLine.getSpecialServiceFee3(),detailRawLine.getBarcodeConstruct());
			addServiceList(serviceList, detailRawLine.getSpecialServiceCode4().trim(), detailRawLine.getSpecialServiceFee4(),detailRawLine.getBarcodeConstruct());
			addServiceList(serviceList, detailRawLine.getSpecialServiceCode5().trim(), detailRawLine.getSpecialServiceFee5(),detailRawLine.getBarcodeConstruct());	
			
			detail.setServicesList(serviceList);
		        
			// R-2006 Rate Case Dimensional Weighting 
			detail.setLength(DataTypes.toDouble(detailRawLine.getLength().trim(), 2)); 
			detail.setWidth(DataTypes.toDouble(detailRawLine.getWidth().trim(), 2)); 
			detail.setHeight(DataTypes.toDouble(detailRawLine.getHeight().trim(), 2)); 
			detail.setDimensionalWeight(DataTypes.toDouble(detailRawLine.getDimensionalWeight().trim(), 2));
	        
			detail.setDuns(detailRawLine.getMailerOwnerMailerId());
			detail.setCustomerInternalReference(detailRawLine.getCustomerInternalReference().trim());
			detail.setSurchargeType(detailRawLine.getSurchargeType().trim());  
			detail.setSurchargeAmount(DataTypes.toBigDecimal(detailRawLine.getSurchargeAmount().trim(), 3)); 
			/*detail.setNonIncidentalEnclosureClass(detailRawLine.getNonIncidentalEnclosureClass().trim());
			detail.setNonIncidentalEnclosureRateIndicator(detailRawLine.getNonIncidentalEnclosureRateIndicator()); 
			detail.setNonIncidentalEnclosurePostage(DataTypes.toBigDecimal(detailRawLine.getNonIncidentalEnclosurePostage().trim(), 3));
			detail.setNonIncidentalEnclosureWeight(DataTypes.toDouble(detailRawLine.getNonIncidentalEnclosureWeight().trim(), 4));*/
//			detail.setCustomsDesignatedNumber(DataTypes.toInteger(detailRawLine.getCustomsDesignatedNumber().trim()));
			detail.setPostalRoutingBarcodeIndicator(detailRawLine.getPostalRoutingBarcodeIndicator().trim());
			detail.setFilteredType(detailRawLine.getFilteredType());
			detail.setFilteredMessage(detailRawLine.getFilteredMessage());
			// 17.1.0
			// Copy to all original mailer
			detail.setMailerWeight(detail.getWeight());
			detail.setMailerDestRateInd(detail.getDestinationRateIndicator());
			detail.setMailerMailClass(detail.getMailClass());
			detail.setMailerDimWeight(detail.getDimensionalWeight());
			detail.setMailerProcessCat(detail.getProcessingCategory());
			detail.setMailerRateInd(detail.getRateIndicator());
			detail.setMailerRoutingBarcode(detail.getPostalRoutingBarcodeIndicator());
			detail.setMailerSurchargeType(detail.getSurchargeType());
			detail.setMailerZone(detail.getZone());

			//REL21.0
			/*detail.setWarningType(detailRawLine.getWarningType());
			detail.setWarningValue(detailRawLine.getWarningValue());
			detail.setWarningMessage(detailRawLine.getWarningMessage());*/
			
			
			// take the rest of 1.5 field
			detail.setDetailRecId(detailRawLine.getDetailRecId());
			detail.setStc(detailRawLine.getStc());
			//detail.setBarcodeConstruct(detailRawLine.getBarcodeConstruct());
			detail.setDestinationFacilityType(detailRawLine.getDestinationFacilityType());
			detail.setPostalCode(detailRawLine.getPostalCode());
			//detail.setCarrierRoute(detailRawLine.getCarrierRoute());
			detail.setLogisticsManagerMailer(detailRawLine.getLogisticsManagerMailer());
			detail.setMailerOwnerMailerId(detailRawLine.getMailerOwnerMailerId());
			/*detail.setContainerId1(detailRawLine.getContainerId1());
			detail.setContainerType1(detailRawLine.getContainerType1());
			detail.setContainerId2(detailRawLine.getContainerId2());
			detail.setContainerType2(detailRawLine.getContainerType2());
			detail.setContainerId3(detailRawLine.getContainerId3());
			detail.setContainerType3(detailRawLine.getContainerType3());
*/			detail.setMailerOwnerCRID(detailRawLine.getMailerOwnerCRID());
			/*detail.setFastReservationNumber(detailRawLine.getFastReservationNumber());
			detail.setFastScheduledInductionDate(DataTypes.toInteger(detailRawLine.getFastScheduledInductionDate()));
			detail.setFastScheduledInducationTime(DataTypes.toInteger(detailRawLine.getFastScheduledInducationTime()));*/
			detail.setPaymentAccountNumber(DataTypes.toInteger(detailRawLine.getPaymentAccountNumber()));
			detail.setPaymentMethod(DataTypes.toInteger(detailRawLine.getMethodPayment()));
			detail.setPostOfficeOfAccountZipCode(detailRawLine.getPostOfficeAccountZip());
			detail.setMeterSerialNumber(detailRawLine.getMeterSerialNumber());
			detail.setChargeBackCode(detailRawLine.getChargeBackCode());
			detail.setPostageType(detailRawLine.getPostageType());
			/*detail.setCSSCNumber(detailRawLine.getCSSCNumber());
			detail.setCSSCProductId(detailRawLine.getCSSCProductId());*/
			detail.setManifestedSurchargeType(detailRawLine.getManifestedSurchargeType());
			detail.setManifestedSurchargeAmount(DataTypes.toDouble(detailRawLine.getManifestedSurchargeAmount()));
			detail.setDiscountType(detailRawLine.getDiscountType());
			detail.setDiscountAmount(DataTypes.toDouble(detailRawLine.getDiscountAmount(), 3));

			detail.setNonIncidentalEnclosureProcessCat(detailRawLine.getNonIncidentalEnclosureProcessCat());
			detail.setOpenDistributedContentsInd(detailRawLine.getOpenDistributedContentsInd());
			detail.setPoBoxIndicator(detailRawLine.getPoBoxIndicator());
			//REL 26.0.0
			detail.setWaiverOfSignature(detailRawLine.getWaiverOfSignature());
			//REL 31.0.0
/*			if (DataTypes.isBarcodeInternational(detail.getBarcodeConstruct().trim())) 
				detail.setIntlMailInd("Y");
			else detail.setIntlMailInd("N");*/
			//detail.setDeliveryOptionIndicator(detailRawLine.getDeliveryOptionIndicator());
			detail.setDestinationDeliveryPoint(detailRawLine.getDestinationDeliveryPoint());
/*			detail.setRemovalIndicator(detailRawLine.getRemovalIndicator());
			detail.setOverlabelIndicator(detailRawLine.getOverlabelIndicator());
*/			detail.setCustomerReferenceNumber2(detailRawLine.getCustomerReferenceNumber2().trim());
/*			if ("01".equals(detail.getOverlabelIndicator().trim())) {
				detail.setOverlabelBarcodeConstructCode(detailRawLine.getOverlabelBarcodeConstructCode().trim());
				detail.setOverlabelNumber(detailRawLine.getOverlabelNumber().trim());
			}
*///			detail.setRecipientName(detailRawLine.getRecipientName());
			detail.setDeliveryAddress(detailRawLine.getDeliveryAddress());
	//		detail.setAncillaryServiceEndorsement(detailRawLine.getAncillaryServiceEndorsement());
		//	detail.setAddressServiceParticipantCode(detailRawLine.getAddressServiceParticipantCode());
			detail.setKeyLine(detailRawLine.getKeyLine());
			detail.setReturnAddress(detailRawLine.getReturnAddress());
			detail.setReturnAddressCity(detailRawLine.getReturnAddressCity());
			detail.setReturnAddressState(detailRawLine.getReturnAddressState());
			detail.setReturnAddressZip(detailRawLine.getReturnAddressZip());
			//detail.setLogisticMailingFacilityCRID(detailRawLine.getLogisticMailingFacilityCRID());
			detail.setInternationalProcessStatus(detailRawLine.getInternationalProcessStatus());
			
			detail.setFiller(detailRawLine.getFiller());	
					
			return detail;
			
		}

}
