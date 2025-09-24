/*
 * SampleHeader.java
 *
 * Author: Nat Meo
 *
 * Sample header record.
 * 
 * Change Log:
 * 		03/24/2007 - Jose Rudel R. de Castro
 * 	    Added fileIndentifier member, along with getter and setter methods.
 * 
 *
 */

package com.usps.evs.vo;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SampleHeader
{
	String fileIdentifier;
    String zipCode;
    String samplingAreaZipCode;
    String facilityType;
    Calendar systemDate;
    String userId;
    String zeroVolumeFlag;
    int recordCount;
    String corpDuns;
    String evsVersion;
    String companyDatVersion;
    String returnType;
    int week;
    String fiscalMonth;
    String fiscalYear;
    int systemType;
    String filename;
    String filetype;
    boolean evsLite;
	String siteID;
	String costCenter;
	String headerString;
	private String tpbFl = "N"; // By-for relationship
	
    /** Creates a new instance of SampleHeaderVO */
    public SampleHeader()
    {
    }
    
	public String toString(){
		ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
		return ToStringBuilder.reflectionToString(this).toString();
    	
	}
	
    /**
     * Getter for property zipCode.
     * @return Value of property zipCode.
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }
    
    /**
     * Setter for property zipCode.
     * @param zipCode New value of property zipCode.
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * Getter for property facilityType.
     * @return Value of property facilityType.
     */
    public java.lang.String getFacilityType() {
        return facilityType;
    }
    
    /**
     * Setter for property facilityType.
     * @param facilityType New value of property facilityType.
     */
    public void setFacilityType(java.lang.String facilityType) {
        this.facilityType = facilityType;
    }
    
    /**
     * Getter for property userId.
     * @return Value of property userId.
     */
    public java.lang.String getUserId() {
        return userId;
    }
    
    /**
     * Setter for property userId.
     * @param userId New value of property userId.
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    /**
     * Getter for property zeroVolumeFlag.
     * @return Value of property zeroVolumeFlag.
     */
    public java.lang.String getZeroVolumeFlag() {
        return zeroVolumeFlag;
    }
    
    /**
     * Setter for property zeroVolumeFlag.
     * @param zeroVolumeFlag New value of property zeroVolumeFlag.
     */
    public void setZeroVolumeFlag(java.lang.String zeroVolumeFlag) {
        this.zeroVolumeFlag = zeroVolumeFlag;
    }
    
    /**
     * Getter for property recordCount.
     * @return Value of property recordCount.
     */
    public int getRecordCount() {
        return recordCount;
    }
    
    /**
     * Setter for property recordCount.
     * @param recordCount New value of property recordCount.
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
    
    /**
     * Getter for property evsVersion.
     * @return Value of property evsVersion.
     */
    public java.lang.String getEvsVersion() {
        if(this.evsVersion != null)
			this.evsVersion = this.evsVersion.trim();
        
        return this.evsVersion;
    }
    
    /**
     * Setter for property evsVersion.
     * @param evsVersion New value of property evsVersion.
     */
    public void setEvsVersion(java.lang.String evsVersion) {
        this.evsVersion = evsVersion;
    }
    
    /**
     * Getter for property companyDatVersion.
     * @return Value of property companyDatVersion.
     */
    public java.lang.String getCompanyDatVersion() {
        return companyDatVersion;
    }
    
    /**
     * Setter for property companyDatVersion.
     * @param companyDatVersion New value of property companyDatVersion.
     */
    public void setCompanyDatVersion(java.lang.String companyDatVersion) {
        this.companyDatVersion = companyDatVersion;
    }
    
    /**
     * Getter for property returnType.
     * @return Value of property returnType.
     */
    public java.lang.String getReturnType() {
        return returnType;
    }
    
    /**
     * Setter for property returnType.
     * @param returnType New value of property returnType.
     */
    public void setReturnType(java.lang.String returnType) {
        this.returnType = returnType;
    }
    
    /**
     * Getter for property week.
     * @return Value of property week.
     */
    public int getWeek() {
        return week;
    }
    
    /**
     * Setter for property week.
     * @param week New value of property week.
     */
    public void setWeek(int week) {
        this.week = week;
    }
    
    /**
     * Getter for property fiscalYear.
     * @return Value of property fiscalYear.
     */
    public java.lang.String getFiscalYear() {
        return fiscalYear;
    }
    
    /**
     * Setter for property fiscalYear.
     * @param fiscalYear New value of property fiscalYear.
     */
    public void setFiscalYear(java.lang.String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }
    
    /**
     * Getter for property fiscalMonth.
     * @return Value of property fiscalMonth.
     */
    public java.lang.String getFiscalMonth() {
        return fiscalMonth;
    }
    
    /**
     * Setter for property fiscalMonth.
     * @param fiscalMonth New value of property fiscalMonth.
     */
    public void setFiscalMonth(java.lang.String fiscalMonth) {
        this.fiscalMonth = fiscalMonth;
    }
    
    /**
     * Setter for property systemDate.
     * @param systemDate New value of property systemDate.
     */
    public void setSystemDate(java.util.Calendar systemDate) {
        this.systemDate = systemDate;
    }
    
    /**
     * Getter for property systemDate.
     * @return Value of property systemDate.
     */
    public java.util.Calendar getSystemDate() {
        return systemDate;
    }
    
    /**
     * Getter for property systemType.
     * @return Value of property systemType.
     */
    public int getSystemType() {
        return systemType;
    }
    
    /**
     * Setter for property systemType.
     * @param systemType New value of property systemType.
     */
    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }
    
    /**
     * Getter for property samplingAreaZipCode.
     * @return Value of property samplingAreaZipCode.
     */
    public java.lang.String getSamplingAreaZipCode() {
        return samplingAreaZipCode;
    }
    
    /**
     * Setter for property samplingAreaZipCode.
     * @param samplingAreaZipCode New value of property samplingAreaZipCode.
     */
    public void setSamplingAreaZipCode(java.lang.String samplingAreaZipCode) {
        this.samplingAreaZipCode = samplingAreaZipCode;
    }
    
    /**
     * Getter for property corpDuns.
     * @return Value of property corpDuns.
     */
    public java.lang.String getCorpDuns() {
        return corpDuns;
    }
    
    /**
     * Setter for property corpDuns.
     * @param corpDuns New value of property corpDuns.
     */
    public void setCorpDuns(java.lang.String corpDuns) {
        this.corpDuns = corpDuns;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public boolean isEvsLite() {
        return evsLite;
    }

    public void setEvsLite(boolean evsLite) {
        this.evsLite = evsLite;
    }

	// Jose Rudel R. de Castro - Added for R-2006 Rate Case
	public String getFileIdentifier() {
		return fileIdentifier;
	}

    // Jose Rudel R. de Castro - Added for R-2006 Rate Case
	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	/**
	 * @return
	 */
	public String getSiteID() {
		//Check for null siteID as they are only passed in the PDA and not in STATS
		if (siteID == null)
			siteID = "";
			
		return siteID;
	}

	/**
	 * @param string
	 */
	public void setSiteID(String string) {
		siteID = string;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public String getTpbFl() {
		return tpbFl;
	}

	public void setTpbFl(String tpbFl) {
		this.tpbFl = tpbFl;
	}

}
