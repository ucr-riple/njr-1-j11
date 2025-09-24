package net.woopa.dungeon.core;

import java.util.Random;

public class RandomUtil {
	private static Random rnd = new Random();

	public static int nextInt(int i) {
		return rnd.nextInt(i);
	}
	
	public static boolean chance(int i){
		return rnd.nextInt(100) + 1 <= i;
	}
}
