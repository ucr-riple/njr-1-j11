package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import neworder.NewOrderController;
import neworder.NewOrderView;
import neworder.NewOrderViewGUI;

public class NewOrderTestDriver {
	
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
		NewOrderController cont = new NewOrderController();
		NewOrderView view = PizzaDeliverySystem.RUN_WITH_GUI ?
								new NewOrderViewGUI() : null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();
		
	}

}
