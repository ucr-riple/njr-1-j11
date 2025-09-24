package fr.noxx90.jflam.functions;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import fr.noxx90.jflam.model.Function;

public class Sinusoidal extends Function {

	@Override
	protected Float doCompute(float x, float y) {
		return new Point2D.Float((float) Math.sin(x), (float) Math.sin(y));
	}
	
}
