package by.epam.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.epam.lab.controller.Configuration;
import static by.epam.lab.utils.AppLogger.*;

public class Building {
	private static final String VALIDATION_HAS_ERRORS = "VALIDATION HAS ERRORS";
	private static final String VALIDATION_COMPLETE = "VALIDATION COMPLETE";
	private static final String VALIDATION_ERROR = "VALIDATION ERROR :";
	private final List<Floor> floors;
	private final Elevator elevator;
	private final ElevatorController controller;

	private Building(List<Floor> floors, Elevator elevator) {
		super();

		this.floors = floors;
		this.elevator = elevator;
		controller = new ElevatorController(this, getElevator());

	}

	public List<Floor> getFloors() {
		return floors;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public static Building getBuilding() {
		
		Configuration configuration = Configuration.getConfiguration();
		int storiesNumber = configuration.getStoriesNumber();
		List<Floor> floorList = new ArrayList<Floor>(storiesNumber);
		Floor floor = new Floor();
		Elevator elevator = new Elevator(floor, configuration.getElevatorCapacity());
		floorList.add(floor);
		for (int i = 1; i < storiesNumber; i++) {
			floorList.add(new Floor());
		}
		return new Building(floorList, elevator);

	}

	public void fillBuilding() {

		Configuration configuration = Configuration.getConfiguration();
		int passengersNumber = configuration.getPassengersNumber();
		Floor[] floorsArr = floors.toArray(new Floor[floors.size()]);
		Random random = new Random();
		Floor curFloor, destFloor;
		int destId, curId;
		int size = floorsArr.length;
		for (int i = 0; i < passengersNumber; i++) {
			curId = random.nextInt(size);
			curFloor = floorsArr[curId];
			while ((destId = random.nextInt(size)) == curId)
				;
			destFloor = floorsArr[destId];
			new Passenger(curFloor, destFloor);
		}
	}

	public void startElevator(int sleepTime) throws InterruptedException {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		controller.setNotReadyPassengers(Configuration.getConfiguration().getPassengersNumber());
		for (Floor floor : floors) {
			for (Passenger passenger : floor.getDispatchStoryContainer()) {
				new Thread(group, new TransportationTask(controller, passenger))
						.start();

			}
		}

	
		controller.doJob(sleepTime);

	}

	public boolean verify() {
		boolean isOk = true;
		
			for (Floor floor : floors) {
				for (Passenger passenger : floor.getArrivalStoryContainer()) {
					if (passenger.getCurrentFloor() != passenger.getDestFloor()
							|| passenger.getTransportationState() != TransportationState.COMPLETED) {
						isOk = false;
						LOG.error(VALIDATION_ERROR + passenger);
					}

				}
				if (!floor.getDispatchStoryContainer().isEmpty()) {
					LOG.error(VALIDATION_ERROR + floor);
					isOk = false;
				}

			}
		    if (elevator.getElevatorContainer().size()!=0)  {
				LOG.error(VALIDATION_ERROR + elevator);
				isOk = false;
			}
		if (isOk)
			LOG.info(VALIDATION_COMPLETE);
		else
			LOG.error(VALIDATION_HAS_ERRORS);

		return isOk;
	}

}
