package org.dclayer.misc;

import java.lang.reflect.Array;

public class Toolbox {

	public static <T> T[] append(T[]... arrays) {
		int numElements = 0;
		for(T[] array : arrays) {
			if(array == null) continue;
			numElements += array.length;
		}
		T[] all = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), numElements);
		int i = 0;
		for(T[] array : arrays) {
			if(array == null) continue;
			for(T t : array) {
				all[i++] = t;
			}
		}
		return all;
	}
	
}
