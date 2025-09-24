package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.circuit.high_level.derived.PCRegController;

import org.junit.Before;
import org.junit.Test;

public class PCRegControlTest {
	protected Register PC;
	protected PCRegController ctrl;

	@Before
	public void setUp() throws Exception {
		PC = new Register(4);
		ctrl = new PCRegController(PC);
		// this initialization is required for control node
		PC.in(0, false);
		PC.in(1, false);
		PC.in(2, false);
		PC.in(3, false);
		PC.in(4, true);
		PC.propagate();
	}

	@Test
	public void INC1Test() {
		int cur_pc = getValue();
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, true);
		ctrl.in(5, false);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		checkValue(cur_pc+1);
	}
	
	@Test
	public void INC2Test() {
		int cur_pc = getValue();
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, false);
		ctrl.in(5, true);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		checkValue(cur_pc+2);
	}
	
	@Test
	public void INC3Test() {
		int cur_pc = getValue();
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, true);
		ctrl.in(5, true);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		checkValue(cur_pc+3);
	}
	
	@Test
	public void SetTest() {
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, false);
		ctrl.in(5, false);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		checkValue(10);
	}
	
	@Test
	public void SequenceTest() {
		int cur_pc = getValue();
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, false);
		ctrl.in(5, true);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		cur_pc += 2;
		checkValue(cur_pc);
		
		// address
		ctrl.in(0, true);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, false);
		ctrl.in(5, false);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		cur_pc = 11;
		checkValue(cur_pc);
		
		// address
		ctrl.in(0, false);
		ctrl.in(1, true);
		ctrl.in(2, false);
		ctrl.in(3, true);
		// mode
		ctrl.in(4, true);
		ctrl.in(5, true);
		// clock
		ctrl.in(6, true);
		ctrl.propagate();
		cur_pc += 3;
		checkValue(cur_pc);
	}

	protected void checkValue(int val) {
		for (int i = 0; i < PC.getBitness(); i++) {
			assertTrue("Bit " + i + ", PC val: " + getValue() , PC.out(i)==((val & 1<<i) == 1<<i));
		}
	}
	
	protected int getValue() {
		int res = 0;
		for (int i = 0; i < PC.getBitness(); i++) {
			int temp = PC.out(i)?1:0;
			res += temp<<i;
		}
		return res;
	}
}
