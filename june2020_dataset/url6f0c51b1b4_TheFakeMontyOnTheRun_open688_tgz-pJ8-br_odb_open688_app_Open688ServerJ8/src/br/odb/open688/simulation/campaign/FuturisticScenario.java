package br.odb.open688.simulation.campaign;

import br.odb.utils.math.Vec3;

public class FuturisticScenario extends Scenario {
	
	private static final Vec3 ENEMY_UNDERWATER_BASE_COORDINATES = new Vec3();
	private static final Vec3 ENEMY_DOCKS_COORDINATES = new Vec3();
	private static final Vec3 FRIENDLY_UNDERWATER_BASE_COORDINATES = new Vec3();
	private static final Vec3 FRIENDLY_DOCKS_COORDINATES = new Vec3( 1.0f, 1.0f, 1.0f );
	private Docks docks;

	public FuturisticScenario() {

		addMaritimeTarget( new UnderwaterBase( ENEMY_UNDERWATER_BASE_COORDINATES, Target.TargetType.Enemy ) );
		addLandTarget( new Docks( ENEMY_DOCKS_COORDINATES, Target.TargetType.Enemy, 1 ) );
		
		docks = new Docks( FRIENDLY_DOCKS_COORDINATES, Target.TargetType.Friendly, 1 );
		addMaritimeTarget( new UnderwaterBase( FRIENDLY_UNDERWATER_BASE_COORDINATES, Target.TargetType.Friendly ) );
		addLandTarget( docks );
	}

	@Override
	Target getFriendlyBase() {
		return docks;
	}
}
