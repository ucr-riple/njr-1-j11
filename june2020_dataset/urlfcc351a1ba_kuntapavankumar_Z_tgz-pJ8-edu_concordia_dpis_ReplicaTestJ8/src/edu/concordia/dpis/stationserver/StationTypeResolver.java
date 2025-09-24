package edu.concordia.dpis.stationserver;

import edu.concordia.dpis.stationserver.domain.StationType;

public class StationTypeResolver {

	private static final int BADGEID_SUFFIX_LENGTH = 4;

	// return the station the user belongs to
	public static StationType resolveStation(String badgeId) {
		StationType officerBelongsToStation = null;
		for (StationType station : StationType.values()) {
			final String stationCode = station.getStationCode();
			if (badgeId.startsWith(stationCode)) {
				String badgeIdWithoutStationCodeAsPrefix = badgeId
						.substring(stationCode.length());
				if (badgeIdWithoutStationCodeAsPrefix.length() != BADGEID_SUFFIX_LENGTH) {
					System.err.println("BadgeId suffix must be of length "
							+ BADGEID_SUFFIX_LENGTH);
					break;
				}
				try {
					Integer.parseInt(badgeIdWithoutStationCodeAsPrefix);
				} catch (NumberFormatException ex) {
					System.err.println("BadgeId suffix must be a number");
					break;
				}
				officerBelongsToStation = station;
				break;
			}
		}
		return officerBelongsToStation;
	}
}
