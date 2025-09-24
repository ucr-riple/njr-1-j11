package controllers.utils;

import java.util.Vector;

public class DebugTools {
	public static void debug(){
		System.out.println("=OK=");		
	}
	
	public static void debug(String s){
		System.out.println("="+s+"=");
	}
	
	public static void debug(Object s){
		debug(""+s);
	}
	
	public static void showSeq(Vector<Integer> is){
		Presentation.showSeq(is);
	}
}
