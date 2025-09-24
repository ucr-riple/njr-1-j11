package xscript.compiler;

public class XTreePosition implements Cloneable {

	public XPosition position;
	public long start;
	public long end;
	
	public XTreePosition(XPosition position, long start, long end){
		this.position = position;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public XTreePosition clone() {
		return new XTreePosition(position.clone(), start, end);
	}
	
	
	
}
