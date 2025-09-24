package graph;

import java.util.ArrayList;
import java.util.List;

import util.Triple;

public class BCDat {
	public int vindex;
	public List<Boolean> visited;
	public List<Integer> lowlink;
	public List<Integer> dfsnum;
	public List<Triple<Integer, Integer, Integer>> cstack;

	public BCDat() {
		vindex = 0;
		visited = new ArrayList<Boolean>();
		lowlink = new ArrayList<Integer>();
		dfsnum = new ArrayList<Integer>();
		cstack = new ArrayList<Triple<Integer, Integer, Integer>>();
	}

	public void reset(int v) {
		vindex = 0;
		visited = new ArrayList<Boolean>();
		lowlink = new ArrayList<Integer>();
		dfsnum = new ArrayList<Integer>();
		cstack = new ArrayList<Triple<Integer, Integer, Integer>>();
		for (int i = 0; i < v; i++) {
			visited.add(false);
			lowlink.add(-1);
			dfsnum.add(-1);
//			cstack.add(new Triple<Integer,Integer,Integer>(-1,-1,-1));
		}
	}
}