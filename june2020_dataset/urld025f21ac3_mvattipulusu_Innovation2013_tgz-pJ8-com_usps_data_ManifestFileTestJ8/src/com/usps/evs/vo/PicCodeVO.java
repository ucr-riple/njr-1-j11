/*
 * Created on Dec 14, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;


/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PicCodeVO  {
	private String postalRoutingCode;
	private String zip;
	private String applicationIdentifier;
	private String serviceTypeCode;
	private String duns;
	private String packageID;
	private String checkDigit;
	private String dunsPkgidDzip;
	private String serialNumber;
	private String originCountryCode;
	private String alternatePackageID;
	private String alternateDuns;
	private String alternateDunsPkgidDzip;
	private String alternateBarCodeConstruct;
	private String alternatePicCode;
		
	/**
	 * 
	 */
	public PicCodeVO() {
	}

	public static void main(String[] args) {
	}


	/**
	 * @return
	 */
	public String getCheckDigit() {
		return checkDigit;
	}

	/**
	 * @return
	 */
	public String getZip() {
		return zip;
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
	public String getDunsPkgidDzip() {
		return dunsPkgidDzip;
	}

	/**
	 * @return
	 */
	public String getPackageID() {
		return packageID;
	}

	/**
	 * @return
	 */
	public String getPostalRoutingCode() {
		return postalRoutingCode;
	}

	/**
	 * @return
	 */
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	/**
	 * @param string
	 */
	public void setCheckDigit(String string) {
		checkDigit = string;
	}

	/**
	 * @param string
	 */
	public void setZip(String string) {
		zip = string;
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
	public void setDunsPkgidDzip(String string) {
		dunsPkgidDzip = string;
	}

	/**
	 * @param string
	 */
	public void setPackageID(String string) {
		packageID = string;
	}

	/**
	 * @param string
	 */
	public void setPostalRoutingCode(String string) {
		postalRoutingCode = string;
	}

	/**
	 * @param string
	 */
	public void setServiceTypeCode(String string) {
		serviceTypeCode = string;
	}

	/**
	 * @return
	 */
	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	/**
	 * @param string
	 */
	public void setApplicationIdentifier(String string) {
		applicationIdentifier = string;
	}
	
	public void setOriginCountryCode(String originCountryCode) {
		this.originCountryCode = originCountryCode;
	}
	
	public String getOriginCountryCode() {
		return originCountryCode;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setAlternatePackageID(String alternatePackageID){
		this.alternatePackageID = alternatePackageID;
	}
	
	public String getAlternatePackageID() {
		return alternatePackageID;
	}
	
	public void setAlternateDuns(String alternateDuns) {
		this.alternateDuns = alternateDuns;
	}
	
	public String getAlternateDuns() {
		return alternateDuns;
	}
	
	public void setAlternateDunsPkgidDzip(String alternateDunsPkgidDzip) {
		this.alternateDunsPkgidDzip = alternateDunsPkgidDzip;
	}
	
	public String getAlternateDunsPkgidDzip() { 
		return alternateDunsPkgidDzip;
	}
	
	public void setAlternateBarCodeConstruct(String alternateBarCodeConstruct) {
		this.alternateBarCodeConstruct = alternateBarCodeConstruct;
	}
	
	public String getAlternateBarCodeContruct() {
		return alternateBarCodeConstruct;
	}

	public void setAlternatePicCode(String alternatePicCode) {
		this.alternatePicCode = alternatePicCode;
	}
	
	public String getAlternatePicCode() {
		return alternatePicCode;
	}
}
