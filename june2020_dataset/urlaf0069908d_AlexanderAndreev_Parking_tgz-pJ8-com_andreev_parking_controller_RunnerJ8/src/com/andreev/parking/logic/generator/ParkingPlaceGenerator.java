package com.andreev.parking.logic.generator;

import com.andreev.parking.model.ParkingPlace;
import com.andreev.parking.model.exception.ParkingException;
import com.andreev.parking.model.factory.ParkingPlaceFactory;

public class ParkingPlaceGenerator {

	private static int idCount = 0;
	private ParkingPlaceFactory parkingPlaceFactory = new ParkingPlaceFactory();

	public ParkingPlaceGenerator() {
	}

	public ParkingPlace generateParkingPlace() throws ParkingException {
		ParkingPlace parkingPlace = parkingPlaceFactory.newParkingPlace(generateId());
		return parkingPlace;
	}

	private int generateId () {
		return ++idCount;
	}

}
