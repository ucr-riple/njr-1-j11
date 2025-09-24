package controllers.utils;

import java.util.Vector;

public class Debug {
	public int counter;
	
	public static void debug(){
		System.out.println("=OK=");		
	}
	
	public static void debug(String s, String deco){
		System.out.println(deco+s+deco);
	}
	
	public static void debug(String s){
		debug(s,"=");
	}
	
	public static void debug(Object s, String deco){
		debug(""+s, deco);
	}
	
	public static void debug(Object s){
		debug(s, "=");
	}
	
	public static void showSeq(Vector<Integer> is){
		Presentation.showSeq(is);
	}
	
	public void showCounter(){
		System.out.println(counter);
	}
}
