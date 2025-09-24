/*
 * Created on Dec 28, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import java.util.Calendar;

import com.usps.evs.vo.PaymentInfoI;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ContractPaymentInfoI extends PaymentInfoI, java.io.Serializable{
	public String getCorpDuns();
	public Calendar getMailingDateTime();
	public String getFileNumber();
	public String getTpbPoDuns();
	public String getTpbFlag();
}
