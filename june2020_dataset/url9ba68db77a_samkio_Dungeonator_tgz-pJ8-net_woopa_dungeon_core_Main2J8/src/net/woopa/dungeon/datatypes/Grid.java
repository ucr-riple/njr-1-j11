package net.woopa.dungeon.datatypes;

import java.util.ArrayList;
import java.util.List;

import net.woopa.dungeon.core.CoreMaterial;
import net.woopa.dungeon.managers.MaterialManager;

public class Grid {
	private final Vector2D size;
	private Material[][] area;
	private int used = 0;

	public Grid(int sx, int sy) {
		this.size = new Vector2D(sx, sy);
		area = new Material[size.getX()][size.getY()];
		for (int x = 0; x < size.getX(); x++) {
			for (int y = 0; y < size.getY(); y++) {
				area[x][y] = CoreMaterial.UNDEF;
			}
		}
	}

	public Grid(String[] strings) {
		this(strings[0].length(), strings.length);
		for (int y = 0; y < size.getY(); y++) {
			for (int x = 0; x < size.getX(); x++) {
				char c = strings[size.getY() - y - 1].charAt(x);
				area[x][y] = MaterialManager.getMaterial(c);
			}
		}
	}

	public Grid(Vector2D levelSize) {
		this(levelSize.getX(), levelSize.getY());
	}

	public float percentUtilized() {
		return ((float) used * 100) / ((float) size.getX() * size.getY());
	}

	public void show() {
		for (String s : getMap()) {
			System.out.println(s);
		}
	}

	public List<String> getMap() {
		List<String> list = new ArrayList<String>();
		String line = "";
		for (int x = 0; x < size.getX() + 2; x++) {
			line += CoreMaterial.NONE.getSymbol();
		}
		list.add(line);
		for (int y = size.getY() - 1; y >= 0; y--) {
			line = "";
			line += CoreMaterial.NONE.getSymbol();
			for (int x = 0; x < size.getX(); x++) {
				Material b = area[x][y];
				line += b.getSymbol();
			}
			line += CoreMaterial.NONE.getSymbol();
			list.add(line);

		}
		line = "";
		for (int x = 0; x < size.getX() + 2; x++) {
			line += CoreMaterial.NONE.getSymbol();
		}
		list.add(line);
		return list;
	}

	public Vector2D getSize() {
		return this.size;
	}

	public void renderBasicEmptyRoom(int origin_x, int origin_y, int size_x,
			int size_y, Material wall, Material floor) {
		if (origin_x + size_x - 1 >= size.getX() || origin_x < 0
				|| origin_y + size_y - 1 >= size.getY() || origin_y < 0)
			return;

		for (int x = origin_x; x < origin_x + size_x; x++) {
			for (int y = origin_y; y < origin_y + size_y; y++) {
				if (x == origin_x || y == origin_y
						|| x == origin_x + size_x - 1
						|| y == origin_y + size_y - 1) {
					if (isUndef(x, y)) {
						set(x, y, wall);
						used++;
					}
				} else {
					if (isUndef(x, y)) {
						set(x, y, floor);
						used++;
					}
				}
			}
		}
	}

	public Boolean isUndef(int x, int y) {
		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY())
			return false;
		return (area[x][y]).isUndef();
	}

	public void set(int x, int y, Material b) {
		if (x >= 0 && y >= 0 || x < size.getX() || y < size.getY())
			area[x][y] = b;
	}

	public Boolean fits(int origin_x, int origin_y, int size_x, int size_y) {
		if (origin_x + size_x - 1 >= size.getX() || origin_x < 0
				|| origin_y + size_y - 1 >= size.getY() || origin_y < 0)
			return false;

		for (int x = origin_x; x < origin_x + size_x; x++) {
			for (int y = origin_y; y < origin_y + size_y; y++) {
				if (x == origin_x || y == origin_y
						|| x == origin_x + size_x - 1
						|| y == origin_y + size_y - 1) {
					if (!(area[x][y]).isBoundary() && (!(area[x][y]).isUndef()))
						return false;
				} else {
					if (!area[x][y].isUndef())
						return false;
				}
			}
		}
		return true;
	}

	public Material get(int x, int y) {
		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY())
			return CoreMaterial.FIXEDWALL;
		return area[x][y];
	}

	public Boolean isFloor(int x, int y) {
		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY())
			return false;
		return (area[x][y]).isFloor();
	}

	public void use() {
		used++;
	}

	public void renderSchematic(int origin_x, int origin_y, Schematic map,
			Direction dir) {
		if (origin_x + map.sx(dir) - 1 >= size.getX() || origin_x < 0
				|| origin_y + map.sy(dir) - 1 >= size.getY() || origin_y < 0)
			return;

		for (int x = origin_x, mx = 0; x < origin_x + map.sx(dir); x++, mx++) {
			for (int y = origin_y, my = 0; y < origin_y + map.sy(dir); y++, my++) {
				Material s = map.get(mx, my, dir);
				if (isUndef(x, y) && s != CoreMaterial.UNDEF) {
					set(x, y, s);
					used++;
				} else if (s == CoreMaterial.DOWN || s == CoreMaterial.BOTHWALL) {
					set(x, y, s);
				}
			}
		}
	}

	public Boolean isWall(int x, int y) {
		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY())
			return false;
		return (area[x][y]).isWall();
	}

	public boolean isChest(int x, int y) {
		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY())
			return false;
		return (area[x][y]).isChest();
	}

}
