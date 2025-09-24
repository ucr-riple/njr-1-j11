package terrain;

import java.awt.Color;

import unit.MoveClass;

abstract public class TerrainType {
		
	// Returns the movement cost for this type of terrain, given a movement class
	public abstract int getMoveCost (MoveClass mClass);
	
	public abstract TerrainEnum getTerrainEnum();
	
	// Returns the display name for this terrain type
	public abstract String displayType ();
	
	// Returns the shorthand display for this terrain type
	// TODO: Replace with whatever graphical display you will use for this terrain type
	public abstract char displayChar ();
	
	public abstract int generateDensity();	
	public abstract int generateObsHeight();
	public abstract int generateHeight();
	
	public abstract Color getColor();
}
