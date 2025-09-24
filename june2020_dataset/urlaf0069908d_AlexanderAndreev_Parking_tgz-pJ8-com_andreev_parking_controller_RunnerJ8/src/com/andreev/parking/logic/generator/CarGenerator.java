package com.andreev.parking.logic.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.andreev.parking.logic.ParkingList;
import com.andreev.parking.model.Car;
import com.andreev.parking.model.IParking;
import com.andreev.parking.model.exception.CarException;
import com.andreev.parking.model.factory.CarFactory;

public class CarGenerator {

	private static final Logger LOG = Logger.getLogger(CarGenerator.class);

	private static final int MAX_WAIT_TIME_MILLIS = 2000;
	private static final int MAX_PARKING_TIME_MILLIS = 400;
	private static final int MAX_CAR_COUNT = 100;

	private static final Random RANDOM = new Random();
	private static int idCount = 0;
	private CarFactory carFactory = new CarFactory();

	public CarGenerator() {
	}

	public Car generateCar(int maxWaitTime, int maxParkingTime)
			throws CarException {
		Car car = carFactory.newCar(generateId());
		car.setParkings(getParkings());
		car.setMaxWaitTimeMillis(generateMaxWaitTime(maxWaitTime));
		car.setParkTimeMillis(generateParkTime(maxParkingTime));
		return car;
	}

	public Car generateCar() throws CarException {
		return generateCar(MAX_WAIT_TIME_MILLIS, MAX_PARKING_TIME_MILLIS);
	}

	public List<Car> generateCars (int carMaxCount) throws CarException {
		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < carMaxCount; i++) {
			cars.add(generateCar());
		}
		LOG.info("!!!Was generated " + carMaxCount + " cars!!!");
		return cars;
	}

	public List<Car> generateCars () throws CarException {
		return generateCars(MAX_CAR_COUNT);
	}

	private int generateId() {
		return ++idCount;
	}

	private Iterable<IParking> getParkings() {
		return ParkingList.getInstence();
	}

	private int generateMaxWaitTime(int maxWaitTime) {
		return RANDOM.nextInt(maxWaitTime);
	}

	private int generateParkTime(int maxParkingTime) {
		return RANDOM.nextInt(maxParkingTime);
	}
}
