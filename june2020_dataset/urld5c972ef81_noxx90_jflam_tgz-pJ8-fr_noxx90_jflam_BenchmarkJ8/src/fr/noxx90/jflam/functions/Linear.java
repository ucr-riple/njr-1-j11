package fr.noxx90.jflam.functions;

import java.awt.geom.Point2D;

import fr.noxx90.jflam.model.Function;

public class Linear extends Function
{

	@Override
	protected Point2D.Float doCompute(float x, float y) {
		return new Point2D.Float(x, y);
	}

}
