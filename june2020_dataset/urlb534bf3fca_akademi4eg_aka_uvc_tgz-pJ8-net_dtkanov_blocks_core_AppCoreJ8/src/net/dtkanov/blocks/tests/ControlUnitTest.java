package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.high_level.derived.ControlUnit;
import net.dtkanov.blocks.logic.ConstantNode;

import org.junit.Before;
import org.junit.Test;

public class ControlUnitTest {
	private ControlUnit cu;
	private ConstantNode in_op[];
	private ConstantNode in_data1[];
	private ConstantNode in_data2[];
	private ConstantNode clock;
	
	public static final int REG_A = 0b111;
	public static final int REG_B = 0b000;
	public static final int REG_C = 0b001;
	public static final int REG_D = 0b010;
	public static final int REG_E = 0b011;
	public static final int REG_H = 0b100;
	public static final int REG_L = 0b101;
	
	@Before
	public void setUp() throws Exception {
		cu = new ControlUnit();
		in_op = new ConstantNode[ControlUnit.BITNESS];
		in_data1 = new ConstantNode[ControlUnit.BITNESS];
		in_data2 = new ConstantNode[ControlUnit.BITNESS];
		for (int i = 0; i < ControlUnit.BITNESS; i++) {
			in_op[i] = new ConstantNode(false);
			in_op[i].connectDst(0, cu, i);
			in_data1[i] = new ConstantNode(false);
			in_data1[i].connectDst(0, cu, i+ControlUnit.BITNESS);
			in_data2[i] = new ConstantNode(false);
			in_data2[i].connectDst(0, cu, i+2*ControlUnit.BITNESS);
		}
		clock = new ConstantNode(true);
		clock.connectDst(0, cu, 3*ControlUnit.BITNESS);
	}
	
	@Test
	public void NOPTest() {
		int cur_pc = getPCValue();
		// NOP
		setOperationAndPropagete(0);
		setValuesAndPropagete(56, 123);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		// MVI B, 01001011b
		moveToReg(REG_B, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// NOP
		setOperationAndPropagete(0);
		setValuesAndPropagete(0, 123);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_B, 0b01001011);
		// NOP
		setOperationAndPropagete(0);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_B, 0b01001011);
	}
	
	@Test
	public void JMPTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		// JMP 0100101101010101b
		setOperationAndPropagete(0b11000011);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void PCHLTest() {
		int cur_pc = getPCValue();
		// MVI H, 01001011b
		moveToReg(REG_H, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI B, 01101011b
		moveToReg(REG_L, 0b01101011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// PCHL
		setOperationAndPropagete(0b11101001);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101101011;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JNZTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		// JNZ 0100101101010101b
		setOperationAndPropagete(0b11000010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// INR L
		setOperationAndPropagete(0b00101100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		cur_pc += 1;
		// DCR L
		setOperationAndPropagete(0b00101101);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==true);
		cur_pc += 1;
		// JNZ 0100101101010101b
		setOperationAndPropagete(0b11000010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JZTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		// JZ 0100101101010101b
		setOperationAndPropagete(0b11001010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
		// INR L
		setOperationAndPropagete(0b00101100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		// DCR L
		setOperationAndPropagete(0b00101101);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==true);
		// JZ 0100101101010101b
		setOperationAndPropagete(0b11001010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JNCTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		// JNC 0100101101010101b
		setOperationAndPropagete(0b11010010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// MOV A, 10001011b
		moveToReg(REG_A, 0b10001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// ADI 10100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b10100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		// JNC 0100101101010101b
		setOperationAndPropagete(0b11010010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JCTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		// JC 0100101101010101b
		setOperationAndPropagete(0b11011010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
		// MOV A, 10001011b
		moveToReg(REG_A, 0b10001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// ADI 10100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b10100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		// JC 0100101101010101b
		setOperationAndPropagete(0b11011010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JNPTest() {
		int cur_pc = getPCValue();
		// ADI 00100110b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100110, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		// JNP 0100101101010101b
		setOperationAndPropagete(0b11100010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// INR A
		setOperationAndPropagete(0b00111100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		cur_pc += 1;
		// JNP 0100101101010101b
		setOperationAndPropagete(0b11100010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JPTest() {
		int cur_pc = getPCValue();
		// ADI 00101110b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00101110, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		// JP 0100101101010101b
		setOperationAndPropagete(0b11101010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// INR A
		setOperationAndPropagete(0b00111100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		cur_pc += 1;
		// JP 0100101101010101b
		setOperationAndPropagete(0b11101010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JNSTest() {
		int cur_pc = getPCValue();
		// ADI 01111111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b01111111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		// JNS 0100101101010101b
		setOperationAndPropagete(0b11110010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// INR A
		setOperationAndPropagete(0b00111100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		cur_pc += 1;
		// JNS 0100101101010101b
		setOperationAndPropagete(0b11110010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void JSTest() {
		int cur_pc = getPCValue();
		// ADI 11111111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b11111111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		// JS 0100101101010101b
		setOperationAndPropagete(0b11111010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc = 0b0100101101010101;
		checkReg(-1, cur_pc);
		// INR A
		setOperationAndPropagete(0b00111100);
		setValuesAndPropagete(0, 0);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		cur_pc += 1;
		// JS 0100101101010101b
		setOperationAndPropagete(0b11111010);
		setValuesAndPropagete(0b01010101, 0b01001011);
		cur_pc += 3;
		checkReg(-1, cur_pc);
	}

	@Test
	public void MVITest() {
		int cur_pc = getPCValue();
		// MVI B, 01001011b
		moveToReg(REG_B, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI H, 11111111b
		moveToReg(REG_H, 0b11111111);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void MOVTest() {
		int cur_pc = getPCValue();
		// MVI C, 10101010b
		moveToReg(REG_C, 0b10101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI E, C
		setOperationAndPropagete(0b01011001);
		setValuesAndPropagete(0b11111111, 0);
		checkReg(REG_E, 0b10101010);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ADITest() {
		int cur_pc = getPCValue();
		
		// MVI A, 11001011b
		moveToReg(REG_A, 0b11001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		
		// 11001011b + 00100111b = 11110010b
		checkReg(REG_A, 0b11110010);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ADDTest() {
		int cur_pc = getPCValue();
		
		// MVI A, 11001011b
		moveToReg(0b111, 0b11001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// MVI D, 00101010b
		moveToReg(0b010, 0b00101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ADD D
		setOperationAndPropagete(0b10000010);
		setValuesAndPropagete(0, 0);

		// 11001011b + 00101010b = 11110101b
		checkReg(REG_A, 0b11110101);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void SUITest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// SUI 00100111b
		setOperationAndPropagete(0b11010110);	
		setValuesAndPropagete(0b00100111, 0);

		// 01001011b - 00100111b = 00100100b
		checkReg(REG_A, 0b00100100);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void SUBTest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// MVI D, 00101010b
		moveToReg(0b010, 0b00101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// SUB D
		setOperationAndPropagete(0b10010010);
		setValuesAndPropagete(0, 0);

		// 01001011b - 00101010b = 00100001b
		checkReg(REG_A, 0b00100001);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ANITest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ANI 00100111b
		setOperationAndPropagete(0b11100110);
		setValuesAndPropagete(0b00100111, 0);
		
		// 01001011b & 00100111b = 00000011b
		checkReg(REG_A, 0b00000011);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ANATest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// MVI C, 00101010b
		moveToReg(0b001, 0b00101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ANA C
		setOperationAndPropagete(0b10100001);
		setValuesAndPropagete(0, 0);

		// 01001011b & 00101010b = 00001010b
		checkReg(REG_A, 0b00001010);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ORITest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ORI 00100111b
		setOperationAndPropagete(0b11110110);
		setValuesAndPropagete(0b00100111, 0);

		// 01001011b | 00100111b = 01101111b
		checkReg(REG_A, 0b01101111);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void ORATest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// MVI L, 00101010b
		moveToReg(0b101, 0b00101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// ORA L
		setOperationAndPropagete(0b10110101);
		setValuesAndPropagete(0, 0);

		// 01001011b | 00101010b = 01101011b
		checkReg(REG_A, 0b01101011);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void XRITest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// XRI 00100111b
		setOperationAndPropagete(0b11101110);
		setValuesAndPropagete(0b00100111, 0);

		// 01001011b XOR 00100111b = 01101100b
		checkReg(REG_A, 0b01101100);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void XRATest() {
		int cur_pc = getPCValue();
		
		// MVI A, 01001011b
		moveToReg(0b111, 0b01001011);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// MVI L, 00101010b
		moveToReg(0b101, 0b00101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// XRA L
		setOperationAndPropagete(0b10101101);
		setValuesAndPropagete(0, 0);

		// 01001011b XOR 00101010b = 01100001b
		checkReg(REG_A, 0b01100001);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void INRTest() {
		int cur_pc = getPCValue();
		
		// MVI L, 00101001b
		moveToReg(0b101, 0b00101001);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// INR L
		setOperationAndPropagete(0b00101100);
		setValuesAndPropagete(0, 0);

		// INR 00101001b = 00101010b
		checkReg(REG_L, 0b00101010);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void DCRTest() {
		int cur_pc = getPCValue();
		
		// MVI L, 00101001b
		moveToReg(0b101, 0b00101001);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// DCR L
		setOperationAndPropagete(0b00101101);
		setValuesAndPropagete(0, 0);

		// DCR 00101001b = 00101000b
		checkReg(REG_L, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void RLCTest() {
		int cur_pc = getPCValue();
		
		// MVI A, 10101000b
		moveToReg(0b111, 0b10101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// RLC
		setOperationAndPropagete(0b00000111);
		setValuesAndPropagete(0, 0);
		
		// 01010001b
		checkReg(REG_A, 0b01010001);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void RRCTest() {
		int cur_pc = getPCValue();
		
		// MVI A, 10101000b
		moveToReg(0b111, 0b10101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		
		// RRC
		setOperationAndPropagete(0b00001111);
		setValuesAndPropagete(0, 0);
		
		// 01010100b
		checkReg(REG_A, 0b01010100);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 1;
		checkReg(-1, cur_pc);
	}

	@Test
	public void CMPTest() {
		int cur_pc = getPCValue();
		// MVI A, 00101000b
		moveToReg(REG_A, 0b00101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI B, 00101001b
		moveToReg(REG_B, 0b00101001);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// CMP B
		setOperationAndPropagete(0b10111000);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		// DCR B
		setOperationAndPropagete(0b00000101);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		// CMP B
		setOperationAndPropagete(0b10111000);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==true);
		// DCR B
		setOperationAndPropagete(0b00000101);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		// CMP B
		setOperationAndPropagete(0b10111000);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.P_FLAG)==false);
	}
	
	@Test
	public void CPITest() {
		int cur_pc = getPCValue();
		// MVI A, 00101000b
		moveToReg(REG_A, 0b00101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// CPI 00101111b
		setOperationAndPropagete(0b11111110);
		setValuesAndPropagete(0b00101111, 0);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// CPI 00101000b
		setOperationAndPropagete(0b11111110);
		setValuesAndPropagete(0b00101000, 0);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// CPI 00101010b
		setOperationAndPropagete(0b11111110);
		setValuesAndPropagete(0b00101010, 0);
		checkReg(REG_A, 0b00101000);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.S_FLAG)==true);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		cur_pc += 2;
		checkReg(-1, cur_pc);
	}
	
	@Test
	public void STATest() {
		int cur_pc = getPCValue();
		// MVI A, 10101000b
		moveToReg(0b111, 0b10101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==0);
		// STA 3242
		setOperationAndPropagete(0b00110010);
		setValuesAndPropagete(0b10101010, 0b00001100);
		cur_pc += 3;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b10101000);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==(byte)0b10101000);
	}
	
	@Test
	public void LDATest() {
		int cur_pc = getPCValue();
		cu.loadToStorage(3242, (byte)33);
		// LDA 3242
		setOperationAndPropagete(0b00111010);
		setValuesAndPropagete(0b10101010, 0b00001100);
		cur_pc += 3;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 33);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==33);
	}
	
	@Test
	public void LDAXTest() {
		int cur_pc = getPCValue();
		cu.loadToStorage(0b0000110010101010, (byte)34);
		// MVI B, 00001100b
		moveToReg(0b000, 0b00001100);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI C, 10101010b
		moveToReg(0b001, 0b0010101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// LDAX BC
		setOperationAndPropagete(0b00001010);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 34);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==34);
	}
	
	@Test
	public void STAXTest() {
		int cur_pc = getPCValue();
		// MVI A, 10101000b
		moveToReg(0b111, 0b10101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==0);
		// MVI D, 00001100b
		moveToReg(0b010, 0b00001100);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// MVI E, 10101010b
		moveToReg(0b011, 0b0010101010);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// STAX D
		setOperationAndPropagete(0b00010010);
		setValuesAndPropagete(0, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b10101000);
		assertTrue(cu.getMemoryAt(0b0000110010101010)==(byte)0b10101000);
	}
	
	@Test
	public void CMATest() {
		int cur_pc = getPCValue();
		// MVI A, 10101000b
		moveToReg(0b111, 0b10101000);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		// CMA
		setOperationAndPropagete(0b00101111);
		setValuesAndPropagete(0b10101010, 0b00001100);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		checkReg(REG_A, 0b01010111);
	}
	
	@Test
	public void STCTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		// STC
		setOperationAndPropagete(0b00110111);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		// STC
		setOperationAndPropagete(0b00110111);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
	}
	
	@Test
	public void CMCTest() {
		int cur_pc = getPCValue();
		// ADI 00100111b
		setOperationAndPropagete(0b11000110);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 2;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
		// CMC
		setOperationAndPropagete(0b00111111);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==true);
		// CMC
		setOperationAndPropagete(0b00111111);
		setValuesAndPropagete(0b00100111, 0);
		cur_pc += 1;
		checkReg(-1, cur_pc);
		assertTrue(cu.getFlag(ControlUnit.Z_FLAG)==false);
		assertTrue(cu.getFlag(ControlUnit.C_FLAG)==false);
	}
	
	protected void moveToReg(int reg_code, int val) {
		// MVI REG, VAL
		in_op[0].setValue(false).propagate();
		in_op[1].setValue(true).propagate();
		in_op[2].setValue(true).propagate();
		in_op[3].setValue((reg_code>>0 & 1) == 1).propagate();
		in_op[4].setValue((reg_code>>1 & 1) == 1).propagate();
		in_op[5].setValue((reg_code>>2 & 1) == 1).propagate();
		in_op[6].setValue(false).propagate();
		in_op[7].setValue(false).propagate();
		
		setValuesAndPropagete(val, 0);
		checkReg(reg_code, val);
	}
	
	protected void setOperationAndPropagete(int op) {
		for (int i = 0; i < 8; i++) {
			in_op[i].setValue((op & 1<<i) == 1<<i).propagate();
		}
	}
	
	protected void setValuesAndPropagete(int val1, int val2) {
		for (int i = 0; i < 8; i++) {
			in_data1[i].setValue((val1 & 1<<i) == 1<<i).propagate();
			in_data2[i].setValue((val2 & 1<<i) == 1<<i).propagate();
		}
		clock.setValue(true).propagate();
	}
	
	protected void checkReg(int reg_code, int val) {
		for (int i = 0; i < 8; i++) {
			switch (reg_code) {
				case 7:
					assertTrue(cu.getRegAValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 0:
					assertTrue(cu.getRegBValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 1:
					assertTrue(cu.getRegCValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 2:
					assertTrue(cu.getRegDValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 3:
					assertTrue(cu.getRegEValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 4:
					assertTrue(cu.getRegHValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 5:
					assertTrue(cu.getRegLValue(i)==((val & 1<<i) == 1<<i));
					break;
				default:
					assertTrue(cu.getRegPCValue(i)==((val & 1<<i) == 1<<i));
					assertTrue(cu.getRegPCValue(i+8)==((val & 1<<(i+8)) == 1<<(i+8)));
					break;
			}
		}
	}
	
	protected int getPCValue() {
		int res = 0;
		for (int i = 0; i < ControlUnit.BITNESS; i++) {
			int temp = cu.getRegPCValue(i)?1:0;
			res += temp<<i;
		}
		return res;
	}
	
	protected void printRegisters() {
		System.out.print("[A:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegAValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[B:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegBValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[C:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegCValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[D:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegDValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[E:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegEValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[H:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegHValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[L:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getRegLValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[F:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cu.getFlag(i)?"1":"0");
		System.out.print("]");
		System.out.print("[PC:");
		for (int i = 15; i >= 0; i--)
			System.out.print(cu.getRegPCValue(i)?"1":"0");
		System.out.print("]");
		System.out.println();
	}

}
