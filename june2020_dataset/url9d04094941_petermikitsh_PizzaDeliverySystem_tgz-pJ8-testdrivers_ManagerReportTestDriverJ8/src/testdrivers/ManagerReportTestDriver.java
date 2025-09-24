package testdrivers;

import main.PizzaDeliverySystem;
import managerreport.ManagerReportController;
import managerreport.ManagerReportViewCL;
import model.Order;
import model.Order.OrderStatus;
import model.PizzaFoodItem;
import model.SideFoodItem;

public class ManagerReportTestDriver {
	
	
	public static void main( String[] args ) {
		
		
		// Initialize the system.
		PizzaDeliverySystem.initialize();

		
		// Add some defaults to the orders database.
		
		
		SideFoodItem item1 = new SideFoodItem("Pizza Log", 6.00, 0, 10, 1 );
		SideFoodItem item2 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0);
		PizzaFoodItem item3 = new PizzaFoodItem("Medium Pizza", 11.00, 10, 15, 2);
		
		Order order1 = new Order();
		order1.addFoodItem(item1);
		order1.addFoodItem(item2);
		order1.addFoodItem(item3);
		
		PizzaFoodItem item4 = new PizzaFoodItem("Large Pizza", 16.00, 15, 20, 4);
		PizzaFoodItem item5 = new PizzaFoodItem("Small Pizza", 8.00, 8, 13, 1);
		SideFoodItem item6 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0);	
		
		Order order2 = new Order();
		order2.addFoodItem(item4);
		order2.addFoodItem(item5);
		order2.addFoodItem(item6);
		
		SideFoodItem item7 = new SideFoodItem("Pizza Log", 6.00, 0, 10, 1);
		SideFoodItem item8 = new SideFoodItem("Tossed Salad", 5.00, 5, 0, 0);
		Order order3 = new Order();
		order3.addFoodItem(item7);
		order3.addFoodItem(item8);
		
		for (int i = 0; i < 1000000000*5; i++);
		
		order1.setStatus( OrderStatus.Delivered );
		order2.setStatus( OrderStatus.Delivered );
		order3.setStatus( OrderStatus.Delivered  );
		
		Order.getDb().add(order1);
		Order.getDb().add(order2);
		Order.getDb().add(order3);
		
		// Create a new controller and view ...
		ManagerReportController cont = new ManagerReportController();
		ManagerReportViewCL view = new ManagerReportViewCL();
		
		// ... connect the view and controller ...
		cont.setView(view);
		view.setController(cont);
		
		
		// ... tell the controller to enter its first state ...
		cont.enterInitialState();
		
		// ... and initiate the input loop in the view.  This last step is
		// only necessary for the CL view.
		view.getUserInput();
		
	}

}
