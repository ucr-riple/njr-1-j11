package de.hrw.mdbt.model;

public class Phone {
	private String type;
	private String prefix;
	private String number;

	public Phone(String prefix, String number, String type) {
		this.setPrefix(prefix);
		this.setNumber(number);
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
