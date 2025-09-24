package net.dtkanov.blocks.tests;

import static org.junit.Assert.*;

import net.dtkanov.blocks.circuit.*;
import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.logic.*;

import org.junit.Test;

public class CircuitsTest {

	@Test
	public void SRLatchTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(false);
		Node out = new SRLatch().connectSrc(in1, 0, 0)
								.connectSrc(in2, 0, 1);
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertFalse(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertFalse(out.out(0));
		
		boolean state = out.out(0);
		in1.setValue(false).propagate();
		in2.propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertTrue(out.out(0)==state);
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertTrue(out.out(0)==state);
		
		in1.propagate();
		in2.setValue(true).propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertTrue(out.out(0));
		
		in1.propagate();
		in2.propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertTrue(out.out(0));
		
		state = out.out(0);
		in1.propagate();
		in2.setValue(false).propagate();
		assertTrue(out.out(0)!=out.out(1));
		assertTrue(out.out(0)==state);
	}
	
	@Test
	public void AllTest() {
		Node all = new AllNode(4);
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(false);
		ConstantNode in3 = new ConstantNode(true);
		ConstantNode in4 = new ConstantNode(false);
		all.connectSrc(in1, 0, 0);
		all.connectSrc(in2, 0, 1);
		all.connectSrc(in3, 0, 2);
		all.connectSrc(in4, 0, 3);
		
		in1.propagate();
		in2.propagate();
		in3.propagate();
		in4.propagate();
		assertTrue(all.out(0)==false);
		
		in1.setValue(false).propagate();
		in2.setValue(false).propagate();
		in3.setValue(false).propagate();
		in4.setValue(false).propagate();
		assertTrue(all.out(0)==false);
		
		in1.setValue(true).propagate();
		in2.setValue(true).propagate();
		in3.setValue(true).propagate();
		in4.setValue(true).propagate();
		assertTrue(all.out(0)==true);
	}
	
	@Test
	public void GatedDLatchTest() {
		ConstantNode in = new ConstantNode(true);
		ConstantNode timer = new ConstantNode(true);
		Node out = new GatedDLatch().connectSrc(in, 0, 0)
									.connectSrc(timer, 0, 1);
		in.propagate();
		timer.propagate();
		assertTrue(out.out(0));
		
		in.propagate();
		timer.setValue(false).propagate();
		assertTrue(out.out(0));
		
		in.setValue(false).propagate();
		timer.setValue(true).propagate();
		assertFalse(out.out(0));
		
		in.propagate();
		timer.setValue(false).propagate();
		assertFalse(out.out(0));
		
		in.setValue(true).propagate();
		timer.setValue(true).propagate();
		assertTrue(out.out(0));
		
		in.propagate();
		timer.setValue(false).propagate();
		assertTrue(out.out(0));
		
		in.propagate();
		timer.propagate();
		assertTrue(out.out(0));
	}
	
	@Test
	public void MuxTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(false);
		ConstantNode control = new ConstantNode(true);
		Node mux = new Mux().connectSrc(in1, 0, 0)
							.connectSrc(in2, 0, 1)
							.connectSrc(control, 0, 2);
		
		in1.propagate();
		in2.propagate();
		control.propagate();
		assertTrue(mux.out(0));
		
		in1.propagate();
		in2.propagate();
		control.propagate();
		assertTrue(mux.out(0));
		
		in1.propagate();
		in2.propagate();
		control.setValue(false).propagate();
		assertFalse(mux.out(0));
		
		in1.propagate();
		in2.setValue(true).propagate();
		control.propagate();
		assertTrue(mux.out(0));
	}
	
	@Test
	public void DeMuxTest() {
		ConstantNode in = new ConstantNode(true);
		ConstantNode control = new ConstantNode(true);
		Node mux = new DeMux().connectSrc(in, 0, 0)
							  .connectSrc(control, 0, 1);
		
		in.propagate();
		control.propagate();
		assertTrue(mux.out(0)==true);
		assertTrue(mux.out(1)==false);
		
		in.setValue(false).propagate();
		control.propagate();
		assertTrue(mux.out(0)==false);
		assertTrue(mux.out(1)==false);
		
		in.setValue(true).propagate();
		control.setValue(false).propagate();
		assertTrue(mux.out(0)==false);
		assertTrue(mux.out(1)==true);
		
		in.setValue(true).propagate();
		control.setValue(false).propagate();
		assertTrue(mux.out(0)==false);
		assertTrue(mux.out(1)==true);
		
		in.setValue(false).propagate();
		control.setValue(false).propagate();
		assertTrue(mux.out(0)==false);
		assertTrue(mux.out(1)==false);
	}
	
	@Test
	public void MultiNOTTest() {
		int num_bits = 4;
		Node reg = new MultiNOT(num_bits);
		ConstantNode in[] = new ConstantNode[num_bits];
		for (int i = 0; i < num_bits; i++) {
			in[i] = new ConstantNode(true);
			in[i].connectDst(0, reg, i);
		}
		
		for (int i = 0; i < num_bits; i++) {
			in[i].propagate();
		}
		for (int i = 0; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in[i].propagate();
		}
		assertTrue(reg.out(0));
		for (int i = 1; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in[i].propagate();
		}
		assertTrue(reg.out(0));
		assertTrue(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in[0].setValue(true);
		for (int i = 0; i < num_bits; i++) {
			in[i].propagate();
		}
		assertFalse(reg.out(0));
		assertTrue(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
	}
	
	@Test
	public void MultiANDTest() {
		int num_bits = 4;
		Node reg = new MultiAND(num_bits);
		ConstantNode in1[] = new ConstantNode[num_bits];
		ConstantNode in2[] = new ConstantNode[num_bits];
		for (int i = 0; i < num_bits; i++) {
			in1[i] = new ConstantNode(true);
			in1[i].connectDst(0, reg, i);
			in2[i] = new ConstantNode(true);
			in2[i].connectDst(1, reg, i+num_bits);
		}
		
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		for (int i = 0; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in1[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		for (int i = 1; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in2[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		assertFalse(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in1[0].setValue(true);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertTrue(reg.out(0));
		assertFalse(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
	}
	
	@Test
	public void MultiORTest() {
		int num_bits = 4;
		Node reg = new MultiOR(num_bits);
		ConstantNode in1[] = new ConstantNode[num_bits];
		ConstantNode in2[] = new ConstantNode[num_bits];
		for (int i = 0; i < num_bits; i++) {
			in1[i] = new ConstantNode(true);
			in1[i].connectDst(0, reg, i);
			in2[i] = new ConstantNode(true);
			in2[i].connectDst(1, reg, i+num_bits);
		}
		
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		for (int i = 0; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in1[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		for (int i = 0; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in2[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		for (int i = 1; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in2[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		assertTrue(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
		
		in1[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		assertFalse(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertTrue(reg.out(i));
		}
	}
	
	@Test
	public void MultiXORTest() {
		int num_bits = 4;
		Node reg = new MultiXOR(num_bits);
		ConstantNode in1[] = new ConstantNode[num_bits];
		ConstantNode in2[] = new ConstantNode[num_bits];
		for (int i = 0; i < num_bits; i++) {
			in1[i] = new ConstantNode(true);
			in1[i].connectDst(0, reg, i);
			in2[i] = new ConstantNode(true);
			in2[i].connectDst(1, reg, i+num_bits);
		}
		
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		for (int i = 0; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in1[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertTrue(reg.out(0));
		for (int i = 1; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in2[0].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		for (int i = 1; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in2[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		assertTrue(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
		
		in1[1].setValue(false);
		for (int i = 0; i < num_bits; i++) {
			in1[i].propagate();
			in2[i].propagate();
		}
		assertFalse(reg.out(0));
		assertFalse(reg.out(1));
		for (int i = 2; i < num_bits; i++) {
			assertFalse(reg.out(i));
		}
	}
	
	@Test
	public void AdderTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode in2 = new ConstantNode(true);
		ConstantNode cin = new ConstantNode(true);
		Node add = new Adder();
		add.connectSrc(in1, 0, 0)
		   .connectSrc(in2, 0, 1)
		   .connectSrc(cin, 0, 2);
		
		in1.propagate();
		in2.propagate();
		cin.propagate();
		assertTrue(add.out(0));
		assertTrue(add.out(1));
		
		in1.propagate();
		in2.setValue(false).propagate();
		cin.propagate();
		assertFalse(add.out(0));
		assertTrue(add.out(1));
		
		in1.setValue(false).propagate();
		in2.setValue(false).propagate();
		cin.propagate();
		assertTrue(add.out(0));
		assertFalse(add.out(1));
		
		in1.setValue(false).propagate();
		in2.setValue(false).propagate();
		cin.setValue(false).propagate();
		assertFalse(add.out(0));
		assertFalse(add.out(1));
		
		in1.setValue(true).propagate();
		in2.setValue(false).propagate();
		cin.setValue(false).propagate();
		assertTrue(add.out(0));
		assertFalse(add.out(1));
		
		in1.setValue(true).propagate();
		in2.setValue(true).propagate();
		cin.setValue(false).propagate();
		assertFalse(add.out(0));
		assertTrue(add.out(1));
	}
	
	@Test
	public void HalfAdderTest() {
		ConstantNode in1 = new ConstantNode(true);
		ConstantNode cin = new ConstantNode(true);
		Node add = new HalfAdder();
		add.connectSrc(in1, 0, 0)
		   .connectSrc(cin, 0, 1);
		
		in1.propagate();
		cin.propagate();
		assertFalse(add.out(0));
		assertTrue(add.out(1));
		
		in1.setValue(false).propagate();
		cin.propagate();
		assertTrue(add.out(0));
		assertFalse(add.out(1));
		
		in1.setValue(false).propagate();
		cin.setValue(false).propagate();
		assertFalse(add.out(0));
		assertFalse(add.out(1));
		
		in1.setValue(true).propagate();
		cin.setValue(false).propagate();
		assertTrue(add.out(0));
		assertFalse(add.out(1));
	}
	
	@Test
	public void ShifterTest() {
		final int num_bits = 4;
		Register reg = new Register(num_bits);
		Shifter sh = new Shifter(num_bits, 1);
		for (int i = 0; i < num_bits; i++)
			sh.connectSrc(reg, i, i);
		// 0101
		reg.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		reg.propagate();
		// (0)1010
		assertTrue(sh.out(0)==false);
		assertTrue(sh.out(1)==true);
		assertTrue(sh.out(2)==false);
		assertTrue(sh.out(3)==true);
		assertTrue(sh.out(4)==false);
		
		sh = new Shifter(num_bits, -2);
		reg.disconnectDst();
		for (int i = 0; i < num_bits; i++)
			sh.connectSrc(reg, i, i);
		// 1011
		reg.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		reg.propagate();
		// (1)0010
		assertTrue(sh.out(0)==false);
		assertTrue(sh.out(1)==true);
		assertTrue(sh.out(2)==false);
		assertTrue(sh.out(3)==false);
		assertTrue(sh.out(4)==true);
	}

	@Test
	public void RotatorTest() {
		final int num_bits = 4;
		Register reg = new Register(num_bits);
		Rotator sh = new Rotator(num_bits, 1);
		for (int i = 0; i < num_bits; i++)
			sh.connectSrc(reg, i, i);
		// 0101
		reg.in(0, true)
		   .in(1, false)
		   .in(2, true)
		   .in(3, false)
		   .in(4, true);
		reg.propagate();
		// 1010
		assertTrue(sh.out(0)==false);
		assertTrue(sh.out(1)==true);
		assertTrue(sh.out(2)==false);
		assertTrue(sh.out(3)==true);
		
		sh = new Rotator(num_bits, -2);
		reg.disconnectDst();
		for (int i = 0; i < num_bits; i++)
			sh.connectSrc(reg, i, i);
		// 1011
		reg.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, true);
		reg.propagate();
		// 1110
		assertTrue(sh.out(0)==false);
		assertTrue(sh.out(1)==true);
		assertTrue(sh.out(2)==true);
		assertTrue(sh.out(3)==true);
	}
	
	@Test
	public void MemoryTest() {
		final int num_bits = 8;
		final int addr_size = 4;
		Register reg = new Register(num_bits);
		Register addr = new Register(addr_size);
		ConstantNode mode = new ConstantNode(true);
		Memory mem = new Memory(addr_size);
		for (int i = 0; i < addr_size; i++)
			addr.connectDst(i, mem, i);
		for (int i = 0; i < num_bits; i++)
			reg.connectDst(i, mem, i+addr_size);
		mode.connectDst(0, mem, addr_size+num_bits);
		// 1011
		addr.in(0, true)
		    .in(1, true)
		    .in(2, false)
		    .in(3, true)
		    .in(4, true);
		// 00001011
		reg.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, false)
		   .in(5, false)
		   .in(6, false)
		   .in(7, false)
		   .in(8, true);
		addr.propagate();
		reg.propagate();
		mode.propagate();
		// 00001011
		assertTrue(mem.out(0)==true);
		assertTrue(mem.out(1)==true);
		assertTrue(mem.out(2)==false);
		assertTrue(mem.out(3)==true);
		assertTrue(mem.out(4)==false);
		assertTrue(mem.out(5)==false);
		assertTrue(mem.out(6)==false);
		assertTrue(mem.out(7)==false);
		//////////////////////
		// 1001
		addr.in(0, true)
		    .in(1, false)
		    .in(2, false)
		    .in(3, true)
		    .in(4, true);
		// 10001011
		reg.in(0, true)
		   .in(1, true)
		   .in(2, false)
		   .in(3, true)
		   .in(4, false)
		   .in(5, false)
		   .in(6, false)
		   .in(7, true)
		   .in(8, true);
		addr.propagate();
		reg.propagate();
		mode.propagate();
		// 00001011
		assertTrue(mem.out(0)==true);
		assertTrue(mem.out(1)==true);
		assertTrue(mem.out(2)==false);
		assertTrue(mem.out(3)==true);
		assertTrue(mem.out(4)==false);
		assertTrue(mem.out(5)==false);
		assertTrue(mem.out(6)==false);
		assertTrue(mem.out(7)==true);
		//////////////////////
		mode.setValue(false);
		// 1011
		addr.in(0, true)
		    .in(1, true)
		    .in(2, false)
		    .in(3, true)
		    .in(4, true);
		addr.propagate();
		reg.propagate(true);
		mode.propagate();
		// 00001011
		assertTrue(mem.out(0)==true);
		assertTrue(mem.out(1)==true);
		assertTrue(mem.out(2)==false);
		assertTrue(mem.out(3)==true);
		assertTrue(mem.out(4)==false);
		assertTrue(mem.out(5)==false);
		assertTrue(mem.out(6)==false);
		assertTrue(mem.out(7)==false);
	}
	
	@Test
	public void LookUpTest() {
		final int addr_size = 4;
		Register addr = new Register(addr_size);
		byte[] data = new byte[1<<addr_size];
		data[11] = 10;
		LookUp mem = new LookUp(addr_size, data);
		for (int i = 0; i < addr_size; i++)
			addr.connectDst(i, mem, i);
		//////////////////////
		// 1011
		addr.in(0, true)
		    .in(1, true)
		    .in(2, false)
		    .in(3, true)
		    .in(4, true);
		addr.propagate();
		// 00001010
		assertTrue(mem.out(0)==false);
		assertTrue(mem.out(1)==true);
		assertTrue(mem.out(2)==false);
		assertTrue(mem.out(3)==true);
		assertTrue(mem.out(4)==false);
		assertTrue(mem.out(5)==false);
		assertTrue(mem.out(6)==false);
		assertTrue(mem.out(7)==false);
	}
}
