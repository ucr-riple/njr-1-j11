package br.odb.open688.simulation.campaign;

import br.odb.utils.math.Vec3;

public abstract class LandTarget extends Target {

	public LandTarget(Vec3 coordinates, TargetType type) {
		super(coordinates, type);
	}
}
