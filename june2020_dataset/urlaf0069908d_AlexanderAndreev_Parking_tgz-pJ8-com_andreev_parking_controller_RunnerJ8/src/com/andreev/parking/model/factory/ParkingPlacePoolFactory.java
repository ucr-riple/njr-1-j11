package com.andreev.parking.model.factory;

import java.util.List;

import com.andreev.parking.model.ParkingPlace;
import com.andreev.parking.model.ParkingPlacePool;

public class ParkingPlacePoolFactory {

	public ParkingPlacePoolFactory() {
	}

	public ParkingPlacePool newParkingPlacePool(List<ParkingPlace> places) {
		ParkingPlacePool pool = new ParkingPlacePool(places);
		return pool;
	}

}
