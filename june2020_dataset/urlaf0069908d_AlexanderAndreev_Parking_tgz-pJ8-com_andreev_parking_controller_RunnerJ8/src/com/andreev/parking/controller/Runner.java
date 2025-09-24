package com.andreev.parking.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.andreev.parking.logic.CarExecuter;
import com.andreev.parking.logic.ParkingList;
import com.andreev.parking.logic.generator.CarGenerator;
import com.andreev.parking.logic.generator.ParkingGenerator;
import com.andreev.parking.model.Car;
import com.andreev.parking.model.Parking;
import com.andreev.parking.model.exception.CarException;
import com.andreev.parking.model.exception.ParkingException;

public class Runner {

	private static final Logger LOG = Logger.getLogger(Runner.class);

	public static void main(String[] args) {
		try {
			List<Parking> parkings = new ParkingGenerator().generateParkingList(50, 10);
			ParkingList.getInstence().addParking(parkings);
			List<Car> cars = new CarGenerator().generateCars(3000);
			CarExecuter executer = new CarExecuter();
			executer.execute(cars);
		} catch (ParkingException e) {
			LOG.fatal("Fatal", e);
		} catch (CarException e) {
			LOG.fatal("Fatal", e);
		}
	}
}
