package risiko;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import risiko.actions.Action;
import risiko.actions.actionPackage;
import risiko.board.Board;
import risiko.board.boardPackage;
import risiko.gamestate.GameState;
import risiko.gamestate.State;
import risiko.gamestate.stateFactory;
import risiko.gamestate.statePackage;

public class Engine {

	private Board board;
	private State state;

	private final ResourceSet resourceSet;
	private final Resource boardResource;
	private final Resource stateResource;
	private final Resource actionResource;
	private final Resource inputResource;

	private ActionExecutor executor;

	/**
	 * Initializes an empty engine. To start playing you will need to set a
	 * Board and initialize a State. The state can be initialized either by
	 * executing AddPlayerActions and an StartGameAction or by calling
	 * setState() initializing this engine to a previously saved state.
	 */
	public Engine() {
		this.resourceSet = initializeResourceSet();
		boardResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.board"));
		stateResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.state"));
		actionResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.action"));
		inputResource = resourceSet.createResource(URI.createURI("input"));
		this.state = stateFactory.eINSTANCE.createState();
		state.setState(GameState.ACCEPTING_PLAYERS);
		stateResource.getContents().add(this.state);
	}

	private void validate(EObject object) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(object);
		if (diagnostic.getSeverity() != Diagnostic.OK) {
			printDiagnostic(diagnostic, "");
			throw new RuntimeException("The " + object + "is not Valid!");
		}
	}

	/**
	 * <!-- begin-user-doc --> Prints diagnostics with indentation. <!--
	 * end-user-doc -->
	 * 
	 * @param diagnostic
	 *            the diagnostic to print.
	 * @param indent
	 *            the indentation for printing.
	 * @generated
	 */
	protected static void printDiagnostic(Diagnostic diagnostic, String indent) {
		System.out.print(indent);
		System.out.println(diagnostic.getMessage());
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(child, indent + "  ");
		}
	}

	protected ResourceSet initializeResourceSet() {
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

	/**
	 * Returns a resourceSet that can be used to load further Objects, e.g.
	 * Actions or a complete GameStates while having access to the board via the
	 * URL "http:///Risiko.board" and access to the current state with the URL
	 * "http:///Risiko.state".
	 * 
	 * @return ResourceSet having access to all objects known to this engine.
	 */
	public ResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/**
	 * Executes the Actions provided in the InputStream and writes the new Game
	 * State to the provided OutputStream.
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public void executeAction(InputStream in, OutputStream out)
			throws IOException {
		actionResource.load(in, null);
		for (EObject o : actionResource.getContents()) {
			Action action = (Action) o;
			validate(action);
			if (executor == null) {
				this.executor = ExecutorFactory.getExecutor(this.board,
						this.state);
			}
			if (executor.execute(action) != null) {
				stateResource.save(out, null);
			}
		}
		actionResource.unload();
	}

	/**
	 * writes the board used for this game into an OutputStream.
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void getBoard(OutputStream out) throws IOException {
		boardResource.save(out, null);
	}

	/**
	 * Reads a Board from a given InputStream.
	 * 
	 * The Board can only be set before the game has started.
	 * 
	 * @param in
	 * @throws IOException
	 * @throws RuntimeException
	 *             if the game has already begun.
	 */
	public void setBoard(InputStream in) throws IOException, RuntimeException {
		if (this.state.getState() != GameState.ACCEPTING_PLAYERS) {
			throw new RuntimeException(
					"You may not change the board during the game!");
		}
		inputResource.load(in, null);
		Board board = (Board) inputResource.getContents().get(0);
		validate(board);
		this.board = board;
		boardResource.getContents().clear();
		boardResource.getContents().add(this.board);
		inputResource.unload();
	}

	/**
	 * writes the current state of this Risiko game into an OutputStream.
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void getState(OutputStream out) throws IOException {
		stateResource.save(out, null);
	}

	/**
	 * Reads the current state of this Risiko game from a given InputStream.
	 * 
	 * @param in
	 * @throws IOException
	 */
	public void setState(InputStream in) throws IOException {
		inputResource.load(in, null);
		State state = (State) inputResource.getContents().get(0);
		validate(state);
		this.state = state;
		stateResource.getContents().clear();
		stateResource.getContents().add(this.state);
		inputResource.unload();
	}
}
