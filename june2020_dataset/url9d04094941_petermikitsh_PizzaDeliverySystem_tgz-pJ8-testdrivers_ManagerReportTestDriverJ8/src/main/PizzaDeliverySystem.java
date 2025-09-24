package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.UIManager;

import login.LoginController;
import login.LoginView;
import login.LoginViewCL;
import login.LoginViewGUI;
import model.Configuration;
import model.User;
import ninja.Kitchen;
import ninja.SystemTime;
import ninja.SystemTime.SystemTimeScale;

/**
 * Main class that executes the Pizza Delivery System simulation.
 */
public class PizzaDeliverySystem implements WindowListener {
	
	/**
	 * Indicates if the system should be run in GUI mode.  False means CL mode.
	 */
	public static final boolean RUN_WITH_GUI = true;
	
	/**
	 * Store the user that is currently logged in.
	 */
	public static User currentUser;
	
	/**
	 * Initializes the simulation by instantiating time.
	 */
	public static void initialize() {
		
		// Tell System time to initialize with its stored value, or use the default.
		SystemTimeScale timeScale = SystemTime.DEFAULT_TIMESCALE;
		
		Configuration tsConfig = Configuration.getDb().get( SystemTime.TIMESCALE_CONFIG_NAME.hashCode() );
		if( tsConfig != null ) {
			timeScale = SystemTimeScale.parseTimeScale( tsConfig.getValue() );
		}
		 
		SystemTime.initialize( timeScale );
		
		// Open the kitchen.
		Kitchen.openKitchen();
		
	}
	
	/**
	 * Executes the simulation.
	 * 
	 * @param args   not used
	 */
	public static void main(String[]args) {
		
		// Print a hello in command line mode.
		if( !PizzaDeliverySystem.RUN_WITH_GUI ) {
			printWelcome();
		}

		// Initialize the time and itchen.
		initialize();
		
		// Create a new controller and view
		LoginController cont = new LoginController();
		
		// Initialize the view based on whether or not we're in GUI mode.
		LoginView view;
		if( !PizzaDeliverySystem.RUN_WITH_GUI ) {
			
			view = new LoginViewCL();
		
		} else {

			// First, set the look and feel to the cross-platform one.
			try {
				UIManager.setLookAndFeel(
						UIManager.getCrossPlatformLookAndFeelClassName());
			} catch( Exception e ) {}

			// If this is in GUI mode, show the main window.
			view = new LoginViewGUI();
			PDSViewManager.setWindowVisible( true );
			PDSViewManager.addNewWindowListener( new PizzaDeliverySystem() );
			
		}

		// Connect the view and controller
		cont.setView(view);
		view.setController(cont);

		// Tell the controller to enter its first state
		cont.enterInitialState();
		
		// Print a goodbye for commandline mode.
		if( !PizzaDeliverySystem.RUN_WITH_GUI ) {
			printLoggingOut();
		}
		
	}
	
	/**
	 * Prints a welcome message at the start of the program.
	 */
	private static void printWelcome() {
		
		System.out.println();
		System.out.println( "               welcome to              " );
		System.out.println( " ===================================== " );
		System.out.println( "        CARIBOU SALE MANAGEMENT        " );
		System.out.println( " ===================================== ");
		System.out.println();
		System.out.println( "         a software solution by 	    " );
		System.out.println( "             TEAM CARIBOU              " );
		System.out.println();
		
	}
	
	/**
	 * Prints a logging out message.
	 */
	private static void printLoggingOut() {
		
		System.out.println( "Logging out..." );
		System.out.println( "Bye!" );
		System.out.println();
		
	}

	/**
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {}

	/**
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {}
	
	/**
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {

		// Shut down the kitchen system.
		Kitchen.closeKitchen();
		SystemTime.stopTime();

	}

	/**
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {}
	
	/**
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {}
	
	/**
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {}

	/**
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {}

}
