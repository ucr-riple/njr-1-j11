package net.woopa.dungeon.datatypes;


public interface Schematic {
	public String[] getMap();

	public int sx(Direction special_dir);

	public Material get(int mx, int my, Direction dir);

	public int sy(Direction dir);

	public int getAccess(Direction rotate180, Direction special_dir);
}
