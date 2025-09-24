package br.odb.open688.simulation.campaign;

import java.util.ArrayList;

import br.odb.open688.simulation.ship.Ship;

public class Mission {
	
	final private ArrayList< Ship > playerSquadron = new ArrayList< Ship >();
	private Ship capitalShip;
	private volatile long ellapsedTime;

	public float getStatus() {
		return 0.0f;
	}
	
	public int getScore() {
		return 0;
	}
	
	public String toString() {
		return "";
	}

	public void setDeparture(Target friendlyBase) {
	
	}

	public void setDestination(Target friendlyBase) {
	}

	public void addPrimaryObjective( Objective objective) {
	}

	public void addSecundaryObjective( Objective objective) {
	}

	public void addPlayerShip( Ship ship ) {

		if ( playerSquadron.size() == 0 ) {
			
			capitalShip = ship;
		}
		
		playerSquadron.add( ship );
	}

	public void update(long ms) {
		for ( Ship s : playerSquadron ) {
			s.update( ms );
		}
		
		ellapsedTime += ms;
	}

	public Ship getPlayerCapitalShip() {
		return capitalShip;
	}

	public float getSimulationEllapsedTime() {
		return ellapsedTime / 1000.0f;
	}
}
