package mp1401.examples.misterx.model.game.states;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.game.GameImpl;
import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.mapparser.MapDataParser;
import mp1401.examples.misterx.model.util.Messsages;

public abstract class AbstractGameState implements GameState {
	
	private static final long serialVersionUID = -7801061201328813086L;
	
	private static final String NOT_POSSIBLE_IN_CURRENT_STATE_MESSAGE = " is not possible in ";
	private static final String FILL_MAP = "Fill Map";
	private static final String ADD_MISTER_X = "Add Mister X";
	private static final String ADD_DETECTIVE = "Add Detective";
	private static final String SET_START_POSITION = "Set Start Position";
	private static final String START_GAME = "Start Game";
	private static final String MISTER_X_MOVEMEMT = "Mister X Movement";
	private static final String DETECTIVE_MOVEMEMT = "Detective Movement";
	private static final String CHECK_SIUTATION = "Check Situation";
	
	protected Game getGame() {
		return GameImpl.getInstance();
	}
	
	protected final void changeGameState(GameState newGameState) {
		getGame().setCurrentGameState(newGameState);
	}
	
	@Override
	public void fillMap(MapDataParser mapDataParser) {
		notPossibleInCurrentStateMessage(FILL_MAP);
	}
	
	@Override
	public void addMisterX(MisterX misterX) {
		notPossibleInCurrentStateMessage(ADD_MISTER_X);
	}
	
	@Override
	public void addDetective(Detective detective) {
		notPossibleInCurrentStateMessage(ADD_DETECTIVE);
	}
	
	@Override
	public void setStartPosition(Character character, City startPosition) {
		notPossibleInCurrentStateMessage(SET_START_POSITION);
	}
	
	@Override
	public void startGame() {
		notPossibleInCurrentStateMessage(START_GAME);
	}

	@Override
	public void moveMisterXTo(City destinationCity) {
		notPossibleInCurrentStateMessage(MISTER_X_MOVEMEMT);
	}
	
	@Override
	public void checkSituation() {
		notPossibleInCurrentStateMessage(CHECK_SIUTATION);
	}

	@Override
	public void moveDetectiveTo(Detective detective, City destinationCity) {
		notPossibleInCurrentStateMessage(DETECTIVE_MOVEMEMT);
	}
	
	private void notPossibleInCurrentStateMessage(String action) {
		Messsages.printMessage(action + NOT_POSSIBLE_IN_CURRENT_STATE_MESSAGE + toString());
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}
}
