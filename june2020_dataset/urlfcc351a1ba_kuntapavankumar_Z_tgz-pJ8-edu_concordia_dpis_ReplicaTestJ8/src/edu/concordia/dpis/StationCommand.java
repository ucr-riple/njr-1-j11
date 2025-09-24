package edu.concordia.dpis;

import edu.concordia.dpis.stationserver.domain.StationType;

// A station command specific to the stationserver application
public abstract class StationCommand implements Command {

	private StationServer spvm;
	private StationServer spb;
	private StationServer spl;

	public StationCommand(StationServer spvm, StationServer spb,
			StationServer spl) {
		this.spvm = spvm;
		this.spb = spb;
		this.spl = spl;
	}

	protected StationServer getStation(String badgeId) {
		StationServer stationServer = null;
		if (badgeId.startsWith(StationType.SPVM.getStationCode())) {
			stationServer = spvm;
		} else if (badgeId.startsWith(StationType.SPB.getStationCode())) {
			stationServer = spb;
		} else if (badgeId.startsWith(StationType.SPL.getStationCode())) {
			stationServer = spl;
		}
		if (stationServer == null) {
			return null;
		}
		return stationServer;
	}
}
