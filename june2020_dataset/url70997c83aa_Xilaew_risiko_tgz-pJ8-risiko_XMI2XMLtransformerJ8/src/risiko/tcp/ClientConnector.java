package risiko.tcp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import risiko.GameMonitor;
import risiko.actions.actionPackage;
import risiko.board.boardPackage;
import risiko.gamestate.statePackage;

public class ClientConnector implements Runnable, TcpListener {

	protected List<ConnectorListener> listeners = new LinkedList<ConnectorListener>();
	protected GameMonitor game;
	protected ClientTcp tcp;

	public ClientConnector() {
		game = new GameMonitor();
		tcp = new ClientTcp();
	}

	public void registerListener(ConnectorListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void deregisterListener(ConnectorListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public List<ConnectorListener> getListeners() {
		return Collections.unmodifiableList(listeners);
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

	public GameMonitor getGame() {
		return game;
	}

	public void setGame(GameMonitor game) {
		this.game = game;
	}

	public boolean send(EObject object, SelectionKey key) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		game.serialize(object, out);
		tcp.send(out.toString(), key);
		return true;
	}

	@Override
	public void run() {
		tcp.registerListener(this);
		tcp.run();
	}

	@Override
	public void handleIncomming(String message, SelectionKey key) {
		ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
		try {
			List<EObject> objects = game.parseAndHandle(in);
			synchronized (listeners){
				for (ConnectorListener l : listeners){
					for (EObject o : objects){
						l.handleIncomming(o, key);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handleOutgoing(String message, SelectionKey key) {
		// Do nothing.
	}
}
