package com.andreev.parking.model.factory;

import com.andreev.parking.model.Parking;
import com.andreev.parking.model.exception.ParkingException;

public class ParkingFactory {

	public ParkingFactory() {
	}

	public Parking newParking(int id) throws ParkingException {
		Parking parking = new Parking();
		parking.setId(id);
		return parking;
	}

}
