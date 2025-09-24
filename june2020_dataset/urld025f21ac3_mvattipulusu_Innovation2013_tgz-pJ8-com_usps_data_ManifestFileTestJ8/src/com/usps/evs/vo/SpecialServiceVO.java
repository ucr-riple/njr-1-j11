/*
 * Created on Sep 19, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;
import java.io.*;

/**
 * @author qy71q0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SpecialServiceVO implements Serializable
{
	/**
	 * 
	 */
	String serviceCode;
	String serviceSubCode;
	double rate;
	double minimum;
	double maximum;
	double value;
	boolean rateFound;
	private int lineNumber; /* REL 34.0 */
	private String section; /* REL 34.0 */
	private String vipCode; /* REL 34.0 */
	private String rateSchedule;
	boolean customRateFound;

	private static final long serialVersionUID = 8062118837725925154L;
	
	
	public SpecialServiceVO() {
		super();
	}
	
	public SpecialServiceVO(String serviceCode, double rate) {
		this.serviceCode = serviceCode;
		this.rate = rate;
	}
	
	public SpecialServiceVO(String serviceCode, double rate, String rateSchedule) {
		this.serviceCode = serviceCode;
		this.rate = rate;
		this.rateSchedule = rateSchedule;
	}
	
	/**
	 * @return
	 */
	public double getRate()
	{
		return rate;
	}

	/**
	 * @return
	 */
	public String getServiceCode()
	{
		
		/* Rate Engine Lookup cannot have null */
		return (serviceCode == null?"00":serviceCode);
	}

	/**
	 * @return
	 */
	public String getServiceSubCode()
	{
		/* Rate Engine Lookup cannot have null */
		return (serviceSubCode == null?"":serviceSubCode);
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		rate = d;
	}

	/**
	 * @param string
	 */
	public void setServiceCode(String string)
	{
		serviceCode = string;
	}

	/**
	 * @param string
	 */
	public void setServiceSubCode(String string)
	{
		serviceSubCode = string;
	}

	/**
	 * @return
	 */
	public double getMaximum()
	{
		return maximum;
	}

	/**
	 * @return
	 */
	public double getMinimum()
	{
		return minimum;
	}

	/**
	 * @param d
	 */
	public void setMaximum(double d)
	{
		maximum = d;
	}

	/**
	 * @param d
	 */
	public void setMinimum(double d)
	{
		minimum = d;
	}

	/**
	 * @return
	 */
	public boolean isRateFound()
	{
		return rateFound;
	}

	/**
	 * @param b
	 */
	public void setRateFound(boolean b)
	{
		rateFound = b;
	}

	/**
	 * @return
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param d
	 */
	public void setValue(double d)
	{
		value = d;
	}

	
	/**
	 * A check to see if the object is an empty 
	 * special service object.
	 * 
	 * This is used when retrieving the special services from the db and
	 * then we have to remove the empty object that get retreived.
	 * @return
	 */
	public boolean isEmptyService()
	{
		if ((serviceCode == null || serviceCode.length()== 0)) 
		{
			return true;
    	}
		
		return false;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}	
	
	public String getRateSchedule() {
		return rateSchedule;
	}

	public void setRateSchedule(String rateSchedule) {
		this.rateSchedule = rateSchedule;
	}
	
	public boolean isCustomRateFound() {
		return customRateFound;
	}

	public void setCustomRateFound(boolean customRateFound) {
		this.customRateFound = customRateFound;
	}
}
