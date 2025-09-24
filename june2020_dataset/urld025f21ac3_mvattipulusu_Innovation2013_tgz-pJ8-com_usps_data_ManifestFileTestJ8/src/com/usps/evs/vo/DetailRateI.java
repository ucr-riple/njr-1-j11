/*
 * Created on Apr 15, 2010
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import java.math.BigDecimal;
import java.util.Vector;

/**
 * @author X6DXB0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DetailRateI extends DetailI{

	public BigDecimal getCalculatedPostage();
	public void setCalculatedPostage(BigDecimal calculatedPostage);
	
	public BigDecimal getCalculatedSurcharge(); 
	public void setCalculatedSurcharge(BigDecimal calculatedSurcharge);
	
	public Vector<SpecialServiceVO> getCalculatedServicesList();
	public void setCalculatedServicesList(Vector<SpecialServiceVO> servicesList);

	public Vector<SpecialServiceVO> getServicesList();
	public void setServicesList(Vector<SpecialServiceVO> servicesList);
	
	public BigDecimal getPublishedPostage(); 
	public void setPublishedPostage(BigDecimal decimal);
	
	public BigDecimal getPostage(); 
	public void setPostage(BigDecimal postage);

	public BigDecimal getTotalPostage(); 
	public void setTotalPostage(BigDecimal postage);

	public BigDecimal getCalculatedTotalPostage();
	public void setCalculatedTotalPostage(BigDecimal calculatedPostage);

	public BigDecimal getPostageDifference();
	public void setPostageDifference(BigDecimal postageDifference);
	
	public String getSubFileNumber();
	public String getFileNumber();
	public int getPsGroupNo();
	public void setFormType(String string);
	public String getFormType();
	public void setWwsProcessingCategory(String string);
	public String getWwsProcessingCategory();
	
	public boolean getValidPermit();
}
