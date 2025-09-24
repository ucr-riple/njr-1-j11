/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rpg.exception.IllegalAddItemException;
import rpg.item.BackPack;
import rpg.item.Dukat;
import rpg.item.Item;
import rpg.item.Purse;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Mathias
 *
 */
public class BackPackTest {
	static BackPack firstBackPack, secondBackPack;
	BackPack backpack1, backpack2, backpack3;
	@BeforeClass
	public static void setUpBeforeClass()
	{
		firstBackPack = new BackPack(new Weight(500, WeightUnit.G),
				new Weight(100, WeightUnit.KG), 20);
		secondBackPack = new BackPack(new Weight(500, WeightUnit.G),
				new Weight(100, WeightUnit.KG), 20);
	}
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		backpack1 = new BackPack(new Weight(500, WeightUnit.G), new Weight(100, WeightUnit.KG), 20);
		backpack2 = new BackPack(new Weight(500, WeightUnit.G), new Weight(100, WeightUnit.KG), 300);
		backpack3 = new BackPack(new Weight(500, WeightUnit.G), new Weight(100, WeightUnit.KG), 500);
	}
	
	/**
	 * Test method for {@link rpg.item.BackPack#generateId()}.
	 */
	@Test
	public void testGenerateId() {
		assertEquals(2, firstBackPack.getId());
		assertEquals(4, secondBackPack.getId());
	}
	
	/**
	 * Test method for {@link rpg.item.BackPack#canHaveAsValue(int)}.
	 */
	@Test
	public void testCanHaveAsValue() {
		assertFalse(backpack1.canHaveAsValue(501));
		assertTrue(backpack1.canHaveAsValue(50));
	}

	/**
	 * Test method for {@link rpg.item.BackPack#getTotalWeight()}.
	 */
	@Test
	public void testGetTotalWeight() {
		Weapon weapon1 = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack2.addItem(weapon1);
		backpack1.addItem(backpack2);
		assertTrue(backpack1.getTotalWeight().
				compareTo(backpack1.getWeight().
						add(backpack2.getWeight()).
						add(weapon1.getWeight())) == 0);
	}

	/**
	 * Test method for {@link rpg.item.BackPack#getTotalValue()}.
	 */
	@Test
	public void testGetTotalValue() {
		Weapon weapon1 = new Weapon(new Weight(800, WeightUnit.G), 20);
		Purse  purse = new Purse(new Weight(50, WeightUnit.G),new Weight(50, WeightUnit.G), 45);
		backpack2.addItem(weapon1);
		backpack1.addItem(backpack2);
		backpack1.addItem(purse);
		
		
		assertEquals(backpack1.getTotalValue(),backpack1.getValue() 
												+ backpack2.getValue() 
												+ weapon1.getValue()
												+purse.getValue());
	}

	/**
	 * Test method for {@link rpg.item.BackPack#addItem(rpg.item.Item)}.
	 */
	@Test
	public void testAddItem_LegalCase() {
		Weapon item = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack1.addItem(item);
		assertTrue(backpack1.containsDirectItem(item));
	}
	
	
	/**
	 * Test method for {@link rpg.item.BackPack#addItem(rpg.item.Item)}.
	 */
	@Test(expected=IllegalAddItemException.class)
	public void testAddItem_IllegalCase() {
		Weapon item = new Weapon(new Weight(800, WeightUnit.KG), 20);
		backpack1.addItem(item);
		
	}

	/**
	 * Test method for {@link rpg.item.BackPack#containsDirectItem(rpg.item.Item)}.
	 */
	@Test
	public void testContainsDirectItem() {
		Weapon item = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack1.addItem(item);
		assertTrue(backpack1.containsDirectItem(item));
		assertFalse(backpack1.containsDirectItem(backpack3));
	}

	/**
	 * Test method for {@link rpg.item.BackPack#removeItem(rpg.item.Item)}.
	 */
	@Test
	public void testRemoveItem() {
		Weapon item = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack1.addItem(item);
		backpack1.removeDirectItem(item);
		assertFalse(backpack1.containsDirectItem(item));
	}

	/**
	 * Test method for {@link rpg.item.BackPack#getDirectItems()}.
	 */
	@Test
	public void testGetDirectItems() {
		Weapon weapon1 = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack2.addItem(weapon1);
		backpack1.addItem(backpack2);
		ArrayList<Item> items = backpack1.getDirectItems();
		assertTrue(items.contains(backpack2));
		assertFalse(items.contains(weapon1));
	}

	/**
	 * Test method for {@link rpg.item.BackPack#getItems()}.
	 */
	@Test
	public void testGetItems() {
		Weapon weapon1 = new Weapon(new Weight(800, WeightUnit.G), 20);
		backpack2.addItem(backpack3);
		backpack3.addItem(weapon1);
		Dukat dukat1, dukat2;
		dukat1 = new Dukat();
		dukat2 = new Dukat();
		backpack2.addItem(dukat1);
		backpack3.addItem(dukat2);
		backpack1.addItem(backpack2);
		Enumeration<Item> enumeration = backpack1.getItems();
		ArrayList<Item> allItems = new ArrayList<Item>();
		
		while(enumeration.hasMoreElements())
			allItems.add(enumeration.nextElement());
		
		assertTrue(allItems.contains(weapon1));
		assertTrue(allItems.contains(backpack2));
		assertTrue(allItems.contains(dukat1));
		assertTrue(allItems.contains(dukat2));
	}
	
	/**
	 * Test method for {@link rpg.item.BackPack#equalsOrIsDirectOrIndirectParentOf()}.
	 */
	@Test
	public void testEqualsOrIsDirectOrIndirectParentOf() {
		backpack1.addItem(backpack2);
		backpack3.addItem(backpack1);
		assertTrue(backpack1.equalsOrIsDirectOrIndirectParentOf(backpack2));
		assertTrue(backpack1.equalsOrIsDirectOrIndirectParentOf(backpack1));
		assertFalse(backpack2.equalsOrIsDirectOrIndirectParentOf(backpack1));
		assertTrue(backpack3.equalsOrIsDirectOrIndirectParentOf(backpack2));
	}


}
