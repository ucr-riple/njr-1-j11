/**
 * 
 */
package rpg.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import rpg.creature.Anchor;
import rpg.creature.Creature;
import rpg.creature.Hero;
import rpg.creature.Monster;
import rpg.item.Dukat;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Frederic
 *
 */
public class CreatureTest {

	Creature hero1, monster1;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hero1 = new Hero("James o'Hara", 103, new Dukat());
		monster1 = new Monster("Destroyer o'Hope", 101, 77, 10, new Dukat(), new Dukat());
	}
	
	/**
	 * Test method for {@link rpg.creature.Creature#getStrength()}.
	 */
	@Test
	public void testGetStrength() {
		hero1.divideStrength(3); // strength == 10/3
		Creature.setStrengthPrecision(1);
		assertEquals(3.3, hero1.getStrength(), 0.000001);
		Creature.setStrengthPrecision(2);
		assertEquals(3.33, hero1.getStrength(), 0.000001);
		Creature.setStrengthPrecision(10);
		assertEquals(3.3333333333, hero1.getStrength(), 0.000000000001);
		Creature.setStrengthPrecision(0);
		assertEquals(3, hero1.getStrength(), 0.000001);
		Creature.setStrengthPrecision(2);
	}

	/**
	 * Test method for {@link rpg.creature.Creature#multiplyStrength(int)}.
	 */
	@Test
	public void testMultiplyStrength() {
		double prevStr;
		prevStr = hero1.getStrength();
		hero1.multiplyStrength(50);
		assertEquals(prevStr*50, hero1.getStrength(), 0.000001);
		prevStr = hero1.getStrength();
		hero1.multiplyStrength(3);
		assertEquals(prevStr*3, hero1.getStrength(), 0.000001);
		
	}

	/**
	 * Test method for {@link rpg.creature.Creature#divideStrength(int)}.
	 */
	@Test
	public void testDivideStrength() {
		double prevStr;
		prevStr = hero1.getStrength();
		hero1.divideStrength(10);
		assertEquals(prevStr/10, hero1.getStrength(), 0.000001);
		prevStr = hero1.getStrength();
		hero1.divideStrength(5);
		assertEquals(prevStr/5, hero1.getStrength(), 0.000001);
	}
	/**
	 * Test method for {@link rpg.creature.Creature#getTotalWeight()}.
	 */
	@Test
	public void testGetTotalWeight() {
		Weight firstWeight = hero1.getTotalWeight();
		hero1.getAnchor("rightHand").addItem(new Dukat());
		assertTrue(hero1.getTotalWeight().compareTo(firstWeight.add(new Dukat().getWeight())) == 0);
	}

	/**
	 * Test method for {@link rpg.creature.Creature#getTotalValue()}.
	 */
	@Test
	public void testGetTotalValue() {
		int firstTotalValue = hero1.getTotalValue();
		hero1.getAnchor("rightHand").addItem(new Dukat());
		assertEquals(firstTotalValue + new Dukat().getValue(), hero1.getTotalValue());
		hero1.getAnchor("rightHand").removeItem();
		assertEquals(firstTotalValue, hero1.getTotalValue());
		
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hasValidName()}.
	 */
	@Test
	public void testHasValidName() {
		assertTrue(monster1.hasValidName());
		assertTrue(hero1.hasValidName());
	}

	/**
	 * Test method for {@link rpg.creature.Creature#canHaveAsHitpoints(int)}.
	 */
	@Test
	public void testCanHaveAsHitpoints() {
		assertTrue(hero1.canHaveAsHitpoints(103));
		assertFalse(hero1.canHaveAsHitpoints(100));
		assertFalse(hero1.canHaveAsHitpoints(1));
		assertFalse(hero1.canHaveAsHitpoints(-5));
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hasValidHitpoints()}.
	 */
	@Test
	public void testHasValidHitpoints() {
		assertTrue(hero1.hasValidHitpoints());
		assertTrue(monster1.hasValidHitpoints());
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hit(rpg.creature.Creature)}.
	 */
	@Test
	public void testHit() {
		hero1.hit(monster1);
	}

	

	/**
	 * Test method for {@link rpg.creature.Creature#weaken(int)}.
	 */
	@Test
	public void testWeaken() {
		int firstHP = hero1.getHitpoints();
		hero1.weaken(100);
		assertEquals(firstHP - 100, hero1.getHitpoints());
		hero1.weaken(3);
		assertTrue(hero1.isTerminated());
	}

	/**
	 * Test method for {@link rpg.creature.Creature#isValidMaximumHitpoints(int)}.
	 */
	@Test
	public void testIsValidMaximumHitpoints() {
		assertTrue(Creature.isValidMaximumHitpoints(103));
		assertTrue(Creature.isValidMaximumHitpoints(101));
		assertTrue(Creature.isValidMaximumHitpoints(7));
		assertFalse(Creature.isValidMaximumHitpoints(0));
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hasValidMaximumHitpoints()}.
	 */
	@Test
	public void testHasValidMaximumHitpoints() {
		assertTrue(hero1.hasValidMaximumHitpoints());
		assertTrue(monster1.hasValidMaximumHitpoints());
	}

	/**
	 * Test method for {@link rpg.creature.Creature#canAddItem(rpg.item.Item)}.
	 */
	@Test
	public void testCanAddItem() {
		Weapon heavyWeapon = new Weapon(new Weight(10000, WeightUnit.KG), 11);
		assertFalse(hero1.canAddItem(heavyWeapon));
		assertTrue(hero1.canAddItem(new Dukat()));
	}

	/**
	 * Test method for {@link rpg.creature.Creature#canHaveAsAnchor(rpg.creature.Anchor)}.
	 */
	@Test
	public void testCanHaveAsAnchor() {
		assertTrue(hero1.canHaveAsAnchor(new Anchor(hero1, "someAnchor")));
		
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hasProperAnchors()}.
	 */
	@Test
	public void testHasProperAnchors() {
		assertTrue(hero1.hasProperAnchors());
		assertTrue(monster1.hasProperAnchors());
	}

	/**
	 * Test method for {@link rpg.creature.Creature#hasAsAnchor(rpg.creature.Anchor)}.
	 */
	@Test
	public void testHasAsAnchor() {
		Anchor someAnchor = new Anchor(hero1, "someAnchor");
		assertTrue(hero1.hasAsAnchor(someAnchor));
	}
	/**
	 * Test method for {@link rpg.creature.Creature#terminate()}.
	 */
	@Test
	public void testTerminate() {

		assertFalse(hero1.isTerminated());
		assertFalse(monster1.isTerminated());
		hero1.terminate();
		monster1.terminate();
		assertTrue(hero1.isTerminated());
		assertTrue(monster1.isTerminated());
	}

}
