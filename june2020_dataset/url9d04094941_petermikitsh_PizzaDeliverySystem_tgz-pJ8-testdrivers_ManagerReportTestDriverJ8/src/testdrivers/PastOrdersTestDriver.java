package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import pastorders.PastOrdersController;
import pastorders.PastOrdersView;
import pastorders.PastOrdersViewGUI;

public class PastOrdersTestDriver {
	
	
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
		PastOrdersController cont = new PastOrdersController();
		PastOrdersView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new PastOrdersViewGUI() : null;
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				
	}

}
