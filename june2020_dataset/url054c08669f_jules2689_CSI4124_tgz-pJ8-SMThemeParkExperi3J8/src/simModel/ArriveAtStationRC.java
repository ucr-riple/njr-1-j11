package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationRC extends ScheduledAction {

	SMThemePark model;

	public ArriveAtStationRC(SMThemePark model) {
		this.model = model;
	}

	@Override
	protected double timeSequence() {
		return model.rvp.uCustomersRC();
	}

	@Override
	protected void actionEvent() {
		model.gStations[Constants.RC].numCustomers += 1;
	}

}
