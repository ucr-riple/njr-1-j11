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

import assignments.farmer.farmerfactories.OrganicFarmerFactory;
import assignments.farmer.seasonstates.FallState;
import assignments.farmer.seasonstates.SummerState;

public class OrganicFarmerTests {
	ByteArrayOutputStream printlnContent = new ByteArrayOutputStream();
	Farmer f;
	FarmerFactory factory = new OrganicFarmerFactory();
	
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
	public void testOrganicSpringPlow() {
		f.plow();
		assertEquals("Plow under green manure.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSpringPlant() {
		f.plant();
		assertEquals("Peas, lettuce.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSpringWeedControl() {
		f.weedControl();
		assertEquals("Employ lots of interns with hoes.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSpringHarvest() {
		f.harvest();
		assertEquals("Employ lots of interns and volunteers.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSpringMarket() {
		f.market();
		assertEquals("Fall garlic, peas & lettuce to farmer's market.\r\n", printlnContent.toString());
	}

	
	
	//Summer Tests
	@Test
	public void testOrganicSummerPlow() {
		f.setSeasonState(summerState);
		f.plow();
		assertEquals("Plow fallow fields to prepare for fall cover crop.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSummerPlant() {
		f.setSeasonState(summerState);
		f.plant();
		assertEquals("Beans, squash, tomatoes, carrots, melons, 2nd round of peas and leafy greens.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSummerWeedControl() {
		f.setSeasonState(summerState);
		f.weedControl();
		assertEquals("Employ lots of interns with hoes.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSummerHarvest() {
		f.setSeasonState(summerState);
		f.harvest();
		assertEquals("Employ lots of interns and volunteers.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicSummerMarket() {
		f.setSeasonState(summerState);
		f.market();
		assertEquals("Peas, carrots, early beans, roma tomatoes to farmer's market.\r\n", printlnContent.toString());
	}
	
	
	
	//Fall Tests
	@Test
	public void testOrganicFallPlow() {
		f.setSeasonState(fallState);
		f.plow();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicFallPlant() {
		f.setSeasonState(fallState);
		f.plant();
		assertEquals("Late beans, squash, potatoes, leafy greens.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicFallWeedControl() {
		f.setSeasonState(fallState);
		f.weedControl();
		assertEquals("No action.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicFallHarvest() {
		f.setSeasonState(fallState);
		f.harvest();
		assertEquals("Employ lots of interns and volunteers; u-pick for squash.\r\n", printlnContent.toString());
	}
	@Test
	public void testOrganicFallMarket() {
		f.setSeasonState(fallState);
		f.market();
		assertEquals("Beans, squash, tomatoes to farmer's market; big harvest party on farm.\r\n", printlnContent.toString());
	}

}
