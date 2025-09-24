package testdrivers;

import java.util.ArrayList;
import java.util.Scanner;

import ninja.Cook;
import ninja.DeliveryRunner;
import ninja.Driver;
import ninja.Kitchen;
import ninja.OvenRunner;
import ninja.SystemTime;

import junit.framework.TestCase;
import model.Address;
import model.Configuration;
import model.Customer;
import model.FoodItem;
import model.Order;
import model.PizzaFoodItem;
import model.Topping;
import model.Topping.ToppingLocation;
import model.database.Database;

public class KitchenTest extends TestCase {
	
	public void setUp() {
	}
	
	public KitchenTest(String name) {
		super(name);
	}
	
	public final void testOpenKitchen() {
		int numCooks = 1;
		int numDrivers = 1;
		int numOvens = 1;
		Configuration ovens = new Configuration("numOvens", ""+ numOvens);
		Configuration drivers = new Configuration("numDrivers", "" + numDrivers);
		Configuration cooks = new Configuration("numCooks", "" + numCooks);
		
		Database<Configuration> db = Configuration.getDb();
		db.add(ovens);
		db.add(drivers);
		db.add(cooks);
		
		SystemTime.initialize();
		Kitchen.openKitchen();
		
		assertEquals(Kitchen.getDrivers().size(), numDrivers );
		assertEquals(Kitchen.getOvens().size(), numOvens );
		assertEquals(Kitchen.getCooks().size(), numCooks );
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Kitchen.closeKitchen();
		SystemTime.stopTime();
	}
	
	public final void testNinjasRunning() {
		int numCooks = 1;
		int numDrivers = 1;
		int numOvens = 1;
		Configuration ovensConfig = new Configuration("numOvens", ""+ numOvens);
		Configuration driversConfig = new Configuration("numDrivers", "" + numDrivers);
		Configuration cooksConfig = new Configuration("numCooks", "" + numCooks);
		
		Database<Configuration> db = Configuration.getDb();
		db.add(ovensConfig);
		db.add(driversConfig);
		db.add(cooksConfig);
		
		SystemTime.initialize();
		Kitchen.openKitchen();
		
		ArrayList<Driver> drivers = Kitchen.getDrivers();
		ArrayList<Cook> cooks = Kitchen.getCooks();
		DeliveryRunner drun = Kitchen.getDeliveryRunner();
		OvenRunner orun = Kitchen.getOvenRunner();
		
		for (Driver driver : drivers) {
			assertTrue(driver.isAlive());
		}
		
		for (Cook cook : cooks) {
			assertTrue(cook.isAlive());
		}
		
		assertTrue(orun.isAlive());
		assertTrue(drun.isAlive());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Kitchen.closeKitchen();
		
		for (Driver driver : drivers) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			assertFalse(driver.isAlive());
		}
		
		for (Cook cook : cooks) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertFalse(cook.isAlive());
		}
		
		assertFalse(orun.isAlive());
		assertFalse(orun.isAlive());
		
		SystemTime.stopTime();
	}
	
	public final void testAddOrder() {
		
		int numCooks = 1;
		int numDrivers = 3;
		int numOvens = 1;
		
		Configuration ovensConfig = new Configuration("numOvens", ""+ numOvens);
		Configuration driversConfig = new Configuration("numDrivers", "" + numDrivers);
		Configuration cooksConfig = new Configuration("numCooks", "" + numCooks);
		
		Database<Configuration> db = Configuration.getDb();
		db.add(ovensConfig);
		db.add(driversConfig);
		db.add(cooksConfig);
		
		SystemTime.initialize();
		Kitchen.openKitchen();
		
		
		int  numorders= 3;
		
		Address[]addresses = new Address[numorders];
		addresses[0] = new Address("RIT", 10);
		addresses[1] = new Address("UofR", 10);
		addresses[2] = new Address("Yo Mamma's House", 10);
//		addresses[3] = new Address("Nazareth", 20);
//		addresses[4] = new Address("RIT", 20);
//		addresses[5] = new Address("UofR", 20);
//		addresses[6] = new Address("RIT", 20);
//		addresses[7] = new Address("UofR", 20);
//		addresses[8] = new Address("UofR", 20);
//		addresses[9] = new Address("Nazareth", 20);
//		addresses[10] = new Address("UofR", 20);
//		addresses[11] = new Address("RIT", 20);
//		addresses[12] = new Address("Nazareth", 20);
//		addresses[13] = new Address("UofR", 20);
//		addresses[14] = new Address("RIT", 20);
//		addresses[15] = new Address("Nazareth", 20);
//		addresses[16] = new Address("UofR", 20);
//		addresses[17] = new Address("RIT", 20);
//		addresses[18] = new Address("Nazareth", 20);
//		addresses[19] = new Address("UofR", 20);
//		addresses[20] = new Address("RIT", 20);
//		addresses[21] = new Address("Nazareth", 20);

		for (int i = 0; i < numorders; i++) {
			Customer myCustomer = new Customer("Isioma", "2020202202", addresses[i]);
			
			ArrayList<Topping> list = new ArrayList<Topping>();
			list.add(new Topping("Pepperoni", ToppingLocation.ToppingLocationRight, 2.0));
			PizzaFoodItem pizza1= new PizzaFoodItem("Pizza 1", 8.0, list, PizzaFoodItem.PizzaSize.PizzaSizeLarge, 5, 5, 5 );
			list.add(new Topping("Ham", ToppingLocation.ToppingLocationLeft, 3.5));
			pizza1.setToppings(list);
			
			ArrayList<FoodItem> orderFI = new ArrayList<FoodItem>();
			orderFI.add(pizza1);
			
			Order newOrder = new Order(orderFI, myCustomer);
			Kitchen.addOrder(newOrder);
		}
		
		int sz = Kitchen.getOrders().size();
		System.out.println(sz);
		assertEquals(sz,numorders);
		
		(new Scanner(System.in)).next();
		
		Kitchen.closeKitchen();		
		SystemTime.stopTime();
		
		assertTrue(true);
		
	}
	

}
