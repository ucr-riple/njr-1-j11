package fr.noxx90.jflam.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class Form
{
	protected String name;
	protected float a,b,c,d,e,f;
	protected float weight;
	protected Map<Function, Float> functions;
	protected Color color;
	
	public Form(float a, float b, float c, float d, float e, float f, float weight, Color color) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.weight = weight;
		this.functions = new HashMap<Function, Float>();
		this.color = color;
	}
	
	public Form(Triangle triangle, float weight, Color color) {
		setByTriangle(triangle);
		this.weight = weight;
		this.functions = new HashMap<Function, Float>();
		this.color = color;
	}
	
	public void compute(Point2D.Float p) {
		float x = 0, y = 0;
		
		for(Function f : functions.keySet()) {
			float w = functions.get(f);
			Point2D.Float current = f.compute(this, p.x, p.y);
			x += w*current.x;
			y += w*current.y;
		}
		
		p.setLocation(x, y);
	}
	
	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getC() {
		return c;
	}

	public void setC(float c) {
		this.c = c;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public float getE() {
		return e;
	}

	public void setE(float e) {
		this.e = e;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public float getWeight() {
		return weight;
	}
	
	public Map<Function, Float> getFunctions() {
		return functions;
	}
	
	public void add(Function f) {
		add(f, 1);
	}
	
	public void add(Function f, float c) {
		functions.put(f, c);
	}
	
	public void remove(Function f) {
		functions.remove(f);
	}

	@Override
	public String toString() {
		return "Form [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e="
				+ e + ", f=" + f + ", weight=" + weight + ", functions="
				+ functions + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}
	
	public void setByTriangle(Triangle t) {
		a = t.getX().x - t.getO().x;
		b = t.getY().x - t.getO().x;
		c = t.getO().x;
		d = t.getX().y - t.getO().y;
		e = t.getY().y - t.getO().y;
		f = t.getO().y;
	}
}
