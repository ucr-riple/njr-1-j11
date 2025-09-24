package edu.concordia.dpis;


public interface StationServer {
	/**
	 * Operation createCRecord
	 */
	public boolean createCRecord(String badegId, String firstName,
			String lastName, String description, String status);

	/**
	 * Operation createMRecord
	 */
	public boolean createMRecord(String badegId, String firstName,
			String lastName, String address, String lastDate,
			String lastLocation, String status);

	/**
	 * Operation getRecordCounts
	 */
	public String getRecordCounts(String badegId);

	/**
	 * Operation editRecord
	 */
	public boolean editRecord(String badegId, String lastName, String recordID,
			String newStatus);

	/**
	 * Operation transferRecord
	 */
	public boolean transferRecord(String badgeId, String recordId,
			String remoteStationServerName);

}
