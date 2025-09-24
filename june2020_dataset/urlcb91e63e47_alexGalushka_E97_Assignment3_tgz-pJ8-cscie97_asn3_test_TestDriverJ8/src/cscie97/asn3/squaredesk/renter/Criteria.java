package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.List;

import cscie97.common.squaredesk.Facility;
import cscie97.common.squaredesk.Location;

public class Criteria
{
	private Facility facility;
	private Location location;
	private List<String> preferredFeatures;
	private float minAverageRating;
	private Date startDate;
	private Date endDate;
	
	public Criteria()
	{
		facility = new Facility();
		location = new Location();
		preferredFeatures = null;
		minAverageRating = (float) 0.0;
	}

	/**
	 * @return the facility
	 */
	public Facility getFacility() {
		return facility;
	}

	/**
	 * @param facility the facility to set
	 */
	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the preferredFeatures
	 */
	public List<String> getPreferredFeatures() {
		return preferredFeatures;
	}

	/**
	 * @param preferredFeatures the preferredFeatures to set
	 */
	public void setPreferredFeatures(List<String> preferredFeatures) {
		this.preferredFeatures = preferredFeatures;
	}

	/**
	 * @return the minAverageRating
	 */
	public float getMinAverageRating() {
		return minAverageRating;
	}

	/**
	 * @param minAverageRating the minAverageRating to set
	 */
	public void setMinAverageRating(float minAverageRating) {
		this.minAverageRating = minAverageRating;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
