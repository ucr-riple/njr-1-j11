package br.odb.open688.simulation.ship;

public class Crew implements ShipPart {

	CrewMember[] crewMembers;
	CrewMember numberOne;
	CrewMember captain;
	
	@Override
	public float getNoiseLevel() {
		return 0;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void setActive(boolean active) {
	}

	@Override
	public void update(long ms) {
	}
	
	@Override
	public String toString() {
		return "Crew: crew is disciplined";
	}
}
