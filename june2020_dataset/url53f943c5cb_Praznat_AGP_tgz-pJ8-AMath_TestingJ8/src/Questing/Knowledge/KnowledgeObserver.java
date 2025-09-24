package Questing.Knowledge;

import java.util.*;

import Sentiens.Clan;

public abstract class KnowledgeObserver<O, T> {
	public abstract void observe(O c);
	public abstract KnowledgeBlock<T> createKnowledgeBlock(Clan creator);
}

/**
 * keys are reusable, so use when a new observation might alter an old y value
 */
abstract class MapKnowledgeObserver<O, T> extends KnowledgeObserver<O, T> {
	protected Map<T, Integer> map = new HashMap<T, Integer>();
}
/**
 * no reusable keys, so use only when each observation finalizes y value
 */
abstract class ListKnowledgeObserver<O, T> extends KnowledgeObserver<O, T> {
	protected List<DataPoint<T>> plot = new ArrayList<DataPoint<T>>();
}

class DataPoint<X> {
	X x;
	int y;
	public DataPoint(X x, int y) {
		this.x = x; this.y = y;
	}
	@Override
	public String toString() {
		return x + " : " + y;
	}
}