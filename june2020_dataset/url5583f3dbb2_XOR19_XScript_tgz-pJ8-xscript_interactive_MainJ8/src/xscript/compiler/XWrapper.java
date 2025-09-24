package xscript.compiler;


final class XWrapper {

	private XWrapper(){}
	
	public static boolean isLong(Object obj){
		return obj instanceof Long || obj instanceof Integer || obj instanceof Short || obj instanceof Byte || obj instanceof Character;
	}
	
	public static long asLong(Object obj){
		if(obj instanceof Number){
			return ((Number)obj).longValue();
		}else if(obj instanceof Character){
			return ((Character)obj).charValue();
		}else if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue()?1:0;
		}
		throw new ClassCastException("Can't cast '"+getClassName(obj)+"' to 'long'");
	}
	
	public static boolean isNumber(Object obj){
		return obj instanceof Number;
	}
	
	public static double asNumber(Object obj){
		if(obj instanceof Number){
			return ((Number)obj).doubleValue();
		}else if(obj instanceof Character){
			return ((Character)obj).charValue();
		}else if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue()?1:0;
		}
		throw new ClassCastException("Can't cast '"+getClassName(obj)+"' to 'double'");
	}
	
	public static boolean isString(Object obj){
		return obj instanceof String;
	}
	
	public static String asString(Object obj){
		return obj.toString();
	}
	
	public static boolean isBool(Object obj){
		return obj instanceof Boolean;
	}
	
	public static boolean asBool(Object obj){
		if(obj == null){
			return false;
		}else if(obj instanceof Number){
			return ((Number)obj).doubleValue()!=0;
		}else if(obj instanceof Character){
			return ((Character)obj).charValue()!=0;
		}else if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue();
		}else{
			return true;
		}
	}
	
	private static String getClassName(Object obj){
		return obj==null?"null":obj.getClass().getName();
	}
	
}
