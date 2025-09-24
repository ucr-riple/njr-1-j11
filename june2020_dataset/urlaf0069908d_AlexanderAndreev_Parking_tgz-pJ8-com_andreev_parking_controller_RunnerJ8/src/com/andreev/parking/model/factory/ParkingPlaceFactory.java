package com.andreev.parking.model.factory;

import com.andreev.parking.model.ParkingPlace;
import com.andreev.parking.model.exception.ParkingException;

public class ParkingPlaceFactory {

	public ParkingPlaceFactory() {
	}

	public ParkingPlace newParkingPlace(int id) throws ParkingException {
		ParkingPlace place = new ParkingPlace();
		place.setId(id);
		return place;
	}

}
