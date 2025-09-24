package com.andreev.parking.model;

import com.andreev.parking.model.exception.ParkingException;

public class Parking implements IParking{

	private int id;
	private ParkingPlacePool placePool;

	public Parking() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws ParkingException {
		if (id < 0) {
			throw new ParkingException("Parking id is under zero");
		}
		this.id = id;
	}

	public void setPlacePool(ParkingPlacePool placePool) throws ParkingException {
		if (placePool == null) {
			throw new ParkingException("Parking place pool is null");
		}
		this.placePool = placePool;
	}

	@Override
	public ParkingPlacePool getPlacePool() {
		return placePool;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[ id = " + getId() + "]");
		return sb.toString();
	}

}
