package by.epam.lab;

import static by.epam.lab.utils.AppLogger.*;

public class TransportationTask implements Runnable {
	private static final String TRANSPORTATION_INTERRUPDED = "TRANSPORTATION  interrupded ";
	private static final String WAITING_FOR_DEBOARDING = "WAITING FOR DEBOARDING ";
	private static final String WANT_TO_DEBOARD = "WANT TO DEBOARD ";
	private static final String WAITING_FOR_BOARDING = "WAITING FOR BOARDING ";
	private static final String WANT_TO_BOARD = "WANT TO BOARD ";
	private static final String READY = "READY ";
	private ElevatorController controller;
	private final Passenger passenger;

	public TransportationTask(ElevatorController controller, Passenger passenger) {
		super();
		this.controller = controller;
		this.passenger = passenger;
	}

	

	@Override
	public void run() {

		passenger.setTransportationState(TransportationState.IN_PROGRESS);

		try {
			Floor floor = passenger.getCurrentFloor();
			Object waitObject = floor.getDispatchStoryContainer();
			synchronized (controller) {
				controller.ready();
				LOG.trace(READY + passenger);
				controller.notifyAll();

			}
			synchronized (waitObject) {
				while (!controller.isWorking())
					waitObject.wait();
			}

			synchronized (waitObject) {
				LOG.trace(WANT_TO_BOARD + passenger.getId());
				while (!controller.addPassenger(passenger)) {
					LOG.trace(WAITING_FOR_BOARDING + passenger.getId());
					waitObject.wait();
					LOG.trace(WANT_TO_BOARD + passenger.getId());
				}
			}
			floor = passenger.getDestFloor();
			waitObject = floor.getArrivalStoryContainer();
			synchronized (waitObject) {
				LOG.trace(WANT_TO_DEBOARD + passenger.getId());
				while (!controller.removePassenger(passenger)) {
					LOG.trace(WAITING_FOR_DEBOARDING + passenger.getId());
					waitObject.wait();
					LOG.trace(WANT_TO_DEBOARD + passenger.getId());
				}
				passenger.setTransportationState(TransportationState.COMPLETED);
			}

		} catch (InterruptedException e) {
			passenger.setTransportationState(TransportationState.ABORTED);
			LOG.trace(TRANSPORTATION_INTERRUPDED + passenger.getId());

		}

	}
}
