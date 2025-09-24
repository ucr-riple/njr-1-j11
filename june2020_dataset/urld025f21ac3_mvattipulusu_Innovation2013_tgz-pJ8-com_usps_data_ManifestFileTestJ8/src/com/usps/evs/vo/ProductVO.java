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
 *  REL17.1.0
 *
 */
public class ProductVO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -60557531130129122L;
	private ContractVO contractVo;
	private String productType;   // Mail class
	private long productSeqNo;  // 0 - No Custom Rate for a given product type, permit, and finance
//	private String rateSchedule = "P";   // P - Published, C - Customized. Default "P"
	private boolean balloonWaived = false;
	private boolean dimWaived = false;;
	private boolean oversizeWaived = false;

	/**
	 *
	 */
	public ProductVO() {
		super();
	}

	/**
	 * @return
	 */
	public boolean isBalloonWaived() {
		return balloonWaived;
	}

	/**
	 * @return
	 */
	public ContractVO getContractVo() {
		return contractVo;
	}

	/**
	 * @return
	 */
	public boolean isDimWaived() {
		return dimWaived;
	}

	/**
	 * @return
	 */
	public boolean isOversizeWaived() {
		return oversizeWaived;
	}

	/**
	 * @return
	 */
	public long getProductSeqNo() {
		return productSeqNo;
	}

	/**
	 * @return
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @return
	 */
//	public String getRateSchedule() {
//		return rateSchedule;
//	}

	/**
	 * @param b
	 */
	public void setBalloonWaived(boolean b) {
		balloonWaived = b;
	}

	/**
	 * @param contractVO
	 */
	public void setContractVo(ContractVO contractVO) {
		contractVo = contractVO;
	}

	/**
	 * @param b
	 */
	public void setDimWaived(boolean b) {
		dimWaived = b;
	}

	/**
	 * @param b
	 */
	public void setOversizeWaived(boolean b) {
		oversizeWaived = b;
	}

	/**
	 * @param l
	 */
	public void setProductSeqNo(long l) {
		productSeqNo = l;
	}

	/**
	 * @param string
	 */
	public void setProductType(String string) {
		productType = string;
	}

	/**
	 * @param string
	 */
//	public void setRateSchedule(String string) {
//		rateSchedule = string;
//	}

	public String toString() {
		return "[ Product-Type="+productType+
				   ", ProductSeqNo="+productSeqNo+
				   //", RateSchedule="+rateSchedule+
				   ", BalloonWaived="+Boolean.toString(balloonWaived)+
				   ", DimWaived="+Boolean.toString(dimWaived)+
				   ", OversizeQaived="+Boolean.toString(oversizeWaived)+
				   " ], "+contractVo;
	}
}
