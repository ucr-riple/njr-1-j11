package ru.spbau.tinydb.btree;

public class BxTreeEntry {
	public final int key;
	public final int value;
	
	public BxTreeEntry(int key, int value) {
		this.key = key;
		this.value = value;
	}
}
