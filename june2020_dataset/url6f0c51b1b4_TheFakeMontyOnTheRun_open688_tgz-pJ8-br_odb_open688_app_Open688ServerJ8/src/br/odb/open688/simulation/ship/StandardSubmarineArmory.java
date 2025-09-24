package br.odb.open688.simulation.ship;


public class StandardSubmarineArmory extends Armory {

	long timeForCompletingArmingOperation;
	private boolean arming;
	private boolean active;
	
	@Override
	public float getNoiseLevel() {
		return 0;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void update(long ms) {
		
		if ( arming ) {
			
			this.timeForCompletingArmingOperation -= ms;
		}
	}
}
