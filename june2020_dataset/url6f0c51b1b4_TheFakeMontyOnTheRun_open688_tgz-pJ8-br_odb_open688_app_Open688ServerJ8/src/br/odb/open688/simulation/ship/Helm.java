package br.odb.open688.simulation.ship;

public class Helm implements ShipPart {

	private int targetHeading;
	private int deltaHeading;
	private int heading;

	@Override
	public float getNoiseLevel() {
		return 0;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void setActive(boolean active) {
	}
	
	public void setNewHeading( int newHeading ) {
		
		targetHeading = newHeading;
		
		deltaHeading = 0;
		
		if ( targetHeading == heading ) {
			return;
		}
		
		//temporary. There a few situations where this is highly undesirable.
		if ( targetHeading > heading ) {
			deltaHeading = 1;
		} else {
			deltaHeading = -1;
		}
	}
	
	public float getCurrentHeading() {
		return heading;	
	}
	
	@Override
	public String toString() {
		
		String toReturn = "Helm: pointing to: " + heading;
		
		if ( deltaHeading < 0 ) {
			toReturn += ", turning left";
		} else if ( deltaHeading > 0 ) {
			toReturn += ", turning right";
		} else {
			toReturn += ".";
		}
		
		return toReturn;
	}

	@Override
	public void update(long ms) {
		
		if ( deltaHeading != 0 ) {
			
			heading += deltaHeading; //should factor in with time...		
		}
		
		if ( heading == targetHeading ) {
			deltaHeading = 0;
		}		
	}
}
