/*
 * ManifestSample
 *
 * Created on January 22, 2007
 * 
 * 	11/06/2007 Req 13.2 , A. Gandhi
 * 	Added new properties to ManifestSampleBean
 */

package com.usps.evs.vo;

/**
 *
 * @author y257b0
 */
public class ManifestSample
{
    private String pkgId;
    private String fileName;
    private String manifestDRI;
    private String manifestCorpDuns;
    private double calcTotalPostage;
	private int sampleDetailSeqNum;
    private String zone;
    private String mailerZone;
    private String entryFacility;
    private String destinationZip;
    private String specialServiceCode1;
	private String specialServiceCode2;
	private String specialServiceCode3;
    private String mailerRoutingBarCode;
    private double specialServiceFee1;
	private double specialServiceFee2;
	private double specialServiceFee3;
	        
    /** Creates a new instance of ManifestSampleVO */
    public ManifestSample()
    {
    }

    public ManifestSample(String pkgId, String fileName, String manifestDRI, 
    	double calcTotalPostage )
    {
        this.pkgId = pkgId;
        this.fileName = fileName;
        this.manifestDRI = manifestDRI;
        this.calcTotalPostage = calcTotalPostage;
    }

    public String getPkgId()
    {
        return pkgId;
    }

    public void setPkgId(String pkgId)
    {
        this.pkgId = pkgId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getManifestDRI()
    {
        return manifestDRI;
    }

    public void setManifestDRI(String manifestDRI)
    {
        this.manifestDRI = manifestDRI;
    }

	public double getCalcTotalPostage()
	{
		return calcTotalPostage;
	}

	public void setCalcTotalPostage(double calcTotalPostage)
	{
		this.calcTotalPostage = calcTotalPostage;
	}

	public String getManifestCorpDuns()
	{
		return manifestCorpDuns;
	}

	public void setManifestCorpDuns(String manifestCorpDuns)
	{
		this.manifestCorpDuns = manifestCorpDuns;
	}
	
	public int getSampleDetailSeqNum()
	{
		return sampleDetailSeqNum;
	}

	public void setSampleDetailSeqNum(int sampleDetailSeqNum)
	{
		this.sampleDetailSeqNum = sampleDetailSeqNum;
	}
	
	/**
	 * 13.2
	 * @return
	 */
	public String getMailerZone() {
		return mailerZone;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setMailerZone(String string) {
		mailerZone = string;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setZone(String string) {
		zone = string;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getDestinationZip() {
		return destinationZip;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getEntryFacility() {
		return entryFacility;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setDestinationZip(String string) {
		destinationZip = string;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setEntryFacility(String string) {
		entryFacility = string;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getSpecialServiceCode1() {
		return specialServiceCode1;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getSpecialServiceCode2() {
		return specialServiceCode2;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getSpecialServiceCode3() {
		return specialServiceCode3;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setSpecialServiceCode1(String string) {
		specialServiceCode1 = string;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setSpecialServiceCode2(String string) {
		specialServiceCode2 = string;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setSpecialServiceCode3(String string) {
		specialServiceCode3 = string;
	}

	/**
	 * 13.2
	 * @return
	 */
	public String getMailerRoutingBarCode() {
		return mailerRoutingBarCode;
	}

	/**
	 * 13.2
	 * @param string
	 */
	public void setMailerRoutingBarCode(String string) {
		mailerRoutingBarCode = string;
	}

	/**
	 * @return
	 */
	public double getSpecialServiceFee1() {
		return specialServiceFee1;
	}

	/**
	 * @return
	 */
	public double getSpecialServiceFee2() {
		return specialServiceFee2;
	}

	/**
	 * @return
	 */
	public double getSpecialServiceFee3() {
		return specialServiceFee3;
	}

	/**
	 * @param d
	 */
	public void setSpecialServiceFee1(double d) {
		specialServiceFee1 = d;
	}

	/**
	 * @param d
	 */
	public void setSpecialServiceFee2(double d) {
		specialServiceFee2 = d;
	}

	/**
	 * @param d
	 */
	public void setSpecialServiceFee3(double d) {
		specialServiceFee3 = d;
	}

}
