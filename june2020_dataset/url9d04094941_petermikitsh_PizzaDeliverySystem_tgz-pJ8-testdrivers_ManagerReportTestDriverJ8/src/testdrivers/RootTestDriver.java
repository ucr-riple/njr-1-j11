package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import ninja.Kitchen;
import actionmenu.RootController;
import actionmenu.RootView;
import actionmenu.RootViewGUI;

public class RootTestDriver {

	public static void main( String[] args ) {

		// Initialize the system.
		PizzaDeliverySystem.initialize();

		// Open the kitchen.
		Kitchen.openKitchen();

		// Set the LaF.
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} catch( Exception e ) {}


		// Initialize the PDSViewManager.
		PDSViewManager.setWindowVisible( true );

		// Create and pass control to a new NewOrderController
		// and View.
		RootController cont = new RootController();
		RootView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new RootViewGUI() : null;
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();

	}

}
