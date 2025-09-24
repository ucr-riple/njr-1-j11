/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.creature.Anchor;
import rpg.creature.Creature;
import rpg.creature.Hero;
import rpg.item.Armor;
import rpg.item.Dukat;
import rpg.item.Item;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Frederic
 *
 */
public class HeroTest {

	Hero hero1;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hero1 = new Hero("James o'Hara", 103, new Dukat());
	}

	/**
	 * Test method for {@link rpg.creature.Hero#getCapacity()}.
	 */
	@Test
	public void testGetCapacity() {
		hero1.divideStrength(100);
		Creature.setStrengthPrecision(0);
		// hero1.getStrength() == 0
		assertTrue(hero1.getCapacity().compareTo(new Weight(0, WeightUnit.KG)) == 0);
		hero1.multiplyStrength(100); // hero1.getStrength() == 10.0
		assertTrue(hero1.getCapacity().compareTo(new Weight(100, WeightUnit.KG)) == 0);
		hero1.divideStrength(10); // hero1.getStrength() == 1.0
		hero1.multiplyStrength(11); // hero1.getStrength() == 11.0
		assertTrue(hero1.getCapacity().compareTo(new Weight(115, WeightUnit.KG)) == 0);
		hero1.divideStrength(11); // hero1.getStrength() == 1.0
		hero1.multiplyStrength(25); // hero1.getStrength() == 11.0
		assertTrue(hero1.getCapacity().compareTo(new Weight(800, WeightUnit.KG)) == 0);
	}

	/**
	 * Test method for {@link rpg.creature.Hero#getProtection()}.
	 */
	@Test
	public void testGetProtection() {
		Armor armor = new Armor(2, new Weight(30, WeightUnit.KG), 500, 30, 40);
		hero1.getAnchor("body").swap(armor);
		assertEquals(Hero.getStandardProtection() + armor.getProtection(), hero1.getProtection());
	}
	/**
	 * Test method for {@link rpg.creature.Hero#treasureContains(rpg.item.Item)}.
	 */
	@Test
	public void testTreasureContains() {
		hero1.terminate();
		ArrayList<Item> anchorItems = new ArrayList<Item>();
		for(Anchor anchor: hero1.getAnchors())
			anchorItems.add(anchor.getItem());
		assertTrue(hero1.getTreasure().containsAll(anchorItems)); // TODO in hero?
	}
	/**
	 * Test method for {@link rpg.creature.Hero#canHaveAsName(java.lang.String)}.
	 */
	@Test
	public void testCanHaveAsName() {
		assertTrue(hero1.canHaveAsName("Abc ' '"));
		assertFalse(hero1.canHaveAsName("Abc:a"));
		assertFalse(hero1.canHaveAsName("abca"));
		assertTrue(hero1.canHaveAsName("Abc: a"));
		assertFalse(hero1.canHaveAsName("Abc ' ' '"));
	}
	/**
	 * Test method for {@link rpg.creature.Hero#Hero(java.lang.String, int, rpg.item.Item[])}.
	 */
	@Test
	public void testHero() {
		Dukat dukat1 = new Dukat();
		Dukat dukat2 = new Dukat();
		Hero hero2 = new Hero("Abc", 103, dukat1, dukat2);
		hero2.getAnchorAt(3).containsDirectItem(dukat1);
		hero2.getAnchorAt(4).containsDirectItem(dukat2);
	}

	/**
	 * Test method for {@link rpg.creature.Hero#isValidStandardProtection(int)}.
	 */
	@Test
	public void testIsValidStandardProtection() {
		assertTrue(Hero.isValidStandardProtection(0));
		assertTrue(Hero.isValidStandardProtection(1));
		assertTrue(Hero.isValidStandardProtection(1000));
		assertFalse(Hero.isValidStandardProtection(-1));
		assertFalse(Hero.isValidStandardProtection(-1000));
	}
	/**
	 * Test method for {@link rpg.creature.Hero#getTotalStrength()}.
	 */
	@Test
	public void testGetTotalStrength() {
		hero1.getAnchor("rightHand").removeItem();
		double firstStr = hero1.getTotalStrength();
		hero1.getAnchor("rightHand").addItem(new Weapon(new Weight(500, WeightUnit.G), 13));
		assertEquals(firstStr + 13, hero1.getTotalStrength(), 0.000001);
	}

}
