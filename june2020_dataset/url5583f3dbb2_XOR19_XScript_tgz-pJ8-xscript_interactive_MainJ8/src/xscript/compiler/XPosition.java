package xscript.compiler;


public class XPosition implements Cloneable {

	public String source;
	public long pos;
	public int line;
	public int column;
	
	public XPosition(String source){
		this.source = source;
		pos = XDiagnostic.NOPOS;
		line = (int) XDiagnostic.NOPOS;
		column = (int) XDiagnostic.NOPOS;
	}
	
	public XPosition(String source, long pos, int line, int column){
		this.source = source;
		this.pos = pos;
		this.line = line;
		this.column = column;
	}
	
	@Override
	public XPosition clone() {
		return new XPosition(source, pos, line, column);
	}

	@Override
	public String toString() {
		return source+":"+pos+","+line+","+column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		result = prime * result + (int) (pos ^ (pos >>> 32));
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof XPosition))
			return false;
		XPosition other = (XPosition) obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		if (pos != other.pos)
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
	
}
