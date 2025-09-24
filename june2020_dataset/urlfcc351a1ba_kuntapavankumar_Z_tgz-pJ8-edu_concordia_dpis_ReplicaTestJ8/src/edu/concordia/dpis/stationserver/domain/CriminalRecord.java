package edu.concordia.dpis.stationserver.domain;

// A SPECIAL TYPE OF RECORD HOLDING CRIMINAL INFORMATION
public class CriminalRecord extends Record {

	// desc
	private String description;
	// status
	private CriminalStatus status;

	public CriminalRecord(final String recordId, final String firstName,
			final String lastName, final String description,
			final CriminalStatus status) {
		super(recordId, firstName, lastName);
		this.description = description;
		this.status = status;
		setRecordType(RecordType.CRIMINAL);
	}

	public String getDescription() {
		return this.description;
	}

	public String getStatus() {
		return status.toString();
	}

	@Override
	public void setStatus(String status) {
		CriminalStatus.valueOf(status);
	}
}