package xscript.object;

public class XFunctionData {

	private static final String[] EMPTY_ARRAY = new String[0];
	
	final XFunction function;
	
	final String[] paramNames;
	
	final int defaultStart;
	
	final boolean useList;
	
	final boolean useMap;
	
	public XFunctionData(XFunction function){
		this(function, -1, false, false, EMPTY_ARRAY);
	}
	
	public XFunctionData(XFunction function, String...paramNames){
		this(function, -1, false, false, paramNames);
	}
	
	public XFunctionData(XFunction function, int defaultStart, String...paramNames){
		this(function, defaultStart, false, false, paramNames);
	}
	
	public XFunctionData(XFunction function, boolean useList, boolean useMap, String...paramNames){
		this(function, -1, useList, useMap, paramNames);
	}
	
	public XFunctionData(XFunction function, int defaultStart, boolean useList, boolean useMap, String...paramNames){
		this.function = function;
		this.paramNames = paramNames;
		this.defaultStart = defaultStart>=paramNames.length?-1:defaultStart;
		this.useList = useList;
		this.useMap = useMap;
	}
	
}
