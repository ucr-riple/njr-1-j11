package com.usps.evs.vo;

public class FiscalMonthVO {
	
	String fiscalMonth;
	String fiscalYear;
	String previousFiscalMonth;
	String previousFiscalYear;
	boolean dateLessThan11;

	//Constructor
	public FiscalMonthVO(boolean dateLessThan11, String fiscalMonth,
			String fiscalYear, String previousFiscalMonth,
			String previousFiscalYear) {
		super();
		this.dateLessThan11 = dateLessThan11;
		this.fiscalMonth = fiscalMonth;
		this.fiscalYear = fiscalYear;
		this.previousFiscalMonth = previousFiscalMonth;
		this.previousFiscalYear = previousFiscalYear;
	}
	
	public String getFiscalMonth() {
		return fiscalMonth;
	}
	public void setFiscalMonth(String fiscalMonth) {
		this.fiscalMonth = fiscalMonth;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getPreviousFiscalMonth() {
		return previousFiscalMonth;
	}
	public void setPreviousFiscalMonth(String previousFiscalMonth) {
		this.previousFiscalMonth = previousFiscalMonth;
	}
	public String getPreviousFiscalYear() {
		return previousFiscalYear;
	}
	public void setPreviousFiscalYear(String previousFiscalYear) {
		this.previousFiscalYear = previousFiscalYear;
	}
	public boolean isDateLessThan11() {
		return dateLessThan11;
	}
	public void setDateLessThan11(boolean dateLessThan11) {
		this.dateLessThan11 = dateLessThan11;
	}
	
}
