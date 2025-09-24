
package com.usps.evs.vo;

public class PRSZipZoneValidationVO { //REL21.0.2
	private String zone;
	private String mailClass;
	private String destinationRateIndicator;
	private String rateIndicator;
	private String routingBarcode;
	    
    public PRSZipZoneValidationVO() {
    }

    public PRSZipZoneValidationVO(String zone, String mailClass, String destinationRateIndicator, String rateIndicator, String routingBarcode)  {
    	this.zone = zone;
    	this.mailClass = mailClass;
    	this.destinationRateIndicator = destinationRateIndicator;
    	this.rateIndicator = rateIndicator;
    	this.routingBarcode = routingBarcode;
    }

	/**
	 * @return
	 */
	public String getDestinationRateIndicator() {
		return destinationRateIndicator;
	}

	/**
	 * @return
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @return
	 */
	public String getRateIndicator() {
		return rateIndicator;
	}

	/**
	 * @return
	 */
	public String getRoutingBarcode() {
		return routingBarcode;
	}

	/**
	 * @return
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param string
	 */
	public void setDestinationRateIndicator(String string) {
		destinationRateIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setMailClass(String string) {
		mailClass = string;
	}

	/**
	 * @param string
	 */
	public void setRateIndicator(String string) {
		rateIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setRoutingBarcode(String string) {
		routingBarcode = string;
	}

	/**
	 * @param string
	 */
	public void setZone(String string) {
		zone = string;
	}

}
