package com.andreev.parking.model.factory;

import com.andreev.parking.model.Car;
import com.andreev.parking.model.exception.CarException;

public class CarFactory {

	public CarFactory() {
	}

	public Car newCar(int id) throws CarException{
		Car car = new Car();
		car.setId(id);
		return car;
	}

}
