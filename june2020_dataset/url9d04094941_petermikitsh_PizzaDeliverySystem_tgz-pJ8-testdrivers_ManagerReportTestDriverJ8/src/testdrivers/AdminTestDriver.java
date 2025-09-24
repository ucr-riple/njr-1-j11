package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import ninja.Kitchen;
import actionmenu.admin.AdminController;
import actionmenu.admin.AdminView;
import actionmenu.admin.AdminViewGUI;


public class AdminTestDriver {
	
	
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
				AdminController cont = new AdminController();
				AdminView view = PizzaDeliverySystem.RUN_WITH_GUI ?
						new AdminViewGUI() : null;
						cont.setView(view);
						view.setController(cont);
						cont.enterInitialState();
						
	}

}
