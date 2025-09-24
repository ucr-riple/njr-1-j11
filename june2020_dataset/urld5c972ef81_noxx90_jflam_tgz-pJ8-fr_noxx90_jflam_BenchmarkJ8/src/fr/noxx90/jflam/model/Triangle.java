package fr.noxx90.jflam.model;

import java.awt.geom.Point2D;

public class Triangle
{
	protected Point2D.Float o;
	protected Point2D.Float x;
	protected Point2D.Float y;
	
	public Triangle(float absX, float ordX, float absY, float ordY, float absO, float ordO) {
		o = new Point2D.Float(absO, ordO);
		x = new Point2D.Float(absX, ordX);
		y = new Point2D.Float(absY, ordY);
	}

	public Point2D.Float getO() {
		return o;
	}

	public Point2D.Float getX() {
		return x;
	}

	public Point2D.Float getY() {
		return y;
	}
}
