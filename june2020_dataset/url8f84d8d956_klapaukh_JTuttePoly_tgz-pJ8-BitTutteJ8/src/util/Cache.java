package util;

import graph.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import polynomial.FactorPoly;

public class Cache {
	private Map<Graph, FactorPoly> data;
	private static final long MIN_MEM = 50 * 1024 * 1024;
	private double delete_prop = 0.5;
	private long acesses = 0;
	private long misses = 0;

	public Cache() {
		this.data = new HashMap<Graph, FactorPoly>();
	}

	public void add(Graph g, FactorPoly f) {
		long mem = Runtime.getRuntime().freeMemory();
		if (mem < MIN_MEM) {
			System.out.println(mem / (1024.0 * 1024.0));
			cleanCache();
		}
		Graph gclone = new Graph(g);
		FactorPoly fclone = new FactorPoly(f);
		data.put(gclone, fclone);
	}

	public FactorPoly get(Graph g) {
		acesses++;
		FactorPoly f = data.get(g);

		// FactorPoly check = null;
		// for(Map.Entry<Graph, FactorPoly> i: data.entrySet()){
		// if(g.equals(i.getKey())){
		// if(g.hashCode() != i.getKey().hashCode() || !g.toString().equals(i.getKey().toString())){
		// throw new RuntimeException("Graph Equality is broken");
		// }
		// if(check != null){
		// throw new RuntimeException("Cache contains double ups");
		// }
		// check = i.getValue();
		// System.out.println("matched");
		// System.out.println(g);
		// System.out.println(check.toString());
		// }
		// }
		//
		// if(check == null && f == null){
		// //all is well
		// }else if(check == null || f == null){
		// throw new RuntimeException("Get Failed one is null, the other isn't");
		// }else if(!check.toString().equals(f.toString())){
		// throw new RuntimeException("Polynomals are different");
		// }

		if (f == null) {
			misses++;
			return null;
		}
		return new FactorPoly(f);
	}

	private void cleanCache() {
		System.out.println("Cleaning cache");
		Iterator<Map.Entry<Graph, FactorPoly>> i = data.entrySet().iterator();
		if (i.hasNext()) {
			for (i.next(); i.hasNext(); i.next()) {
				if (Math.random() < delete_prop) {
					i.remove();
				}
			}
		}

		System.gc();
	}

	public String statistics() {
		return String.format("%d Cache Access Attempts\n%.2f%% Cache Hits\n%d Cache Hits", acesses,
				100.0 * ((double) (acesses - misses) / (double) acesses), acesses - misses);
	}

}
