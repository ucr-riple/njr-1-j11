package net.woopa.dungeon.datatypes;

public interface Material {
	public char getSymbol();

	public String getName();

	public Boolean isChest();

	public Boolean isWall();

	public Boolean isFloor();

	public Boolean isBoundary();

	public Boolean isUndef();
}
