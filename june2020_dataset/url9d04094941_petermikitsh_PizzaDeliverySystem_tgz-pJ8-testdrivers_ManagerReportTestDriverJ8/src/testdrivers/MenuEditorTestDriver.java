package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import menueditor.MenuEditorController;
import menueditor.MenuEditorView;
import menueditor.MenuEditorViewGUI;

public class MenuEditorTestDriver {

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
		
		// Create and pass control to a new NewOrderController
		// and View.
		MenuEditorController cont = new MenuEditorController();
		MenuEditorView view = PizzaDeliverySystem.RUN_WITH_GUI ?
								new MenuEditorViewGUI() :
								null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();

	}

}
