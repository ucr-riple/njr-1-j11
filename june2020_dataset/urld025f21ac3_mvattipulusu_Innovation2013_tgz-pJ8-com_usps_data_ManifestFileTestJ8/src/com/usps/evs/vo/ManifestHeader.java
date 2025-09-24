/*
 * ManifestHeader.java
 *
 * Author: Nat Meo
 *
 * Manifest header record.
 */
/*
 * $Workfile:   ManifestHeader.java  $
 * $Revision:   1.12  $
 * =================================== 
 * $Log:   P:/databases/PostalOne!/archives/PostalOne!/P1_DEV/e-VS/EvsServer/com/usps/evs/vo/ManifestHeader.java-arc  $
 * 
 *    Rev 1.12   Aug 28 2009 12:59:52   gandhia
 * REL22: Account Status added to the VO
 * 
 *    Rev 1.11   Jun 11 2009 23:31:28   huangk
 * Add new ManifestHeaderRawLineVo()
 * 
 *    Rev 1.10   Jun 11 2009 08:50:02   huangk
 * Added new fields to accept RAW file
 * 
 *    Rev 1.9   Jan 20 2009 12:24:18   huangk
 * SCR#29148: Added fields to track/store unique EFN
 * 
 *    Rev 1.8   Sep 11 2008 17:45:38   nimbekaru
 * OMAS Change
 * 
 *    Rev 1.7   Aug 26 2008 10:45:36   huangk
 * Added metricsSeqNo
 */
package com.usps.evs.vo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManifestHeader 
{
    private Calendar originalMailingDateTime;
	private String fileNumber;
    private String mainFileNumber;
    private String fileType;
    private Calendar mailingDateTime;
    private String entryFacility;
    private int paymentAccountNumber;
    private int paymentMethod;
    private String postOfficeOfAccountZipCode;
    private String dsasReservationNumber;
    private String pickupRequestedIndicator;
    private double eFileVersionNumber;
    private String devIdCode;
    private String productVersionNumber;
    private int fileRecordCount;
    private String corpDuns;
    private double totalPostage;
    private String accountPeriod;
    private String fiscalYear;
    private String month;
    private String monthlyFiscalYear;
    private String quarter;
    private int systemType;
    private String financeNumber;
    private String transactionID;

    //REL18.0.0
    private int metricsSeqNo;
    private String mailerId;
    
    //REL18.0 -- Federal Agency Cost Code for 'OI' mailers. Left Padded with Zeros if legth less than 5 characters
    private String fedAgencyCostCode;
    
    // REL 19.0.0
	private int manifestLineNumber;
	private int cewLineNumber;
	private boolean manCewMatching = false;
	private boolean isMainHeader = false;
	private boolean skipManifest = true;
	private String skipManifestReason;
	private boolean skipCew = false;
	private String skipCewReason;
	private String mainHeaderKey;
	//REL22.0.0 add mailer acconut status to the header record.
	private String mailerAcctStatus;
	private boolean isDupInSameFile = false;
	
	// REL24.0.0
	private String entryFacilityType;
	private String entryFacilityZipPlus4;
	private String countryCode;	
	private String shipmentFeeCode;	
	private double shipmentFee;
	private String filler1;	
	private String filler2;	
	private String warningMessage;

	private String preOrPostInd; /* REL 26. */
	private String originalCorpDuns; /* REL 27 */
	private String activeMID; /* REL 27 */
	private boolean validPermit=true;
	private String tpbPoDuns; /* REL 30 */
	private String tpbFlag = "N";   /* REL 30 */
    private double calcPostage;
	
    private List<ManifestDetail> details = new ArrayList<ManifestDetail>();
	//REL 21.0.0
	private ManifestHeaderRawLineVo headerRawLine = new ManifestHeaderRawLineVo();
	
    /** Creates a new instance of ManifestHeader */
    public ManifestHeader()
    {
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getMainFileNumber() {
        return mainFileNumber;
    }

    public void setMainFileNumber(String mainFileNumber) {
        this.mainFileNumber = mainFileNumber;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Calendar getMailingDateTime() {
        return mailingDateTime;
    }

    public void setMailingDateTime(Calendar mailingDateTime) {
        this.mailingDateTime = mailingDateTime;
    }

    public String getEntryFacility() {
        return entryFacility;
    }

    public void setEntryFacility(String entryFacility) {
        this.entryFacility = entryFacility;
    }

    public int getPaymentAccountNumber() {
        return paymentAccountNumber;
    }

    public void setPaymentAccountNumber(int paymentAccountNumber) {
        this.paymentAccountNumber = paymentAccountNumber;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPostOfficeOfAccountZipCode() {
        return postOfficeOfAccountZipCode;
    }

    public void setPostOfficeOfAccountZipCode(String postOfficeOfAccountZipCode) {
        this.postOfficeOfAccountZipCode = postOfficeOfAccountZipCode;
    }

    public String getDsasReservationNumber() {
        return dsasReservationNumber;
    }

    public void setDsasReservationNumber(String dsasReservationNumber) {
        this.dsasReservationNumber = dsasReservationNumber;
    }

    public String getPickupRequestedIndicator() {
        return pickupRequestedIndicator;
    }

    public void setPickupRequestedIndicator(String pickupRequestedIndicator) {
        this.pickupRequestedIndicator = pickupRequestedIndicator;
    }

    public double getEFileVersionNumber() {
        return eFileVersionNumber;
    }

    public void setEFileVersionNumber(double eFileVersionNumber) {
        this.eFileVersionNumber = eFileVersionNumber;
    }

    public String getDevIdCode() {
        return devIdCode;
    }

    public void setDevIdCode(String devIdCode) {
        this.devIdCode = devIdCode;
    }

    public String getProductVersionNumber() {
        return productVersionNumber;
    }

    public void setProductVersionNumber(String productVersionNumber) {
        this.productVersionNumber = productVersionNumber;
    }

    public int getFileRecordCount() {
        return fileRecordCount;
    }

    public void setFileRecordCount(int fileRecordCount) {
        this.fileRecordCount = fileRecordCount;
    }

    public String getCorpDuns() {
        return corpDuns;
    }

    public void setCorpDuns(String corpDuns) {
        this.corpDuns = corpDuns;
    }

    public double getTotalPostage() {
        return totalPostage;
    }

    public void setTotalPostage(double totalPostage) {
        this.totalPostage = totalPostage;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthlyFiscalYear() {
        return monthlyFiscalYear;
    }

    public void setMonthlyFiscalYear(String monthlyFiscalYear) {
        this.monthlyFiscalYear = monthlyFiscalYear;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    public String getQuarter()
    {
        return quarter;
    }

    public void setQuarter(String quarter)
    {
        this.quarter = quarter;
    }

    public String getFinanceNumber()
    {
        return financeNumber;
    }

    public void setFinanceNumber(String financeNumber)
    {
        this.financeNumber = financeNumber;
    }
    
	/**
	 * @return
	 */
	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String string) {
		
					transactionID = string;
		
	}

	/**
	 * @param calendar
	 */
	public void setOriginalMailingDateTime(Calendar calendar) {
		this.originalMailingDateTime = calendar;
	}

	/**
	 * @return
	 */
	public Calendar getOriginalMailingDateTime() {
		return originalMailingDateTime;
	}

	/**
	 * @return
	 */
	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}

	/**
	 * @param i
	 */
	public void setMetricsSeqNo(int i) {
		metricsSeqNo = i;
	}

	public String getFedAgencyCostCode() {
		return fedAgencyCostCode;
	}

	public void setFedAgencyCostCode(String fedAgencyCostCode) {
		this.fedAgencyCostCode = fedAgencyCostCode;
	}

	/**
	 * @return
	 */
	public String getMainHeaderKey() {
		if(mainHeaderKey == null) mainHeaderKey =  this.getOriginalCorpDuns()+"-"+this.getPaymentAccountNumber()+"-"+this.getPaymentMethod()+"-"+this.getPostOfficeOfAccountZipCode()+"-"+((this.getTransactionID()== null)?"":this.getTransactionID())+"-"+this.getEFileVersionNumber();
		return mainHeaderKey;
	}

	/**
	 * @return
	 */
	public int getCewLineNumber() {
		return cewLineNumber;
	}

	/**
	 * @return
	 */
	public boolean isMainHeader() {
		return isMainHeader;
	}

	/**
	 * @return
	 */
	public boolean isManCewMatching() {
		return manCewMatching;
	}

	/**
	 * @return
	 */
	public int getManifestLineNumber() {
		return manifestLineNumber;
	}

	/**
	 * @return
	 */
	public boolean isSkipCew() {
		return skipCew;
	}

	/**
	 * @return
	 */
	public String getSkipCewReason() {
		return skipCewReason;
	}

	/**
	 * @return
	 */
	public boolean isSkipManifest() {
		return skipManifest;
	}

	/**
	 * @return
	 */
	public String getSkipManifestReason() {
		return skipManifestReason;
	}

	/**
	 * @param i
	 */
	public void setCewLineNumber(int i) {
		cewLineNumber = i;
	}

	/**
	 * @param b
	 */
	public void setMainHeader(boolean b) {
		isMainHeader = b;
	}

	/**
	 * @param string
	 */
	public void setMainHeaderKey(String string) {
		mainHeaderKey = string;
	}

	/**
	 * @param b
	 */
	public void setManCewMatching(boolean b) {
		manCewMatching = b;
	}

	/**
	 * @param i
	 */
	public void setManifestLineNumber(int i) {
		manifestLineNumber = i;
	}

	/**
	 * @param b
	 */
	public void setSkipCew(boolean b) {
		skipCew = b;
	}

	/**
	 * @param string
	 */
	public void setSkipCewReason(String string) {
		skipCewReason = string;
	}

	/**
	 * @param b
	 */
	public void setSkipManifest(boolean b) {
		skipManifest = b;
	}

	/**
	 * @param string
	 */
	public void setSkipManifestReason(String string) {
		skipManifestReason = string;
	}

	/**
	 * @return
	 */
	public ManifestHeaderRawLineVo getHeaderRawLine() {
		return headerRawLine;
	}

	/**
	 * @param vo
	 */
	public void setHeaderRawLine(ManifestHeaderRawLineVo vo) {
		headerRawLine = vo;
	}

	/**
	 * @return
	 */
	public String getMailerAcctStatus() {
		return mailerAcctStatus;
	}

	/**
	 * @param string
	 */
	public void setMailerAcctStatus(String string) {
		mailerAcctStatus = string;
	}

	/**
	 * @return
	 */
	public boolean isDupInSameFile() {
		return isDupInSameFile;
	}

	/**
	 * @param b
	 */
	public void setDupInSameFile(boolean b) {
		isDupInSameFile = b;
	}

	/**
	 * @return
	 */
	public String getEntryFacilityZipPlus4() {
		return entryFacilityZipPlus4;
	}

	/**
	 * @return
	 */
	public String getFiller1() {
		return filler1;
	}

	/**
	 * @return
	 */
	public String getFiller2() {
		return filler2;
	}

	/**
	 * @return
	 */
	public double getShipmentFee() {
		return shipmentFee;
	}

	/**
	 * @return
	 */
	public String getShipmentFeeCode() {
		return shipmentFeeCode;
	}

	/**
	 * @param string
	 */
	public void setEntryFacilityZipPlus4(String string) {
		entryFacilityZipPlus4 = string;
	}

	/**
	 * @param string
	 */
	public void setFiller1(String string) {
		filler1 = string;
	}

	/**
	 * @param string
	 */
	public void setFiller2(String string) {
		filler2 = string;
	}

	/**
	 * @param d
	 */
	public void setShipmentFee(double d) {
		shipmentFee = d;
	}

	/**
	 * @param string
	 */
	public void setShipmentFeeCode(String string) {
		shipmentFeeCode = string;
	}

	/**
	 * @return
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @return
	 */
	public String getEntryFacilityType() {
		return entryFacilityType;
	}

	/**
	 * @param string
	 */
	public void setCountryCode(String string) {
		countryCode = string;
	}

	/**
	 * @param string
	 */
	public void setEntryFacilityType(String string) {
		entryFacilityType = string;
	}

	/**
	 * @return
	 */
	public String getWarningMessage() {
		return warningMessage;
	}

	/**
	 * @param string
	 */
	public void setWarningMessage(String string) {
		warningMessage = string;
	}

	public String getPreOrPostInd() {
		return preOrPostInd;
	}

	public void setPreOrPostInd(String preOrPostInd) {
		this.preOrPostInd = preOrPostInd;
	}

	public String getOriginalCorpDuns() {
		return originalCorpDuns;
	}

	public void setOriginalCorpDuns(String originalCorpDuns) {
		this.originalCorpDuns = originalCorpDuns;
	}

	public String getActiveMID() {
		return activeMID;
	}

	public void setActiveMID(String activeMID) {
		this.activeMID = activeMID;
	}
	
	public void setValidPermit(boolean validPermit) {
		this.validPermit = validPermit;
	}

	public boolean getValidPermit() {
		return validPermit;
	}
	public String getTpbPoDuns() {
		return tpbPoDuns;
	}

	public void setTpbPoDuns(String tpbPoDuns) {
		this.tpbPoDuns = tpbPoDuns;
	}

	public String getTpbFlag() {
		return tpbFlag;
	}

	public void setTpbFlag(String tpbFlag) {
		this.tpbFlag = tpbFlag;
	}
	
	public String getMailerId() {
		return mailerId;
	}
	
	public void setMailerId(String mailerId) {
		this.mailerId = mailerId;
	}

	public double getCalcPostage() {
		return calcPostage;
	}

	public void setCalcPostage(double calcPostage) {
		this.calcPostage = calcPostage;
	}

	@Override
	public String toString() {
		return "ManifestHeader [originalMailingDateTime="
				+ originalMailingDateTime + ", fileNumber=" + fileNumber
				+ ", mainFileNumber=" + mainFileNumber + ", fileType="
				+ fileType + ", mailingDateTime=" + mailingDateTime
				+ ", entryFacility=" + entryFacility
				+ ", paymentAccountNumber=" + paymentAccountNumber
				+ ", paymentMethod=" + paymentMethod
				+ ", postOfficeOfAccountZipCode=" + postOfficeOfAccountZipCode
				+ ", dsasReservationNumber=" + dsasReservationNumber
				+ ", pickupRequestedIndicator=" + pickupRequestedIndicator
				+ ", eFileVersionNumber=" + eFileVersionNumber + ", devIdCode="
				+ devIdCode + ", productVersionNumber=" + productVersionNumber
				+ ", fileRecordCount=" + fileRecordCount + ", corpDuns="
				+ corpDuns + ", totalPostage=" + totalPostage
				+ ", accountPeriod=" + accountPeriod + ", fiscalYear="
				+ fiscalYear + ", month=" + month + ", monthlyFiscalYear="
				+ monthlyFiscalYear + ", quarter=" + quarter + ", systemType="
				+ systemType + ", financeNumber=" + financeNumber
				+ ", transactionID=" + transactionID + ", metricsSeqNo="
				+ metricsSeqNo + ", mailerId=" + mailerId
				+ ", fedAgencyCostCode=" + fedAgencyCostCode
				+ ", manifestLineNumber=" + manifestLineNumber
				+ ", cewLineNumber=" + cewLineNumber + ", manCewMatching="
				+ manCewMatching + ", isMainHeader=" + isMainHeader
				+ ", skipManifest=" + skipManifest + ", skipManifestReason="
				+ skipManifestReason + ", skipCew=" + skipCew
				+ ", skipCewReason=" + skipCewReason + ", mainHeaderKey="
				+ mainHeaderKey + ", mailerAcctStatus=" + mailerAcctStatus
				+ ", isDupInSameFile=" + isDupInSameFile
				+ ", entryFacilityType=" + entryFacilityType
				+ ", entryFacilityZipPlus4=" + entryFacilityZipPlus4
				+ ", countryCode=" + countryCode + ", shipmentFeeCode="
				+ shipmentFeeCode + ", shipmentFee=" + shipmentFee
				+ ", filler1=" + filler1 + ", filler2=" + filler2
				+ ", warningMessage=" + warningMessage + ", preOrPostInd="
				+ preOrPostInd + ", originalCorpDuns=" + originalCorpDuns
				+ ", activeMID=" + activeMID + ", validPermit=" + validPermit
				+ ", tpbPoDuns=" + tpbPoDuns + ", tpbFlag=" + tpbFlag
				+ ", calcPostage=" + calcPostage + ", headerRawLine="
				+ headerRawLine + "]";
	}

	public List<ManifestDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ManifestDetail> details) {
		this.details = details;
	}
	
}
