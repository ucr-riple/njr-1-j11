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

import assignments.farmer.farmerfactories.ConventionalFarmerFactory;
import assignments.farmer.seasonstates.FallState;
import assignments.farmer.seasonstates.SummerState;

public class ConventionalFarmerTests {
	
	ByteArrayOutputStream printlnContent = new ByteArrayOutputStream();
	Farmer f;
	FarmerFactory factory = new ConventionalFarmerFactory();
	
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
	public void testConventionalSpringPlow() {
		f.plow();
		assertEquals("Using no-till; no plowing.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSpringPlant() {
		f.plant();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSpringWeedControl() {
		f.weedControl();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSpringHarvest() {
		f.harvest();
		assertEquals("Nothing to harvest.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSpringMarket() {
		f.market();
		assertEquals("Nothing to market.\r\n", printlnContent.toString());
	}

	
	
	//Summer Tests
	@Test
	public void testConventionalSummerPlow() {
		f.setSeasonState(summerState);
		f.plow();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSummerPlant() {
		f.setSeasonState(summerState);
		f.plant();
		assertEquals("Corn.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSummerWeedControl() {
		f.setSeasonState(summerState);
		f.weedControl();
		assertEquals("Spray.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSummerHarvest() {
		f.setSeasonState(summerState);
		f.harvest();
		assertEquals("Nothing to harvest.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalSummerMarket() {
		f.setSeasonState(summerState);
		f.market();
		assertEquals("Nothing to market.\r\n", printlnContent.toString());
	}
	
	
	
	//Fall Tests
	@Test
	public void testConventionalFallPlow() {
		f.setSeasonState(fallState);
		f.plow();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalFallPlant() {
		f.setSeasonState(fallState);
		f.plant();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalFallWeedControl() {
		f.setSeasonState(fallState);
		f.weedControl();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalFallHarvest() {
		f.setSeasonState(fallState);
		f.harvest();
		assertEquals("Hire combine.\r\n", printlnContent.toString());
	}
	@Test
	public void testConventionalFallMarket() {
		f.setSeasonState(fallState);
		f.market();
		assertEquals("Feed corn to elevator.\r\n", printlnContent.toString());
	}

}
