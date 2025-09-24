package br.odb.open688.simulation.campaign;

import java.util.ArrayList;

import br.odb.open688.simulation.ship.Ship;
import br.odb.utils.math.Vec3;

public class Docks extends LandTarget {

	ArrayList< Ship > dockedShips;
	int capacity ;
	
	public Docks(Vec3 coordinates, TargetType type, int capacity ) {
		super(coordinates, type);
		
		this.capacity = capacity;
	}
	
	public void dockShip( Ship newShipToDock ) throws InvalidDockingException {
	}

	@Override
	public void update(long ms) {
	}
}
