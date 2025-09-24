/*******************************************************************************
 * This file is part of sqUIrt
 * 
 *     Copyright (C) 2009  Ori Rawlings
 * 
 *     sqUIrt is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     sqUIrt is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with sqUIrt.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package nui.squirt.component;

import java.awt.Color;

import nui.squirt.ControlPoint;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import processing.core.PApplet;
import processing.core.PVector;

public class PhysicsCircle extends PhysicsComponent {
	
	private float radius;
	
	private Color fillColor = Color.CYAN;
	private Color strokeColor = Color.BLACK;
	private float strokeWeight = 1;
	
	public PhysicsCircle(float x, float y, float r, World w) {
		super(x, y, w);
		this.radius = r;
		
		// Create collision rectangle
		CircleDef shapeDef = new CircleDef();
		// Make sure it has non-zero density, otherwise it will have 0 mass and be static
		shapeDef.density = 0.1f;
		shapeDef.radius = getRadius();
		shapeDef.localPosition = new Vec2();
		getBody().createShape(shapeDef);
		
		// Set mass of body
		getBody().setMassFromShapes();
	}

	public float getRadius() {
		return radius;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public float getStrokeWeight() {
		return strokeWeight;
	}

	public void update() {
		if (getBody().isFrozen()) setFillColor(Color.DARK_GRAY);
		if (getBody().isSleeping()) {
			setStrokeColor(Color.BLUE);
		}
		else {
			setStrokeColor(Color.BLACK);
		}
	}
	
	@Override
	public void preRender(PApplet p) {
		super.preRender(p);
		
		p.ellipseMode(PApplet.CENTER);
		p.fill(getFillColor().getRGB());
		p.stroke(getStrokeColor().getRGB());
		p.strokeWeight(getStrokeWeight());
	}
	
	@Override
	public void render(PApplet p) {
		p.ellipse(0, 0, getRadius()*2, getRadius()*2);
		
		super.render(p);
	}

	public boolean isUnderPoint(ControlPoint cp) {
		PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
		return (l.dist(new PVector(0, 0)) <= getRadius());
	}

	public boolean offer(ControlPoint cp) {
		cp.addControlPointListener(this);
		return true;
	}

}
