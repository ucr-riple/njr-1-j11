package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationSH extends ScheduledAction {

	SMThemePark model;

	public ArriveAtStationSH(SMThemePark model) {
		this.model = model;
	}

	@Override
	protected double timeSequence() {
		return model.rvp.uCustomersSH();
	}

	@Override
	protected void actionEvent() {
		model.gStations[Constants.SH].numCustomers += 1;
	}

}
