package com.andreev.parking.logic;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.andreev.parking.model.Car;

public class CarExecuter {

	private ExecutorService executor = Executors.newCachedThreadPool();

	public CarExecuter() {
	}

	public void execute(List<Car> cars) {
		for (Car c : cars) {
			executor.execute(c);
		}
	}

	public void execute(Car c) {
		executor.execute(c);
	}

	public void shutdown () {
		executor.shutdown();
	}

}
