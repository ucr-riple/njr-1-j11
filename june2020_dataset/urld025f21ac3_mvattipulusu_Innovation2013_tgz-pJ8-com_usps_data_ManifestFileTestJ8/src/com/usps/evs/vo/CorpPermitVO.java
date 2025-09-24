/*
 * Created on May 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CorpPermitVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3332418876342164035L;
	private long corpLocSeqNo;  // Given permitNo, finance No, and duns, it should get corpLocSeqNo and permitSeqNo
	private long permitSeqNo;

	private int permitNo;
	private String financeNo;
	private String dunsMailerId;

	private boolean found = false;

	/**
	 *
	 */
	public CorpPermitVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String toString() {
		return "[ Duns-Mailer-Id="+dunsMailerId+
		           ", PermitNo="+permitNo+
		           ", FinanceNo="+financeNo+
		           ", CorpLocSeqNo="+corpLocSeqNo+
		           ", PermitSeqNo="+permitSeqNo+
		           ", Found="+Boolean.toString(found)+" ]";
	}
	/**
	 * @return
	 */
	public long getCorpLocSeqNo() {
		return corpLocSeqNo;
	}

	/**
	 * @return
	 */
	public String getDunsMailerId() {
		return dunsMailerId;
	}

	/**
	 * @return
	 */
	public String getFinanceNo() {
		return financeNo;
	}

	/**
	 * @return
	 */
	public int getPermitNo() {
		return permitNo;
	}

	/**
	 * @return
	 */
	public long getPermitSeqNo() {
		return permitSeqNo;
	}

	/**
	 * @return
	 */
	public boolean isFound() {
		return found;
	}

	/**
	 * @param long1
	 */
	public void setCorpLocSeqNo(long long1) {
		corpLocSeqNo = long1;
	}

	/**
	 * @param string
	 */
	public void setDunsMailerId(String string) {
		dunsMailerId = string;
	}

	/**
	 * @param long1
	 */
	public void setFinanceNo(String long1) {
		financeNo = long1;
	}

	/**
	 * @param long1
	 */
	public void setPermitNo(int long1) {
		permitNo = long1;
	}

	/**
	 * @param long1
	 */
	public void setPermitSeqNo(long long1) {
		permitSeqNo = long1;
	}

	/**
	 * @param b
	 */
	public void setFound(boolean b) {
		found = b;
	}


}
