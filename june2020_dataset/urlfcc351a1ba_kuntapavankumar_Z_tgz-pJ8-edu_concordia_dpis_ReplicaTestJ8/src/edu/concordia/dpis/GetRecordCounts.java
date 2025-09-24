package edu.concordia.dpis;

import java.util.List;

import edu.concordia.dpis.stationserver.StationServerImpl;

//This class can get the total records from all StationServer 
public class GetRecordCounts extends StationCommand {

	// constructor
	public GetRecordCounts(StationServer spvm, StationServer spb,
			StationServer spl) {
		super(spvm, spb, spl);
	}

	// run the operation
	@Override
	public Object execute(List<Object> params) {
		System.out.println("executing getRecordCounts");
		// get the associated station object based on the badgeId
		StationServer stationServer = getStation(params.get(0).toString());
		if (stationServer == null) {
			return null;
		}
		return stationServer.getRecordCounts((String) params.get(0));
	}
}
