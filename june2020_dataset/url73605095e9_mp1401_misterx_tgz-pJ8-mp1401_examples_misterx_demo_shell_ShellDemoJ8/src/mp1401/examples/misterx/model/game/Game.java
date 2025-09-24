package mp1401.examples.misterx.model.game;

import mp1401.examples.misterx.model.game.helpers.PositionChecker;
import mp1401.examples.misterx.model.game.states.GameState;
import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.collections.GameItemList;
import mp1401.examples.misterx.model.gameitems.enums.DetectiveType;
import mp1401.examples.misterx.model.mapparser.MapDataParser;
import mp1401.examples.misterx.model.observers.GameStateChangeObservable;

public interface Game extends GameStateChangeObservable {
	
	public static final int NUMBER_OF_ROUNDS_UNTIL_MISTER_X_WINS = 20;
	public static final int MAX_NUMBER_OF_DETECTIVES = 4;
	
	public GameState getCurrentGameState();
	
	public void setCurrentGameState(GameState currentGameState);
	
	public void init();
	
	public boolean isReady();
	
	public boolean isFinished();
	
	public PositionChecker getPositionChecker();
	
	public Map getMap();
	
	public int getRound();
	
	public void increaseRound();
	
	public GameItemList<Connection> getConnections();
	
	public MisterX getMisterX();
	
	public void setMisterX(MisterX misterX);

	public GameItemList<Detective> getDetectives();
	
	public Detective getDetective(DetectiveType type);
	
	public Character getWinner();
	
	public void setWinner(Character character);
	
	public void fillMap(MapDataParser mapDataParser);
	
	public void addMisterX(MisterX misterX);
	
	public void addDetective(Detective detective);
	
	public void startGame();
	
	public void setStartPosition(Character character, City startPosition);

	public void moveMisterXTo(City destinationCity);

	public void moveDetectiveTo(Detective detective, City destinationCity);
	
	public void checkSituation();
}
