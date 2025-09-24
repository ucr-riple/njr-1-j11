/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.creature.Anchor;
import rpg.creature.Hero;
import rpg.exception.IllegalAddItemException;
import rpg.item.Dukat;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Frederic
 *
 */
public class AnchorTest {
	
	Anchor a1, a2;
	Hero hero1, hero2;
	Weapon weapon1, weapon2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		hero1 = new Hero("James o'Hara", 103);
		hero2 = new Hero("Abc", 103);
		a1 = new Anchor(hero1, "a1");
		a2 = new Anchor(hero2, "a2");
		weapon1 = new Weapon(new Weight(1, WeightUnit.KG), 13);
		weapon2 = new Weapon(new Weight(1, WeightUnit.KG), 13);
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#Anchor(rpg.creature.Creature, java.lang.String, rpg.item.Item)}.
	 */
	@Test
	public void testAnchorCreatureStringItem() {
		Dukat d = new Dukat();
		Anchor b = new Anchor(hero1, "abc",d);
		assertEquals(d, hero1.getAnchor("abc").getItem());
		assertEquals(hero1, b.getHolder());
		assertEquals("abc", b.getName());
		assertTrue(hero1.hasAsAnchor(b));
		assertEquals(d, b.getItem());
		
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#canHaveAsHolder(rpg.creature.Creature)}.
	 */
	@Test
	public void testCanHaveAsHolder() {
		assertTrue(a1.canHaveAsHolder(hero1));
		hero1.terminate();
		assertFalse(a1.canHaveAsHolder(hero1));
	}
	/**
	 * Test method for {@link rpg.creature.Anchor#addItem(rpg.item.Item)}.
	 */
	@Test 
	public void testAddItem() {
		a1.removeItem();
		Dukat d = new Dukat();
		a1.addItem(d);
		a1.containsDirectItem(d);
		
	}
	/**
	 * Test method for {@link rpg.creature.Anchor#addItem(rpg.item.Item)}.
	 */
	@Test (expected=IllegalAddItemException.class) 
	public void testAddItemIllegalAddItemException() {
		new Anchor(hero1, weapon1);
		a1.addItem(weapon1);
		
		
	}
	/**
	 * Test method for {@link rpg.creature.Anchor#canAddItem(rpg.item.Item)}.
	 */
	@Test
	public void testCanAddItem() {
		assertFalse(a1.canAddItem(null));
		a1.addItem(weapon2);
		assertFalse(a2.canAddItem(weapon2));
		assertFalse(a1.canAddItem(weapon1));
		a1.removeItem();
		assertTrue(a2.canAddItem(weapon2));
		assertTrue(a1.canAddItem(weapon1));
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#swap(rpg.item.Item)}.
	 */
	@Test
	public void testSwap() {
		a1.swap(weapon1);
		assertEquals(weapon1, a1.getItem());
		a1.swap(weapon2);
		assertEquals(weapon2, a1.getItem());
		assertEquals(a1, weapon2.getParent());
		assertTrue(weapon1.getParent() != a1);
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#removeItem()}.
	 */
	@Test
	public void testRemoveItem() {
		a1.addItem(new Dukat());
		assertNotNull(a1.getItem());
		a1.removeItem();
		assertNull(a1.getItem());
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#containsDirectItem(rpg.item.Item)}.
	 */
	@Test
	public void testContainsDirectItem() {
		assertFalse(a1.containsDirectItem(weapon2));
		a1.addItem(weapon2);
		assertTrue(a1.containsDirectItem(weapon2));
	}

	/**
	 * Test method for {@link rpg.creature.Anchor#removeDirectItem(rpg.item.Item)}.
	 */
	@Test
	public void testRemoveDirectItem() {
		a1.addItem(weapon1);
		assertTrue(a1.containsDirectItem(weapon1));
		a1.removeDirectItem(weapon1);
		assertFalse(a1.containsDirectItem(weapon1));
	}

}
