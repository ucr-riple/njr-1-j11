package br.odb.open688.simulation.ship;

import br.odb.utils.math.Vec3;

public class Submarine extends Ship {

	public BallastTanks tanks = new StandardSubmarineBallastTanks();
	Periscope periscope = new StandardSubmarinePeriscope();
	
	@Override
	public void update(long ms) {
		
		super.update(ms);
		coordinates.y += tanks.getDepthChange();
	}
	
	public Submarine(Vec3 coordinates, TargetType type) {
		super(coordinates, type);
		
		addStation( "Tanks", tanks );
		addStation( "Periscope", periscope );
	}

	@Override
	public Communications makeCommunications() {
		return new SubmarineCommSystem();
	}

	@Override
	public Crew makeCrew() {
		//I know, I know. Arronax was not a officer, but rather a guest.
		return new StandardSubmarineCrew( "Nemo", "Aronnax" );
	}

	@Override
	public Sonar makeSonar() {
		return new StandardSubmarineSonar();
	}

	@Override
	public Radar makeRadar() {
		return new StandardSubmarineRadar();
	}

	@Override
	public Armory makeArmory() {
		return new StandardSubmarineArmory();
	}

	@Override
	public Engine makeEngine() {
		return new StandardAtomicSubmarineEngine();
	}

	@Override
	public float getShipDrag() {
		return 100; //completely random!
	}
}
