package xscript.compiler.treemaker;

import java.util.HashMap;

public enum XKeyword{
	NULL("null"),
	TRUE("true"),
	FALSE("false"),
	FUNC("func"),
	CLASS("class"),
	IF("if"),
	ELSE("else"),
	FOR("for"),
	FOREACH("foreach"),
	WHILE("while"),
	DO("do"),
	TRY("try"),
	CATCH("catch"),
	FINALLY("finally"),
	SWITCH("switch"), 
	CASE("case"), 
	DEFAULT("default"), 
	BREAK("break"), 
	CONTINUE("continue"),
	THROW("throw"),
	RETURN("return"),
	YIELD("yield"),
	IMPORT("import"),
	FROM("from"),
	INSTANCEOF("instanceof"),
	TYPEOF("typeof"),
	ISSUBCLASS("issubclass"),
	SAND("and"),
	SOR("or"),
	SXOR("xor"),
	SBAND("bitand"), 
	SBOR("bitor"),
	SMOD("mod"), 
	SNOT("not"), 
	SBNOT("bitnot"), 
	SPOW("pow"),
	THIS("this"), 
	SUPER("super"),
	ASSERT("assert"),
	ADD("+"),
	SUB("-"),
	MUL("*"),
	DIV("/"),
	IDIV("\\"),
	MOD("%"),
	AND("&"),
	OR("|"),
	XOR("^"),
	NOT("!"),
	INVERSE("~"),
	EQUAL("="), 
	GREATER(">"), 
	SMALLER("<"),
	COMMA(","),
	DOT("."),
	QUESTIONMARK("?"),
	COLON(":"),
	LBRAKET("("),
	RBRAKET(")"),
	LSCOPE("{"),
	RSCOPE("}"),
	LINDEX("["),
	RINDEX("]"),
	SEMICOLON(";"),
	LOCAL("local"),
	GLOBAL("global"),
	NONLOCAL("nonlocal"),
	DELETE("del"),
	SYNCHRONIZED("synchronized"),
	AS("as"),
	;
	private final String nameList;
	public final String name;
	
	XKeyword(String name, String...names){
		this.name = name;
		registerAs(name, this);
		String nl = name;
		for(String n:names){
			registerAs(n, this);
			nl += ", "+n;
		}
		nameList = nl;
	}
	
	@Override
	public String toString() {
		return nameList==null?super.toString():"'"+nameList+"'";
	}

	private static final class Data{
		static final HashMap<String, XKeyword> keywords = new HashMap<String, XKeyword>();
	}
	
	private static void registerAs(String name, XKeyword kw){
		if(Data.keywords.containsKey(name))
			throw new IllegalArgumentException("Keyword with name '"+name+"' allready rigisterd as"+Data.keywords.get(name)+" when "+kw+" tries to register");
		Data.keywords.put(name, kw);
	}
	
	public static XKeyword getKeyword(String name){
		return Data.keywords.get(name);
	}
	
}