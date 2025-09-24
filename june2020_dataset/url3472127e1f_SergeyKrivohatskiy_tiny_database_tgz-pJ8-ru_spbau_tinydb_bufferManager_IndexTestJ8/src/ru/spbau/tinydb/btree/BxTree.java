package ru.spbau.tinydb.btree;

import java.util.Iterator;

import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.bufferManager.BufferView;

public class BxTree {
	private final BufferManager bm;
	private Node root;
	private int rootPageIndex;
	private final int headerPageIndex;

    public BxTree(BufferManager bufferManager, int firstPage) {
    	this.bm = bufferManager;
    	this.headerPageIndex = firstPage;
    	loadHeader();
    	if(rootPageIndex == 0) {
    		rootPageIndex = bm.getFreePage();
    	}
    	writeHeader();
    	this.root = Node.loadNode(rootPageIndex, bufferManager);
	}

	private void writeHeader() {
		try(BufferView headerView = bm.getPage(headerPageIndex)) {
			headerView.setInt(0, rootPageIndex);
			headerView.setChanged();
		}
	}

	private void loadHeader() {
		try(BufferView headerView = bm.getPage(headerPageIndex)) {
			rootPageIndex = headerView.getInt(0);
		}
	}

	public void insert(int key, int recordId) {
		Split split = root.insert(key, recordId);
        if (split != null) {
            // The old root was splitted in two parts.
            // We have to create a new root pointing to them
        	root = new IndexNode(bm.getFreePage(), bm, split);
        	rootPageIndex = root.pageIndex;
        	writeHeader();
        }
	}
	
	public Iterator<BxTreeEntry> find(int from, final int to, boolean includeFrom, final boolean includeTo) {
		final Iterator<BxTreeEntry> baseIter = root.find(from, includeFrom); // >(or >=) from iterator
		
		return new Iterator<BxTreeEntry>() {
			private BxTreeEntry entry = getNext();
			@Override
			public BxTreeEntry next() {
				if(!hasNext()) {
					throw new IllegalStateException();
				}
				BxTreeEntry old = entry;
				entry = getNext();
				return old;
			}
			
			private BxTreeEntry getNext() {
				if(baseIter.hasNext()) {
					BxTreeEntry val = baseIter.next();
					if((includeTo && val.key <= to) || (!includeTo && val.key < to)) {
						return val;
					}
				}
				return null;
			}

			@Override
			public boolean hasNext() {
				return entry != null;
			}

			@Override
			public void remove() {
				throw new RuntimeException();
			}
		};
	}
}