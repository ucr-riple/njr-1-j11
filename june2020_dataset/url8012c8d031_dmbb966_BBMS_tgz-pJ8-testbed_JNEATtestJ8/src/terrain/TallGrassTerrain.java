package terrain;

import java.awt.Color;

import unit.MoveClass;
import bbms.GlobalFuncs;

public class TallGrassTerrain extends TerrainType{
	
	@Override
	public int getMoveCost(MoveClass mClass) {
		
		switch (mClass) {
		
		case FOOT: return 2;
		case WHEEL: return 2;
		case AT_WHEEL: return 3;
		case TRACK: return 2;
		
		case BOAT: return 9999;
		default: return 9999;
		
		}		
	}

	@Override
	public String displayType() {
		return "Tall Grass";
	}

	@Override
	public char displayChar() {
		return 't';
	}

	@Override
	public int generateDensity() {
		return GlobalFuncs.randRange(9, 10);
	}

	@Override
	public int generateObsHeight() {
		return 2;
	}

	@Override
	public int generateHeight() {
		return GlobalFuncs.randRange(1, 3);
	}
	
	@Override
	public TerrainEnum getTerrainEnum() {
		return TerrainEnum.T_GRASS;
	}
	
	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

}
