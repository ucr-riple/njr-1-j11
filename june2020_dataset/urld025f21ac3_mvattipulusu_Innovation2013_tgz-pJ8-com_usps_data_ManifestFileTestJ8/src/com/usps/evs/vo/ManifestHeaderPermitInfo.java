/*
 * ManifestHeader.java
 *
 * Manifest header record to store all permit info related.
 */

package com.usps.evs.vo;

public class ManifestHeaderPermitInfo 
{
    private String fileNumber;
    private int paymentAccountNumber;
    private String postOfficeOfAccountZipCode;
    private ManifestHeader header;
    
    public ManifestHeaderPermitInfo()
    {
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String print =
		 
		"[FileNumber]="+fileNumber+
		"[PaymentAccountNumber]="+paymentAccountNumber+
		"[PostOfficeAccountZipCode]="+postOfficeOfAccountZipCode;
		
		return print;
	}

	/**
	 * @return
	 */
	public String getFileNumber() {
		return fileNumber;
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
	public String getPostOfficeOfAccountZipCode() {
		return postOfficeOfAccountZipCode;
	}

	/**
	 * @param string
	 */
	public void setFileNumber(String string) {
		fileNumber = string;
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
	public void setPostOfficeOfAccountZipCode(String string) {
		postOfficeOfAccountZipCode = string;
	}
	/**
	 * @param header
	 */
	public void setHeader(ManifestHeader header) {
		this.header = header;
	}

	/**
	 * @return
	 */
	public ManifestHeader getHeader() {
		return header;
	}

}
