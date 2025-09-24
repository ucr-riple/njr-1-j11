package br.odb.open688.simulation.ship;

public interface ShipPart {
	float getNoiseLevel();
	boolean isActive();
	void setActive( boolean active );
	void update( long ms );
}
