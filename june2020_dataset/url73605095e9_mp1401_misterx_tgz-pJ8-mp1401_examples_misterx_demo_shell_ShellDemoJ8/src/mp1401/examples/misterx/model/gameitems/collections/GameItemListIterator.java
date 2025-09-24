package mp1401.examples.misterx.model.gameitems.collections;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import mp1401.examples.misterx.model.gameitems.GameItem;


public class GameItemListIterator<E extends GameItem> implements Iterator<E> {
	
	List<E> gameItems;
	int position;

	public GameItemListIterator(List<E> gameItems) {
		this.gameItems = gameItems;
		position = 0;
	}

	@Override
	public boolean hasNext() {
		return gameItems.size() > position && gameItems != null;
	}

	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E gameItem = gameItems.get(position);
		position++;
		return gameItem;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
