package xscript.compiler.parser;

import xscript.compiler.XPosition;

public class XToken {

	public XTokenKind kind;
	public XPosition pos;
	public long end;
	public int spaces;
	public int newLines;
	public Object data;
	public String doc;
	
	public XToken(XTokenKind kind, XPosition pos, long end, int spaces, int newLines, Object data, String doc) {
		this.kind = kind;
		this.pos = pos;
		this.end = end;
		this.spaces = spaces;
		this.newLines = newLines;
		this.data = data;
		this.doc = doc;
	}

	@Override
	public String toString() {
		switch (kind) {
		case EOF:
			return "EOF";
		case FLOAT:
		case IDENT:
		case INT:
			return "'"+data+"'";
		case KEYWORD:
			return data.toString();
		case STRING:
			return "\""+data+"\"";
		default:
			return "<unknown token>";
		}
	}
	
}
