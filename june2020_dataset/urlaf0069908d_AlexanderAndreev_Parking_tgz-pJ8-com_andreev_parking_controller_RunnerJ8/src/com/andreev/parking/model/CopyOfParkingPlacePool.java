package com.andreev.parking.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.parking.model.exception.ParkingException;

public final class CopyOfParkingPlacePool {

	private final int SIZE;
	private final Semaphore SEMAPHORE;
	private final Queue<ParkingPlace> PLACES = new LinkedList<ParkingPlace>();
	private final Lock LOCK = new ReentrantLock();

	public CopyOfParkingPlacePool(List<ParkingPlace> places) {
		this.SIZE = places.size();
		this.SEMAPHORE = new Semaphore(SIZE);
		this.PLACES.addAll(places);
	}

	public ParkingPlace getParkingPlace(long maxWaitMillis)
			throws ParkingException {
		try {
			if (SEMAPHORE.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
				return pollParkingPlace();
			}
		} catch (InterruptedException e) {
			throw new ParkingException(e);
		}
		throw new ParkingException("Timeout");
	}

	public void returnParkingPlace(ParkingPlace place) throws ParkingException {
		PLACES.add(place);
		SEMAPHORE.release();
	}

	private ParkingPlace pollParkingPlace() {
		LOCK.lock();
		try {
			ParkingPlace place = PLACES.poll();
			return place;
		} finally {
			LOCK.unlock();
		}
	}

}
