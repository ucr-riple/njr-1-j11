package com.usps.evs.vo;

import java.util.ArrayList;
import java.util.Collection;

public class StatusExtractVO {

	private CewHeader statusExtractHeader;
	private ArrayList statusExtractDetails;
	public StatusExtractVO() {
	}
	public CewHeader getStatusExtractHeader() {
		return statusExtractHeader;
	}
	public void setStatusExtractHeader(CewHeader statusExtractHeader) {
		this.statusExtractHeader = statusExtractHeader;
	}
	public ArrayList getStatusExtractDetails() {
		return statusExtractDetails;
	}
	public void setStatusExtractDetails(ArrayList statusExtractDetails) {
		this.statusExtractDetails = statusExtractDetails;
	}    
}
