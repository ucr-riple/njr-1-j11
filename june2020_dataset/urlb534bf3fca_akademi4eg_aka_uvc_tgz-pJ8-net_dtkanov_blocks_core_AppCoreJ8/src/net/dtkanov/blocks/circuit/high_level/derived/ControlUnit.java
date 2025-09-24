package net.dtkanov.blocks.circuit.high_level.derived;

import net.dtkanov.blocks.circuit.AllNode;
import net.dtkanov.blocks.circuit.DeMux;
import net.dtkanov.blocks.circuit.LookUp;
import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.circuit.MultiNOT;
import net.dtkanov.blocks.circuit.MultiOR;
import net.dtkanov.blocks.circuit.Mux;
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.derived.NORNode;
import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.blocks.logic.derived.XORNode;
/** Implements control unit. */
public class ControlUnit extends Node {
	/** Bitness of CPU. */
	public static int BITNESS = 8;
	/** Carry-flag index. */
	public static int C_FLAG = 0;
	/** Flag is not used. */
	public static int F1_FLAG = 1;
	/** Parity-flag index. */
	public static int P_FLAG = 2;
	/** Flag is not used. */
	public static int F3_FLAG = 3;
	/** Aux.carry-flag index. */
	public static int H_FLAG = 4;
	/** Interrupt-flag index. */
	public static int I_FLAG = 5;
	/** Zero-flag index. */
	public static int Z_FLAG = 6;
	/** Sign-flag index. */
	public static int S_FLAG = 7;
	/** Accumulator */
	private Register A;
	/** Flags */
	private Register F;
	private Register B;
	private Register C;
	private Register D;
	private Register E;
	private Register H;
	private Register L;
	/** Stack Pointer */
	private Register SP;
	/** Program Counter */
	private Register PC;
	/** Operation selection. */
	private Node opNOPs[];
	/** Data-byte 1 */
	private Node inNOPs_A[];
	/** Data-byte 2 */
	private Node inNOPs_B[];
	/** Clock input */
	private Node clock;
	/** Selector for ALU operand 1 */
	private Node ALU_in_mux_A[];
	/** Selector for output to registers. */
	private Node out_demux[];
	/** ALU */
	private ALU alu;
	/** Memory */
	private Memory mem;
	/** RO access to memory. */
	private LookUp mem_ro;
	/** Lookup table for ALU control */
	private LookUp alu_ctrl;
	/** Storage */
	private byte storage[];
	/** Fake zero */
	private ConstantNode zero;
	/** Checks if it is rotations instruction. */
	private ANDNode comb_rot_ctrl[];
	/** Controller for PC register. */
	private PCRegController pc_ctrl;
	/** ALU disabler. */
	private NOPNode is_alu_on;
	/** True if jump condition is not satisfied. */
	private XORNode not_jump_cond;
	/** True if jump is not requested. */
	private ORNode is_not_jump;
	/** True if instruction works with memory. */
	private ANDNode is_mem_instr;
	/** True if instruction loads from memory. */
	private ANDNode is_mem_load;
	/** True if instruction sores to memory. */
	private ANDNode is_mem_store;
	/** Registers pair selector (DE/BC). */
	private MultiMux reg_pair_sel;
	
	public ControlUnit() {
		super(null);
		zero = new ConstantNode(false);
		storage = new byte[1<<(BITNESS*2)];
		mem = new Memory(BITNESS*2, storage);// 16-bit addressing
		mem_ro = new LookUp(BITNESS*2, storage);// 16-bit addressing
		initInputs();
		initElements();
		zero.propagate();
	}
	
	public void loadToStorage(int offset, byte[] data) {
		if (offset < 0)
			throw new IllegalArgumentException("Memory offset can't be negative.");
		for (int i = 0; i < data.length; i++) {
			if (offset + i >= storage.length)
				break;
			storage[offset+i] = data[i];
		}
	}
	
	public void loadToStorage(byte[] data) {
		loadToStorage(0, data);
	}
	
	public void loadToStorage(int offset, byte data) {
		byte temp[] = new byte[1];
		temp[0] = data;
		loadToStorage(offset, temp);
	}
	
	public byte getMemoryAt(int addr) {
		return storage[addr];
	}
	public boolean getRegAValue(int index) {
		return A.out(index);
	}
	public boolean getRegBValue(int index) {
		return B.out(index);
	}
	public boolean getRegCValue(int index) {
		return C.out(index);
	}
	public boolean getRegDValue(int index) {
		return D.out(index);
	}
	public boolean getRegEValue(int index) {
		return E.out(index);
	}
	public boolean getRegHValue(int index) {
		return H.out(index);
	}
	public boolean getRegLValue(int index) {
		return L.out(index);
	}
	public boolean getFlag(int index) {
		return F.out(index);
	}
	public boolean getRegSPValue(int index) {
		return SP.out(index);
	}
	public boolean getRegPCValue(int index) {
		return PC.out(index);
	}
	
	private void initInputs() {
		inNOPs_A = new NOPNode[BITNESS];
		inNOPs_B = new NOPNode[BITNESS];
		for (int i = 0; i < BITNESS; i++) {
			inNOPs_A[i] = new NOPNode();
			inNOPs_B[i] = new NOPNode();
		}
		opNOPs = new NOPNode[BITNESS];
		for (int i = 0; i < BITNESS; i++) {
			opNOPs[i] = new NOPNode();
		}
		clock = new NOPNode();
	}
	
	private void initElements() {
		A = new Register(BITNESS);
		F = new Register(BITNESS);
		B = new Register(BITNESS);
		C = new Register(BITNESS);
		D = new Register(BITNESS);
		E = new Register(BITNESS);
		H = new Register(BITNESS);
		L = new Register(BITNESS);
		SP = new Register(2*BITNESS);
		PC = new Register(2*BITNESS);
		///////////////////////////////////////////////////////////////////////
		initJumps();
		///////////////////////////////////////////////////////////////////////
		is_mem_load = new ANDNode();
		is_mem_store = new ANDNode();
		reg_pair_sel = new MultiMux(2*BITNESS);
		alu = new ALU(BITNESS);// 8-bit ALU
		// control byte
		// TODO replace lookup with clever logic
		// table format: xRPPAAAA, R - is ALU output to registers on?, PP - PC controller mode, AAAA - ALU mode
		byte[] alu_lookup = new byte[1<<BITNESS];
		for (int i = 0; i < 1<<6; i++) {
			alu_lookup[(1<<6) + i] = 0b1010111;// MOV => OP1
			alu_lookup[i] = 0b1100111;// MVI => OP1
		}
		for (int i = 0; i < 1<<3; i++) {
			alu_lookup[0b10000000 + i] = 0b1010001;// ADD => ADD
			alu_lookup[0b10001000 + i] = 0b1010001;// ADC => ADD
			
			alu_lookup[0b10010000 + i] = 0b1011001;// SUB => SUB
			alu_lookup[0b10011000 + i] = 0b1011001;// SBB => SUB
			
			alu_lookup[0b00000100 + (i<<3)] = 0b1010101;// INR => INC
			alu_lookup[0b00000101 + (i<<3)] = 0b1011101;// DER => DEC
			
			alu_lookup[0b10100000 + i] = 0b1010000;// ANA => AND
			alu_lookup[0b10110000 + i] = 0b1011000;// ORA => OR
			alu_lookup[0b10101000 + i] = 0b1010100;// XRA => XOR
			
			alu_lookup[0b10111000 + i] = 0b0011001;// CMP => SUB
			
			alu_lookup[0b11000010 + (i<<3)] = 0b0000111;// Jccc
		}
		alu_lookup[0b00001010] = 0b1010111;// LDAX => OP1
		alu_lookup[0b00011010] = 0b1010111;// LDAX => OP1
		alu_lookup[0b00000010] = 0b1010111;// STAX => OP1
		alu_lookup[0b00010010] = 0b1010111;// STAX => OP1
		alu_lookup[0b00111010] = 0b1110111;// LDA => OP1
		alu_lookup[0b00110010] = 0b1110111;// STA => OP1
		alu_lookup[0b00000000] = 0b0010111;// NOP
		alu_lookup[0b11000011] = 0b0000111;// JMP
		alu_lookup[0b11101001] = 0b0000111;// PCHL
		alu_lookup[0b11000110] = 0b1100001;// ADI => ADD
		alu_lookup[0b11001110] = 0b1100001;// ACI => ADD
		alu_lookup[0b11010110] = 0b1101001;// SUI => SUB
		alu_lookup[0b11011110] = 0b1101001;// SBI => SUB
		alu_lookup[0b11100110] = 0b1100000;// ANI => AND
		alu_lookup[0b11110110] = 0b1101000;// ORI => OR
		alu_lookup[0b11101110] = 0b1100100;// XRI => XOR
		alu_lookup[0b00000111] = 0b1010110;// RLC => ROL
		alu_lookup[0b00001111] = 0b1011110;// RRC => ROR
		alu_lookup[0b11111110] = 0b0101001;// CPI => SUB
		alu_lookup[0b00101111] = 0b1011100;// CPA => NOT
		alu_lookup[0b00111111] = 0b0010111;// CMC
		alu_lookup[0b00110111] = 0b0010111;// STC
		alu_ctrl = new LookUp(BITNESS, alu_lookup);
		///////////////////////////////////////////////////////////////////////
		NORNode bits7_and_0 = new NORNode();
		bits7_and_0.connectSrc(opNOPs[0], 0, 0);
		bits7_and_0.connectSrc(opNOPs[7], 0, 1);
		NORNode bits6_and_2 = new NORNode();
		bits6_and_2.connectSrc(opNOPs[2], 0, 0);
		bits6_and_2.connectSrc(opNOPs[6], 0, 1);
		ANDNode comb_st_1 = new ANDNode();
		comb_st_1.connectSrc(opNOPs[1], 0, 0);
		comb_st_1.connectSrc(bits6_and_2, 0, 1);
		is_mem_instr = new ANDNode();
		is_mem_instr.connectSrc(bits7_and_0, 0, 0);
		is_mem_instr.connectSrc(comb_st_1, 0, 1);
		///////////////////////////////////////////////////////////////////////
		pc_ctrl = new PCRegController(PC);
		// lowest bit of opcode = 0 => conditional jump
		is_not_jump = new ORNode(alu_ctrl, 4, alu_ctrl, 5);
		NOTNode neg_is_cond = new NOTNode();
		neg_is_cond.connectSrc(opNOPs[0], 0, 0);// true when conditional jump
		AllNode is_pchl = new AllNode(BITNESS);
		NOTNode rev1_bit = new NOTNode(opNOPs[1], 0);
		NOTNode rev2_bit = new NOTNode(opNOPs[2], 0);
		NOTNode rev4_bit = new NOTNode(opNOPs[4], 0);
		is_pchl.connectSrc(opNOPs[0], 0, 0);
		is_pchl.connectSrc(rev1_bit, 0, 1);
		is_pchl.connectSrc(rev2_bit, 0, 2);
		is_pchl.connectSrc(opNOPs[3], 0, 3);
		is_pchl.connectSrc(rev4_bit, 0, 4);
		is_pchl.connectSrc(opNOPs[5], 0, 5);
		is_pchl.connectSrc(opNOPs[6], 0, 6);
		is_pchl.connectSrc(opNOPs[7], 0, 7);
		ANDNode skip_jump = new ANDNode(new ANDNode(neg_is_cond, 0,
													new NOTNode(is_pchl, 0), 0), 0,
										not_jump_cond, 0);
		Mux jumper = new Mux();
		jumper.connectSrc(is_not_jump, 0, 2);
		jumper.connectSrc(alu_ctrl, 4, 0);
		jumper.connectSrc(skip_jump, 0, 1);
		pc_ctrl.connectSrc(jumper, 0, BITNESS*2);
		jumper = new Mux();
		jumper.connectSrc(is_not_jump, 0, 2);
		jumper.connectSrc(alu_ctrl, 5, 0);
		jumper.connectSrc(skip_jump, 0, 1);
		pc_ctrl.connectSrc(jumper, 0, BITNESS*2 + 1);
		pc_ctrl.connectSrc(clock, 0, BITNESS*2 + 2);
		MultiMux pc_src_low = new MultiMux(BITNESS);
		pc_src_low.connectSrc(is_pchl, 0, 2*BITNESS);
		MultiMux pc_src_high = new MultiMux(BITNESS);
		pc_src_high.connectSrc(is_pchl, 0, 2*BITNESS);
		for (int i = 0; i < BITNESS; i++) {
			pc_src_low.connectSrc(L, i, i);
			pc_src_low.connectSrc(inNOPs_A[i], 0, i+BITNESS);
			pc_src_high.connectSrc(H, i, i);
			pc_src_high.connectSrc(inNOPs_B[i], 0, i+BITNESS);
			pc_ctrl.connectSrc(pc_src_low, i, i);
			pc_ctrl.connectSrc(pc_src_high, i, i + BITNESS);
		}
		///////////////////////////////////////////////////////////////////////
		is_alu_on = new NOPNode();
		is_alu_on.connectSrc(alu_ctrl, 6, 0);
		///////////////////////////////////////////////////////////////////////
		
		initOutputToRegisters();
		initInputFromRegisters();
	}
	
	private void initJumps() {
		Mux flag_sel[] = new Mux[3];
		flag_sel[0] = new Mux();
		flag_sel[0].connectSrc(F, S_FLAG, 0);
		flag_sel[0].connectSrc(F, C_FLAG, 1);
		flag_sel[0].connectSrc(opNOPs[5], 0, 2);
		flag_sel[1] = new Mux();
		flag_sel[1].connectSrc(F, P_FLAG, 0);
		flag_sel[1].connectSrc(F, Z_FLAG, 1);
		flag_sel[1].connectSrc(opNOPs[5], 0, 2);
		flag_sel[2] = new Mux();
		flag_sel[2].connectSrc(flag_sel[0], 0, 0);
		flag_sel[2].connectSrc(flag_sel[1], 0, 1);
		flag_sel[2].connectSrc(opNOPs[4], 0, 2);
		not_jump_cond = new XORNode();
		not_jump_cond.connectSrc(flag_sel[2], 0, 0);
		not_jump_cond.connectSrc(opNOPs[3], 0, 1);
	}
	
	private void initInputFromRegisters() {
		final int REG_SEL_CNT = 3;
		// FIXME fix indirect addressing (aka 110-register)
		// Control byte format: xxDDDSSS or CCCCCSSS.
		// Also 00DDD10X means inc/dec, so DDD==SSS.
		/* Briefly: this code checks if opNOPs pattern
		 * is not 00XXX10X (not_incdec_comb).
		 * not_incdec_comb==0 means usage of DDD as source.
		 * not_incdec_comb==1 means usage of SSS as source. */
		NOTNode incdec_3rd_bit_inv = new NOTNode();
		incdec_3rd_bit_inv.connectSrc(opNOPs[2], 0, 0);
		ORNode not_incdec_pre1 = new ORNode();
		ORNode not_incdec_pre2 = new ORNode();
		ORNode not_incdec_comb = new ORNode();
		not_incdec_pre1.connectSrc(opNOPs[1], 0, 0);
		not_incdec_pre1.connectSrc(incdec_3rd_bit_inv, 0, 1);
		not_incdec_pre2.connectSrc(opNOPs[BITNESS-2], 0, 0);
		not_incdec_pre2.connectSrc(opNOPs[BITNESS-1], 0, 1);
		not_incdec_comb.connectSrc(not_incdec_pre1, 0, 0);
		not_incdec_comb.connectSrc(not_incdec_pre2, 0, 1);
		MultiMux incdec_sel = new MultiMux(REG_SEL_CNT);
		for (int i = 0; i < 2*REG_SEL_CNT; i++) {
			incdec_sel.connectSrc(opNOPs[i], 0, i);
		}
		incdec_sel.connectSrc(not_incdec_comb, 0, 2*REG_SEL_CNT);
		
		/* If it is memory store operation, then use A as source. */
		MultiOR mem_store_check = new MultiOR(REG_SEL_CNT);
		NOTNode rev_4bit = new NOTNode();
		rev_4bit.connectSrc(opNOPs[3], 0, 0);
		is_mem_store.connectSrc(is_mem_instr, 0, 0);
		is_mem_store.connectSrc(rev_4bit, 0, 1);
		for (int i = 0; i < REG_SEL_CNT; i++) {
			mem_store_check.connectSrc(incdec_sel, i, i);
			mem_store_check.connectSrc(is_mem_store, 0, i+REG_SEL_CNT);
		}
		
		ALU_in_mux_A = new MultiMux[(1<<REG_SEL_CNT) - 1];
		ALU_in_mux_A[0] = new MultiMux(BITNESS);
		ALU_in_mux_A[0].connectSrc(mem_store_check, 0, 2*BITNESS);
		for (int i = 1; i < ALU_in_mux_A.length; i++) {
			ALU_in_mux_A[i] = new MultiMux(BITNESS);
			// heap-like organization of indexes
			for (int j = 0; j < BITNESS; j++) {
				ALU_in_mux_A[(i-1)/2].connectSrc(ALU_in_mux_A[i], j, j + ((i+1)%2)*BITNESS);
			}
			// Adding small magic number to prevent rounding errors.
			int level = (int)Math.floor(Math.log(i+1)/Math.log(2) + 1e-10);
			ALU_in_mux_A[i].connectSrc(mem_store_check, level, 2*BITNESS);
		}
		
		// connecting ALU inputs
		// For rotations we should pass 1 as second op for ALU.
		// In other cases it should be A register.
		NOTNode not_rot = new NOTNode();
		not_rot.connectSrc(comb_rot_ctrl[4], 0, 0);
		for (int j = 1; j < BITNESS; j++) {
			ANDNode temp_con = new ANDNode();
			temp_con.connectSrc(not_rot, 0, 0);
			temp_con.connectSrc(A, j, 1);
			temp_con.connectDst(0, alu, j+BITNESS);
		}
		ORNode temp_con = new ORNode();
		temp_con.connectSrc(comb_rot_ctrl[4], 0, 0);
		temp_con.connectSrc(A, 0, 1);
		temp_con.connectDst(0, alu, BITNESS);
		/* If it is memory load operation, then use memory as source. */
		MultiMux mem_reg_sel = new MultiMux(BITNESS);
		is_mem_load.connectSrc(is_mem_instr, 0, 0);
		is_mem_load.connectSrc(opNOPs[3], 0, 1);
		mem_reg_sel.connectSrc(is_mem_load, 0, 2*BITNESS);
		/* Source could be data bytes or registers pair. */
		reg_pair_sel.connectSrc(opNOPs[4], 0, 4*BITNESS);// 1 -> DE, 0 -> BC
		MultiMux source_sel = new MultiMux(2*BITNESS);
		source_sel.connectSrc(opNOPs[5], 0, 4*BITNESS);
		for (int j = 0; j < BITNESS; j++) {
			reg_pair_sel.connectSrc(E, j, j);
			reg_pair_sel.connectSrc(C, j, j+2*BITNESS);
			reg_pair_sel.connectSrc(D, j, j+BITNESS);
			reg_pair_sel.connectSrc(B, j, j+3*BITNESS);
			source_sel.connectSrc(inNOPs_A[j], 0, j);
			source_sel.connectSrc(reg_pair_sel, j, j+2*BITNESS);
			source_sel.connectSrc(inNOPs_B[j], 0, j+BITNESS);
			source_sel.connectSrc(reg_pair_sel, j+BITNESS, j+3*BITNESS);
			mem_ro.connectSrc(source_sel, j, j);
			mem_ro.connectSrc(source_sel, j+BITNESS, j+BITNESS);
			mem_reg_sel.connectSrc(mem_ro, j, j);
			mem_reg_sel.connectSrc(ALU_in_mux_A[0], j, j+BITNESS);
			mem_reg_sel.connectDst(j, alu, j);
		}
		for (int j = 0; j < BITNESS; j++) {
			alu_ctrl.connectSrc(opNOPs[j], 0, j);
		}
		for (int j = 0; j < ALU.NUM_CMD_BITS; j++) {
			alu_ctrl.connectDst(j, alu, j+2*BITNESS);
		}
		
		// connecting registers
		for (int j = 0; j < BITNESS; j++) {
			// 000
			B.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-1], j+BITNESS);
			// 100
			H.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-1], j);
			// 010
			D.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-2], j+BITNESS);
			// 110
			inNOPs_A[j].connectDst(0, ALU_in_mux_A[ALU_in_mux_A.length-2], j);
			// 001
			C.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-3], j+BITNESS);
			// 101
			L.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-3], j);
			// 011
			E.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-4], j+BITNESS);
			// 111
			A.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-4], j);
		}
	}
	
	private void initOutputToRegisters() {
		// It seems like we can take in account last opNOPs bit
		//   to check if there is a specific destination.
		ORNode dst_ctrl[] = new ORNode[3];
		for (int i = 0; i < dst_ctrl.length; i++) {
			dst_ctrl[i] = new ORNode();
			dst_ctrl[i].connectSrc(opNOPs[BITNESS-1], 0, 0)
					   .connectSrc(opNOPs[3+i], 0, 1);
		}
		// Also 000XX111 pattern (rotations) should set A as destination.
		ORNode dst_ctrl_rot[] = new ORNode[3];
		MultiNOT rev_high_3bit = new MultiNOT(3);
		comb_rot_ctrl = new ANDNode[5];
		for (int i = 0; i < 3; i++) {
			rev_high_3bit.connectSrc(opNOPs[BITNESS-1-i], 0, i);
			comb_rot_ctrl[i] = new ANDNode();
			comb_rot_ctrl[i].connectSrc(opNOPs[i], 0, 0);
			comb_rot_ctrl[i].connectSrc(rev_high_3bit, i, 1);
		}
		comb_rot_ctrl[3] = new ANDNode();
		comb_rot_ctrl[3].connectSrc(comb_rot_ctrl[0], 0, 0);
		comb_rot_ctrl[3].connectSrc(comb_rot_ctrl[1], 0, 1);
		comb_rot_ctrl[4] = new ANDNode();
		comb_rot_ctrl[4].connectSrc(comb_rot_ctrl[2], 0, 0);
		comb_rot_ctrl[4].connectSrc(comb_rot_ctrl[3], 0, 1);
		for (int i = 0; i < dst_ctrl_rot.length; i++) {
			dst_ctrl_rot[i] = new ORNode();
			dst_ctrl_rot[i].connectSrc(dst_ctrl[i], 0, 0)
			   			   .connectSrc(comb_rot_ctrl[4], 0, 1);
		}
		// Special case: CMA command 00101111, DST = A.
		MultiNOT revs = new MultiNOT(3);
		revs.connectSrc(opNOPs[7], 0, 0);
		revs.connectSrc(opNOPs[6], 0, 1);
		revs.connectSrc(opNOPs[4], 0, 2);
		AllNode is_cma = new AllNode(BITNESS);
		is_cma.connectSrc(opNOPs[0], 0, 0);
		is_cma.connectSrc(opNOPs[1], 0, 1);
		is_cma.connectSrc(opNOPs[2], 0, 2);
		is_cma.connectSrc(opNOPs[3], 0, 3);
		is_cma.connectSrc(revs, 0, 4);
		is_cma.connectSrc(opNOPs[5], 0, 5);
		is_cma.connectSrc(revs, 1, 6);
		is_cma.connectSrc(revs, 2, 7);
		MultiOR cma_ctrl = new MultiOR(3);
		cma_ctrl.connectSrc(dst_ctrl_rot[0], 0, 0);
		cma_ctrl.connectSrc(dst_ctrl_rot[1], 0, 1);
		cma_ctrl.connectSrc(dst_ctrl_rot[2], 0, 2);
		cma_ctrl.connectSrc(is_cma, 0, 3);
		cma_ctrl.connectSrc(is_cma, 0, 4);
		cma_ctrl.connectSrc(is_cma, 0, 5);
		// Also for some of load operations, we should use A as DST. 
		MultiOR is_a_dst = new MultiOR(3);
		for (int i = 0; i < 3; i++) {
			is_a_dst.connectSrc(is_mem_load, 0, i+3);
			is_a_dst.connectSrc(cma_ctrl, i, i);
		}
		/* For store operations memory should be a source. */
		Node ctrl_mem[] = new Node[3];
		ctrl_mem[0] = new ANDNode();
		NOTNode neg_is_mem_store = new NOTNode();
		neg_is_mem_store.connectSrc(is_mem_store, 0, 0);
		ctrl_mem[0].connectSrc(neg_is_mem_store, 0, 0);
		ctrl_mem[0].connectSrc(is_a_dst, 0, 1);
		ctrl_mem[1] = new ORNode();
		ctrl_mem[1].connectSrc(is_mem_store, 0, 0);
		ctrl_mem[1].connectSrc(is_a_dst, 1, 1);
		ctrl_mem[2] = new ORNode();
		ctrl_mem[2].connectSrc(is_mem_store, 0, 0);
		ctrl_mem[2].connectSrc(is_a_dst, 2, 1);
		
		
		ANDNode comb_alu = new ANDNode();
		comb_alu.connectSrc(is_alu_on, 0, 0);
		comb_alu.connectSrc(clock, 0, 1);
		out_demux = new DeMux[(1<<3) - 1];
		out_demux[0] = new DeMux();
		out_demux[0].connectSrc(comb_alu, 0, 0);
		out_demux[0].connectSrc(ctrl_mem[0], 0, 1);
		for (int i = 1; i < out_demux.length; i++) {
			out_demux[i] = new DeMux();
			// heap-like organization of indexes
			out_demux[(i-1)/2].connectDst((i+1)%2, out_demux[i], 0);
			// Adding small magic number to prevent rounding errors.
			int level = (int)Math.floor(Math.log(i+1)/Math.log(2) + 1e-10);
			out_demux[i].connectSrc(ctrl_mem[level], 0, 1);
		}
		
		// connecting registers
		// 000
		B.connectSrc(out_demux[out_demux.length-1], 1, BITNESS);
		// 100
		H.connectSrc(out_demux[out_demux.length-1], 0, BITNESS);
		// 010
		D.connectSrc(out_demux[out_demux.length-2], 1, BITNESS);
		// 110
		mem.connectSrc(out_demux[out_demux.length-2], 0, 3*BITNESS);
		// 001
		C.connectSrc(out_demux[out_demux.length-3], 1, BITNESS);
		// 101
		L.connectSrc(out_demux[out_demux.length-3], 0, BITNESS);
		// 011
		E.connectSrc(out_demux[out_demux.length-4], 1, BITNESS);
		// 111
		A.connectSrc(out_demux[out_demux.length-4], 0, BITNESS);
		// flags
		// enable flags for comparisons [1x111SSS]
		Node is_cmp = new AllNode(4);
		is_cmp.connectSrc(opNOPs[7], 0, 0);
		is_cmp.connectSrc(opNOPs[5], 0, 1);
		is_cmp.connectSrc(opNOPs[4], 0, 2);
		is_cmp.connectSrc(opNOPs[3], 0, 3);
		ORNode set_flags = new ORNode();
		set_flags.connectSrc(is_cmp, 0, 0);
		set_flags.connectSrc(comb_alu, 0, 1);
		// also enable flags for CPC & STC ops
		Node is_c_op = new AllNode(7);
		Node rev_7bit = new NOTNode();
		rev_7bit.connectSrc(opNOPs[7], 0, 0);
		Node rev_6bit = new NOTNode();
		rev_6bit.connectSrc(opNOPs[6], 0, 0);
		is_c_op.connectSrc(rev_7bit, 0, 0);
		is_c_op.connectSrc(rev_6bit, 0, 1);
		is_c_op.connectSrc(opNOPs[5], 0, 2);
		is_c_op.connectSrc(opNOPs[4], 0, 3);
		is_c_op.connectSrc(opNOPs[2], 0, 4);
		is_c_op.connectSrc(opNOPs[1], 0, 5);
		is_c_op.connectSrc(opNOPs[0], 0, 6);
		ORNode set_flags_enh = new ORNode();
		set_flags_enh.connectSrc(set_flags, 0, 0);
		set_flags_enh.connectSrc(is_c_op, 0, 1);
		F.connectSrc(set_flags_enh, 0, BITNESS);
		
		// connect ALU output
		for (int i = 0; i < BITNESS; i++) {
			A.connectSrc(alu, i, i);
			B.connectSrc(alu, i, i);
			C.connectSrc(alu, i, i);
			D.connectSrc(alu, i, i);
			E.connectSrc(alu, i, i);
			H.connectSrc(alu, i, i);
			L.connectSrc(alu, i, i);
			mem.connectSrc(alu, i, i+2*BITNESS);
			// for initialization
			A.connectSrc(zero, 0, i);
			B.connectSrc(zero, 0, i);
			C.connectSrc(zero, 0, i);
			D.connectSrc(zero, 0, i);
			E.connectSrc(zero, 0, i);
			H.connectSrc(zero, 0, i);
			L.connectSrc(zero, 0, i);
			F.connectSrc(zero, 0, i);
			PC.connectSrc(zero, 0, i);
			PC.connectSrc(zero, 0, i + BITNESS);
		}
		// memory addressing
		MultiMux addr_sel = new MultiMux(2*BITNESS);
		NOTNode neg_bit5 = new NOTNode();
		neg_bit5.connectSrc(opNOPs[5], 0, 0);
		ANDNode is_reg_pair = new ANDNode();
		is_reg_pair.connectSrc(neg_bit5, 0, 0);
		is_reg_pair.connectSrc(is_mem_store, 0, 1);
		addr_sel.connectSrc(is_reg_pair, 0, 4*BITNESS);
		for (int i = 0; i < BITNESS; i++) {
			addr_sel.connectSrc(reg_pair_sel, i, i);
			addr_sel.connectSrc(reg_pair_sel, i+BITNESS, i+BITNESS);
			addr_sel.connectSrc(inNOPs_A[i], 0, i+2*BITNESS);
			addr_sel.connectSrc(inNOPs_B[i], 0, i+3*BITNESS);
			mem.connectSrc(addr_sel, i, i);
			mem.connectSrc(addr_sel, i+BITNESS, i+BITNESS);
		}
		// TODO prevent flags change for mov, inc, dec etc.
		Mux not_c_flag_val = new Mux();
		not_c_flag_val.connectSrc(F, C_FLAG, 0);
		not_c_flag_val.connectSrc(opNOPs[3], 0, 1);
		not_c_flag_val.connectSrc(opNOPs[3], 0, 2);
		Node c_flag_val = new NOTNode();
		c_flag_val.connectSrc(not_c_flag_val, 0, 0);
		Mux c_flag_src = new Mux();
		c_flag_src.connectSrc(c_flag_val, 0, 0);
		c_flag_src.connectSrc(alu, BITNESS+ALU.C_FLAG_SHIFT, 1);
		c_flag_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(c_flag_src, 0, C_FLAG);
		Mux z_flag_src = new Mux();
		z_flag_src.connectSrc(F, Z_FLAG, 0);
		z_flag_src.connectSrc(alu, BITNESS+ALU.Z_FLAG_SHIFT, 1);
		z_flag_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(z_flag_src, 0, Z_FLAG);
		Mux s_flag_src = new Mux();
		s_flag_src.connectSrc(F, S_FLAG, 0);
		s_flag_src.connectSrc(alu, BITNESS+ALU.S_FLAG_SHIFT, 1);
		s_flag_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(s_flag_src, 0, S_FLAG);
		Mux p_flag_src = new Mux();
		p_flag_src.connectSrc(F, P_FLAG, 0);
		p_flag_src.connectSrc(alu, BITNESS+ALU.P_FLAG_SHIFT, 1);
		p_flag_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(p_flag_src, 0, P_FLAG);
		// TODO implement A-flag (H-flag)
		F.connectSrc(clock, 0, H_FLAG);
		F.connectSrc(clock, 0, I_FLAG);
		F.connectSrc(clock, 0, F3_FLAG);
		F.connectSrc(clock, 0, F1_FLAG);
		
		// for initialization
		A.connectSrc(zero, 0, BITNESS);
		B.connectSrc(zero, 0, BITNESS);
		C.connectSrc(zero, 0, BITNESS);
		D.connectSrc(zero, 0, BITNESS);
		E.connectSrc(zero, 0, BITNESS);
		H.connectSrc(zero, 0, BITNESS);
		L.connectSrc(zero, 0, BITNESS);
		F.connectSrc(zero, 0, BITNESS);
		PC.connectSrc(zero, 0, 2*BITNESS);
	}
	
	/** Input: Opcode byte followed by two data bytes. */
	@Override
	public Node in(int index, boolean value) {
		if (index < BITNESS)
			opNOPs[index].in(0, value);
		else if (index < 2*BITNESS)
			inNOPs_A[index-BITNESS].in(0, value);
		else if (index < 3*BITNESS)
			inNOPs_B[index-2*BITNESS].in(0, value);
		else
			clock.in(0, value);
		return this;
	}

	/** Returns address of next instruction for execution. */
	@Override
	public boolean out(int index) {
		return PC.out(index);
	}

	@Override
	public boolean isReady() {
		for (Node n : opNOPs)
			if (!n.isReady())
				return false;
		for (Node n : inNOPs_A)
			if (!n.isReady())
				return false;
		for (Node n : inNOPs_B)
			if (!n.isReady())
				return false;
		return clock.isReady();
	}

	@Override
	public void reset() {
		if (inNOPs_A != null) {
			for (int i = 0; i < BITNESS; i++) {
				inNOPs_A[i].reset();
				inNOPs_B[i].reset();
			}
		}
		if (opNOPs != null) {
			for (int i = 0; i < BITNESS; i++) {
				opNOPs[i].reset();
			}
		}
		if (clock != null)
			clock.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (int i = 0; i < BITNESS; i++) {
			inNOPs_A[i].propagate();
			inNOPs_B[i].propagate();
		}
		for (int i = 0; i < BITNESS; i++) {
			opNOPs[i].propagate();
		}
		clock.propagate();
		super.propagate(true);
	}

}
