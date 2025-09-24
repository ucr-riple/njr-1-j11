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
import java.util.ArrayList;
import java.util.Collection;

import nui.squirt.ControlPoint;
import nui.squirt.Valuable;
import nui.squirt.event.ValueEvent;
import nui.squirt.listener.ValueListener;
import processing.core.PApplet;
import processing.core.PVector;


public class Knob extends Circle implements Valuable {
	
	private static final Color KNOB_COLOR = Color.DARK_GRAY;
	private static final Color KNOB_BORDER_COLOR = Color.BLACK;
	private static final Color INDICATOR_COLOR = Color.ORANGE;
	private static final Color INDICATOR_BORDER_COLOR = Color.BLACK;
	private static final float KNOB_BORDER_WEIGHT = 4;
	
	private Collection<ValueListener> listeners = new ArrayList<ValueListener>();
	
	private float minValue = 0;
	private float maxValue = 1;
	private float value = (float) 0.5;
	
	private float minAngle = 0;
	private float maxAngle = (float) (2 * Math.PI);
	

	public Knob(float x, float y, float r) {
		super(x, y, r);
	}
	
	public Knob(float x, float y, float r, float minValue, float maxValue) {
		this(x, y, r);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}
	
	public Knob(float x, float y, float r, float minValue, float maxValue, float initValue) {
		this(x, y, r, minValue, maxValue);
		setValue(initValue);
	}
	
	public Knob(float x, float y, float r, float minValue, float maxValue, float minAngle, float maxAngle) {
		this(x, y, r, minValue, maxValue);
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		setValue(getCenterValue());
	}
	
	public Knob(float x, float y, float r, float minValue, float maxValue, float minAngle, float maxAngle, float initValue) {
		this(x, y, r, minValue, maxValue, minAngle, maxAngle);
		setValue(initValue);
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
		ValueEvent e = new ValueEvent(this);
		e.setOldValue(getValue());
		
		float v = value;
		if (value > getMaxValue())
			v = getMaxValue();
		else if (value < getMinValue())
			v = getMinValue();
		this.value = v;
		
		e.setNewValue(getValue());
		fireValueChanged(e);
	}

	public float getCenterValue() {
		return getValueRange()/2 + getMinValue();
	}

	public float getValueRange() {
		return getMaxValue() - getMinValue();
	}

	public float getMinAngle() {
		return minAngle;
	}

	public void setMinAngle(float minAngle) {
		this.minAngle = minAngle;
	}

	public float getMaxAngle() {
		return maxAngle;
	}

	public void setMaxAngle(float maxAngle) {
		this.maxAngle = maxAngle;
	}

	public float getAngleRange() {
		return getMaxAngle() - getMinAngle();
	}
	
	public float getRotation() {
		return getAngleRange() * (getValue()-getMinValue())/getValueRange() + getMinAngle();
	}

	public void setRotation(float rotation) {
		float r = rotation;
		
		if (rotation > getMaxAngle())
			r = getMaxAngle();
		else if (rotation < getMinAngle())
			r = getMinAngle();
		
		setValue((r-getMinAngle())/getAngleRange() * getValueRange() + getMinValue());
	}
	
	@Override
	public void update() {
		super.update();
		
		setFillColor(KNOB_COLOR);
		setStrokeColor(KNOB_BORDER_COLOR);
		setStrokeWeight(KNOB_BORDER_WEIGHT);
	}
	
	@Override
	public void render(PApplet p) {
		super.render(p);
		
		// Draw indicator
		p.fill(INDICATOR_COLOR.getRGB());
		p.stroke(INDICATOR_BORDER_COLOR.getRGB());
		p.strokeWeight(KNOB_BORDER_WEIGHT/2);
		
		float r = getRadius()*0.85F;
		
		p.ellipse(r*PApplet.cos(getRotation()), r*PApplet.sin(getRotation()), getRadius()*(float)0.1, getRadius()*(float)0.1);
	}
	
	@Override
	public boolean canAcceptMoreControlPoints() {
		return true;
	}
	
	@Override
	public void controlPointUpdated(ControlPoint cp) {
		PVector newPos = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
		PVector oldPos = transformToLocalSpace(new PVector(cp.getPreviousX(), cp.getPreviousY()));
		double angleNew = Math.atan2(newPos.y, newPos.x);
		double angleOld = Math.atan2(oldPos.y, oldPos.x);
		double angle = angleNew - angleOld;
		if (angle > Math.PI) {
			angle = angle - 2*Math.PI;
		}
		if (angle < -Math.PI) {
			angle = 2*Math.PI + angle;
		}
		setRotation((float) (getRotation()+angle));
	}
}
