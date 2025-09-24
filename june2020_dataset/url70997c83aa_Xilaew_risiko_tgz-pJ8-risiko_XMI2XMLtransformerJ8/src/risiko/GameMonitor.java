package risiko;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import risiko.gamestate.State;
import risiko.gamestate.statePackage;

public class GameMonitor {
	private static final Logger LOG = Logger.getLogger(GameMonitor.class
			.getName());

	private Board board;
	private State state;
	private List<Action> actions;

	private final ResourceSet resourceSet;
	private final Resource boardResource;
	private final Resource stateResource;
	private final Resource actionResource;
	private final Resource inputResource;

	/**
	 * Initializes an empty monitor. To start playing you will need to set a
	 * Board and initialize a State. The state can be initialized either by
	 * executing AddPlayerActions and an StartGameAction or by calling
	 * setState() initializing this engine to a previously saved state.
	 */
	public GameMonitor() {
		this.resourceSet = initializeResourceSet();
		boardResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.board"));
		stateResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.state"));
		actionResource = resourceSet.createResource(URI
				.createURI("http:///Risiko.action"));
		inputResource = resourceSet.createResource(URI.createURI("input"));
		// this.state = stateFactory.eINSTANCE.createState();
		// state.setState(GameState.ACCEPTING_PLAYERS);
		// stateResource.getContents().add(this.state);
	}

	public void validate(EObject object) {
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
	 * @deprecated use parseAndHandle instead
	 * @param in
	 * @return number of successfully read Actions
	 * @throws IOException
	 */
	public int addActions(InputStream in) throws IOException {
		inputResource.load(in, null);
		int i = 0;
		for (EObject o : inputResource.getContents()) {
			i++;
			this.addAction((Action) o);
		}
		return i;
	}

	public void addAction(Action action) {
		validate(action);
		actions.add(action);
		actionResource.getContents().add(action);
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
	 * @deprecated use parseAndHandle instead to read stuff from an input stream
	 * @param in
	 * @throws IOException
	 */
	public void setBoard(InputStream in) throws IOException {
		inputResource.load(in, null);
		Board board = (Board) inputResource.getContents().get(0);
		this.setBoard(board);
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
	 * @deprecated use parseAndHandle instead
	 * @param in
	 * @throws IOException
	 */
	public void setState(InputStream in) throws IOException {
		inputResource.load(in, null);
		State state = (State) inputResource.getContents().get(0);
		this.setState(state);
		inputResource.unload();
	}

	public List<EObject> parseAndHandle(InputStream in) throws IOException {
		inputResource.load(in, null);
		LinkedList<EObject> result = new LinkedList<EObject>();
		while (!inputResource.getContents().isEmpty()) {
			EObject o = inputResource.getContents().get(0);
			if (o instanceof Board) {
				this.setBoard((Board) o);
			} else if (o instanceof Action) {
				this.addAction((Action) o);
			} else if (o instanceof State) {
				this.setState((State) o);
			}
			result.add(o);
		}
		inputResource.unload();
		return result;
	}

	public void serialize(EObject in, OutputStream out) {
		inputResource.getContents().add(in);
		try {
			inputResource.save(out, null);
		} catch (IOException e) {
			LOG.log(Level.WARNING, "failed serialize object.", e);
		}
		inputResource.unload();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		validate(board);
		this.board = board;
		this.boardResource.getContents().clear();
		this.boardResource.getContents().add(board);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		// XXX validation commented out for debugging
		// validate(state);
		this.state = state;
		stateResource.getContents().clear();
		stateResource.getContents().add(this.state);
	}

	public Resource getBoardResource() {
		return boardResource;
	}

	public Resource getStateResource() {
		return stateResource;
	}

	public Resource getActionResource() {
		return actionResource;
	}

	public Resource getInputResource() {
		return inputResource;
	}

	public List<Action> getActions() {
		return Collections.unmodifiableList(actions);
	}
}
