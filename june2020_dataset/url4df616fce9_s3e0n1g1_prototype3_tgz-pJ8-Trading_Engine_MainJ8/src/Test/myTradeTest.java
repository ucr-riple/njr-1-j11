package Test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import Trading_Engine.myTrade;

public class myTradeTest {

	@Test
	public void test() {
		myTrade testTrade = new myTrade();
		LinkedList<Double> testList = new LinkedList<Double>();
		double testPrice = 25.00;
		testList.add(testPrice);
		testTrade.addPice(testPrice);
		Assert.assertEquals("Error at adding one price",1,testTrade.getLength());
		testList.add(0.0);
		testTrade.addPice(0.0);
		testList.add(0.0);
		testTrade.addPice(0.0);
		Assert.assertEquals("Error at adding three prices",3,testTrade.getLength());
		Assert.assertArrayEquals("Error at adding three prices in array", testList.toArray(), testTrade.getAllPrice().toArray());
	}

}
