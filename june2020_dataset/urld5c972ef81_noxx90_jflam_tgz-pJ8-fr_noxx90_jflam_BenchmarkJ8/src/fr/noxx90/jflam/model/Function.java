package fr.noxx90.jflam.model;

import java.awt.geom.Point2D;

public abstract class Function {
	public Point2D.Float compute(Form form, float x, float y) {
		return doCompute(form.getA()*x + form.getB()*y + form.getC(), form.getD()*x + form.getE()*y + form.getF());
	}
	
	protected abstract Point2D.Float doCompute(float x, float y);
	
	public float computeR(float x, float y) {
		return (float) Math.hypot(x, y);
	}
	
	public float computeR2(float x, float y) {
		return x*x + y*y;
	}
	
	public float computeTheta(float x, float y) {
		return (float) Math.atan(x/y);
	}
	
	public float computePsi(float x, float y) {
		return (float) Math.atan(y/x);
	}
}
