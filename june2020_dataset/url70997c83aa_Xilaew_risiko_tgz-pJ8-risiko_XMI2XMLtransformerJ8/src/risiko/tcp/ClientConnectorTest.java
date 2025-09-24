package risiko.tcp;

import java.nio.channels.SelectionKey;

import org.eclipse.emf.ecore.EObject;

import risiko.actions.AddPlayer;
import risiko.actions.StartGame;
import risiko.actions.actionFactory;
import risiko.board.Board;
import risiko.gamestate.GameState;
import risiko.gamestate.Player;
import risiko.gamestate.State;
import risiko.gamestate.stateFactory;

public class ClientConnectorTest implements ConnectorListener {

	ClientConnector con;

	public static void main(String[] args) {
		ClientConnector con = new ClientConnector();
		ClientConnectorTest tester = new ClientConnectorTest();
		tester.con = con;
		con.registerListener(tester);
		new Thread(con).start();
	}

	@Override
	public void handleIncomming(EObject object, SelectionKey key) {
		if (object instanceof Board) {
			System.out.println("received Board");
		} else if (object instanceof State) {
			State state = (State) object;
			System.out.println("received new State");
			if (GameState.ACCEPTING_PLAYERS.equals(state.getState())) {
				if (state.getPlayers().size() < 2) {
					Player p = stateFactory.eINSTANCE.createPlayer();
					p.setName("xilaew");
					AddPlayer addPlayer = actionFactory.eINSTANCE
							.createAddPlayer();
					addPlayer.getPlayers().add(p);
					con.send(addPlayer, key);
				} else {
					StartGame start = actionFactory.eINSTANCE.createStartGame();
					con.send(start, key);
				}
			} else{
				System.out.println(state.getState().toString());
			}
		}
	}

	@Override
	public void handleOutgoing(EObject object, SelectionKey key) {
		// do nothing
	}

}
