package rpg.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.item.Weight;
import rpg.item.WeightUnit;

/**
 * @author Mathias
 *
 */
public class WeightTest {

	public Weight kg1000, g100, pounds500;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		kg1000 = new Weight(1000, WeightUnit.KG);
		g100 = new Weight(100, WeightUnit.G);
		pounds500 = new Weight(500, WeightUnit.POUND);
	}

	
	/**
	 * Test method for {@link rpg.item.Weight#Weight(double, rpg.item.WeightUnit)}.
	 */
	@Test
	public void testWeight() {
		assertEquals(1000.0,kg1000.getNumeral(), 0.0);
		assertEquals(WeightUnit.KG, kg1000.getUnit());
	}

	/**
	 * Test method for {@link rpg.item.Weight#compareTo(rpg.item.Weight)}.
	 */
	@Test
	public void testCompareTo() {
		assertEquals(0, kg1000.compareTo(null));
		assertTrue( kg1000.compareTo(g100) > 0 );
		assertTrue( kg1000.compareTo(pounds500) > 0 );
	}

	/**
	 * Test method for {@link rpg.item.Weight#toUnit(rpg.item.WeightUnit)}.
	 */
	@Test
	public void testToUnit() {
		Weight g100tokg = g100.toUnit(WeightUnit.KG);
		Weight g100topounds = g100.toUnit(WeightUnit.POUND);
		assertEquals(0.1, g100tokg.getNumeral(), 0.0);
		assertEquals(0.220462262, g100topounds.getNumeral(), 0.00001);
	}

	/**
	 * Test method for {@link rpg.item.Weight#add(rpg.item.Weight)}.
	 */
	@Test
	public void testAdd() {
		Weight kg1000plusg100 = kg1000.add(g100);
		assertEquals(1000.1, kg1000plusg100.getNumeral(), 0.0);
		assertEquals(WeightUnit.KG, kg1000plusg100.getUnit());
	}
	
	/**
	 * Test method for {@link rpg.item.Weight#subtract(rpg.item.Weight)}.
	 */
	@Test
	public void testsubtract() {
		Weight kg1000minusg100 = kg1000.subtract(g100);
		assertEquals(999.9, kg1000minusg100.getNumeral(), 0.0);
		assertEquals(WeightUnit.KG, kg1000minusg100.getUnit());
	}

	/**
	 * Test method for {@link rpg.item.Weight#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("1000.0 KG", kg1000.toString());
	}

	/**
	 * Test method for {@link rpg.item.Weight#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Weight pounds500test = new Weight(500, WeightUnit.POUND);
		assertTrue(pounds500test.equals(pounds500));
	}

}
