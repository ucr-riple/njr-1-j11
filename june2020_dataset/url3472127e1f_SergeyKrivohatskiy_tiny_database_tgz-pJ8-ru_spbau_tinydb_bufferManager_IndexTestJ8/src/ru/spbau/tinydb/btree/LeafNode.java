package ru.spbau.tinydb.btree;

import java.util.Iterator;

import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.utils.Utils;

public class LeafNode extends Node {

	protected LeafNode(int pageIndex, BufferManager bm) {
		super(pageIndex, bm);
	}

	@Override
	public Split insert(int key, int value) {
		int size = getSize();
		int i = findKey(key, true);
		if(size != N) {
			insert(i, key, value);
			return null;
		}
		int mid = (N + 1) / 2;
        int sNum = N - mid;
        LeafNode sibling = new LeafNode(bm.getFreePage(), bm);

        for(int j = 0; j < sNum; j ++) {
        	sibling.insert(j, getKey(mid + j), getData(mid + j));
        }
        
        sibling.setLink(getLink());
        setLink(sibling.pageIndex);
        setSize(mid);
        
        if (i < mid) {
            // Inserted element goes to left sibling
            this.insert(i, key, value);
        } else {
            // Inserted element goes to right sibling
            sibling.insert(i - mid, key, value);
        }
        // Notify the parent about the split
        Split result = new Split(sibling.getKey(0), this.pageIndex, sibling.pageIndex);
        return result;
	}

	@Override
	public Iterator<BxTreeEntry> find(final int key, final boolean includeKey) {
		int size = getSize();
		if(size == 0) {
			return Utils.emptyIterator();
		}
		return new Iterator<BxTreeEntry>() {
			private LeafNode nextNode = LeafNode.this;
			private Iterator<BxTreeEntry> currentBaseIter = null;
			private BxTreeEntry val = getNext();
			
			@Override
			public boolean hasNext() {
				
				return val != null;
			}

			private BxTreeEntry getNext() {
				while(currentBaseIter == null || !currentBaseIter.hasNext()) {
					if(nextNode == null) {
						return null;
					}
					currentBaseIter = nextNode.baseFind(key, includeKey);
					int nextNodePage = nextNode.getLink();
					nextNode = nextNodePage == 0 ? null : new LeafNode(nextNodePage, bm);
				}
				return currentBaseIter.next();
			}

			@Override
			public BxTreeEntry next() {
				if(!hasNext()) {
					throw new IllegalStateException();
				}
				BxTreeEntry old = val;
				val = getNext();
				return old;
			}

			@Override
			public void remove() {
				throw new RuntimeException();
			}
		};
	}
	
	private Iterator<BxTreeEntry> baseFind(final int key, final boolean includeKey) {
		return new Iterator<BxTreeEntry>() {
			private int index = findKey(key, includeKey); 
			private int size = getSize();
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public BxTreeEntry next() {
				if(!hasNext()) {
					throw new IllegalStateException();
				}
				BxTreeEntry result = new BxTreeEntry(getKey(index), getData(index));
				index ++;
				return result;
			}

			@Override
			public void remove() {
				throw new RuntimeException();
			}
		};
	}

}
