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
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;

import nui.squirt.ControlPoint;
import nui.squirt.Valuable;
import nui.squirt.event.ValueEvent;
import nui.squirt.listener.ValueListener;
import processing.core.PApplet;
import processing.core.PVector;


public class Slider extends Rectangle implements Valuable {

	private Collection<ValueListener> listeners = new ArrayList<ValueListener>();
	
	private float length;
	
	private float minValue;
	private float maxValue;
	private float value;
	
	private ControlPoint current = null;

	public Slider(float x, float y, float l) {
		super(x, y, 60, 90);
		this.length = l;
	}
	
	public Slider(float x, float y, float l, float minValue, float maxValue) {
		this(x, y, l);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = getCenterValue();
	}
	
	public Slider(float x, float y, float l, float minValue, float maxValue, float initValue) {
		this(x, y, l, minValue, maxValue);
		this.value = initValue;
	}

	public void addValueListener(ValueListener l) {
		listeners.add(l);
	}

	public void removeValueListener(ValueListener l) {
		listeners.remove(l);
	}

	public void fireValueChanged(ValueEvent e) {
		for (ValueListener l: listeners) {
			l.valueChanged(e);
		}
	}
	
	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		float oldValue = getValue();
		
		float v = value;
		if (value > getMaxValue())
			v = getMaxValue();
		else if (value < getMinValue())
			v = getMinValue();
		this.value = v;
		
		AffineTransform t = getTransformMatrix();
		double a = getValue()-oldValue;
		double b = a/getValueRange();
		double c = b*getLength();
		double d = -c;
		t.translate(0, d);
		
		ValueEvent e = new ValueEvent(this);
		e.setOldValue(oldValue);
		e.setNewValue(getValue());
		fireValueChanged(e);
	}

	public float getCenterValue() {
		return getValueRange()/2 + getMinValue();
	}

	public float getValueRange() {
		return getMaxValue() - getMinValue();
	}
	
	@Override
	public void render(PApplet p) {
		// Draw the path of the slider
		p.stroke(25);
		p.fill(75);

		float h = (getValue()-getCenterValue()) / getValueRange() * getLength();
		p.rect(0, h, 5, getLength());
		
		setStrokeColor(new Color(25));
		super.render(p);
		p.stroke(100);
		p.line(-getWidth()/2, 0, getWidth()/2, 0);
	}
	
	@Override
	public boolean canAcceptMoreControlPoints() {
		return current == null;
	}
	
	@Override
	public void controlPointCreated(ControlPoint cp) {
		if (current == null) {
			PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			if (l.x > -getWidth()/2 && l.x < getWidth()/2 && l.y > -getHeight()/2 && l.y < getHeight()/2) {
				current = cp;
			}
		}
	}
	
	@Override
	public void controlPointDied(ControlPoint cp) {
		if (current != null && cp.equals(current)) {
			current = null;
		}
	}
	
	@Override
	public void controlPointUpdated(ControlPoint cp) {
		if (current != null && cp.equals(current)) {
			PVector newPos = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
//			PVector oldPos = transformToLocalSpace(new PVector(cp.getPreviousX(), cp.getPreviousY()));
			if (newPos.x >= -getWidth() && newPos.x <= getWidth()) {
				setValue(-newPos.y*getValueRange()/getLength() + getValue());
			}
			else current = null;
		}
	}
	
	// Old code for rendering
//	public void draw(Slider s) {
//	ProcessingRenderingEngine engine = (ProcessingRenderingEngine) getRenderingEngine();
//	PApplet pApplet = engine.getPApplet();
//	
//	pApplet.stroke(25);
//	pApplet.fill(75);
//	pApplet.rectMode(PApplet.CENTER);
//	
//	pApplet.rect(0, 0, 5, s.getLength());
//	
//	float h = -(s.getValue()-s.getCenterValue()) / s.getValueRange() * s.getLength();
//	
//	pApplet.stroke(25);
//	pApplet.rect(0, h, s.getThumbWidth(), s.getThumbHeight());
//	pApplet.stroke(100);
//	pApplet.line(-s.getThumbWidth()/2, h, s.getThumbWidth()/2, h);
//}

}
