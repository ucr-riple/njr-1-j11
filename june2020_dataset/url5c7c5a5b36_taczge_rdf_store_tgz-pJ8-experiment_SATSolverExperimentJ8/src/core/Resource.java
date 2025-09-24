package core;

import java.util.HashMap;
import java.util.Map;

import query.Substitution;
import query.Token;

public class Resource implements Token {

	// 1度生成したインスタンスを使いまわす
	private static final Map<String,Resource> pool = new HashMap<>();
	
	public static Resource of(String name) {
		if ( !pool.containsKey(name) ) {
			pool.put( name, new Resource(name) );
		}

		return pool.get(name);
	}
	
	private final String name;

	private Resource(String name) {
		super();
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Token apply(Substitution s) {
		return this;
	}

}
