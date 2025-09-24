package serialization;

/**
 * Basic MapElement. Has all the units and enviroments and information a game. 
 * @author tianyu shi
 * 
 */

import java.util.ArrayList;
import java.util.List;

import modes.EditMode.GameState;

public class MapElement implements java.io.Serializable {
	
	private int level;

	private int pixelsX;
	private int pixelsY;

	private int tilesX;
	private int tilesY;

	private GameState State;

	private List<UnitElement> unitlist = new ArrayList<UnitElement>();
	private List<EnvironmentElement> enviromentlist = new ArrayList<EnvironmentElement>();

	public MapElement() {
	}

	public GameState getState() {
		return this.State;
	}
	
	public void setState(GameState State) {
		this.State = State;
	}

	public void setpixelsX(int pixelsX) {
		this.pixelsX = pixelsX;
	}

	public int getpixelsX() {
		return this.pixelsX;
	}

	public void setpixelsY(int pixelsY) {
		this.pixelsY = pixelsY;
	}

	public int getpixelsY() {
		return this.pixelsY;
	}

	public void settilesX(int tilesX) {
		this.tilesX = tilesX;
	}

	public int gettilesX() {
		return this.tilesX;
	}

	public void settilesY(int tilesY) {
		this.tilesY = tilesY;
	}

	public int gettilesY() {
		return this.tilesY;
	}


	public void setunitlist(List<UnitElement> unitlist) {
		this.unitlist = unitlist;
	}

	public List<UnitElement> getunitlist() {
		return this.unitlist;
	}

	public void setEvironmentlist(List<EnvironmentElement> enviromentlist) {
		this.enviromentlist = enviromentlist;
	}

	public List<EnvironmentElement> getEnviromentList() {
		return this.enviromentlist;
	}
	public String toString() {
		return "MapElement [pixelsX=" + pixelsX + ", pixelsY=" + pixelsY + ", tilesX=" + tilesX + ",tilesY=" + tilesY + ", unitlist=" + unitlist + ", enviromentlist = " + enviromentlist + "]";
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}