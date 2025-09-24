package by.epam.lab;

import java.util.ArrayList;
import java.util.List;

public class Floor implements Comparable<Floor> {
	private static int nextId = 1;
	private final int id;
	private List<Passenger> dispatchStoryContainer;
	private List<Passenger> arrivalStoryContainer;

	public Floor() {
		super();
		synchronized (Floor.class) {
			id = nextId++;
		}
		dispatchStoryContainer = new ArrayList<Passenger>();
		arrivalStoryContainer = new ArrayList<Passenger>();
	}

	public List<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}

	public void setDispatchStoryContainer(List<Passenger> dispatchStoryContainer) {
		this.dispatchStoryContainer = dispatchStoryContainer;
	}

	public List<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}

	public synchronized Passenger[] getDispatchPassengers() {
		return dispatchStoryContainer
				.toArray(new Passenger[dispatchStoryContainer.size()]);
	}

	public synchronized Passenger[] getArrivalPassengers() {
		return arrivalStoryContainer
				.toArray(new Passenger[arrivalStoryContainer.size()]);
	}

	public void setArrivalStoryContainer(List<Passenger> arrivalStoryContainer) {
		this.arrivalStoryContainer = arrivalStoryContainer;
	}

	public int getId() {
		return id;
	}

	public synchronized void addArrivalPassenger(Passenger passenger) {
		arrivalStoryContainer.add(passenger);
	}

	public synchronized void removeDispatchPassenger(Passenger passenger) {
		dispatchStoryContainer.remove(passenger);
	}

	public synchronized void addDispatchPassenger(Passenger passenger) {
		dispatchStoryContainer.add(passenger);
	}

	public synchronized boolean hasPassengers() {
		if (dispatchStoryContainer.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Floor o) {
		return o == null ? 1 : id - o.id;
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
		Floor other = (Floor) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Floor [id=");
		builder.append(id);
		builder.append(", dispatchStoryContainer.size=");
		builder.append(dispatchStoryContainer.size());
		builder.append(", arrivalStoryContainer.size=");
		builder.append(arrivalStoryContainer.size());
		builder.append("]");
		return builder.toString();
	}

}
