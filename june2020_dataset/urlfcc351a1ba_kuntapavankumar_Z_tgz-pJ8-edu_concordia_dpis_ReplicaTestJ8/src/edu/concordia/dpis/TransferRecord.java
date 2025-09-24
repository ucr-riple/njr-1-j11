package edu.concordia.dpis;

import java.util.List;

//  A command object which can transfer a record from one station to other
public class TransferRecord extends StationCommand {

	public TransferRecord(StationServer spvm, StationServer spb,
			StationServer spl) {
		super(spvm, spb, spl);
	}

	@Override
	public Object execute(List<Object> params) {
		System.out.println("Executing Transfer Record");
		StationServer stationserver = getStation(params.get(0).toString());

		if (stationserver == null) {
			return null;
		}
		return stationserver.transferRecord(params.get(0).toString(), params
				.get(1).toString(), params.get(2).toString())
				+ "";
	}

}
