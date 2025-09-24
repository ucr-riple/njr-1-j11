/*
 * Created on Dec 15, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import com.evs.data.util.CommonUtils;
import com.evs.data.util.PicCodeI;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PicCodeParserManifestV20 implements PicCodeI {
	 
	/**
	 * 
	 */
	public PicCodeParserManifestV20() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.validator.attributes.v15.PicCodeI#parse(java.lang.Object, java.lang.Object)
	 */
	public void parse(Object parseFrom, Object parseTo) {
		PicCodeVO picCodeVo = (PicCodeVO) parseTo;
		ManifestDetailRawLineV20Vo detailVo = (ManifestDetailRawLineV20Vo) parseFrom;
		
		String barcodeConstruct = detailVo.getBarcodeConstruct().trim();
		String overlabelBarcodeConstruct = detailVo.getOverlabelBarcodeConstructCode().trim();
		
		if (! "INV".equals(barcodeConstruct)) {  // INV is set when minimum of detail length is not statisfied.
			String pic = CommonUtils.padPicCodeV15(detailVo.getPicCode(), barcodeConstruct);
			detailVo.setPicCode(pic);
		}
		
		if ("C01".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 12));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(12, 14));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(14, 17));
			picCodeVo.setDuns(detailVo.getPicCode().substring(17, 26));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(26, 33));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(33, 34));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(12, 34));
		} else if ("C02".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 8));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(8, 10));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(10, 13));
			picCodeVo.setDuns(detailVo.getPicCode().substring(13, 22));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(22, 33));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(33, 34));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(8, 34));
		} else if ("C03".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 8));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(8, 10));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(10, 13));
			picCodeVo.setDuns(detailVo.getPicCode().substring(13, 22));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(22, 29));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(29, 30));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(8,30));
		} else if ("C04".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0, 2));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(2, 5));
			picCodeVo.setDuns(detailVo.getPicCode().substring(5, 14));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(14, 21));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(21, 22));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(0, 22));
		} else if ("C05".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 12));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(12, 14));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(14, 17));
			picCodeVo.setDuns(detailVo.getPicCode().substring(17, 23));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(23, 33));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(33, 34));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(12, 34));
		} else if ("C06".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 8));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(8, 10));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(10, 13));
			picCodeVo.setDuns(detailVo.getPicCode().substring(13, 19));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(19, 33));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(33, 34));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(8, 34));
		} else if ("C07".equals(barcodeConstruct)) {
			picCodeVo.setPostalRoutingCode(detailVo.getPicCode().substring(0,3));
			picCodeVo.setZip(detailVo.getPicCode().substring(3, 8));
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(8, 10));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(10, 13));
			picCodeVo.setDuns(detailVo.getPicCode().substring(13, 19));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(19, 29));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(29, 30));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(8, 30));
		} else if ("C08".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0, 2));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(2, 5));
			picCodeVo.setDuns(detailVo.getPicCode().substring(5, 11));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(11, 21));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(21, 22));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(0,22));
		} else if ("C09".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0, 2));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(2, 5));
			picCodeVo.setDuns(detailVo.getPicCode().substring(5, 11));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(11, 25));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(25, 26));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(0, 26));
		} else if ("C10".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0, 2));
			picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(2, 5));
			picCodeVo.setDuns(detailVo.getPicCode().substring(5, 14));
			picCodeVo.setPackageID(detailVo.getPicCode().substring(14, 25));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(25, 26));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(0, 26));
		} else if ("L01".equals(barcodeConstruct)) { /* Old format Pic Code in a new format file V1.5 */
			//Release 32 added check to see of the mailer is sending anything other than 22 digit long PICs
			if(detailVo.getPicCode().trim().length()==PicCodeParserManifestV15.L01PicCodeLength){
				picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0, 2));
				picCodeVo.setServiceTypeCode(detailVo.getPicCode().substring(2, 4));
				picCodeVo.setDuns(detailVo.getPicCode().substring(4, 13));
				picCodeVo.setPackageID(detailVo.getPicCode().substring(13, 21));
				picCodeVo.setCheckDigit(detailVo.getPicCode().substring(21, 22));
				picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().substring(0, 22));
			} else {
				picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().trim());
				picCodeVo.setApplicationIdentifier(" ");
				picCodeVo.setPackageID(" ");
				picCodeVo.setDuns("000000000");
				picCodeVo.setServiceTypeCode(" ");
			}
		}else if ("I01".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0,2));
			picCodeVo.setSerialNumber(detailVo.getPicCode().substring(2,11));
			picCodeVo.setOriginCountryCode(detailVo.getPicCode().substring(12));
			picCodeVo.setServiceTypeCode(detailVo.getStc());
			picCodeVo.setDuns(detailVo.getMailerOwnerMailerId());
			picCodeVo.setPackageID(detailVo.getPicCode().substring(3,11));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(11, 12));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode());
		} else if ("G01".equals(barcodeConstruct)) {
			picCodeVo.setApplicationIdentifier(detailVo.getPicCode().substring(0,2));
			picCodeVo.setSerialNumber(detailVo.getPicCode().substring(2,10));
			picCodeVo.setServiceTypeCode(detailVo.getStc());
			picCodeVo.setDuns(detailVo.getMailerOwnerMailerId());
			picCodeVo.setPackageID(detailVo.getPicCode().substring(3,10));
			picCodeVo.setCheckDigit(detailVo.getPicCode().substring(10));
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode());						
		}	 else  if ("".equals(barcodeConstruct)) { /* Competitor */
			// because we do not know how to parse IMpb to its attribute and some attibutes are not nullable, system needs to fill out
			picCodeVo.setApplicationIdentifier("00");
			picCodeVo.setDuns("000000000");
			picCodeVo.setPackageID(" ");
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().trim());
			picCodeVo.setServiceTypeCode(" ");
		} else { /* Invalid */
			// because we do not know how to parse IMpb to its attribute and some attibutes are not nullable, system needs to fill out
			picCodeVo.setDunsPkgidDzip(detailVo.getPicCode().trim());
			picCodeVo.setApplicationIdentifier(" ");
			picCodeVo.setPackageID(" ");
			picCodeVo.setDuns("000000000");
			picCodeVo.setServiceTypeCode(" ");
		}
	
		// now look for alternate package id for Detail17 records
		if ("01".equals(detailVo.getOverlabelIndicator().trim()))
		{
		if ("C01".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(17, 26));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(26, 33));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(12, 34));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C02".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(13, 22));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(22, 33));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(8, 34));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C03".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(13, 22));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(22, 29));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(8,30));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C04".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(5, 14));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(14, 21));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(0, 22));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C05".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(17, 23));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(23, 33));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(12, 34));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C06".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(13, 19));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(19, 33));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(8, 34));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C07".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(13, 19));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(19, 29));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(8, 30));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C08".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(5, 11));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(11, 21));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(0,22));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C09".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(5, 11));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(11, 25));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(0, 26));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("C10".equals(overlabelBarcodeConstruct)) {
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(5, 14));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(14, 25));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(0, 26));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		} else if ("L01".equals(overlabelBarcodeConstruct)) { /* Old format Pic Code in a new format file V1.5 */
			picCodeVo.setAlternateDuns(detailVo.getOverlabelNumber().substring(4, 13));
			picCodeVo.setAlternatePackageID(detailVo.getOverlabelNumber().substring(13, 21));
			picCodeVo.setAlternateDunsPkgidDzip(detailVo.getOverlabelNumber().substring(0, 22));
			picCodeVo.setAlternateBarCodeConstruct(detailVo.getOverlabelBarcodeConstructCode());
			picCodeVo.setAlternatePicCode(detailVo.getOverlabelNumber());
		}
		} // end if ("01".equals(detailVo.getOverlabelIndicator().trim()))

	
	}

	public static void main(String[] args) {
	}
}
