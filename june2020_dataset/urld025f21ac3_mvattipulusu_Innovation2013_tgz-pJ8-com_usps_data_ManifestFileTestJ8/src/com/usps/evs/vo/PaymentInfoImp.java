/*
 * Created on Jan 13, 2010
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
public class PaymentInfoImp implements PaymentInfoI {

	private int paymentAccountNumber;
	private String postOfficeOfAccountZipCode;
	private String financeNumber;
	private int paymentMethod;
	private String corpDuns;
	private boolean valid;
	
	
	/**
	 * 
	 */
	public PaymentInfoImp() {
		super();
	}

	public PaymentInfoImp(int paymentAccountNumber, String postOfficeOfAccountZipCode, String financeNumber, int paymentMethod, boolean valid, String corpDuns) {
		this.paymentAccountNumber = paymentAccountNumber;
		this.postOfficeOfAccountZipCode = postOfficeOfAccountZipCode;
		this.financeNumber = financeNumber;
		this.paymentMethod = paymentMethod;
		this.corpDuns = corpDuns;
		this.valid = valid;
	}


	public PaymentInfoImp(int paymentAccountNumber, String postOfficeOfAccountZipCode, String financeNumber, int paymentMethod) {
		this(paymentAccountNumber, postOfficeOfAccountZipCode, financeNumber, paymentMethod, false, null);
	}


	/* (non-Javadoc)
	 * @see com.usps.evs.vo.PaymentInfoI#getPaymentAccountNumber()
	 */
	public int getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.PaymentInfoI#getPostOfficeOfAccountZipCode()
	 */
	public String getPostOfficeOfAccountZipCode() {
		return postOfficeOfAccountZipCode;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.PaymentInfoI#getFinanceNumber()
	 */
	public String getFinanceNumber() {
		return financeNumber;
	}

	/* (non-Javadoc)
	 * @see com.usps.evs.vo.PaymentInfoI#getPaymentMethod()
	 */
	public int getPaymentMethod() {
		return paymentMethod;
	}

	public static void main(String[] args) {
	}
	/**
	 * @param string
	 */
	public void setFinanceNumber(String string) {
		financeNumber = string;
	}

	/**
	 * @param i
	 */
	public void setPaymentAccountNumber(int i) {
		paymentAccountNumber = i;
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
	public void setPostOfficeOfAccountZipCode(String string) {
		postOfficeOfAccountZipCode = string;
	}

	/**
	 * @return
	 */
	public String getCorpDuns() {
		return corpDuns;
	}

	/**
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param string
	 */
	public void setCorpDuns(String string) {
		corpDuns = string;
	}

	/**
	 * @param b
	 */
	public void setValid(boolean b) {
		valid = b;
	}
	
	public String toString() {
		String ret = 
		"PaymentNo="+paymentAccountNumber+", "+
		"PaymentZip="+postOfficeOfAccountZipCode+", "+
		"FinanceNo="+financeNumber+", "+
		"PaymentMethod="+paymentMethod+", "+
		"CorpDuns="+corpDuns+", "+
		"isValid="+valid;
		return ret;
	}

}
