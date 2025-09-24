package com.scb.cleanCode;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Position {
	private final Collection<String> names=new CopyOnWriteArrayList<String>();
	public final int x;
	public final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static void main(String[] args) {
		Position p=new Position(10,20);		
	}
}
