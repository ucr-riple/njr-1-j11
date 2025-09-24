package com.usps.evs.vo;
/*
* $Workfile:   CewRecord.java  $
* $Revision:   1.3  $
* =================================== 
* $Log:   P:/databases/PostalOne!/archives/PostalOne!/P1_DEV/e-VS/EvsServer/com/usps/evs/vo/CewRecord.java-arc  $
 * 
 *    Rev 1.3   Jan 20 2009 12:18:58   huangk
 * SCR#29148: added fy, month and metricsSeqNo
*/
public class CewRecord {
	
    private int duplicateSequence;
    
    private String corpDuns;
    
    private String mainFileNumber;
    
    private String subFileNumber;
    
    private String fileNumber;

	private String permitNo;
	
    private int systemType;

	private String transactionID;
	
	private int metricsSeqNo;
	
	private String fiscalYear;
	
	private String fiscalMonth;
	
	public int getDuplicateSequence() {
		return duplicateSequence;
	}

	public void setDuplicateSequence(int duplicateSequence) {
		this.duplicateSequence = duplicateSequence;
	}

	public String getCorpDuns() {
		return corpDuns;
	}

	public void setCorpDuns(String corpDuns) {
		this.corpDuns = corpDuns;
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

	public int getSystemType() {
		return systemType;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	/**
	 * @return
	 */
	public String getPermitNo() {
		return permitNo;
	}

	/**
	 * @param string
	 */
	public void setPermitNo(String string) {
		permitNo = string;
	}

	/**
	 * @return
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @param string
	 */
	public void setTransactionID(String string) {
		transactionID = string;
	}

	/**
	 * @return
	 */
	public String getFiscalMonth() {
		return fiscalMonth;
	}

	/**
	 * @return
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @return
	 */
	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}

	/**
	 * @param string
	 */
	public void setFiscalMonth(String string) {
		fiscalMonth = string;
	}

	/**
	 * @param string
	 */
	public void setFiscalYear(String string) {
		fiscalYear = string;
	}

	/**
	 * @param i
	 */
	public void setMetricsSeqNo(int i) {
		metricsSeqNo = i;
	}

	public String getSubFileNumber() {
		return subFileNumber;
	}

	public void setSubFileNumber(String subFileNumber) {
		this.subFileNumber = subFileNumber;
	}

}
