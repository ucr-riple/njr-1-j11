package units.upgrades;

import map.LevelMap;

/**
 * A modification that changes an aspect of the LevelMap
 * @author Alex Mariakakis
 *
 */
abstract public class MapModification extends UnitDecorator {

	private LevelMap map;

	public MapModification(UnitUpgradable unit, int cost, LevelMap levelMap) {
		super(unit, cost);
		map = levelMap;
	}

	public LevelMap getMap() {
		return map;
	}

	public void setMap(LevelMap levelmap) {
		this.map = levelmap;
	}

}
