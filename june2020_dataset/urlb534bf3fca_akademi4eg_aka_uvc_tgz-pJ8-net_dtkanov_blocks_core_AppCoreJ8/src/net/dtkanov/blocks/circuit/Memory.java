package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

/** Represents memory. Uses array as storage element. */
public class Memory extends Node {
	/** Size of address. */
	protected int addr_size;
	protected byte storage[];
	protected Node in_addr[];
	protected Node in_data[];
	protected Node in_mode;
	protected Node out_data[];
	public static int BITS_IN_BYTE = 8;
	
	public Memory(int pointer_size) {
		this(pointer_size, null);
	}
	
	public Memory(Memory other) {
		this(other.addr_size, other.storage);
	}
	
	public Memory(int pointer_size, byte[] a_storage) {
		super(null);
		addr_size = pointer_size;
		if (a_storage != null)
			storage = a_storage;
		else
			storage = new byte[1<<addr_size];
		in_addr = new NOPNode[addr_size];
		for (int i = 0; i < in_addr.length; i++) {
			in_addr[i] = new NOPNode();
		}
		in_mode = new NOPNode();
		in_data = new NOPNode[BITS_IN_BYTE];
		out_data = new NOPNode[BITS_IN_BYTE];
		for (int i = 0; i < BITS_IN_BYTE; i++) {
			out_data[i] = new NOPNode();
			in_data[i] = new NOPNode();
		}
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index < in_addr.length)
			in_addr[index].in(0, value);
		else if (index < in_addr.length + in_data.length)
			in_data[index-in_addr.length].in(0, value);
		else
			in_mode.in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return out_data[index].out(0);
	}

	@Override
	public boolean isReady() {
		for (Node n : in_addr)
			if (!n.isReady())
				return false;
		for (Node n : in_data)
			if (!n.isReady())
				return false;
		return in_mode.isReady();
	}

	@Override
	public void reset() {
		if (in_addr != null) {
			for (Node n : in_addr)
				n.reset();
		}
		if (in_data != null) {
			for (Node n : in_data)
				n.reset();
		}
		if (in_mode != null)
			in_mode.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		// memory logic is here
		int index = 0;
		for (int i = 0; i < in_addr.length; i++) {
			if (in_addr[i].out(0))
				index += 1<<i;
		}
		if (in_mode.out(0)) {
			// write mode
			storage[index] = 0;
			for (int i = 0; i < in_data.length; i++) {
				if (in_data[i].out(0))
					storage[index] += 1<<i;
			}
		}
		// do read anyway
		for (int i = 0; i < out_data.length; i++) {
			out_data[i].in(i, ((storage[index]>>i) & 1)!=0);
		}
		super.propagate(true);
	}
}
