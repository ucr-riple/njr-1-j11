package edu.concordia.dpis;

import java.util.List;
import edu.concordia.dpis.stationserver.StationServerImpl;

//This class can create the Missing record on a associated StationServer 
public class CreateMissingRecord extends StationCommand {

	// constructor
	public CreateMissingRecord(StationServer spvm, StationServer spb,
			StationServer spl) {
		super(spvm, spb, spl);
	}

	// run the operation
	@Override
	public Object execute(List<Object> params) {
		System.out.println("Executing create Missing Record");
		// get the associated station object based on the badgeId
		StationServer stationserver = getStation(params.get(0).toString());

		if (stationserver == null) {
			return null;
		}
		// create missing record
		return stationserver.createMRecord(params.get(0).toString(), params
				.get(1).toString(), params.get(2).toString(), params.get(3)
				.toString(), params.get(4).toString(),
				params.get(5).toString(), params.get(6).toString())
				+ "";
	}
}
