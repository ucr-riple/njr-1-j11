package testdrivers;

import java.util.ArrayList;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.FoodItem;
import model.Topping;
import neworder.addpizza.AddPizzaController;
import neworder.addpizza.AddPizzaView;
import neworder.addpizza.AddPizzaViewCL;
import neworder.addpizza.AddPizzaViewGUI;



public class AddPizzaTestDriver {
	
	
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
		
		Topping bacon = new Topping("Bacon",Topping.ToppingLocation.ToppingLocationWhole,1.0);
		Topping.getDb().add(bacon);
		// Create a new controller and view ...
		AddPizzaController cont = new AddPizzaController( new ArrayList<FoodItem>() );
		AddPizzaView view = (AddPizzaView) (PizzaDeliverySystem.RUN_WITH_GUI ?
				new AddPizzaViewGUI() :
					new AddPizzaViewCL());
		
		// ... connect the view and controller ...
		cont.setView(view);
		view.setController(cont);
		
		// ... tell the controller to enter its first state ...
		cont.enterInitialState();
		
		// ... and initiate the input loop in the view.  This last step is
		// only necessary for the CL view.
		//view.getUserInput();
		
		
		
		cont.getPizzas();
		
		
	}

}
