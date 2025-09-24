package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationGI extends ScheduledAction {

	SMThemePark model;

	public ArriveAtStationGI(SMThemePark model) {
		this.model = model;
	}

	@Override
	protected double timeSequence() {
		return model.rvp.uCustomersGI();
	}

	@Override
	protected void actionEvent() {
		model.gStations[Constants.GI].numCustomers += 1;
	}

}
