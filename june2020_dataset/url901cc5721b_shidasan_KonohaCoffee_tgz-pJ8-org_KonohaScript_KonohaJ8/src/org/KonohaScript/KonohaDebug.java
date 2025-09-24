package org.KonohaScript;

public final class KonohaDebug {
	public final static boolean UseBuiltInTest = true;
	
	public static void P(String msg) {
		System.out.println("DEBUG: "+ msg);
//		Exception e = new Exception();
//		e.printStackTrace();
	}
	
	public static void Indent(int Level, String Tab) {
		for(int i = 0; i < Level; i++) {
			System.out.print(Tab);
		}
	}
}
