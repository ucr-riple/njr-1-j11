package simModel;

public class UDPs {
	SMThemePark model; // for accessing the clock

	// Constructor
	public UDPs(SMThemePark model) {
		this.model = model;
	}

	// Check if a station has an "Arrived" train
	public boolean checkTrainArrived(int id) {
		Tracks track = model.rqTracks[id];
		// at least two trains are on the track
		if (track.trainList != null && track.getN() > 1) {
			Trains t2 = model.rcgTrains[track.trainList.get(1)];
			if (t2.status == Trains.StatusType.ARRIVED) {
				return true;
			}
		}
		return false;
	}

	// List of stations are ready for unboarding
	protected int stationReadyForUnboarding() {
		for (int i = 0; i < model.rqTracks.length; i++) {
			Tracks track = model.rqTracks[i];
			if (track != null && track.getN() > 0) {
				Trains headTrain = model.rcgTrains[track.trainList.get(0)];
				if (headTrain.status == Trains.StatusType.ARRIVED) {
					return i;
				}
			}
		}
		return Constants.NO_STATION;
	}

	//Process Output for Station
	protected void setOutputForStation(Stations stn) {
		if (stn.numCustomers == 0) {
			model.output.incrType1BoardingEvent();
		} else if (stn.numCustomers >= 1 && stn.numCustomers < 25) {
			model.output.incrType2BoardingEvent();
		} else if (stn.numCustomers >= 25 && stn.numCustomers < 50) {
			model.output.incrType3BoardingEvent();
		} else {
			model.output.incrType4BoardingEvent();
		}
		model.output.incrTotalEvent();
	}

	//Process customers from Station to Train
	protected void boardAtStation(int id) {
		Integer trainID = model.rqTracks[id].trainList.get(0);
		Trains train = model.rcgTrains[trainID];
		Stations station = model.gStations[id];

		int capacityAvailableForTrain = getAvailableCapacity(trainID);
		int numCustomersBoarding = 0;

		// boarding:

		if (station.numCustomers >= capacityAvailableForTrain) {
			// train is full, some customer cannot boarding
			numCustomersBoarding = capacityAvailableForTrain;
		} else {
			// train is not full, all customers boarding in the train
			numCustomersBoarding = station.numCustomers;
		}
		train.numCustomers += numCustomersBoarding;
		station.numCustomers -= numCustomersBoarding;

		for (int i = 0; i < Constants.NUM_STATIONS; i++) {
			int numLeaving = (int) (numCustomersBoarding * model.dvp.getPercentageOfCustomersLeaving(id, i));
			if (i == id) {
				train.numLeavingCustomers[i] = 0;
			} else {
				train.numLeavingCustomers[i] += numLeaving;
			}
		}
	}
	
	protected int getMaxNumberOfCustomers(Integer train) {
		return model.rcgTrains[train].numCars * Constants.MAX_CUSTOMERS_PER_CAR;
	}

	protected int getAvailableCapacity(Integer train) {
		return getMaxNumberOfCustomers(train) - model.rcgTrains[train].numCustomers;
	}
}
