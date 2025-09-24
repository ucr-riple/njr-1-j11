package net.dtkanov.blocks.circuit.high_level.derived;

import net.dtkanov.blocks.circuit.MultiAND;
import net.dtkanov.blocks.circuit.MultiNOT;
import net.dtkanov.blocks.circuit.MultiOR;
import net.dtkanov.blocks.circuit.MultiXOR;
import net.dtkanov.blocks.circuit.Mux;
import net.dtkanov.blocks.circuit.high_level.AdvancedRotator;
import net.dtkanov.blocks.circuit.high_level.AdvancedShifter;
import net.dtkanov.blocks.circuit.high_level.Complementer;
import net.dtkanov.blocks.circuit.high_level.Incrementer;
import net.dtkanov.blocks.circuit.high_level.MultiAdder;
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.blocks.logic.derived.XORNode;
/** Implements 2 operands arithmetic-logic unit. */
public class ALU extends Node {
	/** Number of bits representing command selection. */
	public static final int NUM_CMD_BITS = 4; 
	public static final int Z_FLAG_SHIFT = 0;
	public static final int S_FLAG_SHIFT = 1;
	public static final int P_FLAG_SHIFT = 2;
	public static final int C_FLAG_SHIFT = 3;
	
	protected int bitness;
	protected Node inNOPs_A[];
	protected Node inNOPs_B[];
	protected Node opNOPs[];
	protected Node outMUXs[];
	protected Node out_z_flag;
	protected Node out_s_flag;
	protected Node out_p_flag;
	protected Node out_c_flag;
	// operations
	protected MultiAND opAND;
	protected MultiOR opOR;
	protected MultiXOR opXOR;
	protected MultiNOT opNOT;
	protected AdvancedShifter sh_left;
	protected AdvancedShifter sh_right;
	protected AdvancedRotator rot_left;
	protected AdvancedRotator rot_right;
	protected MultiAdder adder;
	protected Complementer compl;
	protected MultiAdder subtr;
	protected ConstantNode zero;
	protected Incrementer inc;
	protected Complementer compl_pre;
	protected Incrementer inc_dec;
	protected Complementer compl_post;
	protected ORNode cmpz[];
	
	public ALU(int num_bits) {
		super(null);
		bitness = num_bits;
		inNOPs_A = new NOPNode[num_bits];
		inNOPs_B = new NOPNode[num_bits];
		for (int i = 0; i < num_bits; i++) {
			inNOPs_A[i] = new NOPNode();
			inNOPs_B[i] = new NOPNode();
		}
		opNOPs = new NOPNode[NUM_CMD_BITS];
		for (int i = 0; i < NUM_CMD_BITS; i++) {
			opNOPs[i] = new NOPNode();
		}
		out_z_flag = new NOPNode();
		out_s_flag = new NOPNode();
		out_p_flag = new NOPNode();
		out_c_flag = new NOPNode();
		outMUXs = new MultiMux[(1<<NUM_CMD_BITS) - 1];
		outMUXs[0] = new MultiMux(bitness);
		outMUXs[0].connectSrc(opNOPs[0], 0, 2*bitness);
		for (int i = 1; i < outMUXs.length; i++) {
			outMUXs[i] = new MultiMux(bitness);
			// heap-like organization of indexes
			for (int j = 0; j < bitness; j++) {
				outMUXs[(i-1)/2].connectSrc(outMUXs[i], j, j + ((i+1)%2)*bitness);
			}
			// Adding small magic number to prevent rounding errors.
			int level = (int)Math.floor(Math.log(i+1)/Math.log(2) + 1e-10);
			outMUXs[i].connectSrc(opNOPs[level], 0, 2*bitness);
		}
		initOperations();
		initFlags();
	}
	
	protected void initFlags() {
		// Z-flag. Set if result is zero.
		ORNode cmpz_out[] = new ORNode[bitness-1];
		cmpz_out[0] = new ORNode();
		cmpz_out[0].connectSrc(outMUXs[0], 0, 0);
		cmpz_out[0].connectSrc(outMUXs[0], 1, 1);
		for (int j = 1; j < bitness-1; j++) {
			cmpz_out[j] = new ORNode();
			cmpz_out[j].connectSrc(outMUXs[0], j+1, 0);
			cmpz_out[j].connectSrc(cmpz_out[j-1], 0, 1);
		}
		NOTNode cmp_neg = new NOTNode();
		cmp_neg.connectSrc(cmpz_out[bitness-2], 0, 0);
		cmp_neg.connectDst(0, out_z_flag, 0);
		
		// S-flag. Set if result is negative.
		out_s_flag.connectSrc(outMUXs[0], bitness-1, 0);
		
		// P-flag. Set if number of 1s in result is even, e.g. 0110.
		// TODO. What is a proper value for zero result of operation?
		XORNode ones_out[] = new XORNode[bitness-1];
		ones_out[0] = new XORNode();
		ones_out[0].connectSrc(outMUXs[0], 0, 0);
		ones_out[0].connectSrc(outMUXs[0], 1, 1);
		for (int j = 1; j < bitness-1; j++) {
			ones_out[j] = new XORNode();
			ones_out[j].connectSrc(outMUXs[0], j+1, 0);
			ones_out[j].connectSrc(ones_out[j-1], 0, 1);
		}
		NOTNode ones_neg = new NOTNode();
		ones_neg.connectSrc(ones_out[bitness-2], 0, 0);
		ones_neg.connectDst(0, out_p_flag, 0);
		
		// C-flag. Set if it was set
		// in last add/sub/rotate/shift/inc/dec ops. Zero otherwise.
		Mux c_muxs[] = new Mux[7];
		c_muxs[0] = new Mux();
		c_muxs[0].connectSrc(sh_right, bitness, 0);
		c_muxs[0].connectSrc(sh_left, bitness, 1);
		c_muxs[0].connectSrc(opNOPs[3], 0, 2);
		
		c_muxs[1] = new Mux();
		c_muxs[1].connectSrc(outMUXs[0], bitness-1, 0); // rotate right
		c_muxs[1].connectSrc(outMUXs[0], 0, 1); // rotate left
		c_muxs[1].connectSrc(opNOPs[3], 0, 2);
		
		c_muxs[2] = new Mux();
		NOTNode subtr_neg = new NOTNode();
		subtr_neg.connectSrc(subtr, bitness, 0);
		c_muxs[2].connectSrc(subtr_neg, 0, 0);
		c_muxs[2].connectSrc(adder, bitness, 1);
		c_muxs[2].connectSrc(opNOPs[3], 0, 2);
		
		c_muxs[3] = new Mux();
		c_muxs[3].connectSrc(inc_dec, bitness, 0);
		c_muxs[3].connectSrc(inc, bitness, 1);
		c_muxs[3].connectSrc(opNOPs[3], 0, 2);
		
		c_muxs[4] = new Mux();
		c_muxs[4].connectSrc(c_muxs[1], bitness, 0);
		c_muxs[4].connectSrc(c_muxs[0], bitness, 1);
		c_muxs[4].connectSrc(opNOPs[2], 0, 2);
		
		c_muxs[5] = new Mux();
		c_muxs[5].connectSrc(c_muxs[3], bitness, 0);
		c_muxs[5].connectSrc(c_muxs[2], bitness, 1);
		c_muxs[5].connectSrc(opNOPs[2], 0, 2);
		
		c_muxs[6] = new Mux();
		c_muxs[6].connectSrc(c_muxs[4], bitness, 0);
		c_muxs[6].connectSrc(c_muxs[5], bitness, 1);
		c_muxs[6].connectSrc(opNOPs[1], 0, 2);
		
		XORNode is_c = new XORNode();
		is_c.connectSrc(opNOPs[0], 0, 0);
		is_c.connectSrc(opNOPs[1], 0, 1);
		
		ANDNode res_c = new ANDNode();
		res_c.connectSrc(is_c, 0, 0);
		res_c.connectSrc(c_muxs[6], 0, 1);
		out_c_flag.connectSrc(res_c, 0, 0);
	}
	
	protected void initOperations() {
		opAND = new MultiAND(bitness);
		for (int j = 0; j < bitness; j++) {
			opAND.connectSrc(inNOPs_A[j], 0, j);
			opAND.connectSrc(inNOPs_B[j], 0, j+bitness);
			// 0000
			opAND.connectDst(j, outMUXs[outMUXs.length-1], j+bitness);
		}
		opOR = new MultiOR(bitness);
		for (int j = 0; j < bitness; j++) {
			opOR.connectSrc(inNOPs_A[j], 0, j);
			opOR.connectSrc(inNOPs_B[j], 0, j+bitness);
			// 1000
			opOR.connectDst(j, outMUXs[outMUXs.length-1], j);
		}
		opXOR = new MultiXOR(bitness);
		for (int j = 0; j < bitness; j++) {
			opXOR.connectSrc(inNOPs_A[j], 0, j);
			opXOR.connectSrc(inNOPs_B[j], 0, j+bitness);
			// 0100
			opXOR.connectDst(j, outMUXs[outMUXs.length-2], j+bitness);
		}
		opNOT = new MultiNOT(bitness);
		for (int j = 0; j < bitness; j++) {
			opNOT.connectSrc(inNOPs_A[j], 0, j);
			// 1100
			opNOT.connectDst(j, outMUXs[outMUXs.length-2], j);
		}
		sh_left = new AdvancedShifter(bitness, true);
		for (int j = 0; j < bitness; j++) {
			sh_left.connectSrc(inNOPs_A[j], 0, j);
			// 0010
			sh_left.connectDst(j, outMUXs[outMUXs.length-3], j+bitness);
		}
		for (int j = 0; j < sh_left.countCtrlBits(); j++) {
			sh_left.connectSrc(inNOPs_B[j], 0, j+bitness);
		}
		sh_right = new AdvancedShifter(bitness, false);
		for (int j = 0; j < bitness; j++) {
			sh_right.connectSrc(inNOPs_A[j], 0, j);
			// 1010
			sh_right.connectDst(j, outMUXs[outMUXs.length-3], j);
		}
		for (int j = 0; j < sh_right.countCtrlBits(); j++) {
			sh_right.connectSrc(inNOPs_B[j], 0, j+bitness);
		}
		rot_left = new AdvancedRotator(bitness, true);
		for (int j = 0; j < bitness; j++) {
			rot_left.connectSrc(inNOPs_A[j], 0, j);
			// 0110
			rot_left.connectDst(j, outMUXs[outMUXs.length-4], j+bitness);
		}
		for (int j = 0; j < rot_left.countCtrlBits(); j++) {
			rot_left.connectSrc(inNOPs_B[j], 0, j+bitness);
		}
		rot_right = new AdvancedRotator(bitness, false);
		for (int j = 0; j < bitness; j++) {
			rot_right.connectSrc(inNOPs_A[j], 0, j);
			// 1110
			rot_right.connectDst(j, outMUXs[outMUXs.length-4], j);
		}
		for (int j = 0; j < rot_right.countCtrlBits(); j++) {
			rot_right.connectSrc(inNOPs_B[j], 0, j+bitness);
		}
		zero = new ConstantNode(false);
		zero.connectSrc(inNOPs_A[0], 0, 0);
		adder = new MultiAdder(bitness);
		for (int j = 0; j < bitness; j++) {
			adder.connectSrc(inNOPs_A[j], 0, j);
			adder.connectSrc(inNOPs_B[j], 0, j + bitness);
			// 0001
			adder.connectDst(j, outMUXs[outMUXs.length-5], j+bitness);
		}
		adder.connectSrc(zero, 0, 2*bitness);
		compl = new Complementer(bitness);
		subtr = new MultiAdder(bitness);
		for (int j = 0; j < bitness; j++) {
			subtr.connectSrc(inNOPs_B[j], 0, j);
			compl.connectSrc(inNOPs_A[j], 0, j);
			subtr.connectSrc(compl, j, j + bitness);
			// 1001
			subtr.connectDst(j, outMUXs[outMUXs.length-5], j);
		}
		subtr.connectSrc(zero, 0, 2*bitness);
		inc = new Incrementer(bitness);
		for (int j = 0; j < bitness; j++) {
			inc.connectSrc(inNOPs_A[j], 0, j);
			// 0101
			inc.connectDst(j, outMUXs[outMUXs.length-6], j+bitness);
		}
		compl_pre = new Complementer(bitness);
		compl_post = new Complementer(bitness);
		inc_dec = new Incrementer(bitness);
		for (int j = 0; j < bitness; j++) {
			compl_pre.connectSrc(inNOPs_A[j], 0, j);
			inc_dec.connectSrc(compl_pre, j, j);
			compl_post.connectSrc(inc_dec, j, j);
			// 1101
			compl_post.connectDst(j, outMUXs[outMUXs.length-6], j);
		}
		cmpz = new ORNode[bitness-1];
		cmpz[0] = new ORNode();
		cmpz[0].connectSrc(inNOPs_A[0], 0, 0);
		cmpz[0].connectSrc(inNOPs_A[1], 0, 1);
		for (int j = 1; j < bitness-1; j++) {
			cmpz[j] = new ORNode();
			cmpz[j].connectSrc(inNOPs_A[j+1], 0, 0);
			cmpz[j].connectSrc(cmpz[j-1], 0, 1);
		}
		// 0011
		cmpz[bitness-2].connectDst(0, outMUXs[outMUXs.length-7], bitness);
		for (int j = 1; j < bitness; j++) {
			zero.connectDst(0, outMUXs[outMUXs.length-7], j+bitness);
		}
		// 1011
		inNOPs_A[bitness-1].connectDst(0, outMUXs[outMUXs.length-7], 0);
		for (int j = 1; j < bitness; j++) {
			zero.connectDst(0, outMUXs[outMUXs.length-7], j);
		}
		for (int j = 0; j < bitness; j++) {
			// 0111
			outMUXs[outMUXs.length-8].connectSrc(inNOPs_A[j], 0, j + bitness);
			// 1111
			outMUXs[outMUXs.length-8].connectSrc(inNOPs_B[j], 0, j);
		}
		// stub for all other operations
		for (int i = outMUXs.length-9; i >= outMUXs.length-(1<<(NUM_CMD_BITS-1)); i--) {
			for (int j = 0; j < bitness; j++) {
				outMUXs[i].connectSrc(inNOPs_A[j], 0, j);
				outMUXs[i].connectSrc(inNOPs_B[j], 0, j + bitness);
			}
		}
	}

	@Override
	public Node in(int index, boolean value) {
		if (index < bitness) {// first operand
			inNOPs_A[index].in(0, value);
		} else if (index < 2*bitness) {// second operand
			inNOPs_B[index-bitness].in(0, value);
		} else {// control
			opNOPs[index - 2*bitness].in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index < bitness)
			return outMUXs[0].out(index);
		else if (index == bitness) // Z-flag
			return out_z_flag.out(0);
		else if (index == bitness + 1) // S-flag
			return out_s_flag.out(0);
		else if (index == bitness + 2) // P-flag
			return out_p_flag.out(0);
		else // C-flag
			return out_c_flag.out(0);
	}

	@Override
	public boolean isReady() {
		for (int i = 0; i < bitness; i++) {
			if (!inNOPs_A[i].isReady() || !inNOPs_B[i].isReady())
				return false;
		}
		for (int i = 0; i < NUM_CMD_BITS; i++) {
			if (!opNOPs[i].isReady())
				return false;
		}
		return true;
	}

	@Override
	public void reset() {
		if (inNOPs_A != null) {
			for (int i = 0; i < bitness; i++) {
				inNOPs_A[i].reset();
				inNOPs_B[i].reset();
			}
		}
		if (opNOPs != null) {
			for (int i = 0; i < NUM_CMD_BITS; i++) {
				opNOPs[i].reset();
			}
		}
	}

	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (int i = 0; i < bitness; i++) {
			inNOPs_A[i].propagate();
			inNOPs_B[i].propagate();
		}
		for (int i = 0; i < NUM_CMD_BITS; i++) {
			opNOPs[i].propagate();
		}
		super.propagate(true);
	}
}
