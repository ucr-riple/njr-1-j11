package br.odb.open688.simulation.campaign;

import br.odb.open688.simulation.ship.Ship;
import br.odb.open688.simulation.ship.Submarine;
import br.odb.utils.math.Vec3;

public abstract class Target {
	
	public enum TargetType {
		Enemy,
		Friendly
	}
	
	final protected Vec3 coordinates = new Vec3();
	final TargetType type;

	/**
	 * Initialize the target in space, with basic characteristics.
	 * @param coordinates target position in space - depending on the scenario water line, 
	 * z could be under water.
	 * @param type kind of target. This will influence its behavior and split the teams.
	 */
	public Target(Vec3 coordinates, TargetType type ) {
		this.coordinates.set( coordinates );
		this.type = type;
	}

	public Ship createSubmarine() {
		return new Submarine( coordinates, Target.TargetType.Friendly );
	}
	
	public abstract void update(long ms);
}
