package by.epam.lab;

import java.util.Iterator;
import java.util.List;

import by.epam.lab.utils.ReverseIterator;
import static by.epam.lab.utils.AppLogger.*;

public class ElevatorController {
	private static final String COMPLETION_TRANSPORTATION = "COMPLETION_TRANSPORTATION";
	private static final String WAITING_FOR_UNBOARDING_ON = ": waiting for unboarding on ";
	private static final String CONTROLLER_SEARCH_NEXT_FLOOR = "Controller search nextFloor";
	private static final String WAKES_UP_ON = ": wakes up on";
	private static final String WAITING_FOR_BOARDING_ON = ": waiting for boarding on ";
	private static final String CONTROLLER = "CONTROLLER";
	private static final String STARTING_TRANSPORTATION = "STARTING_TRANSPORTATION";
	private static final String NEW_NEXT_FLOOR = "NEW NEXT_FLOOR ";

	private enum Direction {
		UP(1), DOWN(-1);
		private final int intDirection;

		private Direction(int i) {
			intDirection = i;
		}
	}

	private int sleepTime;
	private final Building building;
	private final Elevator elevator;
	private Direction direction;
	private Floor nextFloor;
	private int outOnNextFloor;
	private boolean working;
	private int notReadyPassengers = 0;
	private int inOnCurrFloor;
	

	public synchronized  void ready() {
		notReadyPassengers--;
	}

	public void setNotReadyPassengers(int i) {
		notReadyPassengers = i;
	}
	
	public ElevatorController(Building building, Elevator elevator) {
		super();
		this.building = building;
		this.elevator = elevator;
		this.nextFloor = elevator.getCurrentFloor();
		this.outOnNextFloor = 0;
		this.working = false;
		this.direction = Direction.UP;
	}

	public boolean isWorking() {
		return working;
	}

	public Building getBuilding() {
		return building;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public Floor getFloor() {
		return elevator.getCurrentFloor();
	}

	public void changeNextFloor() {
		Floor passengerFloor;
		nextFloor = null;
		outOnNextFloor = 0;
		for (Passenger passenger : elevator.getElevatorContainer()) {
			passengerFloor = passenger.getDestFloor();
			if (nextFloor == null)
				nextFloor = passengerFloor;
			int i = nextFloor.compareTo(passengerFloor);
			if (i == 0) {
				outOnNextFloor++;
			} else if ((direction.intDirection) * i > 0) {
				nextFloor = passengerFloor;
				outOnNextFloor = 1;
			}
		}
		LOG.trace(NEW_NEXT_FLOOR + nextFloor);
	}

	public boolean addPassenger(Passenger passenger)
			throws InterruptedException {
		Floor passengerFloor = passenger.getCurrentFloor();

		synchronized (this) {

			try {
				if (!elevator.getCurrentFloor().equals(
						passenger.getCurrentFloor()))
					return false;
				inOnCurrFloor--;
				if (elevator.getCurrentFloor().compareTo(
						passenger.getDestFloor())
						* direction.intDirection > 0)
					return false;
				if (!elevator.addPassenger(passenger))
					return false;

				passengerFloor.removeDispatchPassenger(passenger);
				Thread.sleep(sleepTime);

			} finally {
				this.notify(); // maybe better notifyAll?
			}
		}
		return true;
	}

	public boolean removePassenger(Passenger passenger)
			throws InterruptedException {
		Floor passengerFloor = passenger.getDestFloor();
		synchronized (this) {
			try {
				if (!elevator.removePassenger(passenger))
					return false;

				outOnNextFloor--;
				passengerFloor.addArrivalPassenger(passenger);
				Thread.sleep(sleepTime);
			} finally {
				this.notify();
			}
		}
		return true;
	}

	public void doJob(int sleepTime) throws InterruptedException {
		this.sleepTime = sleepTime;
		List<Floor> floors = building.getFloors();
		Iterator<Floor> itr = floors.iterator();
		Floor floor;
		int elevatorMoved = 0;
		LOG.info(STARTING_TRANSPORTATION);
		Object waitObject;
		synchronized (this) {
			while (notReadyPassengers != 0)
				this.wait();

		}
		final int TWO_DIRECTIONS = 2;
		while (elevatorMoved < TWO_DIRECTIONS) {
			while (itr.hasNext()) {
				floor = elevator.getCurrentFloor();
				waitObject = floor.getDispatchStoryContainer();
				synchronized (waitObject) {
					working = true;
					if (floor.hasPassengers()) {
						inOnCurrFloor =floor
								.getDispatchStoryContainer().size();
						waitObject.notifyAll();

					}
				}
				synchronized (this) {
					while (inOnCurrFloor != 0) {
						LOG.trace(CONTROLLER + WAITING_FOR_BOARDING_ON + floor);
						this.wait();
						LOG.trace(CONTROLLER + WAKES_UP_ON + floor);
					}
					changeNextFloor();

				}

				LOG.trace(CONTROLLER_SEARCH_NEXT_FLOOR);
				while (itr.hasNext()) {
					floor = itr.next();
					elevator.move(floor);
					Thread.sleep(this.sleepTime);
					if (floor.equals(nextFloor)
							|| (elevator.hasPlaces() && floor.hasPassengers())) {

						elevatorMoved = 0;
						waitObject = floor.getArrivalStoryContainer();
						synchronized (waitObject) {

							waitObject.notifyAll();
						}
						if (floor.compareTo(nextFloor) == 0) {
							synchronized (this) {
								while (outOnNextFloor != 0) {
									LOG.trace(CONTROLLER
											+ WAITING_FOR_UNBOARDING_ON + floor);
									this.wait();
									LOG.trace(CONTROLLER + WAKES_UP_ON + floor);
								}
							}
						}
						break;
					}
				}

			}
			switch (direction) {
			case UP:
				itr = new ReverseIterator<Floor>(floors);
				direction = Direction.DOWN;
				elevatorMoved++;
				break;
			case DOWN:
				itr = floors.iterator();
				direction = Direction.UP;
				elevatorMoved++;
				break;
			}
		}
		LOG.info(COMPLETION_TRANSPORTATION);
	}
}
