package com.usps.evs.vo;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ExtractFileVO {
	
	private String fileName;
	private int metricsSeqNo;
	private String manifestFileName;
	private String corpDuns;
	private int fileFormat;
	private int systemType = 1;
	private String fiscalYear;
	private String fiscalMonth;
	private ArrayList extractVOs;
	private Map<String,Object> pstgStmtStatusMap = new TreeMap<String, Object>();
	private String tpbFlag;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getMetricsSeqNo() {
		return metricsSeqNo;
	}
	public void setMetricsSeqNo(int metricsSeqNo) {
		this.metricsSeqNo = metricsSeqNo;
	}
	public String getManifestFileName() {
		return manifestFileName;
	}
	public void setManifestFileName(String manifestFileName) {
		this.manifestFileName = manifestFileName;
	}
	public String getCorpDuns() {
		return corpDuns;
	}
	public void setCorpDuns(String corpDuns) {
		this.corpDuns = corpDuns;
	}
	public int getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(int fileFormat) {
		this.fileFormat = fileFormat;
	}
	public ArrayList getExtractVOs() {
		return extractVOs;
	}
	public void setExtractVOs(ArrayList extractVOs) {
		this.extractVOs = extractVOs;
	}
	public Map getPstgStmtStatusMap() {
		return pstgStmtStatusMap;
	}
	public void setPstgStmtStatusMap(Map pstgStmtStatusMap) {
		this.pstgStmtStatusMap = pstgStmtStatusMap;
	}
	public int getSystemType() {
		return systemType;
	}
	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getFiscalMonth() {
		return fiscalMonth;
	}
	public void setFiscalMonth(String fiscalMonth) {
		this.fiscalMonth = fiscalMonth;
	}
	/**
	 * @return the tpbFlag
	 */
	public String getTpbFlag() {
		return tpbFlag;
	}
	/**
	 * @param tpbFlag the tpbFlag to set
	 */
	public void setTpbFlag(String tpbFlag) {
		this.tpbFlag = tpbFlag;
	}
	
		
	
}
