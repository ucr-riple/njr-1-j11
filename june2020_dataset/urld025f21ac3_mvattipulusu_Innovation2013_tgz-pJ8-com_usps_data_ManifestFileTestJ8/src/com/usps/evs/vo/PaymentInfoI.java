/*
 * Created on Dec 28, 2009
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
public interface PaymentInfoI  extends java.io.Serializable{
	public int getPaymentAccountNumber();
	public String getPostOfficeOfAccountZipCode();
	public String getFinanceNumber();
	public int getPaymentMethod() ;
}
