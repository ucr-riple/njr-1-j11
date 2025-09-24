package serialization;

/**
 * File for creation of a game. A list of mapElements
 * @author tianyu shi
 * 
 */

import java.util.HashMap;

import ai.StrategyAI;

public class GameElement implements java.io.Serializable {
		
	private HashMap<Integer, MapElement> game = new HashMap<Integer, MapElement>();
	
	public HashMap<Integer, MapElement> getLevelMap() {
		return this.game;
	}
	
	public void setLevelMap(HashMap<Integer, MapElement> levelmap) {
		this.game = levelmap;
	}
	
	public String toString() {
		return "GameElement [game=" + game + "]";
	}
}
