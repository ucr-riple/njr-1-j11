package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.logic.*;

import org.junit.Test;

public class BaseLogicTest {

	@Test
	public void NOTTest() {
		ConstantNode in = new ConstantNode(true);
		Node out = new NOTNode().connectSrc(in, 0, 0);
		
		in.propagate();
		assertFalse(out.out(0));
		
		in.propagate();
		assertFalse(out.out(0));
		
		in.setValue(false).propagate();
		assertTrue(out.out(0));
		
		in.propagate();
		assertTrue(out.out(0));
		
		in.setValue(true).propagate();
		assertFalse(out.out(0));
	}

	@Test
	public void NOPTest() {
		ConstantNode in = new ConstantNode(true);
		Node out = new NOPNode().connectSrc(in, 0, 0);
		
		in.propagate();
		assertTrue(out.out(0));
		
		in.propagate();
		assertTrue(out.out(0));
		
		in.setValue(false).propagate();
		assertFalse(out.out(0));
		
		in.propagate();
		assertFalse(out.out(0));
		
		in.setValue(true).propagate();
		assertTrue(out.out(0));
	}
	
	@Test
	public void ANDTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		Node out = new ANDNode().connectSrc(in1, 0, 0)
								.connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.setValue(false).propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.setValue(false).propagate();
		assertFalse(out.out(0));
		
		in1.setValue(true).propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertTrue(out.out(0));
	}
	
}
