package risiko;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import risiko.actions.AddPlayer;
import risiko.actions.SetTroops;
import risiko.actions.StartGame;
import risiko.actions.actionFactory;
import risiko.actions.actionPackage;
import risiko.board.Board;
import risiko.board.boardPackage;
import risiko.gamestate.CountryState;
import risiko.gamestate.GameState;
import risiko.gamestate.Player;
import risiko.gamestate.State;
import risiko.gamestate.stateFactory;
import risiko.gamestate.statePackage;

public class ExecutorTest {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		ResourceSet ress = initializeResourceSet();
		File boardFile = new File("examples/default.board");
		// File stateFile = new File("examples/default.state");
		actionFactory af = actionFactory.eINSTANCE;
		stateFactory sf = stateFactory.eINSTANCE;
		Resource boardRes = ress.createResource(URI
				.createURI("http://Risiko.board"));
		// Resource stateRes = ress.createResource(URI
		// .createURI("http://Risiko.state"));
		Resource resultRes = ress.createResource(URI.createURI("test"));
		boardRes.load(new FileInputStream(boardFile), null);

		State state = sf.createState();
		// state.setState(GameState.ACCEPTING_PLAYERS);

		// stateRes.load(new FileInputStream(stateFile), null);
		ActionExecutor executor = new ActionExecutor((Board) boardRes
				.getContents().get(0), state);

		AddPlayer addPlayers = af.createAddPlayer();
		Player p1 = sf.createPlayer();
		p1.setName("felix");
		Player p2 = sf.createPlayer();
		p2.setName("Jana");
		Player p3 = sf.createPlayer();
		p3.setName("Matthias");
		addPlayers.getPlayers().add(p1);
		addPlayers.getPlayers().add(p2);
		addPlayers.getPlayers().add(p3);

		StartGame startGame = af.createStartGame();

		SetTroops setTroops = af.createSetTroops();

		executor.execute(addPlayers);
		State result = executor.execute(startGame);
		Random random = new Random();
		while (result.getState() != GameState.PLAY) {
			setTroops.setTroops(result.getTroopsToSet());
			assert result.getTroopsToSet() == 1;
			assert !result.getTurn().equals(setTroops.getPlayer());
			setTroops.setPlayer(result.getTurn());
			List<CountryState> countries = result.getTurn().getOwnedCountries();
			setTroops.setCountry(countries
					.get(random.nextInt(countries.size())).getCountry());
			state = executor.execute(setTroops);
		}

		resultRes.getContents().add(result);
		resultRes.save(System.out, null);
	}

	protected static ResourceSet initializeResourceSet() {
		// Create a resource set to hold the resources.
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the appropriate resource factory to handle all file
		// extensions.
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		// Register the board package to ensure it is available during loading.
		resourceSet.getPackageRegistry().put(boardPackage.eNS_URI,
				boardPackage.eINSTANCE);

		// Register the state package to ensure it is available during loading.
		resourceSet.getPackageRegistry().put(statePackage.eNS_URI,
				statePackage.eINSTANCE);

		// Register the action package to ensure it is available during loading.
		resourceSet.getPackageRegistry().put(actionPackage.eNS_URI,
				actionPackage.eINSTANCE);

		return resourceSet;
	}

}
