/*
 * Created on Sep 4, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import java.util.Calendar;

/**
 * @author X6DXB0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DestinationZipEditRulesVO {
	private Calendar MailingDate;
	private String entryFacilityZip;
	private String DestinationZip;
	private String paramModBMC;
	private String paramModSCF;
	private String paramModDDU;
	private String message;
	
	/**
	 * 
	 */
	public DestinationZipEditRulesVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public String getDestinationZip() {
		return DestinationZip;
	}

	/**
	 * @return
	 */
	public String getEntryFacilityZip() {
		return entryFacilityZip;
	}

	/**
	 * @return
	 */
	public Calendar getMailingDate() {
		return MailingDate;
	}

	/**
	 * @return
	 */
	public String getParamModBMC() {
		return paramModBMC;
	}

	/**
	 * @return
	 */
	public String getParamModDDU() {
		return paramModDDU;
	}

	/**
	 * @return
	 */
	public String getParamModSCF() {
		return paramModSCF;
	}

	/**
	 * @param string
	 */
	public void setDestinationZip(String string) {
		DestinationZip = string;
	}

	/**
	 * @param string
	 */
	public void setEntryFacilityZip(String string) {
		entryFacilityZip = string;
	}

	/**
	 * @param calendar
	 */
	public void setMailingDate(Calendar calendar) {
		MailingDate = calendar;
	}

	/**
	 * @param string
	 */
	public void setParamModBMC(String string) {
		paramModBMC = string;
	}

	/**
	 * @param string
	 */
	public void setParamModDDU(String string) {
		paramModDDU = string;
	}

	/**
	 * @param string
	 */
	public void setParamModSCF(String string) {
		paramModSCF = string;
	}


	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	public void resetValue() {
		MailingDate = null;
		entryFacilityZip = null;
		DestinationZip = null;
		paramModBMC = null;
		paramModSCF = null;
		paramModDDU = null;
		message = null;
	}
}
