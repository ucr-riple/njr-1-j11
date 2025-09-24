package br.odb.open688.simulation.ship;


public class BallastTanks implements ShipPart {

	private int deltaLevel;
	private long level = 50;
	private boolean active;

	@Override
	public float getNoiseLevel() {
		return ( deltaLevel != 00 && active ) ? 5 : 0;
	}
	
	public void fill() {
		deltaLevel = 1;
	}
	
	public void empty() {
		deltaLevel = -1;
	}
	
	@Override
	public String toString() {
		
		String toReturn = "Ballast Tanks: level is " + level;
		
		if ( deltaLevel > 0 && active ) {
			toReturn += ", filling up";
		} else if ( deltaLevel < 0 && active ) {
			toReturn += ", emptying it";
		} else {
			toReturn += ".";
		}
		
		
		return  toReturn; 
	}
	

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void setActive(boolean active) {
		if ( !active ) {
			this.active = false;
			//so we keep the deltaLevel...
		}
	}

	@Override
	public void update(long ms) {

		if ( deltaLevel != 0 ) {
			active = true;
			level += deltaLevel;
		}
		
		if ( deltaLevel > 0 && level > 100 ) {
			level = 100;
			active = false;
			deltaLevel = 0;
		}
		
		if ( deltaLevel < 0 && level < 100 ) {
			level = 0;
			active = false;
			deltaLevel = 0;
		}
		
		
	}
	
	public float getDepthChange() {
		return 50 - level; //probably much more complex than that.
	}

	public void setLevel(int newLevel) {
		
		active = true;
		
		if ( newLevel > level ) {
			deltaLevel = 1;
		} else if ( newLevel < level ){
			deltaLevel = -1;
		} else {
			deltaLevel = 0;
			active = false;
		}		
	}
}
