package net.dtkanov.blocks.logic;
/** Logic gate that does nothing. Simply returns its input. */
public class NOPNode extends NOTNode {
	@Override
	public boolean out(int index) {
		return data;
	}
	@Override
	public String toString() {
		return "["+(isReady()?"+":"-")+getId()+":"+getClass().getSimpleName()+"|"+data+"]";
	}
}
