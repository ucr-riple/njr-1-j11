package com.andreev.parking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.andreev.parking.model.exception.ParkingException;

public final class ParkingPlacePool {

	private final int SIZE;
	private final Semaphore SEMAPHORE;
	private final List<ParkingPlace> PLACES = new ArrayList<ParkingPlace>();
	private final Lock LOCK = new ReentrantLock();

	public ParkingPlacePool(List<ParkingPlace> places) {
		this.SIZE = places.size();
		this.SEMAPHORE = new Semaphore(SIZE);
		this.PLACES.addAll(places);
	}

	public ParkingPlace getParkingPlace(long maxWaitMillis)
			throws ParkingException {
		try {
			if (SEMAPHORE.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
				return getItem();
			}
		} catch (InterruptedException e) {
			throw new ParkingException(e);
		}
		throw new ParkingException("Timeout");
	}

	public void returnParkingPlace(ParkingPlace place) throws ParkingException {
		if (releaseItem(place)) {
			SEMAPHORE.release();
		}
	}

	@SuppressWarnings("unused")
	private ParkingPlace getItem() {
		ParkingPlace place = null;
		LOCK.lock();
		try {
			for (int i = 0; i < SIZE; i++) {
				place = PLACES.get(i);
				if (!place.isUsed()) {
					place.togleUsed();
				}
				return place;
			}
		} finally {
			LOCK.unlock();
		}
		return place;
	}

	private boolean releaseItem(ParkingPlace place) {
		LOCK.lock();
		try {
			int index = PLACES.indexOf(place);
			if (index == -1) {
				return false;
			}
			if (PLACES.get(index).isUsed()) {
				place.togleUsed();
				return true;
			}
		} finally {
			LOCK.unlock();
		}
		return false;
	}

}
