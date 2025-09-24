package net.dtkanov.blocks.core;


import net.dtkanov.blocks.circuit.high_level.derived.CPU;
import net.dtkanov.blocks.logic.*;

public class AppCore {
	private static CPU cpu;
	private static ConstantNode clock;
	
	public static void main(String[] args) {
		cpu = new CPU();
		clock = new ConstantNode(true);
		clock.connectDst(0, cpu, 0);
		fibonacci();
	}
	
	public static void fibonacci() {
		// MVI D, 97
		cpu.writeToMemory(0, 0b00010110);
		cpu.writeToMemory(1, 97);
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
		
		long ops = 490;
		long st = System.nanoTime();
		for (int i = 0; i < 490; i++) {
			clock.propagate();
		}
		long en = System.nanoTime();
		long total = en-st;
		System.out.printf("Fibonacci test: %3.3f ms, %d operations. Avg.speed: %d ops/sec."
							+System.getProperty("line.separator"), total/(double)1e6, ops, ops*1000/(total/1000000));
	}

}
