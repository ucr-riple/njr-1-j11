package br.odb.open688.simulation.ship;

public abstract class Engine implements ShipPart {

	protected static final int KNOTS = 1;

	float currentThrust;
	boolean active;

	private float inertia;
	
	public void setThrust( float thrust ) {
		this.currentThrust = thrust;
	}
	
	@Override
	public float getNoiseLevel() {
		return active ? getMaxNoiseLevel() : 0;
	}
	
	public abstract int getMaxNoiseLevel();

	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public void setActive(boolean active) {
		
		if ( !active ) {
			inertia = getSpeedGain();
		}
		
		this.active = active;
	}
	
	@Override
	public void update(long ms) {
		if ( inertia > 0 ) {
			
			inertia -= ms / 1000.0f;
		} else {
			inertia = 0;
		}
	}

	public float getSpeedGain() {		
		return active ? currentThrust * getTopSpeed() : inertia;
	}

	public abstract float getTopSpeed();
	
	@Override
	public String toString() {
		return "Engines: " + ( active ? "active" : "inactive " ) + ", current thrust: " + currentThrust + ", current speed: " + getSpeedGain();
	}
}
