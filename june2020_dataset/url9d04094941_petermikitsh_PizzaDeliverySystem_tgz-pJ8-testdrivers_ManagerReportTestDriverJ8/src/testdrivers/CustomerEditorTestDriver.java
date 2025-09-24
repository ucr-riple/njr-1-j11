package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Address;
import customereditor.CustomerEditorController;
import customereditor.CustomerEditorView;
import customereditor.CustomerEditorViewCL;
import customereditor.CustomerEditorViewGUI;

public class CustomerEditorTestDriver {

	public static void main( String[] args ) {

		// Add the default addresses.
		Address.getDb().add( new Address( "RIT", 18 ) );
		Address.getDb().add( new Address( "University of Rochester", 12 ) );
		Address.getDb().add( new Address( "Nazareth College", 25 ) );
		Address.getDb().add( new Address( "St. John Fisher College", 21 ) );
		Address.getDb().add( new Address( "Roberts Wesleyan College", 25 ) );
		Address.getDb().add( new Address( "Monroe Community College", 18 ) );

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
		CustomerEditorController cont = new CustomerEditorController();
		CustomerEditorView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new CustomerEditorViewGUI() :
					new CustomerEditorViewCL();
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();

	}

}
