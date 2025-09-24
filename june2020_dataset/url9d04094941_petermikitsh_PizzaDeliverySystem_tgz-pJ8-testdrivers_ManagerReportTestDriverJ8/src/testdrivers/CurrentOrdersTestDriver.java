package testdrivers;

import javax.swing.UIManager;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Address;
import model.Customer;
import model.Order;
import model.PizzaFoodItem;
import model.PizzaFoodItem.PizzaSize;
import model.SideFoodItem;
import ninja.Kitchen;
import currentorders.CurrentOrdersController;
import currentorders.CurrentOrdersView;
import currentorders.CurrentOrdersViewGUI;

public class CurrentOrdersTestDriver {
	
	
	public static void main( String[] args ) {
		
		// Initialize the system.
		PizzaDeliverySystem.initialize();
		
		Customer cus1 = new Customer( "Drew Zemke", 
				"8453997878",
				Address.getAddressForAlias("rit") );
		Customer cus2 =  new Customer( "Jenny",
				"5858675309",
				Address.getAddressForAlias("naz") );
		
		// Create an initial order so there is something in the kitchen.
		Order order1 = new Order();
		order1.setCustomer( cus1 );
	
		PizzaFoodItem item4 = new PizzaFoodItem("Large Pizza", 16.00, 15, 20, 4 );
		item4.setSize( PizzaSize.PizzaSizeLarge );
		SideFoodItem item7 = new SideFoodItem("Pizza Log", 6.00, 0, 10, 1 );
		SideFoodItem item8 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0 );
	
		order1.addFoodItem(item4);
		order1.addFoodItem(item7);
		order1.addFoodItem(item8);
		
		Kitchen.addOrder( order1 );
		
		
		Order order2 = new Order();
		order2.setCustomer( cus2 );
		
		PizzaFoodItem item5 = new PizzaFoodItem("Small Pizza", 8.00, 8, 13, 1 );
		item5.setSize( PizzaSize.PizzaSizeSmall);
		SideFoodItem item6 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0);

		order2.addFoodItem(item5);
		order2.addFoodItem(item6);
		
		Kitchen.addOrder( order2 );
		
		// Set the LaF.
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} catch( Exception e ) {}


		// Initialize the PDSViewManager.
		PDSViewManager.setWindowVisible( true );

		// Create and pass control to a new NewOrderController
		// and View.
		CurrentOrdersController cont = new CurrentOrdersController();
		CurrentOrdersView view = PizzaDeliverySystem.RUN_WITH_GUI ?
				new CurrentOrdersViewGUI() : null;
		cont.setView(view);
		view.setController(cont);
		cont.enterInitialState();
		
		
		/* OLD TEST DRIVER
		
		// Add the default addresses.
		Address.getDb().add( new Address( "Rochester Institute of Technology", 18 ) );
		Address.getDb().add( new Address( "University of Rochester", 12 ) );
		Address.getDb().add( new Address( "Nazareth College", 25 ) );
		Address.getDb().add( new Address( "St. John Fisher College", 21 ) );
		Address.getDb().add( new Address( "Roberts Wesleyan College", 25 ) );
		Address.getDb().add( new Address( "Monroe Community College", 18 ) );
		

		// Add some defaults to the customer database.
		Customer cus1 = new Customer( "Drew Zemke", 
									"8453997878",
									Address.getAddressForAlias("rit") );
		Customer cus2 =  new Customer( "Jenny",
									"5858675309",
									Address.getAddressForAlias("naz") );
		Customer cus3 = new Customer( "Santa Claus",
									"1234567890",
									new Address("The North Pole", 2000) );
		Customer.getDb().add( cus1 );
		Customer.getDb().add( cus2 );
		Customer.getDb().add( cus3 );
		
		// Add some defaults to the orders database.
		SideFoodItem item1 = new SideFoodItem("Pizza Log", 6.00, 0, 10, 1, true);
		SideFoodItem item2 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0, true);
		PizzaFoodItem item3 = new PizzaFoodItem("Medium Pizza", 11.00, 10, 15, 2, true);
		
		Order order1 = new Order();
		order1.addFoodItem(item1);
		order1.addFoodItem(item2);
		order1.addFoodItem(item3);
		
		PizzaFoodItem item4 = new PizzaFoodItem("Large Pizza", 16.00, 15, 20, 4, true);
		PizzaFoodItem item5 = new PizzaFoodItem("Small Pizza", 8.00, 8, 13, 1, true);
		SideFoodItem item6 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0, true);	
		
		Order order2 = new Order();
		order2.addFoodItem(item4);
		order2.addFoodItem(item5);
		order2.addFoodItem(item6);
		
		SideFoodItem item7 = new SideFoodItem("Pizza Log", 6.00, 0, 10, 1, true);
		SideFoodItem item8 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0, true);
		Order order3 = new Order();
		order3.addFoodItem(item7);
		order3.addFoodItem(item8);
		
		order1.setStatus( OrderStatus.AwaitingPreparation);
		order2.setStatus( OrderStatus.Cooking );
		order3.setStatus( OrderStatus.OnRoute );
		
		order1.setCustomer( cus1 );
		order2.setCustomer( cus2 );
		order3.setCustomer( cus3 );
		
		// Create a new controller and view ...
		CurrentOrdersController cont = new CurrentOrdersController();
		CurrentOrdersViewCL view = new CurrentOrdersViewCL();
		
		// ... connect the view and controller ...
		cont.setView(view);
		view.setController(cont);
		
		
		// ... and tell the controller to enter its first state.
		cont.enterInitialState();
		*/
		
	}

}
