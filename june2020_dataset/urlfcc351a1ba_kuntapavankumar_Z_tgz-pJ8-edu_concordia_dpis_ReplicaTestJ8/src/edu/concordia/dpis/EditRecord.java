package edu.concordia.dpis;

import java.util.List;

import edu.concordia.dpis.stationserver.StationServerImpl;

//This class can edit record on a associated StationServer 
public class EditRecord extends StationCommand {

	// constructor
	public EditRecord(StationServer spvm, StationServer spb, StationServer spl) {
		super(spvm, spb, spl);
	}

	// run the operation
	@Override
	public Object execute(List<Object> params) {
		System.out.println("Executing Edit Record");
		// get the associated station object based on the badgeId
		StationServer stationserver = getStation(params.get(0).toString());

		if (stationserver == null) {
			return null;
		}
		// edit record
		return stationserver
				.editRecord(params.get(0).toString(), params.get(1).toString(),
						params.get(2).toString(), params.get(3).toString())
				+ "";
	}

}
