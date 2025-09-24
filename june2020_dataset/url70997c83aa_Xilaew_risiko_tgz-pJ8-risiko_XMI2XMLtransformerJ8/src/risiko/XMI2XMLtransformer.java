package risiko;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import risiko.actions.actionPackage;
import risiko.board.boardPackage;
import risiko.gamestate.statePackage;

public class XMI2XMLtransformer {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ResourceSet xmlResSet = initializeResourceSet(new XMLResourceFactoryImpl());
		ResourceSet xmiResSet = initializeResourceSet(new XMIResourceFactoryImpl());
		Resource boardIn = xmiResSet.createResource(URI.createURI("boardIn"));
		Resource boardOut = xmlResSet.createResource(URI.createURI("boardOut"));
		boardIn.load(new FileInputStream("examples/default.board"),null);
		boardOut.getContents().add(boardIn.getContents().get(0));
		boardOut.save(System.out, null);
	}
	
	protected static ResourceSet initializeResourceSet(Factory resFactory) {
		// Create a resource set to hold the resources.
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the appropriate resource factory to handle all file
		// extensions.
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						resFactory);

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
