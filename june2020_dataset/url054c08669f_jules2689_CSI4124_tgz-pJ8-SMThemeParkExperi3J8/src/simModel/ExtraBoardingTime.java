package simModel;

import absmodJ.ExtSequelActivity;

public class ExtraBoardingTime extends ExtSequelActivity {
	SMThemePark model;
	int id;

	public ExtraBoardingTime(SMThemePark model, int id) {
		this.model = model;
		this.id = id;
	}

	@Override
	public int interruptionPreCond() {
		int flagValue = 0;
		// fixedBoardingTime = TRUE OR
		// UP.checkTrainArrived(G.Stations[ID]) = TRUE OR
		// RQ.Tracks[ID].Trains[0].NumCustomers =
		// RQ.Tracks[ID].Trains[0].maxCustomers
		if (model.fixBoardingTime) {
			flagValue = 1;
		} else if (model.udp.checkTrainArrived(id)) {
			flagValue = 2;
		} else if (model.rqTracks[id].getN() > 0) {
			Integer trainID = model.rqTracks[id].trainList.get(0);
			Trains train = model.rcgTrains[trainID];
			if (train.numCustomers == model.udp.getMaxNumberOfCustomers(trainID)) {
				flagValue = 3;
			}
		}
		
		return flagValue;
	}

	@Override
	public void interruptionSCS(int arg0) {
		// board train
		model.udp.boardAtStation(id);
		// set output
		model.udp.setOutputForStation(model.gStations[id]);
		TravellingBetweenStations travelAct = new TravellingBetweenStations(this.model, this.id);
		model.spStart(travelAct);
	}

	@Override
	protected double duration() {
		return 1; // 1 minute;
	}

	@Override
	public void startingEvent() {

	}

	@Override
	protected void terminatingEvent() {
		// board train
		model.udp.boardAtStation(id);
		// set output
		model.udp.setOutputForStation(model.gStations[id]);
		// SP.Start(TravellingBetweenStations)
		TravellingBetweenStations travelAct = new TravellingBetweenStations(this.model, this.id);
		model.spStart(travelAct);
	}

}
