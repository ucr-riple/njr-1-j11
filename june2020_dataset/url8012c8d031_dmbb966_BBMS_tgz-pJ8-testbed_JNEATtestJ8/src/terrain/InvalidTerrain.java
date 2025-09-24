package terrain;

import java.awt.Color;

import unit.MoveClass;

public class InvalidTerrain extends TerrainType{
	
	@Override
	public int getMoveCost(MoveClass mClass) {
		
		switch (mClass) {
		
		case FOOT: return 9999;
		case WHEEL: return 9999;
		case AT_WHEEL: return 9999;
		case TRACK: return 9999;
		
		case BOAT: return 9999;
		default: return 9999;
		
		}		
	}

	@Override
	public String displayType() {
		return "";
	}

	@Override
	public char displayChar() {
		return 'x';
	}

	@Override
	public int generateDensity() {
		return 0;
	}

	@Override
	public int generateObsHeight() {
		return 0;
	}

	@Override
	public int generateHeight() {
		return 0;
	}

	@Override
	public TerrainEnum getTerrainEnum() {
		return TerrainEnum.INVALID;
	}
	
	@Override
	public Color getColor() {
		return Color.GRAY;
	}
	

}
