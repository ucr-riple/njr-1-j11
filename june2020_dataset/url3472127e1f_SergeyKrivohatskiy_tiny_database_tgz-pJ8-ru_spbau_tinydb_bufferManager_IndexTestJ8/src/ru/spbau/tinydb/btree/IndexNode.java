package ru.spbau.tinydb.btree;

import java.util.Iterator;

import ru.spbau.tinydb.bufferManager.BufferManager;

public class IndexNode extends Node {

	protected IndexNode(int pageIndex, BufferManager bm) {
		super(pageIndex, bm);
	}

	public IndexNode(int pageIndex, BufferManager bm, Split split) {
		super(pageIndex, bm);
		setIndexType();
		insert(0, split.key, split.left);
		setLink(split.right);
	}

	@Override
	public Split insert(int key, int value) {

        /* Early split if node is full.
         * This is not the canonical algorithm for B+ trees,
	     * but it is simpler and it does break the definition
	     * which might result in immature split, which might not be desired in database
	     * because additional split lead to tree's height increase by 1, thus the number of disk read
	     * so first search to the leaf, and split from bottom up is the correct approach.
	     */
		int size = getSize();
		if (size == N) { // Split
            int mid = (N + 1) / 2;
            int sNum = size - mid;
            IndexNode sibling = new IndexNode(bm.getFreePage(), bm);
            
            for(int j = 0; j < sNum; j ++) {
            	sibling.insert(j, getKey(mid + j), getData(mid + j));
            }

            sibling.setLink(getLink());
            setLink(getData(mid));
            int removedKey = getKey(mid - 1);
            setSize(mid - 1);//this is important, so the middle one elevate to next depth(height), inner node's key don't repeat itself

            // Set up the return variable
            Split result = new Split(removedKey,
                    this.pageIndex,
                    sibling.pageIndex);

            // Now insert in the appropriate sibling
            if (key <= result.key) {
            	insert(key, value);
            } else {
                sibling.insert(key, value);
            }
            return result;

        }
		int index = findKey(key, true);
		if(index == size) {
			index = size - 1;
		}

		int currentKey = getKey(index);
		int nextNodePage;
		if(key <= currentKey) {
			nextNodePage = getData(index);
		} else {
			if(index == size - 1) {
				nextNodePage = getLink();
			} else {
				nextNodePage = getData(index + 1);
			}
		}
		Split split = loadNode(nextNodePage, bm).insert(key, value);
		if(split != null) {
			insert(index, split.key, split.left);
			if(index == size) {
				setLink(split.right);
			} else {
				setData(index + 1, split.right);
			}
		}
		return null;
	}

	@Override
	public Iterator<BxTreeEntry> find(int key, boolean includeKey) {
		int size = getSize();
		int index = findKey(key, includeKey);
		if(index == size) {
			index = size - 1;
		}
		int currentKey = getKey(index);
		int nextNodePage;
		if(key <= currentKey) {
			nextNodePage = getData(index);
		} else {
			if(index == size - 1) {
				nextNodePage = getLink();
			} else {
				nextNodePage = getData(index + 1);
			}
		}
		return loadNode(nextNodePage, bm).find(key, includeKey);
	}

}
