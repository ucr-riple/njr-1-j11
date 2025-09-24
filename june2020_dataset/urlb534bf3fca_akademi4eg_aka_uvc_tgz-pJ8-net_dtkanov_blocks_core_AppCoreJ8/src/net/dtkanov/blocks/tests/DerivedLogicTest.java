package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.logic.*;
import net.dtkanov.blocks.logic.derived.*;

import org.junit.Test;

public class DerivedLogicTest {

	@Test
	public void ORTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		Node out = new ORNode().connectSrc(in1, 0, 0)
							   .connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.setValue(false).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(false).propagate();
		assertFalse(out.out(0));
		
		in1.setValue(true).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertTrue(out.out(0));
	}
	
	@Test
	public void NORTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		Node out = new NORNode().connectSrc(in1, 0, 0)
							    .connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.setValue(false).propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.setValue(false).propagate();
		assertTrue(out.out(0));
		
		in1.setValue(true).propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertFalse(out.out(0));
	}
	
	@Test
	public void NANDTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		Node out = new NANDNode().connectSrc(in1, 0, 0)
								 .connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.setValue(false).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(false).propagate();
		assertTrue(out.out(0));
		
		in1.setValue(true).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertFalse(out.out(0));
	}
	
	@Test
	public void XORTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		Node out = new XORNode().connectSrc(in1, 0, 0)
						 	    .connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertFalse(out.out(0));
		
		in1.setValue(false).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(false).propagate();
		assertFalse(out.out(0));
		
		in1.setValue(true).propagate();
		in2.propagate();
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertFalse(out.out(0));
	}

}
