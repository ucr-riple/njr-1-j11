package net.woopa.dungeon.core;

public class Dbg {
	private static long time = 0;

	public static void tic() {
		Dbg.time = System.currentTimeMillis();
	}
	
	public static void toc(String s){
		System.err.println(s+": "+((System.currentTimeMillis()-time))+"ms");
	}
}
