package org.dclayer.datastructure.map;

public class Int2Map {

	Integer[][] map = null;
	
	public void unset(int key1, int key2) {
		if(map == null || map.length <= key1) {
			return;
		}
		Integer[] submap = map[key1];
		if(submap == null || submap.length <= key2) {
			return;
		}
		submap[key2] = null;
	}
	
	public void set(int key1, int key2, int n) {
		if(map == null) {
			map = new Integer[key1+1][];
		} else if(map.length <= key1) {
			Integer[][] newmap = new Integer[key1+1][];
			System.arraycopy(map, 0, newmap, 0, map.length);
			map = newmap;
		}
		Integer[] submap = map[key1];
		if(submap == null) {
			map[key1] = submap = new Integer[key2+1];
		} else if(submap.length <= key2) {
			Integer[] newsubmap = new Integer[key2+1];
			System.arraycopy(submap, 0, newsubmap, 0, submap.length);
			map[key1] = submap = newsubmap;
		}
		submap[key2] = n;
	}
	
	public Integer get(int key1, int key2) {
		if(map == null || map.length <= key1) {
			return null;
		}
		Integer[] submap = map[key1];
		if(submap == null || submap.length <= key2) {
			return null;
		}
		return submap[key2];
	}
	
}
