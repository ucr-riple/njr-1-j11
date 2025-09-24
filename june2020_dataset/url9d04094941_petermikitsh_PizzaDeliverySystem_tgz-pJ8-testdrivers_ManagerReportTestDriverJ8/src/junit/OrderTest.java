/*
 * OrderTest.java
 */

package junit;

import java.util.ArrayList;

import junit.framework.TestCase;
import model.Address;
import model.Customer;
import model.FoodItem;
import model.Order;
import model.SideFoodItem;

public class OrderTest extends TestCase {
	
	public final void testCalculateCost() {
		
		SideFoodItem side1 = new SideFoodItem();
		side1.setPrice(5.0);
		
		SideFoodItem side2 = new SideFoodItem();
		side1.setPrice(12.0);
		
		ArrayList<FoodItem> myItems = new ArrayList<FoodItem>();
		
		myItems.add(side1);
		myItems.add(side2);
		
		Customer customer = new Customer();
		
		Order newOrder = new Order(myItems, customer);
		
		assertTrue(newOrder.calculateSubtotal() == 17.0);
		
	}

	public final void testAddFoodItem() {
		
		Order newOrder = new Order(new ArrayList<FoodItem>(), new Customer());
		assertTrue(newOrder.getFoodItems().isEmpty());
		newOrder.addFoodItem(new SideFoodItem());
		assertTrue(newOrder.getFoodItems().size() == 1);
		
	}

	public final void testRemoveFoodItem() {
		
		Order newOrder = new Order(new ArrayList<FoodItem>(), new Customer());
		SideFoodItem food = new SideFoodItem();
		newOrder.addFoodItem(food);
		newOrder.removeFoodItem(food);
		assertTrue(newOrder.getFoodItems().isEmpty());
		
	}

	public final void testGetPreparationTime() {
		
		Order newOrder = new Order(new ArrayList<FoodItem>(), new Customer());
		SideFoodItem mySide = new SideFoodItem();
		mySide.setPrepTime(1);
		newOrder.addFoodItem(mySide);
		assertTrue(newOrder.getPreparationTime() == 1);
		
	}
	
	public final void testGetCookingTime() {
		
		Order newOrder = new Order(new ArrayList<FoodItem>(), new Customer());
		SideFoodItem mySide = new SideFoodItem();
		mySide.setCookTime(1);
		newOrder.addFoodItem(mySide);
		assertTrue(newOrder.getCookingTime() == 1);
		
	}
	
	public final void testCalculateEstimatedDeliveryTime() {
		
		Customer myCustomer = new Customer();
		Address myAddress = new Address();
		myAddress.setTimeToLocation(20);
		myCustomer.setStreetAddress(myAddress);
		Order newOrder = new Order(new ArrayList<FoodItem>(), myCustomer);
		SideFoodItem mySide = new SideFoodItem();
		mySide.setCookTime(5);
		mySide.setPrepTime(2);
		newOrder.addFoodItem(mySide);
		assertTrue(newOrder.calculateEstimatedDeliveryWaitTime() == 27);
		
	}

	public final void testCalculateOvenSpaceUnitsRequired() {
		
		Order newOrder = new Order(new ArrayList<FoodItem>(), new Customer());
		SideFoodItem side1 = new SideFoodItem();
		SideFoodItem side2 = new SideFoodItem();
		side1.setOvenSpaceUnits(50);
		side2.setOvenSpaceUnits(15);
		newOrder.addFoodItem(side1);
		newOrder.addFoodItem(side2);
		assertTrue(newOrder.calculateOvenSpaceUnitsRequired() == 65);
		
	}

}
