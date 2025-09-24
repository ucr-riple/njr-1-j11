package com.usps.data;

import java.io.Serializable;
import java.util.List;

import com.usps.evs.vo.ManifestDetail;
import com.usps.evs.vo.ManifestHeader;

public class ManifestVO implements Serializable{
	
	private ManifestHeader header;
	
	private List<ManifestDetail> details;
	
	public ManifestHeader getHeader() {
		return header;
	}
	public void setHeader(ManifestHeader header) {
		this.header = header;
	}
	public List<ManifestDetail> getDetails() {
		return details;
	}
	public void setDetails(List<ManifestDetail> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "ManifestVO [header=" + header + ", details=" + details + "]";
	}
	
}
