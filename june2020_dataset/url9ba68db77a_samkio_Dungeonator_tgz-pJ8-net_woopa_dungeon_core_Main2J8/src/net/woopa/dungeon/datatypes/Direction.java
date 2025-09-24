package net.woopa.dungeon.datatypes;

import java.util.Random;

public enum Direction {
	NORTH, EAST, SOUTH, WEST;
	private static Random rnd = new Random();

	static public Direction randomDirection() {
		return Direction.values()[rnd.nextInt(4)];
	}

	// Rotate 90 degrees clockwise
	public Direction rotate90() {
		return values()[(ordinal() + 1) % 4];
	}

	// Rotate 180 degrees
	public Direction rotate180() {
		return values()[(ordinal() + 2) % 4];
	}

	// Rotate 270 degrees clockwise (90 counterclockwise)
	public Direction rotate270() {
		return values()[(ordinal() + 3) % 4];
	}

	public Boolean isHorizontal() {
		return this == EAST || this == WEST;
	}

	public Boolean isVertical() {
		return this == NORTH || this == SOUTH;
	}

	public int dx(int steps) {
		if (this == EAST) {
			return steps;
		}
		if (this == WEST) {
			return -steps;
		}
		return 0;
	}

	public int dy(int steps) {
		if (this == NORTH) {
			return steps;
		}
		if (this == SOUTH) {
			return -steps;
		}
		return 0;
	}

	public int forwards_x(int n) {
		if (this == EAST) {
			return n + 1;
		}
		if (this == WEST) {
			return n - 1;
		}
		return n;
	}

	public int forwards_y(int n) {
		if (this == NORTH) {
			return n + 1;
		}
		if (this == SOUTH) {
			return n - 1;
		}
		return n;
	}

	public int backwards_x(int n) {
		if (this == EAST) {
			return n - 1;
		}
		if (this == WEST) {
			return n + 1;
		}
		return n;
	}

	public int backwards_y(int n) {
		if (this == NORTH) {
			return n - 1;
		}
		if (this == SOUTH) {
			return n + 1;
		}
		return n;
	}

	public int left_x(int n) {
		if (this == NORTH) {
			return n - 1;
		}
		if (this == SOUTH) {
			return n + 1;
		}
		return n;
	}

	public int left_y(int n) {
		if (this == EAST) {
			return n + 1;
		}
		if (this == WEST) {
			return n - 1;
		}
		return n;
	}

	public int right_x(int n) {
		if (this == NORTH) {
			return n + 1;
		}
		if (this == SOUTH) {
			return n - 1;
		}
		return n;
	}

	public int right_y(int n) {
		if (this == EAST) {
			return n - 1;
		}
		if (this == WEST) {
			return n + 1;
		}
		return n;
	}
}
