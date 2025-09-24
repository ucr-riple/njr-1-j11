package Utils;

import java.io.Serializable;

import factory.PartType;

public class BinData implements Serializable {

	Location binLocation;
	PartType binPartType;
	
	public BinData (Location newLocation, PartType newPartType) {
		binLocation = newLocation;
		binPartType = newPartType;
	}

	public Location getBinLocation() {
		return binLocation;
	}

	public PartType getBinPartType() {
		return binPartType;
	}
	
}
