/*
 * Created on May 16, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DetailI  extends java.io.Serializable{

	public Calendar getMailingDate();
	public String getMailClass();
	public String getProcessingCategory();
	public String getDestinationRateIndicator();
	public String getRateIndicator();
	public String getMailerRateInd();
	public String getRoutingBarcode();
	public String getDiscountSurchargeType();
	public double getWeight();
	public double getMailerWeight();
	public String getZone();
	public ProductVO getProductVO();
	public String getRateSchedule();
	//REL18.0.0-DiscountSurcharge
	public String getEvsWwsDiscountSurchargeType();
	//18.1.0
	public java.lang.String getCorpDuns();
	public java.lang.String getPriceType();

	public int getFilteredType(); // REL 26.1.0
	public String getFilteredMessage(); // REL 26.1.0
	
	public int getSystemType();
	public void setSystemType(int systemType);

	public void setMailingDate(Calendar mailingDate);
	public void setMailClass(String s);
	public void setProcessingCategory(String s);
	public void setDestinationRateIndicator(String s);
	public void setRateIndicator(String s);
	public void setRoutingBarcode(String s);
	public void setDiscountSurchargeType(String s);
	public void setWeight(double d);
	public void setMailerWeight(double d);
	public void setZone(String s);
	public void setProductVO(ProductVO productVO);
	public void setRateSchedule(String d);
	//REL18.0.0-DiscountSurcharge
	public void setEvsWwsDiscountSurchargeType(String string);
	//18.1.0
	public void setCorpDuns(String corpDuns);

	public void setPriceType(String priceType); //REL23.0
	public void setCorpPriceType(String corpPriceType);
	public String getCorpPriceType();
	public void setRoundUpHalfPound(boolean roundUpHalfPound);
	public boolean isRoundUpHalfPound();
	public void setRateIngredModInd(String string);
	public String getRateIngredModInd();
	public String getDunsPkgid();
	public int getSequenceNumber(); // REL 25.0.0
	public String getEntryFacilityZip();
	public String getDestinationZip();
	public double getCubicSize();
	public String getTier();
	public void setTier(String tier);
	public boolean isLHWValidForCPC();
	public double getLength();
	public double getWidth();
	public double getHeight();
	
	public void setFilteredType(int filteredType); // REL 26.1.0
	public void setFilteredMessage(String filteredMessage); // REL 26.1.0
	
	//public BigDecimal getCalcFeeEDA();
	//public void setCalcFeeEDA(BigDecimal calcFeeEDA);

	public BigDecimal getCalcFeeFuel();
	public void setCalcFeeFuel(BigDecimal calcFeeFuel);

	public BigDecimal getCalcFeePPI();
	public void setCalcFeePPI(BigDecimal calcFeePPI);

	public boolean isZipEDA();
	public void setZipEDA(boolean zipEDA);
	
	public boolean isFuelFeeEnabled();
	public void setFuelFeeEnabled(boolean fuelFeeEnabled);	
}
