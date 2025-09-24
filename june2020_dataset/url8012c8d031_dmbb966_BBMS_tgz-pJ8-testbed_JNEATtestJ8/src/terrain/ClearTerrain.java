package terrain;

import java.awt.Color;

import unit.MoveClass;

public class ClearTerrain extends TerrainType{
	
	@Override
	public int getMoveCost(MoveClass mClass) {
		
		switch (mClass) {
		
		case FOOT: return 2;
		case WHEEL: return 3;
		case AT_WHEEL: return 2;
		case TRACK: return 2;
		
		case BOAT: return 9999;
		default: return 9999;
		
		}		
	}

	@Override
	public String displayType() {
		return "Clear";
	}

	@Override
	public char displayChar() {
		return 'c';
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
		return TerrainEnum.CLEAR;
	}
	
	@Override
	public Color getColor() {
		return Color.WHITE;
	}

}
