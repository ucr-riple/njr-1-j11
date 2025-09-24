/*
 * CewDetail.java
 *
 * Author: Nat Meo
 *
 * CEW detail record.
 */

package com.usps.evs.vo;

public class CewDetail extends CewRecord {
	private String type;

	private int lineNumber;

	private String picCode;

	private String field;

	private String message;

	private int sequence;

	/** Creates a new instance of CewDetail */
	public CewDetail() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
