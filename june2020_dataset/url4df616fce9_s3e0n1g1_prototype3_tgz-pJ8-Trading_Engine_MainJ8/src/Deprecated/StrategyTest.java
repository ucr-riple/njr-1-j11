package Deprecated;

import org.junit.Test;



public class StrategyTest {

	@Test
	public void test() {
		System.out.println ("Testing begins:");
		
		Trade curr = new Trade(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, null, null, null);
		Trade prev = new Trade(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, null, null, null);
		
		curr.setPrice(31);
		prev.setPrice(41);
		
		
		Strategy ll = new Strategy();
		
		
		double moo = ll.getReturn(curr, prev);
		System.out.println (moo);
		ll.getReturn(curr, prev);
		ll.getReturn(curr, prev);
		ll.getReturn(curr, prev);
		
		moo = ll.getAverage();
		System.out.println (moo);
		moo = ll.getSignal();
		
		System.out.println (moo);
	}

}
