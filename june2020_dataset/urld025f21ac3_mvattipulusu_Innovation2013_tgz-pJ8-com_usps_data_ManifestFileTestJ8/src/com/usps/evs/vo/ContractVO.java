/*
 * Created on May 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.usps.evs.vo;

import java.sql.Date;


/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContractVO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7586094110453706913L;
	private CorpPermitVO corpPermitVo;
	private Date mailingDate;

	private boolean contractValid = false;  // T/F for contract is valid or not  Default starts with No Contract meaning Published Rate
	private long contractSeqNo;   // 0 - No contract for a given permit and finance no and duns/mailer id

	/**
	 *
	 */
	public ContractVO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String toString() {
		return "[ Contract-Valid="+Boolean.toString(contractValid)+
		           ", Contract Seq No="+contractSeqNo+
		           ", Mailing-Date="+mailingDate+
		           " ], "+corpPermitVo;
	}
	/**
	 * @return
	 */
	public long getContractSeqNo() {
		return contractSeqNo;
	}

	/**
	 * @return
	 */
	public boolean isContractValid() {
		return contractValid;
	}

	/**
	 * @return
	 */
	public CorpPermitVO getCorpPermitVo() {
		return corpPermitVo;
	}

	/**
	 * @param long1
	 */
	public void setContractSeqNo(long long1) {
		contractSeqNo = long1;
	}

	/**
	 * @param b
	 */
	public void setContractValid(boolean b) {
		contractValid = b;
	}

	/**
	 * @param permitVO
	 */
	public void setCorpPermitVo(CorpPermitVO permitVO) {
		corpPermitVo = permitVO;
	}

	/**
	 * @return
	 */
	public Date getMailingDate() {
		return mailingDate;
	}

	/**
	 * @param date
	 */
	public void setMailingDate(Date date) {
		mailingDate = date;
	}

}
