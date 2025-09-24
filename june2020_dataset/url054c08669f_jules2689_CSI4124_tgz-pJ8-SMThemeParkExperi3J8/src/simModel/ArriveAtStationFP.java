package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationFP extends ScheduledAction {

	SMThemePark model;

	public ArriveAtStationFP(SMThemePark model) {
		this.model = model;
	}

	@Override
	protected double timeSequence() {
		return model.rvp.uCustomersFP();
	}

	@Override
	protected void actionEvent() {
		model.gStations[Constants.FP].numCustomers += 1;
	}

}
