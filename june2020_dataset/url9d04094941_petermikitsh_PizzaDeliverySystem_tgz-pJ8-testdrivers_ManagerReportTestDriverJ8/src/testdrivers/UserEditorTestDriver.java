package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import usereditor.UserEditorController;
import usereditor.UserEditorView;
import usereditor.UserEditorViewGUI;

public class UserEditorTestDriver {

	public static void main( String[] args ) {

		// Initialize the system.
		PizzaDeliverySystem.initialize();
		
		// Set the LaF.
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} catch( Exception e ) {}

		
		// Initialize the PDSViewManager.
		PDSViewManager.setWindowVisible( true );
		
		// Create and pass control to a new UserEditorController
		// and View.
		UserEditorController cont = new UserEditorController();
		UserEditorView view = PizzaDeliverySystem.RUN_WITH_GUI ?
								new UserEditorViewGUI() : null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();

	}

}
