package risiko;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import risiko.actions.Action;
import risiko.actions.AddPlayer;
import risiko.actions.Atack;
import risiko.actions.CoinCards;
import risiko.actions.MoveTroops;
import risiko.actions.RemovePlayer;
import risiko.actions.SetTroops;
import risiko.actions.StartGame;
import risiko.actions.util.actionSwitch;
import risiko.board.Board;
import risiko.board.Country;
import risiko.gamestate.CountryState;
import risiko.gamestate.GameState;
import risiko.gamestate.Player;
import risiko.gamestate.State;
import risiko.gamestate.TurnPhase;
import risiko.gamestate.stateFactory;

/**
 * An executor executes Actions in a Risiko Game. It may have extension points
 * to allow adaptations of the rules or to manipulate the dices.
 * 
 * @author xilaew
 *
 */
public class ActionExecutor extends actionSwitch<State> {

	private final State state;
	private final Board board;

	public ActionExecutor(Board board, State state) {
		this.state = state;
		this.board = board;
	}

	/**
	 * Executes the given action and updates state. The board is left unchanged.
	 * The passed action might also be invalidated.
	 * 
	 * @param action
	 */
	public State execute(Action action) {
		System.out.println("executing an Action");
		return this.doSwitch(action);
	}

	@Override
	public State caseAtack(Atack object) {
		// TODO Auto-generated method stub
		return super.caseAtack(object);
	}

	@Override
	public State caseSetTroops(SetTroops arg) {
		if (arg.getPlayer().equals(state.getTurn())
				&& state.getTroopsToSet() >= arg.getTroops()
				&& state.getPhase().getValue() <= TurnPhase.SET_TROOPS_VALUE
				&& (state.getState().equals(GameState.PLAY) || state.getState()
						.equals(GameState.INITIAL_TROOP_DISTRIBUTION))) {

			// set Troops
			CountryState cs = state.getCountryState().get(arg.getCountry());
			cs.setTroops(cs.getTroops() + arg.getTroops());
			state.setTroopsToSet(state.getTroopsToSet() - arg.getTroops());

			if (state.getState().equals(GameState.INITIAL_TROOP_DISTRIBUTION)) {
				// move on to the next player that has not set all its initial
				// Troops yet.
				List<Player> players = state.getPlayers();
				int playerCount = players.size();
				int nextIndex = (players.indexOf(state.getTurn()) + 1)
						% playerCount;
				Player nextPlayer = players.get(nextIndex);
				while (nextPlayer.getTotalTroops() >= board.getInitialTroops()
						.get(playerCount)) {
					if (nextPlayer == state.getTurn()) {
						// obviously all Players have set all their initial
						// troops. move on to the GameState Play.
						state.setState(GameState.PLAY);
						state.setPhase(TurnPhase.FIGHT);
						state.setTroopsToSet(0);
						nextPlayer = players.get(0);
						break;
					}
					nextIndex = (nextIndex + 1) % playerCount;
					nextPlayer = players.get(nextIndex);
				}
				state.setTurn(nextPlayer);
			} else if (state.getTroopsToSet() == 0) {
				// No more Troops to set? move on to Fighting Phase!
				state.setPhase(TurnPhase.FIGHT);
			}
			return state;
		}
		return super.caseSetTroops(arg);
	}

	@Override
	public State caseCoinCards(CoinCards object) {
		// TODO Auto-generated method stub
		return super.caseCoinCards(object);
	}

	@Override
	public State caseMoveTroops(MoveTroops object) {
		// TODO Auto-generated method stub
		return super.caseMoveTroops(object);
	}

	@Override
	public State caseAddPlayer(AddPlayer object) {
		if (state.getState() == GameState.ACCEPTING_PLAYERS) {
			state.getPlayers().addAll(object.getPlayers());
			return state;
		}
		return null;
	}

	@Override
	public State caseStartGame(StartGame object) {
		if (state.getState() == GameState.ACCEPTING_PLAYERS
				&& state.getPlayers().size() >= 2) {

			distributeCountriesAmongPlayers();

			state.setState(GameState.INITIAL_TROOP_DISTRIBUTION);
			state.setTroopsToSet(1);
			state.setTurn(state.getPlayers().get(0));
			state.setPhase(TurnPhase.SET_TROOPS);
			return state;
		}
		return super.caseStartGame(object);
	}

	private void distributeCountriesAmongPlayers() {
		List<Country> toDistribute = new LinkedList<Country>();
		toDistribute.addAll(board.getCountries());
		while (toDistribute.size() / state.getPlayers().size()
				* state.getPlayers().size() != toDistribute.size()) {
			toDistribute.add(null);
		}
		Collections.shuffle(toDistribute);
		Iterator<Player> playerIt = state.getPlayers().iterator();
		Player player;
		for (Country c : toDistribute) {
			if (!playerIt.hasNext()) {
				playerIt = state.getPlayers().iterator();
			}
			player = playerIt.next();
			if (c != null) {
				CountryState countryState = stateFactory.eINSTANCE
						.createCountryState();
				countryState.setTroops(1);
				countryState.setCountry(c);
				countryState.setPlayer(player);
				state.getCountryState().put(c, countryState);
			}
		}
	}

	@Override
	public State caseRemovePlayer(RemovePlayer object) {
		if (state.getState() == GameState.ACCEPTING_PLAYERS) {
			state.getPlayers().removeAll(object.getPlayers());
			return state;
		}
		return super.caseRemovePlayer(object);
	}
}
