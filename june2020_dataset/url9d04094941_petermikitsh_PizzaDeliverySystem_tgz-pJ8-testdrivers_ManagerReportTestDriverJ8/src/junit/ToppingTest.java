package junit;

import junit.framework.TestCase;
import model.Topping;

public class ToppingTest extends TestCase {

	// The following are unimplemented test cases for constructors, getters,
	// and setters.
	//
	//public final void testTopping() {
	//	fail("Not yet implemented");
	//}
	//
	//public final void testToppingStringToppingLocationDouble() {
	//	fail("Not yet implemented");
	//}
	//
	//public final void testGetLocation() {
	//	fail("Not yet implemented");
	//}
	//
	//public final void testSetLocation() {
	//	fail("Not yet implemented");
	//}

	public final void testCalculatePrice() {
		new Topping();
		Topping t1 = new Topping("Pepperoni", Topping.ToppingLocation.ToppingLocationLeft, 4);
		assertEquals("t1 has value of 2.0", 2.0, t1.calculatePrice());
		Topping t2 = new Topping("Mushroom", Topping.ToppingLocation.ToppingLocationRight, 7);
		assertEquals("t1 has value of 3.5", 3.5, t2.calculatePrice());
		Topping t3 = new Topping("Ham", Topping.ToppingLocation.ToppingLocationWhole, 5);
		assertEquals("t1 has value of 5.0", 5.0, t3.calculatePrice());
		t3.setLocation(Topping.ToppingLocation.ToppingLocationLeft);
		assertEquals("t3 is now left.", Topping.ToppingLocation.ToppingLocationLeft, t3.getLocation());
	}

}
