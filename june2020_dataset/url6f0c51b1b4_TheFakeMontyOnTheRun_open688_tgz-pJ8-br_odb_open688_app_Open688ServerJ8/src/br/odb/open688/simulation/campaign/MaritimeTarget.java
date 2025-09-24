package br.odb.open688.simulation.campaign;

import br.odb.utils.math.Vec3;

public abstract class MaritimeTarget extends Target {

	public MaritimeTarget(Vec3 coordinates, TargetType type) {
		super(coordinates, type);
	}
}
