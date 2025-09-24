package com.andreev.parking.logic.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andreev.parking.model.Parking;
import com.andreev.parking.model.exception.ParkingException;
import com.andreev.parking.model.factory.ParkingFactory;

public class ParkingGenerator {

	private static final Logger LOG = Logger.getLogger(ParkingGenerator.class);

	private static final int PARKING_LIST_SIZE = 10;
	private static final int MAX_PLACES_COUNT = 100;

	private static int idCount = 0;

	private ParkingFactory parkingFactory = new ParkingFactory();
	private ParkingPlacePoolGenerator generator = new ParkingPlacePoolGenerator();

	public ParkingGenerator() {
	}

	public Parking generateParking (int placePoolSize) throws ParkingException {
		Parking parking = parkingFactory.newParking(generateId());
		parking.setPlacePool(generator.generateParkingPlacePool(placePoolSize));
		return parking;
	}

	public Parking generateParking () throws ParkingException {
		return generateParking(MAX_PLACES_COUNT);
	}

	public List<Parking> generateParkingList (int listSize, int placePoolCount) throws ParkingException {
		List<Parking> parkings = new ArrayList<Parking>();
		for(int i = 0; i < listSize; i++) {
			parkings.add(generateParking(placePoolCount));
		}
		LOG.info("!!!Was generated " + listSize + " parkings!!!");
		return parkings;
	}

	public List<Parking> generateParkingList () throws ParkingException {
		return generateParkingList(PARKING_LIST_SIZE, MAX_PLACES_COUNT);
	}

	private int generateId () {
		return ++idCount;
	}

}
