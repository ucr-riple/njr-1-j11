/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rpg.item.*;

/**
 * @author Mathias
 *
 */
public class WeaponTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	Weapon weapon1, weapon2, weapon3;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		weapon1 = new Weapon(new Weight(500, WeightUnit.G), 21, 50);
		weapon2 = new Weapon(new Weight(600, WeightUnit.G), 7, 200);
		weapon3 = new Weapon(new Weight(400, WeightUnit.G), 35, 50);
		
	}

	

	/**
	 * Test method for {@link rpg.item.Weapon#canHaveAsValue(int)}.
	 */
	@Test
	public void testCanHaveAsValue() {
		assertTrue(weapon1.canHaveAsValue(100));
		assertFalse(weapon1.canHaveAsValue(201));
		assertFalse(weapon1.canHaveAsValue(-1));
	}
	
	/**
	 * Test method for {@link rpg.item.Weapon#canHaveAsValue(int)}.
	 */
	@Test
	public void testGetValue() {
		assertEquals(Weapon.getDamageValueFactor()*weapon1.getDamage(), weapon1.getValue(), 0.001);
		Weapon.setUseFormula(false);
		assertEquals(50, weapon1.getValue());
		
	}


	/**
	 * Test method for {@link rpg.item.Weapon#isValidMaxDamage(int)}.
	 */
	@Test
	public void testIsValidMaxDamage() {
		assertFalse(Weapon.isValidMaxDamage(0));
		assertTrue(Weapon.isValidMaxDamage(500));
	}

	/**
	 * Test method for {@link rpg.item.Weapon#hasValidMaxDamage()}.
	 */
	@Test
	public void testHasValidMaxDamage() {
		assertTrue(weapon1.hasValidMaxDamage());
		assertTrue(weapon2.hasValidMaxDamage());
		assertTrue(weapon3.hasValidMaxDamage());
	}

	

	/**
	 * Test method for {@link rpg.item.Weapon#isValidDamage(int)}.
	 */
	@Test
	public void testIsValidDamage() {
		assertTrue(Weapon.isValidDamage(21));
		assertFalse(Weapon.isValidDamage(Weapon.getMaxDamage()+1));
		assertFalse(Weapon.isValidDamage(0));
		assertFalse(Weapon.isValidDamage(33));
		
	}

	/**
	 * Test method for {@link rpg.item.Weapon#hasValidDamage()}.
	 */
	@Test
	public void testHasValidDamage() {
		assertTrue(weapon1.hasValidDamage());
		assertTrue(weapon2.hasValidDamage());
		assertTrue(weapon3.hasValidDamage());
	}


}
