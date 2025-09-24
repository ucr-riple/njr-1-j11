package edu.concordia.dpis.stationserver.domain;

public enum StationType {

	SPVM("SPVM", "Montreal"), SPL("SPL", "Longueuil"), SPB("SPB", "Brossard");

	private String stationName;

	private String stationCode;

	private StationType(String stationCode, String stationName) {
		this.stationName = stationName;
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return this.stationName;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	@Override
	public String toString() {
		return this.stationCode + ":" + this.stationName;
	}
}
