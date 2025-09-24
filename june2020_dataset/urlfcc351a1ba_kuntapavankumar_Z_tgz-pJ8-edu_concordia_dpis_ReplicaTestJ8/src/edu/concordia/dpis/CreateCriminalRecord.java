package edu.concordia.dpis;

import java.util.List;

// This class can create the criminal record on a associated StationServer 
public class CreateCriminalRecord extends StationCommand {

	// constructor
	public CreateCriminalRecord(StationServer spvm, StationServer spb,
			StationServer spl) {
		super(spvm, spb, spl);
	}

	// run the operation
	@Override
	public Object execute(List<Object> params) {
		System.out.println("executing create criminal record");
		// get the associated station object based on the badgeId
		StationServer stationServer = getStation(params.get(0).toString());
		if (stationServer == null) {
			return null;
		}
		// create criminal record
		return stationServer.createCRecord(params.get(0).toString(), params
				.get(1).toString(), params.get(2).toString(), params.get(3)
				.toString(), params.get(4).toString())
				+ "";
	}

}
