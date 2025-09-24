package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.circuit.high_level.derived.ALU;
import net.dtkanov.blocks.logic.ConstantNode;

import org.junit.Before;
import org.junit.Test;

public class ALUTest {
	private ALU alu;
	private int num_bits;
	private Register op1;
	private Register op2;
	private ConstantNode ctrl[];

	@Before
	public void setUp() throws Exception {
		num_bits = 4;
		alu = new ALU(num_bits);
		op1 = new Register(num_bits);
		op2 = new Register(num_bits);
		ctrl = new ConstantNode[ALU.NUM_CMD_BITS];
		for (int i = 0; i < num_bits; i++) {
			op1.connectDst(i, alu, i);
			op2.connectDst(i, alu, i+num_bits);
		}
		for (int i = 0; i < ctrl.length; i++) {
			ctrl[i] = new ConstantNode(true);
			ctrl[i].connectDst(0, alu, i + 2*num_bits);
		}
	}

	@Test
	public void ANDTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(false);
		ctrl[2].setValue(false);
		ctrl[3].setValue(false);
		
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==true);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// C-flag
		assertTrue(alu.out(7)==false);
	}
	
	@Test
	public void ORTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(false);
		ctrl[2].setValue(false);
		ctrl[3].setValue(true);
		
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==true);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
	}

	@Test
	public void XORTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(false);
		ctrl[2].setValue(true);
		ctrl[3].setValue(false);
		
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==true);
		
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==true);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==false);
	}
	
	@Test
	public void NOTTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(false);
		ctrl[2].setValue(true);
		ctrl[3].setValue(true);
		
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==true);
		
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// P-flag
		assertTrue(alu.out(6)==true);
	}
	
	@Test
	public void SHLTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(true);
		ctrl[2].setValue(false);
		ctrl[3].setValue(false);
		// SHL 1001b, 3
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1000b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==true);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// SHL 1010b, 1
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0100b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// S-flag
		assertTrue(alu.out(5)==alu.out(3));
		// C-flag
		assertTrue(alu.out(7)==true);
	}
	
	@Test
	public void SHRTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(true);
		ctrl[2].setValue(false);
		ctrl[3].setValue(true);
		// SHR 1001b, 3
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0001b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// SHR 1010b, 1
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0101b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
	}
	
	@Test
	public void ROLTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(true);
		ctrl[2].setValue(true);
		ctrl[3].setValue(false);
		// ROL 1001b, 3
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1100b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// ROL 1010b, 1
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0101b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==true);
	}
	
	@Test
	public void RORTest() {
		ctrl[0].setValue(false);
		ctrl[1].setValue(true);
		ctrl[2].setValue(true);
		ctrl[3].setValue(true);
		// ROR 1001b, 3
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0011b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// ROR 1010b, 1
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0101b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
	}
	
	@Test
	public void ADDTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(false);
		ctrl[2].setValue(false);
		ctrl[3].setValue(false);
		// 1001
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		// 0101
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1110
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// 1010
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		// 1001
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0011
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==true);
	}
	
	@Test
	public void SUBTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(false);
		ctrl[2].setValue(false);
		ctrl[3].setValue(true);
		// 0011
		op1.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		// 0101
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0010
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		// 0110
		op1.in(0, false)
		   .in(1, true)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		// 0101
		op2.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1111
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// C-flag
		assertTrue(alu.out(7)==true);
	}
	
	@Test
	public void INCTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(false);
		ctrl[2].setValue(true);
		ctrl[3].setValue(false);
		// INC 1001b
		op1.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1010b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==true);
		// Z-flag
		assertTrue(alu.out(4)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// INC 1111b
		op1.in(0, true)
		   .in(1, true)
		   .in(2, true)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0000b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==true);
		// C-flag
		assertTrue(alu.out(7)==true);
	}
	
	@Test
	public void DECTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(false);
		ctrl[2].setValue(true);
		ctrl[3].setValue(true);
		// DEC 0010b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0001b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// C-flag
		assertTrue(alu.out(7)==false);
		
		// DEC 1111b
		op1.in(0, true)
		   .in(1, true)
		   .in(2, true)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 1110b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==true);
		assertTrue(alu.out(3)==true);
		// C-flag
		assertTrue(alu.out(7)==false);
	}
	
	@Test
	public void CMPNZTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(true);
		ctrl[2].setValue(false);
		ctrl[3].setValue(false);
		// CMPNZ 0010b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0001b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==false);
		
		// CMPNZ 0000b
		op1.in(0, false)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0000b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==true);
	}
	
	@Test
	public void SIGNTest() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(true);
		ctrl[2].setValue(false);
		ctrl[3].setValue(true);
		// SIGN 0010b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0000b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==true);
		
		// CMPNZ 1010b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, false)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0001b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==false);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
		// Z-flag
		assertTrue(alu.out(4)==false);
	}
	
	@Test
	public void OP1Test() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(true);
		ctrl[2].setValue(true);
		ctrl[3].setValue(false);
		// OP1 0010b, 0011b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0010b
		assertTrue(alu.out(0)==false);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
	}
	
	@Test
	public void OP2Test() {
		ctrl[0].setValue(true);
		ctrl[1].setValue(true);
		ctrl[2].setValue(true);
		ctrl[3].setValue(true);
		// OP2 0010b, 0011b
		op1.in(0, false)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		op2.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, false)
		   .in(4, true);
		
		op1.propagate();
		op2.propagate();
		for (int i = 0; i < ctrl.length; i++)
			ctrl[i].propagate();
		// 0010b
		assertTrue(alu.out(0)==true);
		assertTrue(alu.out(1)==true);
		assertTrue(alu.out(2)==false);
		assertTrue(alu.out(3)==false);
	}
}
