/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.creature.Anchor;
import rpg.creature.Monster;
import rpg.item.Dukat;
import rpg.item.Item;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Frederic
 *
 */
public class MonsterTest {

	Monster monster1;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		monster1 = new Monster("Destroyer o'Hope", 101, 77, 10, new Dukat(), new Dukat());
	}

	/**
	 * Test method for {@link rpg.creature.Monster#getCapacity()}.
	 */
	@Test
	public void testGetCapacity() {
		assertTrue(new Weight(9, WeightUnit.KG).compareTo(monster1.getCapacity()) == 0); //
	}

	/**
	 * Test method for {@link rpg.creature.Monster#canHaveAsName(java.lang.String)}.
	 */
	@Test
	public void testCanHaveAsName() {
		assertTrue(monster1.canHaveAsName("Abc   ' ' ' ' "));
		assertFalse(monster1.canHaveAsName("abc   ' ' ' ' "));
		assertFalse(monster1.canHaveAsName("Ab%"));
	}
	/**
	 * Test method for {@link rpg.creature.Monster#treasureContains(rpg.item.Item)}.
	 */
	@Test
	public void testTreasureContains() {
		monster1.terminate();
		ArrayList<Item> anchorItems = new ArrayList<Item>();
		for(Anchor anchor: monster1.getAnchors())
			anchorItems.add(anchor.getItem());
		assertTrue(monster1.getTreasure().containsAll(anchorItems));
	}

	/**
	 * Test method for {@link rpg.creature.Monster#Monster(java.lang.String, int, int, int, rpg.item.Item[])}.
	 */
	@Test
	public void testMonster() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		itemList.add(new Dukat());
		itemList.add(new Dukat());
		itemList.add(new Weapon(new Weight(100, WeightUnit.G), 17));
		Monster monster2 = new Monster("Abc", 101, 77, 10, itemList.get(0), itemList.get(1), itemList.get(2));
		ArrayList<Item> monsterItems = new ArrayList<Item>();
		
		for(Anchor anchor: monster2.getAnchors())
			monsterItems.add(anchor.getItem());
		for(Item item: itemList)
			assertTrue(monsterItems.contains(item));
		
	}

}
