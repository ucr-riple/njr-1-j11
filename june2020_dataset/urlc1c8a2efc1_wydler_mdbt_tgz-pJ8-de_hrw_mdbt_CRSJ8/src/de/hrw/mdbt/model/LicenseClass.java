package de.hrw.mdbt.model;

public class LicenseClass {
	private String abbreviation;
	private String description;

	public LicenseClass() {
	}

	public LicenseClass(String abbreviation, String description) {
		this.abbreviation = abbreviation;
		this.description = description;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
