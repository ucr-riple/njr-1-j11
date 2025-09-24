package simModel;

import absmodJ.SequelActivity;

public class TravellingBetweenStations extends SequelActivity {

	SMThemePark model;
	int id;
	Trains train;

	public TravellingBetweenStations(SMThemePark model, int id) {
		this.model = model;
		this.id = id;
	}

	@Override
	protected double duration() {
		return model.dvp.travelTime((this.id + 1) % Constants.NUM_STATIONS);
	}

	@Override
	public void startingEvent() {
		int trainID = model.rqTracks[id].trainList.get(0);
		train = model.rcgTrains[trainID];
		// RQ.Tracks[ID].Trains[0] = TRAVELLING
		train.status = Trains.StatusType.TRAVELLING;
		// SP.insertQue(RQ.Tracks[ID+1], RQ.Tracks[ID].Trains[0])
		int nextId = (this.id + 1) % Constants.NUM_STATIONS; // 0-3
		// SP.RemoveQue(RQ.Tracks[ID].Trains[0])
		model.rqTracks[this.id].spRemoveQue();
		model.rqTracks[nextId].spInsertQue(trainID);
	}

	@Override
	protected void terminatingEvent() {
		// RCG.Trains[ID].status = ARRIVED
		train.status = Trains.StatusType.ARRIVED;
	}

}
