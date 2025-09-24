package br.odb.open688.simulation.campaign;

import br.odb.utils.math.Vec3;

public class UnderwaterBase extends MaritimeTarget {

	public UnderwaterBase(Vec3 coordinates, TargetType type) {
		super(coordinates, type);
	}

	@Override
	public void update(long ms) {
	}
}
