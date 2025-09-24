package com.andreev.parking.model;

import java.util.concurrent.TimeUnit;

import com.andreev.parking.model.exception.ParkingException;

public class ParkingPlace {

	private int id;
	private volatile boolean isUsed;

	public ParkingPlace() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws ParkingException {
		if (id < 0) {
			throw new ParkingException("Id is under zero");
		}
		this.id = id;
	}

	public boolean isUsed() {
		return isUsed;
	}

	protected void togleUsed() {
		this.isUsed = !this.isUsed;
	}

	public void using(int usingTimeMillis) throws ParkingException {
		try {
			TimeUnit.MILLISECONDS.sleep(usingTimeMillis);
		} catch (InterruptedException e) {
			throw new ParkingException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[ id = " + getId() + "]");
		return sb.toString();
	}

}
