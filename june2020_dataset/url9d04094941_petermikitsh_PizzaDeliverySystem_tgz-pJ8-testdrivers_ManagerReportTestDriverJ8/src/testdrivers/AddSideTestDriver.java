package testdrivers;

import java.util.HashMap;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.FoodItem;
import model.SideFoodItem;
import neworder.addside.AddSideController;
import neworder.addside.AddSideView;
import neworder.addside.AddSideViewGUI;

public class AddSideTestDriver {

	public static void main( String[] args ) {

		// Initialize the system.
		PizzaDeliverySystem.initialize();

		// Set the LaF.
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} catch( Exception e ) {}

		// Create the quantity map.
		HashMap<SideFoodItem, Integer> map = new HashMap<SideFoodItem, Integer>();
		for( FoodItem sfi : SideFoodItem.getDb().list() ) {
			map.put( (SideFoodItem)sfi, 0 );
		}
		
		// Initialize the PDSViewManager.
		PDSViewManager.setWindowVisible( true );

		// Create and pass control to a new NewOrderController
		// and View.
		AddSideController cont = new AddSideController( map );
		AddSideView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new AddSideViewGUI() : null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();

	}

}
