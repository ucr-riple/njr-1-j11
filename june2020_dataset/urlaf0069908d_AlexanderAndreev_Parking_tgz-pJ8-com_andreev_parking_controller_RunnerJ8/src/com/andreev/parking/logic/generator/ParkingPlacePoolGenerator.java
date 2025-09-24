package com.andreev.parking.logic.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.andreev.parking.model.ParkingPlace;
import com.andreev.parking.model.ParkingPlacePool;
import com.andreev.parking.model.exception.ParkingException;
import com.andreev.parking.model.factory.ParkingPlacePoolFactory;

public class ParkingPlacePoolGenerator {

	private static final Logger LOG = Logger.getLogger(ParkingPlacePoolGenerator.class);

	private static final int MAX_PLACES_COUNT = 100;

	private static final Random RANDOM = new Random();

	private ParkingPlacePoolFactory parkingPlacePoolFactory = new ParkingPlacePoolFactory();
	private ParkingPlaceGenerator parkingPlaceGenerator = new ParkingPlaceGenerator();

	public ParkingPlacePoolGenerator() {
	}

	public ParkingPlacePool generateParkingPlacePool(int maxPlacesCount) throws ParkingException {
		List<ParkingPlace> places = new ArrayList<ParkingPlace>();
		for (int i = 0; i < generatePlaceCount(maxPlacesCount); i++) {
			places.add(parkingPlaceGenerator.generateParkingPlace());
		}
		ParkingPlacePool pool = parkingPlacePoolFactory
				.newParkingPlacePool(places);
		LOG.info("!!!Was generated " + maxPlacesCount + " places!!!");
		return pool;
	}

	public ParkingPlacePool generateParkingPlacePool () throws ParkingException {
		return generateParkingPlacePool(MAX_PLACES_COUNT);
	}

	private int generatePlaceCount(int maxCount) {
		return RANDOM.nextInt(maxCount);
	}

}
