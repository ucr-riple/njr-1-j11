package edu.concordia.dpis.stationserver.domain;

import java.util.Date;

// A SPECIAL TYPE OF RECORD HOLDING MISSING INFORMATION
public class MissingRecord extends Record {
	private String lastKnownAddress;
	private Date lastSeenDate;
	private String lastSeenPlace;
	private MissingStatus status;

	public MissingRecord(final String recordId, final String firstName,
			final String lastName, final String lastKnownAddress,
			final Date lastSeenDate, final String lastSeenPlace,
			MissingStatus status) {
		super(recordId, firstName, lastName);
		this.lastKnownAddress = lastKnownAddress;
		this.lastSeenDate = lastSeenDate;
		this.lastSeenPlace = lastSeenPlace;
		this.status = status;
		this.setRecordType(RecordType.MISSING);
	}

	public String getLastKnownAddress() {
		return lastKnownAddress;
	}

	public Date getLastSeenDate() {
		return lastSeenDate;
	}

	public String getLastSeenPlace() {
		return lastSeenPlace;
	}

	@Override
	public void setStatus(String status) {
		this.status = MissingStatus.valueOf(status);
	}

	@Override
	public String getStatus() {
		return this.status.toString();
	}
}
