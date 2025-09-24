package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.high_level.derived.CPU;
import net.dtkanov.blocks.circuit.high_level.derived.ControlUnit;
import net.dtkanov.blocks.logic.ConstantNode;

import org.junit.Before;
import org.junit.Test;

public class CPUTest {
	private CPU cpu;
	private ConstantNode clock;
	
	@Before
	public void setUp() throws Exception {
		cpu = new CPU();
		clock = new ConstantNode(false);
		clock.connectDst(0, cpu, 0);
	}

	/**
	 * Gets 8th Fibonacci number (21).
	 */
	@Test
	public void fibonacciTest() {
		// MVI D, 5
		cpu.writeToMemory(0, 0b00010110);
		cpu.writeToMemory(1, 0b00000101);
		// MVI A, 1 [2]
		cpu.writeToMemory(2, 0b00111110);
		cpu.writeToMemory(3, 0b00000001);
		// INR B [1]
		cpu.writeToMemory(4, 0b00000100);
		// MOV C, A
		cpu.writeToMemory(5, 0b01001111);
		// ADD B [3]
		cpu.writeToMemory(6, 0b10000000);
		// MOV B, C <--loop start
		cpu.writeToMemory(7, 0b01000001);
		// MOV C, A
		cpu.writeToMemory(8, 0b01001111);
		// ADD B [3+i]
		cpu.writeToMemory(9, 0b10000000);
		// DCR D
		cpu.writeToMemory(10, 0b00010101);
		// JNZ 5 <--loop end
		cpu.writeToMemory(11, 0b11000010);
		cpu.writeToMemory(12, 7);
		cpu.writeToMemory(13, 0);
		cpu.init();
		
		while (getPCValue() < 14) {
			clock.setValue(true).propagate();
		}
		checkReg(-1, 14);
		checkReg(ControlUnitTest.REG_A, 21);
	}
	
	protected int getPCValue() {
		int res = 0;
		for (int i = 0; i < ControlUnit.BITNESS; i++) {
			int temp = cpu.getControlUnit().getRegPCValue(i)?1:0;
			res += temp<<i;
		}
		return res;
	}
	
	protected void checkReg(int reg_code, int val) {
		for (int i = 0; i < 8; i++) {
			switch (reg_code) {
				case 7:
					assertTrue(cpu.getControlUnit().getRegAValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 0:
					assertTrue(cpu.getControlUnit().getRegBValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 1:
					assertTrue(cpu.getControlUnit().getRegCValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 2:
					assertTrue(cpu.getControlUnit().getRegDValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 3:
					assertTrue(cpu.getControlUnit().getRegEValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 4:
					assertTrue(cpu.getControlUnit().getRegHValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 5:
					assertTrue(cpu.getControlUnit().getRegLValue(i)==((val & 1<<i) == 1<<i));
					break;
				default:
					assertTrue(cpu.getControlUnit().getRegPCValue(i)==((val & 1<<i) == 1<<i));
					break;
			}
		}
	}
	
	protected void printRegisters() {
		System.out.print("[A:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegAValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[B:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegBValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[C:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegCValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[D:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegDValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[E:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegEValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[H:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegHValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[L:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegLValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[F:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getFlag(i)?"1":"0");
		System.out.print("]");
		System.out.print("[PC:");
		for (int i = 15; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegPCValue(i)?"1":"0");
		System.out.print("]");
		System.out.println();
	}

}
