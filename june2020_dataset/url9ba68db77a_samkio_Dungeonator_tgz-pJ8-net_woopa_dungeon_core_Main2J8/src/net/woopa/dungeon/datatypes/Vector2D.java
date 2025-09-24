package net.woopa.dungeon.datatypes;

import java.util.Random;

public class Vector2D {
	private int x;
	private int y;

	/*public Vector2D() {
		this.x = -1;
		this.y = -1;
	}*/

	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D v, int offset) {
		this.x = v.x + offset;
		this.y = v.y + offset;
	}

	public Vector2D(Random rnd, int x, int y) {
		this.x = (x < 1) ? -1 : rnd.nextInt(x);
		this.y = (y < 1) ? -1 : rnd.nextInt(y);
	}

	public Vector2D(Random rnd, Vector2D v) {
		this.x = (v.x < 1) ? -1 : rnd.nextInt(v.x);
		this.y = (v.y < 1) ? -1 : rnd.nextInt(v.y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void set(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}
/*
	public Vector2D move(Vector2D step) {
		return new Vector2D(x + step.x - 1, y + step.y - 1);
	}

	public Vector2D north() {
		return new Vector2D(this.x, this.y + 1);
	}

	public Vector2D east() {
		return new Vector2D(this.x + 1, this.y);
	}

	public Vector2D south() {
		return new Vector2D(this.x, this.y - 1);
	}

	public Vector2D west() {
		return new Vector2D(this.x - 1, this.y);
	}

	public Boolean isOutside(Vector2D that) {
		if (this.x < 0 || this.y < 0 || this.x >= that.x || this.y >= that.y)
			return true;
		return false;
	}

	public Boolean isInside(Vector2D that) {
		return !isOutside(that);
	}*/

	public Vector2D clone() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
