package fr.noxx90.jflam.functions;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import fr.noxx90.jflam.model.Function;

public class Spherical extends Function {

	@Override
	protected Float doCompute(float x, float y) {
		float r2 = computeR2(x, y);
		return new Point2D.Float(x/r2, y/r2);
	}

}
