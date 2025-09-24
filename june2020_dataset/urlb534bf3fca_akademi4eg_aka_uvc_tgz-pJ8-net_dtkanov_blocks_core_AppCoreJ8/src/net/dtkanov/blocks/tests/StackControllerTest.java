package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.circuit.high_level.derived.StackController;

import org.junit.Before;
import org.junit.Test;

public class StackControllerTest {
	private StackController stack;
	private byte storage[];
	private Register SP; 

	@Before
	public void setUp() throws Exception {
		SP = new Register(16);
		storage = new byte[1<<16];
		Memory mem = new Memory(16, storage);
		stack = new StackController(SP, mem);
		// SP = 0b00010000
		for (int i = 0; i < SP.getBitness(); i++) {
			if (i != 4)
				SP.in(i, false);
		}
		SP.in(4, true);
		SP.in(SP.getBitness(), true);
		SP.propagate();
	}

	@Test
	public void PUSHTest() {
		// PUSH 0b0010
		stack.in(0, false);
		stack.in(1, true);
		for (int i = 2; i < SP.getBitness(); i++) {
			stack.in(i, false);
		}
		stack.in(SP.getBitness(), true);
		stack.in(SP.getBitness()+1, true);
		stack.propagate();
		checkSPValue(0b00001110);
		checkStackValue(0b00000010);
		assertTrue(storage[0b00001110]==0b0010);
		assertTrue(storage[0b00001111]==0b0000);
		// PUSH 0b01...0010
		stack.in(0, true);
		stack.in(1, true);
		for (int i = 2; i < SP.getBitness()-2; i++) {
			stack.in(i, false);
		}
		stack.in(SP.getBitness()-2, true);
		stack.in(SP.getBitness()-1, false);
		stack.in(SP.getBitness(), true);
		stack.in(SP.getBitness()+1, true);
		stack.propagate();
		checkSPValue(0b00001100);
		checkStackValue(0b0100000000000011);
		assertTrue(storage[0b00001100]==0b00000011);
		assertTrue(storage[0b00001101]==0b01000000);
	}
	
	@Test
	public void POPTest() {
		storage[0b00010000] = 0b00000010;
		storage[0b00010001] = 0b01100000;
		// POP 0b0010
		stack.in(0, false);
		stack.in(1, true);
		for (int i = 2; i < SP.getBitness(); i++) {
			stack.in(i, false);
		}
		stack.in(SP.getBitness(), false);
		stack.in(SP.getBitness()+1, true);
		stack.propagate();
		checkSPValue(0b00010010);
		checkStackValue(0b0110000000000010);
		assertTrue(storage[0b00010000]==0b00000010);
		assertTrue(storage[0b00010001]==0b01100000);
	}

	protected void checkSPValue(int val) {
		for (int i = 0; i < SP.getBitness(); i++) {
			assertTrue("SP["+i+"] = "+SP.out(i)+", expected: "+((val & 1<<i) == 1<<i), SP.out(i)==((val & 1<<i) == 1<<i));
		}
	}
	
	protected void checkStackValue(int val) {
		for (int i = 0; i < SP.getBitness(); i++) {
			assertTrue("stack["+i+"] = "+stack.out(i)+", expected: "+((val & 1<<i) == 1<<i), stack.out(i)==((val & 1<<i) == 1<<i));
		}
	}
}
