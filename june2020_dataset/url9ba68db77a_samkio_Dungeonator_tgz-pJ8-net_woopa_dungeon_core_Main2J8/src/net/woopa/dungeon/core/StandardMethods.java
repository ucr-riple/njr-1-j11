package net.woopa.dungeon.core;

import net.woopa.dungeon.datatypes.Direction;
import net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Material;
import net.woopa.dungeon.datatypes.Vector2D;

public class StandardMethods {
	public static void build_door(Direction dir, Vector2D loc, Material door,
			Grid g) {
		int wayin_x = loc.getX();
		int wayin_y = loc.getY();
		g.set(wayin_x, wayin_y, door);
		fixWall(dir.left_x(wayin_x), dir.left_y(wayin_y), g);
		fixWall(dir.right_x(wayin_x), dir.right_y(wayin_y), g);
		fixFloor(dir.backwards_x(wayin_x), dir.backwards_y(wayin_y), g);
		fixFloor(dir.forwards_x(wayin_x), dir.forwards_y(wayin_y), g);
	}

	public static void fixWall(int x, int y, Grid grid) {
		if (grid.get(x, y) == CoreMaterial.WALL
				|| grid.get(x, y) == CoreMaterial.UNDEF)
			grid.set(x, y, CoreMaterial.FIXEDWALL);
	}

	public static void fixFloor(int x, int y, Grid grid) {
		if (grid.get(x, y) == CoreMaterial.FLOOR
				|| grid.get(x, y) == CoreMaterial.UNDEF)
			grid.set(x, y, CoreMaterial.FIXEDFLOOR);
	}

	public static void startUpStaircase(int x, int y, CoreMaterial up,
			Grid grid, Direction dir) {
		grid.set(x, y, CoreMaterial.UP);
		grid.set(dir.forwards_x(x), dir.forwards_y(y),
				CoreMaterial.FIXEDFLOORUP);
		upWall(dir.backwards_x(x), dir.backwards_y(y), grid);
		upWall(dir.left_x(x), dir.left_y(y), grid);
		upWall(dir.right_x(x), dir.right_y(y), grid);
	}

	public static void upWall(int x, int y, Grid grid) {
		if (grid.get(x, y) == CoreMaterial.DOWNWALL) {
			grid.set(x, y, CoreMaterial.BOTHWALL);
		} else if (grid.get(x, y) == CoreMaterial.WALL
				|| grid.get(x, y) == CoreMaterial.FIXEDWALL) {
			grid.set(x, y, CoreMaterial.UPWALL);
		} else if (grid.get(x, y) == CoreMaterial.UNDEF) {
			grid.set(x, y, CoreMaterial.UPWALL);
			grid.use();
		}
	}
}
