package edu.concordia.dpis.stationserver.domain;

public enum RecordType {

	CRIMINAL("CR", "criminal"), MISSING("MR", "missing");

	private String code;

	private String name;

	private RecordType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
}
