package com.andreev.parking.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.andreev.parking.model.IParking;
import com.andreev.parking.model.Parking;

public class ParkingList implements Iterable<IParking> {
	private List<IParking> list = new ArrayList<IParking>();

	private static final class ParkingListHolder {
		public static final ParkingList PARKING_LIST = new ParkingList();
	}

	private ParkingList() {
	}

	public static ParkingList getInstence() {
		return ParkingListHolder.PARKING_LIST;
	}

	public void addParking(Parking parking) {
		list.add(parking);
	}

	public void addParking(List<Parking> parkings) {
		list.addAll(parkings);
	}

	public void removeParking(Parking parking) {
		list.remove(parking);
	}

	@Override
	public Iterator<IParking> iterator() {
		return list.iterator();
	}

}
