package com.andreev.parking.model;

import org.apache.log4j.Logger;

import com.andreev.parking.model.exception.CarException;
import com.andreev.parking.model.exception.ParkingException;

public class Car implements Runnable {

	private static final Logger LOG = Logger.getLogger(Car.class);

	private int id;
	private int maxWaitTimeMillis;
	private int parkTimeMillis;
	private Iterable<IParking> parkings;

	public Car() {
	}

	public long getId() {
		return id;
	}

	public void setId(int id) throws CarException {
		if (id < 0) {
			throw new CarException("Id is under zero");
		}
		this.id = id;
	}

	public int getMaxWaitTimeMillis() {
		return maxWaitTimeMillis;
	}

	public void setMaxWaitTimeMillis(int maxWaitMillis) throws CarException {
		if (maxWaitMillis < 0) {
			throw new CarException("Wait time is under zero");
		}
		this.maxWaitTimeMillis = maxWaitMillis;
	}

	public int getParkTimeMillis() {
		return parkTimeMillis;
	}

	public void setParkTimeMillis(int parkTimeMillis) throws CarException {
		if (parkTimeMillis < 0) {
			throw new CarException("Park time is under zero");
		}
		this.parkTimeMillis = parkTimeMillis;
	}

	public Iterable<IParking> getParkings() {
		return parkings;
	}

	public void setParkings(Iterable<IParking> iterable)
			throws ParkingException {
		if (iterable == null) {
			throw new ParkingException("Parkings is null");
		}
		this.parkings = iterable;
	}

	@Override
	public void run() {
		ParkingPlace parkingPlace = null;
		for (IParking parking : parkings) {
			LOG.info(toString() + " trying to park on " + parking + "!!!");
			try {
				parkingPlace = take(parking);
				using(parkingPlace);
			} catch (ParkingException e) {
				LOG.info(toString() + "---Parking failed--- " + e.getMessage());
			} finally {
				if (parkingPlace != null) {
					try {
						leave(parking, parkingPlace);
					} catch (ParkingException e) {
						LOG.error("Place returning failed", e);
					}
					break;
				}
			}
		}
		if (parkingPlace == null) {
			LOG.warn(toString() + " was't parked!");
		}
	}

	private ParkingPlace take(IParking parking) throws ParkingException {
		ParkingPlace place = null;
		place = parking.getPlacePool().getParkingPlace(getMaxWaitTimeMillis());
		LOG.info(toString() + " ---> " + place);
		return place;
	}

	private void using(ParkingPlace place) throws ParkingException {
		place.using(getParkTimeMillis());
	}

	private void leave(IParking parking, ParkingPlace place)
			throws ParkingException {
		parking.getPlacePool().returnParkingPlace(place);
		LOG.info(toString() + " <--- " + place);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[id = " + getId() + "]");
		return sb.toString();
	}

}
