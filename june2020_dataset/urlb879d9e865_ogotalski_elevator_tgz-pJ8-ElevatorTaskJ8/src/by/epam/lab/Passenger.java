package by.epam.lab;

public class Passenger {
	private static int nextId = 1;
	private TransportationState transportationState;
	private Floor destFloor;
	private Floor currentFloor;
	private final int id;

	public Passenger(Floor currentFloor, Floor destFloor) {
		synchronized (Passenger.class) {
			id = nextId++;
		}
		this.destFloor = destFloor;
		this.currentFloor = currentFloor;
		transportationState = TransportationState.NOT_STARTED;
		currentFloor.addDispatchPassenger(this);
	}

	public Floor getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public Floor getDestFloor() {
		return destFloor;
	}

	public void setDestFloor(Floor destFloor) {
		this.destFloor = destFloor;
	}

	public int getId() {
		return id;
	}

	public TransportationState getTransportationState() {
		return transportationState;
	}

	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Passenger [id=");
		builder.append(id);
		builder.append(", currentFloor=");
		builder.append(currentFloor.getId());
		builder.append(", destFloor=");
		builder.append(destFloor.getId());
		builder.append(", transportationState=");
		builder.append(transportationState);
		builder.append("]");
		return builder.toString();
	}

}
