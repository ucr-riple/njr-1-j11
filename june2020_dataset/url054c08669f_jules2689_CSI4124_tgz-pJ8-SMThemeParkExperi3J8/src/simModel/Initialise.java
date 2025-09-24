package simModel;

import absmodJ.ScheduledAction;

class Initialise extends ScheduledAction {
	SMThemePark model;

	double[] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0; // set index to first entry.
	
	public Initialise(SMThemePark model) {
		this.model = model;
	}

	@Override
	public double timeSequence() {
		return ts[tsix++]; // only invoked at t=0
	}

	@Override
	public void actionEvent() {
		// System Initialisation
		// Add initilisation instructions
		
		//Initialize an array for the purpose of this method
		model.rcgTrains = new Trains[model.numberOfTrains];
		
		int numRemainderCars = model.totalNumberOfCars%model.numberOfTrains;
		
		for (int i = 0; i < model.numberOfTrains; i++) {
			int numCars = (int)(model.totalNumberOfCars/model.numberOfTrains);
			if (numRemainderCars > 0) {
				numCars++;
				numRemainderCars--;
			}
			//RCG.Trains[ID].numCars ← numCars (Done in the construction method)
			Trains train = new Trains(numCars, Constants.NUM_STATIONS);
			train.status = Trains.StatusType.ARRIVED;
			model.rcgTrains[i] = train;
			
			//Initialize trains in the station. 
			//Suppose that before the park is open, the trains is already put in different stations 
			//in order to service customer more efficiently
			int stationID = i % model.rqTracks.length; // from 0 - 3
			model.rqTracks[stationID].spInsertQue(i);
		}
	}

}
