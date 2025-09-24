package simModel;

public class Trains {

	protected static enum StatusType {
		BOARDING, TRAVELLING, ARRIVED
	};

	protected int numCars; // an integer value between 1 and 9 which represents the number of car
	protected int numCustomers; 
	protected int[] numLeavingCustomers; // id matches station
	StatusType status;

	public Trains(int numCars, int numStations) {
		this.numCars = numCars; // default length of train
		numLeavingCustomers = new int[numStations];
	}

}
