/*
 * Created on Jan 17, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

/**
 * @author yw8bk0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MailerIDInfo {
	private String corporateDuns = "";
	private String siteCustomerDuns = "";
	private String siteCustomerName = "";
	private String statusFlag = "";


	/**
	 * @return
	 */
	public String getCorporateDuns() {
		return corporateDuns;
	}

	/**
	 * @return
	 */
	public String getSiteCustomerDuns() {
		return siteCustomerDuns;
	}

	/**
	 * @return
	 */
	public String getSiteCustomerName() {
		return siteCustomerName;
	}

	/**
	 * @return
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param string
	 */
	public void setCorporateDuns(String string) {
		corporateDuns = string;
	}

	/**
	 * @param string
	 */
	public void setSiteCustomerDuns(String string) {
		siteCustomerDuns = string;
	}

	/**
	 * @param string
	 */
	public void setSiteCustomerName(String string) {
		siteCustomerName = string;
	}

	/**
	 * @param string
	 */
	public void setStatusFlag(String string) {
		statusFlag = string;
	}

}
