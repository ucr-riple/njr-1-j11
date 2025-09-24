package br.com.innovatium.mumps2java.datastructure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.*;

public class OrderDataCache {
	final Set<String> cache = new HashSet<String>();

	public OrderDataCache() {
	}

	public boolean isCached(Object[] subs) {
		int last = subs.length;
		Object[] subscripts = null;
		for (int i = last; i > 1; i--) {
			subscripts = Arrays.copyOf(subs, i);
			if (cache.contains(generateKeyOfParent(subscripts))) {
				return true;
			}
		}
		return false;
	}

	public void add(Object[] subs) {
		cache.add(generateKeyOfParent(subs));
	}
}
