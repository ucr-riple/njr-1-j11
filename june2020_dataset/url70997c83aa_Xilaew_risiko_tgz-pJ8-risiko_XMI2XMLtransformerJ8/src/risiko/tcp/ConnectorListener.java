package risiko.tcp;

import java.nio.channels.SelectionKey;

import org.eclipse.emf.ecore.EObject;

public interface ConnectorListener {

	public void handleIncomming(EObject object, SelectionKey key);

	public void handleOutgoing(EObject object, SelectionKey key);

}
