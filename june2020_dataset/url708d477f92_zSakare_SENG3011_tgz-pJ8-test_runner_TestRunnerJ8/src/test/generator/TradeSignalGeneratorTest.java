package test.generator;

import main.generator.TradeSignalGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.helper.ListGenerator;

public class TradeSignalGeneratorTest {

	@Before
	public void setUp() throws Exception {
		
		System.out.println("Testing TradeStrategyEvaluator.");
	}

	@After
	public void tearDown() throws Exception {
		
		System.out.println("Testing Complete.");
	}
	
	@Test
	public void testRandomAskSignal() {
		System.out.print("Testing generation of random ask signal.....");
		TradeSignalGenerator randomGenerator = new TradeSignalGenerator(ListGenerator.generateRandomBidList(), 
				ListGenerator.generateRandomAskList(),
				null);
		
		assert(randomGenerator.newRandomAsk("23") != null);
		assert(randomGenerator.newRandomAsk("23").get(0).getPrice() >= 1000);
		assert(!randomGenerator.newRandomAsk("23").get(0).isBid());
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testRandomBidSignal() {
		System.out.print("Testing generation of random bid signal.....");
		TradeSignalGenerator randomGenerator = new TradeSignalGenerator(ListGenerator.generateRandomBidList(), 
				ListGenerator.generateRandomAskList(),
				null);
		
		assert(randomGenerator.newRandomBid("23") != null);
		assert(randomGenerator.newRandomBid("23").get(0).getPrice() >= 1000);
		assert(randomGenerator.newRandomBid("23").get(0).isBid());
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testMomentumSignal() {
		System.out.print("Testing generation of momentum signal.....");
		TradeSignalGenerator momentumGenerator = new TradeSignalGenerator(ListGenerator.generateMomentumBidList(), 
				ListGenerator.generateAskMomentumList(),
				ListGenerator.generatePositiveMomentumTradeList());
		
		assert(momentumGenerator.newMomentumOrders("23", 100, 0.0) != null);
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testMeanReversionSignal() {
		System.out.print("Testing generation of mean reversion signal.....");
		TradeSignalGenerator meanReversionGenerator = new TradeSignalGenerator(ListGenerator.generateMeanRevBidList(), 
				ListGenerator.generateMeanRevAskList(),
				ListGenerator.generatePositiveMeanRevTradeList());
		
		assert(meanReversionGenerator.newMeanReversionOrders("23", 10, 0.0) != null);
		System.out.println("Test Passed.");
	}
}
