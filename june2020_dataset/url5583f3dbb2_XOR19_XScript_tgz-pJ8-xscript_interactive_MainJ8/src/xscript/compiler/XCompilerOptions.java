package xscript.compiler;

import java.util.Map;

import xscript.XScriptLang;

public class XCompilerOptions {

	public static final String COMPILER_OPT_REMOVE_LINES = "REMOVE_LINES";
	public static final String COMPILER_OPT_REMOVE_ASSERTS = "REMOVE_ASSERTS";
	public static final String COMPILER_OPT_OPTIMIZATION_LEVEL = "OPTIMIZATION_LEVEL";
	
	public boolean removeLines;
	public int optimizionLevel;
	public boolean removeAsserts;

	public void from(Map<String, Object> options){
		removeLines = asBool(options.get(XScriptLang.COMPILER_OPT_REMOVE_LINES));
		removeAsserts = asBool(options.get(XScriptLang.COMPILER_OPT_REMOVE_ASSERTS));
		optimizionLevel = asInt(options.get(XScriptLang.COMPILER_OPT_OPTIMIZATION_LEVEL));
	}

	private static boolean asBool(Object obj){
		if(obj == null){
			return false;
		}else if(obj instanceof Number){
			return ((Number)obj).doubleValue()!=0;
		}else if(obj instanceof Character){
			return ((Character)obj).charValue()!=0;
		}else if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue();
		}else if(obj instanceof String){
			return Boolean.parseBoolean((String)obj);
		}
		return true;
	}
	
	private static int asInt(Object obj){
		if(obj == null){
			return 0;
		}else if(obj instanceof Number){
			return ((Number)obj).intValue();
		}else if(obj instanceof Character){
			return ((Character)obj).charValue();
		}else if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue()?1:0;
		}else if(obj instanceof String){
			try{
				return Integer.parseInt((String)obj);
			}catch(NumberFormatException e){}
		}
		return 0;
	}
	
}