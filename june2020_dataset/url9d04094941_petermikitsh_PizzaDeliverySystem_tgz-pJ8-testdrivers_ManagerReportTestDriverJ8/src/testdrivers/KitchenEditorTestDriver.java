package testdrivers;

import javax.swing.UIManager;

import kitcheneditor.KitchenEditorController;
import kitcheneditor.KitchenEditorView;
import kitcheneditor.KitchenEditorViewGUI;
import main.PDSViewManager;
import main.PizzaDeliverySystem;


public class KitchenEditorTestDriver {


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
		KitchenEditorController cont = new KitchenEditorController();
		KitchenEditorView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new KitchenEditorViewGUI() : null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();

	}

}
