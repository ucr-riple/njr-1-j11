package mp1401.examples.misterx.model.gameitems.enums;

public enum ConnectionType {

	CAR("Car"), 
	TRAIN("Train"), 
	PLANE("Flight");

	private String typeAsString;

	ConnectionType(String stringRepresentation) {
		this.typeAsString = stringRepresentation;
	}

	public String toString() {
		return typeAsString + "-Connection";
	}
}
