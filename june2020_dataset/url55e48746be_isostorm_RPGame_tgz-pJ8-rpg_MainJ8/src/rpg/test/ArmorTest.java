/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;
import rpg.item.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias, Frederic
 *
 */
public class ArmorTest {

	Armor armor1, armor2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		armor1 = new Armor(3, new Weight(50, WeightUnit.G), 500, 50, 60);
	}

	/**
	 * Test method for {@link rpg.item.Armor#Armor(long, rpg.item.Weight, int, int, int)}.
	 */
	@Test
	public void testArmor() {
		armor2 = new Armor(8, new Weight(50, WeightUnit.G), 500, 50, 60);
		assertEquals(2, armor2.getId());
	}

	/**
	 * Test method for {@link rpg.item.Armor#canHaveAsId(long)}.
	 */
	@Test
	public void testCanHaveAsId() {
		assertTrue(armor1.canHaveAsId(5));
		assertFalse(armor1.canHaveAsId(10));
		assertFalse(armor1.canHaveAsId(-5));
	}

	/**
	 * Test method for {@link rpg.item.Armor#isValidMaxValue(int)}.
	 */
	@Test
	public void testIsValidMaxValue() {
		assertTrue(Armor.isValidMaxValue(500));
		assertFalse(Armor.isValidMaxValue(-1));
	}

	/**
	 * Test method for {@link rpg.item.Armor#isValidMaxProtection(int)}.
	 */
	@Test
	public void testIsValidMaxProtection() {
		assertFalse(Armor.isValidMaxProtection(0));
		assertTrue(Armor.isValidMaxProtection(50));
		assertFalse(Armor.isValidMaxProtection(101));
	}

	/**
	 * Test method for {@link rpg.item.Armor#canHaveAsProtection(int)}.
	 */
	@Test
	public void testCanHaveAsProtection() {
		assertTrue(armor1.canHaveAsProtection(40));
		assertFalse(armor1.canHaveAsProtection(armor1.getMaxProtection()+1));
	}

	public void testGetValue()
	{
		assertEquals((int)((double)armor1.getMaxValue() *
				((double)armor1.getProtection()/(double)armor1.getMaxProtection())),
				armor1.getValue());
	}

}
