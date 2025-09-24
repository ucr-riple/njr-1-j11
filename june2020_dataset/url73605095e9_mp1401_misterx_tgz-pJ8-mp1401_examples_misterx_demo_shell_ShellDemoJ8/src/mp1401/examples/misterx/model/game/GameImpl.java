package mp1401.examples.misterx.model.game;

import java.util.ArrayList;
import java.util.List;

import mp1401.examples.misterx.model.factory.GameItemFactory;
import mp1401.examples.misterx.model.factory.GameItemFactoryImpl;
import mp1401.examples.misterx.model.game.helpers.PositionChecker;
import mp1401.examples.misterx.model.game.states.DefaultGameState;
import mp1401.examples.misterx.model.game.states.FinishedGameState;
import mp1401.examples.misterx.model.game.states.GameState;
import mp1401.examples.misterx.model.game.states.StartGameState;
import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.collections.GameItemList;
import mp1401.examples.misterx.model.gameitems.enums.DetectiveType;
import mp1401.examples.misterx.model.gameitems.impl.collections.GameItemListImpl;
import mp1401.examples.misterx.model.mapparser.MapDataParser;
import mp1401.examples.misterx.model.observers.GameStateChangeObserver;

public class GameImpl implements Game {

  private static Game instance;

  private final List<GameStateChangeObserver> observers;
  private GameState currentGameState;
  private Map map;
  private MisterX misterX;
  private GameItemList<Detective> detectives;
  private PositionChecker positionChecker;
  private int round;
  private Character winner;

  private GameImpl() {
    observers = new ArrayList<GameStateChangeObserver>();
    initHelpers();
    initGameState();
    setDefaultValues();

  }

  private void initHelpers() {
    this.positionChecker = new PositionChecker();
  }

  private void initGameState() {
    this.currentGameState = new DefaultGameState();
  }

  private void setDefaultValues() {
    final GameItemFactory factory = GameItemFactoryImpl.getInstance();
    this.map = factory.createMap();
    this.misterX = factory.createMisterX();
    this.detectives = new GameItemListImpl<Detective>();
    this.winner = factory.createUnknownCharacter();
    round = 0;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new GameImpl();
    }
    return instance;
  }

  @Override
  public void init() {
    this.currentGameState = new StartGameState(this);
  }

  @Override
  public boolean isReady() {
    return detectives.size() > 0 && map.isFilled();
  }

  @Override
  public boolean isFinished() {
    return getCurrentGameState() instanceof FinishedGameState;
  }

  @Override
  public PositionChecker getPositionChecker() {
    return positionChecker;
  }

  @Override
  public int getRound() {
    return round;
  }

  @Override
  public void increaseRound() {
    round = round + 1;
  }

  @Override
  public GameState getCurrentGameState() {
    return currentGameState;
  }

  @Override
  public void setCurrentGameState(final GameState currentGameState) {
    this.currentGameState = currentGameState;
    notifyGameStateChangeObservers();
  }

  @Override
  public void fillMap(final MapDataParser mapDataParser) {
    getCurrentGameState().fillMap(mapDataParser);
  }

  @Override
  public void addMisterX(final MisterX misterX) {
    getCurrentGameState().addMisterX(misterX);
  }

  @Override
  public void addDetective(final Detective detective) {
    getCurrentGameState().addDetective(detective);
  }

  @Override
  public void startGame() {
    getCurrentGameState().startGame();
  }

  @Override
  public void setStartPosition(final Character character, final City startPosition) {
    getCurrentGameState().setStartPosition(character, startPosition);
  }

  @Override
  public void moveMisterXTo(final City destinationCity) {
    getCurrentGameState().moveMisterXTo(destinationCity);
  }

  @Override
  public void moveDetectiveTo(final Detective detective, final City destinationCity) {
    getCurrentGameState().moveDetectiveTo(detective, destinationCity);
  }

  @Override
  public void checkSituation() {
    getCurrentGameState().checkSituation();
  }

  @Override
  public MisterX getMisterX() {
    return misterX;
  }

  @Override
  public void setMisterX(final MisterX misterX) {
    this.misterX = misterX;
  }

  @Override
  public GameItemList<Detective> getDetectives() {
    return detectives;
  }

  @Override
  public Detective getDetective(final DetectiveType type) {
    for (final Detective detective : detectives) {
      if (detective.getType().equals(type)) {
        return detective;
      }
    }
    return GameItemFactoryImpl.getInstance().createDetective(DetectiveType.DEFAULT);
  }

  @Override
  public GameItemList<Connection> getConnections() {
    return map.getConnections();
  }

  @Override
  public Map getMap() {
    return map;
  }

  @Override
  public Character getWinner() {
    return winner;
  }

  @Override
  public void setWinner(final Character winner) {
    this.winner = winner;
  }

  @Override
  public void addGameStateChangeObserver(final GameStateChangeObserver observer) {
    observers.add(observer);

  }

  @Override
  public void removeGameStateChangeObserver(final GameStateChangeObserver observer) {
    observers.remove(observer);

  }

  @Override
  public void notifyGameStateChangeObservers() {
    for (final GameStateChangeObserver observer : observers) {
      observer.gameStateChangeUpdate();
    }
  }
}
