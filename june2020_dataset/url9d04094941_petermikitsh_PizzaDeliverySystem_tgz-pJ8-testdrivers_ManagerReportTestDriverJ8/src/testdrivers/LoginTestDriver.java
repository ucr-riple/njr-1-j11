package testdrivers;

import javax.swing.UIManager;

import login.LoginController;
import login.LoginView;
import login.LoginViewCL;
import login.LoginViewGUI;
import main.PDSViewManager;
import main.PizzaDeliverySystem;

public class LoginTestDriver {

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
		LoginController cont = new LoginController();
		LoginView view = PizzaDeliverySystem.RUN_WITH_GUI ?
								new LoginViewGUI() :
								new LoginViewCL();
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();
		
	}
	
}
