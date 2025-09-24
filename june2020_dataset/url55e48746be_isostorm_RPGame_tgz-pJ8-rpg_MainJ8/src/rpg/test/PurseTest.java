package rpg.test;

import static org.junit.Assert.*;

import java.util.Enumeration;

import rpg.exception.IllegalAddItemException;
import rpg.item.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	Purse purse1, purse2;
	@Before
	public void setUp() throws Exception {
		purse1 = new Purse(new Weight(50, WeightUnit.G), new Weight(1, WeightUnit.KG), 10);
		purse2 = new Purse(new Weight(50, WeightUnit.G), new Weight(40, WeightUnit.G));
	}

	@Test
	public void testCanHaveAsId() {
		assertTrue(purse1.canHaveAsId(10946));
		assertFalse(purse1.canHaveAsId(33));
		assertTrue(purse1.canHaveAsId(5));
	}


	@Test
	public void testPurse() {
		Enumeration<Item> dukats = purse1.getItems();
		int i = 0;
		while(dukats.hasMoreElements()){
			i++;
			dukats.nextElement();
		}
		assertEquals(10, i);
		assertEquals(10, purse1.getValue());
	}

	@Test
	public void testAddDukat() {
		Dukat dukat = new Dukat();
		purse1.addDukat(dukat);
		assertTrue(purse1.containsDirectItem(dukat));
		purse2.addDukat(dukat);
		assertTrue(purse2.isTerminated());
	}
	
	@Test(expected=IllegalAddItemException.class)
	public void testAddPurse_overflow() {
		purse2.addPurse(purse1);
	}
	
	@Test
	public void testAddPurse() {
		purse1.addPurse(purse2);
		Enumeration<Item> dukats = purse1.getItems();
		
		while(dukats.hasMoreElements()){
			assertTrue(purse1.containsDirectItem(dukats.nextElement()));
		}
	}

}
