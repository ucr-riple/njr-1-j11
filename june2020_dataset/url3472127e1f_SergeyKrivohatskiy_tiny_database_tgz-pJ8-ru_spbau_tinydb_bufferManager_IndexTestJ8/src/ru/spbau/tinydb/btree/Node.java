package ru.spbau.tinydb.btree;

import java.util.Iterator;

import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.bufferManager.BufferView;

public abstract class Node {
	// View:
	// 0b   | 4b   | 8b									| 12b
	// Type | Size | link(next leaf or right subtree) 	| (key, data(left subtree or value))
	private static final int DATA_OFFSET = 12;
	private static final int DATA_SIZE = (int) (BufferManager.PAGE_SIZE - DATA_OFFSET);
	protected static final int N = Node.DATA_SIZE / 8;
	protected final int pageIndex;
	protected final BufferManager bm;
	
	public Node(int pageIndex, BufferManager bm) {
		this.pageIndex = pageIndex;
		this.bm = bm;
	}
	
	protected BufferView getDataView() {
		BufferView pageView = bm.getPage(pageIndex);
		BufferView dataVIew = pageView.getSubView(DATA_OFFSET, DATA_SIZE);
		pageView.close();
		return dataVIew;
	}
	
	abstract public Iterator<BxTreeEntry> find(int key, boolean includeKey);

	// returns null if no split, otherwise returns split info
	abstract public Split insert(int key, int value);

	protected int getSize() {
		return getInt(pageIndex, bm, 4);
	}
	
	protected void setSize(int size) {
		setInt(pageIndex, bm, 4, size);
	}
	
	protected int getLink() {
		return getInt(pageIndex, bm, 8);
	}
	
	protected void setLink(int value) {
		setInt(pageIndex, bm, 8, value);
	}
	
	protected int getType() {
		return getType(pageIndex, bm);
	}
	
	/**
	 * 
	 * @param key
	 * @param include
	 * @return index of first elem >(or >=) key or Size if not found
	 */
	protected int findKey(int key, boolean include) {
		int index = 0;
		int size = getSize();
		BufferView dataView = getDataView();
		for(; index < size; index ++) {
			int current = dataView.getInt(8 * index);
			if((include && current >= key) || (!include && current > key)) {
				break;
			}
		}
		dataView.close();
		return index;
	}

	protected int getKey(int index) {
		return getInt(pageIndex, bm, DATA_OFFSET + 8 * index);
	}
	
	protected int getData(int index) {
		return getInt(pageIndex, bm, DATA_OFFSET + 8 * index + 4);
	}

	protected void setData(int index, int value) {
		setInt(pageIndex, bm, DATA_OFFSET + 8 * index + 4, value);
	}
	
	protected void insert(int index, int key, int data) {
		int size = getSize();
		if(size == N) {
			throw new IllegalStateException();
		}

		BufferView dataView = getDataView();
		// |...|...|.| => |...|.|...|
		for(int i = size; i > index; i --) {
			dataView.setInt(8 * i, dataView.getInt(8 * (i - 1)));
			dataView.setInt(8 * i + 4, dataView.getInt(8 * (i - 1) + 4));
		}
		
		dataView.setInt(8 * index, key);
		dataView.setInt(8 * index + 4, data);
		
		dataView.setChanged();
		dataView.close();
		
		setSize(size + 1);
	}

	protected void setIndexType() {
		setInt(pageIndex, bm, 0, 1);
	}

	public int getPageIndex() {
		return pageIndex;
	}
	
	/**
	 * 
	 * @param pageIndex
	 * @param bm
	 * @return 0 if leafNode
	 */
	protected static int getType(int pageIndex, BufferManager bm) {
		return getInt(pageIndex, bm, 0);
	}

	private static int getInt(int pageIndex, BufferManager bm, int offset) {
		BufferView pageView = bm.getPage(pageIndex);
		int value = pageView.getInt(offset);
		pageView.close();
		return value;
	}

	private static void setInt(int pageIndex, BufferManager bm, int offset, int value) {
		BufferView pageView = bm.getPage(pageIndex);
		pageView.setInt(offset, value);
		pageView.setChanged();
		pageView.close();
	}
	
	public static Node loadNode(int pageIndex, BufferManager bm) {
		if(getType(pageIndex, bm) == 0) {
			return new LeafNode(pageIndex, bm);
		} else {
			return new IndexNode(pageIndex, bm);
		}
	}
}
