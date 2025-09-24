package by.epam.lab;

import static by.epam.lab.utils.AppLogger.*;
import java.util.ArrayList;

import java.util.List;

public class Elevator {
	private static final String ELEVATOR_CONTAINS = "ELEVATOR CONTAINS :";
	private static final String BOADING_OF_PASSENGER_D_ON_STORY_D = "BOADING_OF_PASSENGER ( %d on story %d )";
	private static final String DEBOADING_OF_PASSENGER_D_ON_STORY_D = "DEBOADING_OF_PASSENGER ( %d on story %d )";
	private static final String MOVING_ELEVATOR_FROM_D_TO_D = "MOVING_ELEVATOR (from %d to %d)";
	public static final String ELEVATOR_CAPACITY_MUST_BE_GREATER_THAN_0 = "Elevator capacity must be greater than 0";
	public static final int MIN_ELEVATOR_COMPACITY = 1;
	private Floor currentFloor;
	private final int capacity;

	
	public int getCapacity() {
		return capacity;
	}

	private final List<Passenger> elevatorContainer;

	public Elevator(Floor floor) {
		super();
		capacity = MIN_ELEVATOR_COMPACITY;
		currentFloor = floor;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public Elevator(Floor currentFloor, int capacity) {
		super();
		validateCapacity(capacity);
		this.currentFloor = currentFloor;
		this.capacity = capacity;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public static void validateCapacity(int capacity) {
		if (capacity < MIN_ELEVATOR_COMPACITY)
			throw new IllegalArgumentException(
					ELEVATOR_CAPACITY_MUST_BE_GREATER_THAN_0);
	}

	public synchronized void move(Floor floor) {
		LOG.info(String.format(MOVING_ELEVATOR_FROM_D_TO_D,
				currentFloor.getId(), floor.getId()));
		currentFloor = floor;
		LOG.trace("=========================================================");
		for (Passenger passenger : elevatorContainer)
			LOG.trace(ELEVATOR_CONTAINS + passenger);
		LOG.trace("=========================================================");
	}

	public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}

	public synchronized Passenger[] getElevatorPassengers() {
		return elevatorContainer
				.toArray(new Passenger[elevatorContainer.size()]);
	}

	public synchronized Floor getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public boolean hasPlaces() {
		if (capacity > elevatorContainer.size())
			return true;
		else
			return false;
	}

	public synchronized boolean addPassenger(Passenger passenger) {
		if (passenger.getCurrentFloor().equals(currentFloor) && hasPlaces()) {
			elevatorContainer.add(passenger);
			LOG.info(String.format(BOADING_OF_PASSENGER_D_ON_STORY_D,
					passenger.getId(), currentFloor.getId()));
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean removePassenger(Passenger passenger) {
		if (passenger.getDestFloor().equals(currentFloor)) {
			passenger.setCurrentFloor(currentFloor);
			elevatorContainer.remove(passenger);
			LOG.info(String.format(DEBOADING_OF_PASSENGER_D_ON_STORY_D,
					passenger.getId(), currentFloor.getId()));
			return true;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Elevator [capacity=");
		builder.append(capacity);
		builder.append(", currentFloor=");
		builder.append(currentFloor.getId());
		builder.append(", elevatorContainer.size=");
		builder.append(elevatorContainer.size());
		builder.append("]");
		return builder.toString();
	}

}
