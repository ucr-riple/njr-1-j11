package assignments.farmer.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import assignments.farmer.Farmer;
import assignments.farmer.FarmerFactory;
import assignments.farmer.SeasonState;

import assignments.farmer.farmerfactories.AmishFarmerFactory;
import assignments.farmer.seasonstates.FallState;
import assignments.farmer.seasonstates.SummerState;

public class AmishFarmerTests {

	ByteArrayOutputStream printlnContent = new ByteArrayOutputStream();
	Farmer f;
	FarmerFactory factory = new AmishFarmerFactory();
	
	SeasonState summerState = new SummerState();
	SeasonState fallState = new FallState();

	@Before
	public void setup() {
	    System.setOut(new PrintStream(printlnContent));
	    f = factory.create("");
	}
	
	@After
	public void resetOutputToNormal(){
	    System.setOut(System.out);			
	}
	
	
	//Spring Tests
	@Test
	public void testAmishSpringPlow() {
		f.plow();
		assertEquals("Plow corn fields.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSpringPlant() {
		f.plant();
		assertEquals("Peas, lettuce, oats.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSpringWeedControl() {
		f.weedControl();
		assertEquals("Walking cultivator in garden.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSpringHarvest() {
		f.harvest();
		assertEquals("Wife and kids help out.\r\n", printlnContent.toString());
	}
	
	@Test
	public void testAmishSpringMarket() {
		f.market();
		assertEquals("Jams, jellies, peas & lettuce to auction.\r\n", printlnContent.toString());
	}

	
	
	//Summer Tests
	@Test
	public void testAmishSummerPlow() {
		f.setSeasonState(summerState);
		f.plow();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSummerPlant() {
		f.setSeasonState(summerState);
		f.plant();
		assertEquals("Beans, squash, tomatoes, beets, carrots.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSummerWeedControl() {
		f.setSeasonState(summerState);
		f.weedControl();
		assertEquals("Walking cultivator in garden, two-row, horse-drawn cultivator in fields.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSummerHarvest() {
		f.setSeasonState(summerState);
		f.harvest();
		assertEquals("Wife and kids help out in garden; neighbors help with oats and hay.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishSummerMarket() {
		f.setSeasonState(summerState);
		f.market();
		assertEquals("Peas, carrots, early beans, roma tomatoes to auction.\r\n", printlnContent.toString());
	}
	
	
	
	//Fall Tests
	@Test
	public void testAmishFallPlow() {
		f.setSeasonState(fallState);
		f.plow();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishFallPlant() {
		f.setSeasonState(fallState);
		f.plant();
		assertEquals("Late beans, squash, potatoes.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishFallWeedControl() {
		f.setSeasonState(fallState);
		f.weedControl();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishFallHarvest() {
		f.setSeasonState(fallState);
		f.harvest();
		assertEquals("Wife and kids help out in garden; neighbors help with corn and hay.\r\n", printlnContent.toString());
	}
	@Test
	public void testAmishFallMarket() {
		f.setSeasonState(fallState);
		f.market();
		assertEquals("Beans, squash, tomatoes to auction.\r\n", printlnContent.toString());
	}
}
