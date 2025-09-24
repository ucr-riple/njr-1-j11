package edu.concordia.dpis.stationserver.domain;

// AN ABSTRACT RECORD WITH THE BASIC INFORMATION PERTAINGING TO A PERSON'S FIRST NAME AND LAST NAME
public abstract class Record {

	private String recordID;
	private RecordType recordType;
	private String firstName;
	private String lastName;

	public Record(final String id, final String firstName, final String lastName) {
		this.recordID = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getRecordID() {
		return recordID;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	protected void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public abstract void setStatus(String status);

	public abstract String getStatus();

	@Override
	public String toString() {
		return recordID + "," + recordType.toString() + "," + firstName + ","
				+ lastName + "," + getStatus();
	}

}
