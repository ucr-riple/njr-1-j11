/*
 * MisShippedVO.java
 *
 * Created on May 9, 2006, 1:12 PM
 * 
 * Modified 2/13/2007 Margaret Added routingBarcodeIndicator
 */

package com.usps.evs.vo;
import java.util.*;

/**
 *
 * @author QY71Q0
 * 
 * Change Log:
 * 			ivan 02/27/2007
 * 					- Add specialServiceCode and Fee into this bean
 * 
 * 			Ivan 10/10/2007
 * 					-- 14.0 Release, RF3.2.  Add more fields
 * 
 */
public class MisShippedVO implements CommonAttributes
{
    private String dunsPkgid;
    private String eventZip;
    private String destinationZip;
    private String mailClass;
    private String destinationRateIndicator;
    private String rateIndicator;
    private String processingCategory;
	private String routingBarcodeIndicator;
    private String zone;
    private double weight;
    private double manifestPostage;
    private double misShippedPostage;
    private double postageDifference;
    private Calendar eventDate;
    private String dcSpecialServiceCode1;
    private double dcSpecialServiceFee1;
	private String dcSpecialServiceCode2;
	private double dcSpecialServiceFee2;
	private String dcSpecialServiceCode3;
	private double dcSpecialServiceFee3;
//  14.0 RF3.2 
	private Vector reshippedSpecialServicesList;
	private String dcSurchargeType;
	private double dcSurchargeAmount;
	private String reshippedSurchargeType;
	private double reshippedSurchargeAmount;
	private String recalcReshippedInd;
	private String statusFlag;
	private double dcTotalPostage;
	private double dcCalcPostage;
	private double dcCalcTotalPostage;
	//end of 14.0 RF3.2
	private double articleValue = 0;
	private double codAmountDueSender = 0;
	
	//19.0.0
	private String corpDuns;
	
	//Release 30.0.0
	private String dcSpecialServiceCode4;
	private double dcSpecialServiceFee4;
	private String dcSpecialServiceCode5;
	private double dcSpecialServiceFee5;

    /** Creates a new instance of MisShippedVO */
    public MisShippedVO()
    {
    }

    public String getDunsPkgid()
    {
        return dunsPkgid;
    }

    public void setDunsPkgid(String dunsPkgid)
    {
        this.dunsPkgid = dunsPkgid;
    }

    public String getEventZip()
    {
        return eventZip;
    }

    public void setEventZip(String eventZip)
    {
        this.eventZip = eventZip;
    }

    public String getDestinationZip()
    {
        return destinationZip;
    }

    public void setDestinationZip(String destinationZip)
    {
        this.destinationZip = destinationZip;
    }

    public String getMailClass()
    {
        return mailClass;
    }

    public void setMailClass(String mailClass)
    {
        this.mailClass = mailClass;
    }

    public String getDestinationRateIndicator()
    {
        return destinationRateIndicator;
    }

    public void setDestinationRateIndicator(String destinationRateIndicator)
    {
        this.destinationRateIndicator = destinationRateIndicator;
    }

    public String getRateIndicator()
    {
        return rateIndicator;
    }

    public void setRateIndicator(String rateIndicator)
    {
        this.rateIndicator = rateIndicator;
    }

    public String getProcessingCategory()
    {
        return processingCategory;
    }

    public void setProcessingCategory(String processingCategory)
    {
        this.processingCategory = processingCategory;
    }

    public String getZone()
    {
        return zone;
    }

    public void setZone(String zone)
    {
        this.zone = zone;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public double getManifestPostage()
    {
        return manifestPostage;
    }

    public void setManifestPostage(double manifestPostage)
    {
        this.manifestPostage = manifestPostage;
    }

    public double getMisShippedPostage()
    {
        return misShippedPostage;
    }

    public void setMisShippedPostage(double misShippedPostage)
    {
        this.misShippedPostage = misShippedPostage;
    }

    public double getPostageDifference()
    {
        return postageDifference;
    }

    public void setPostageDifference(double postageDifference)
    {
        this.postageDifference = postageDifference;
    }

    public Calendar getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate)
    {
        this.eventDate = eventDate;
    }
    
	/**
	 * @return
	 */
	public String getRoutingBarcodeIndicator() {
		return routingBarcodeIndicator;
	}

	/**
	 * @param string
	 */
	public void setRoutingBarcodeIndicator(String string) {
		routingBarcodeIndicator = string;
	}

	/**
	 * @return
	 */
	public String getDcSpecialServiceCode2() {
		return dcSpecialServiceCode2;
	}

	/**
	 * @return
	 */
	public String getDcSpecialServiceCode3() {
		return dcSpecialServiceCode3;
	}
	
	/**
	 * @return
	 */
	public String getDcSpecialServiceCode4() {
		return dcSpecialServiceCode4;
	}
	
	/**
	 * @return
	 */
	public String getDcSpecialServiceCode5() {
		return dcSpecialServiceCode5;
	}

	/**
	 * @return
	 */
	public double getDcSpecialServiceFee1() {
		return dcSpecialServiceFee1;
	}

	/**
	 * @return
	 */
	public double getDcSpecialServiceFee2() {
		return dcSpecialServiceFee2;
	}

	/**
	 * @return
	 */
	public double getDcSpecialServiceFee3() {
		return dcSpecialServiceFee3;
	}
	
	/**
	 * @return
	 */
	public double getDcSpecialServiceFee4() {
		return dcSpecialServiceFee4;
	}
	
	/**
	 * @return
	 */
	public double getDcSpecialServiceFee5() {
		return dcSpecialServiceFee5;
	}

	/**
	 * @param string
	 */
	public void setDcSpecialServiceCode2(String string) {
		dcSpecialServiceCode2 = string;
	}

	/**
	 * @param string
	 */
	public void setDcSpecialServiceCode3(String string) {
		dcSpecialServiceCode3 = string;
	}
	
	/**
	 * @param string
	 */
	public void setDcSpecialServiceCode4(String string) {
		dcSpecialServiceCode4 = string;
	}
	
	/**
	 * @param string
	 */
	public void setDcSpecialServiceCode5(String string) {
		dcSpecialServiceCode5 = string;
	}

	/**
	 * @param d
	 */
	public void setDcSpecialServiceFee1(double d) {
		dcSpecialServiceFee1 = d;
	}

	/**
	 * @param d
	 */
	public void setDcSpecialServiceFee2(double d) {
		dcSpecialServiceFee2 = d;
	}

	/**
	 * @param d
	 */
	public void setDcSpecialServiceFee3(double d) {
		dcSpecialServiceFee3 = d;
	}
	
	/**
	 * @param d
	 */
	public void setDcSpecialServiceFee4(double d) {
		dcSpecialServiceFee4 = d;
	}
	
	/**
	 * @param d
	 */
	public void setDcSpecialServiceFee5(double d) {
		dcSpecialServiceFee5 = d;
	}

	/**
	 * @return
	 */
	public String getDcSpecialServiceCode1() {
		return dcSpecialServiceCode1;
	}

	/**
	 * @param string
	 */
	public void setDcSpecialServiceCode1(String string) {
		dcSpecialServiceCode1 = string;
	}
	public double getArticleValue() {
		 return articleValue;
	 }

	 public void setArticleValue(double articleValue) {
		 this.articleValue = articleValue;
	 }

	 public double getCodAmountDueSender() {
		 return codAmountDueSender;
	 }

	 public void setCodAmountDueSender(double codAmountDueSender) {
		 this.codAmountDueSender = codAmountDueSender;
	 }

//14.0 RF3.2
	/**
	 * @return
	 */
	public String getRecalcReshippedInd() {
		return recalcReshippedInd;
	}


	/**
	 * @return
	 */
	public double getReshippedSurchargeAmount() {
		return reshippedSurchargeAmount;
	}

	/**
	 * @return
	 */
	public String getReshippedSurchargeType() {
		return reshippedSurchargeType;
	}

	/**
	 * @param string
	 */
	public void setRecalcReshippedInd(String string) {
		recalcReshippedInd = string;
	}

	/**
	 * @param d
	 */
	public void setReshippedSurchargeAmount(double d) {
		reshippedSurchargeAmount = d;
	}

	/**
	 * @param string
	 */
	public void setReshippedSurchargeType(String string) {
		reshippedSurchargeType = string;
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
	public void setStatusFlag(String string) {
		statusFlag = string;
	}

	/**
	 * @return
	 */
	public double getDcSurchargeAmount() {
		return dcSurchargeAmount;
	}

	/**
	 * @return
	 */
	public String getDcSurchargeType() {
		return dcSurchargeType;
	}

	/**
	 * @param d
	 */
	public void setDcSurchargeAmount(double d) {
		dcSurchargeAmount = d;
	}

	/**
	 * @param string
	 */
	public void setDcSurchargeType(String string) {
		dcSurchargeType = string;
	}

	/**
	 * @return
	 */
	public Vector getReshippedSpecialServicesList() {
		return reshippedSpecialServicesList;
	}
	
	/**
	 * @param vector
	 */
	public void setReshippedSpecialServicesList(Vector vector) {
		reshippedSpecialServicesList = vector;
	}

	/**
	 * @return
	 */
	public double getDcCalcPostage() {
		return dcCalcPostage;
	}

	/**
	 * @return
	 */
	public double getDcCalcTotalPostage() {
		return dcCalcTotalPostage;
	}

	/**
	 * @return
	 */
	public double getDcTotalPostage() {
		return dcTotalPostage;
	}

	/**
	 * @param d
	 */
	public void setDcCalcPostage(double d) {
		dcCalcPostage = d;
	}

	/**
	 * @param d
	 */
	public void setDcCalcTotalPostage(double d) {
		dcCalcTotalPostage = d;
	}

	/**
	 * @param d
	 */
	public void setDcTotalPostage(double d) {
		dcTotalPostage = d;
	}

	/**
	 * @return
	 */
	public String getCorpDuns() {
		return corpDuns;
	}

	/**
	 * @param string
	 */
	public void setCorpDuns(String string) {
		corpDuns = string;
	}

}
