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
import java.util.List;
import java.util.ListIterator;

import nui.squirt.Component;
import nui.squirt.Container;
import nui.squirt.ControlPoint;
import processing.core.PApplet;
import processing.core.PVector;


public class Frame extends Rectangle implements Container {
	
	private List<Component> components = new ArrayList<Component>();
	
	private ControlPoint[] cornerCPs = new ControlPoint[4];
	private static final int UPPER_LEFT = 0;
	private static final int UPPER_RIGHT = 1;
	private static final int LOWER_RIGHT = 2;
	private static final int LOWER_LEFT = 3;
	
	public Frame(float x, float y, float w, float h) {
		super(x,y,w,h);
		setFillColor(new Color(75, 75, 75, 220));
		setStrokeColor(new Color(0, 0, 0, 0));
	}

	public Frame(float x, float y) {
		this(x, y, 100, 100);
	}

	public void render(PApplet p) {
		super.render(p);
		
//		p.rect(-getWidth()*0.475F, -getHeight()*0.475F, getWidth()*0.05F, getHeight()*0.05F);
//		p.rect(getWidth()*0.475F, -getHeight()*0.475F, getWidth()*0.05F, getHeight()*0.05F);
//		p.rect(getWidth()*0.475F, getHeight()*0.475F, getWidth()*0.05F, getHeight()*0.05F);
//		p.rect(-getWidth()*0.475F, getHeight()*0.475F, getWidth()*0.05F, getHeight()*0.05F);

		List<Component> l = new ArrayList<Component>(getComponents());
		for (Component c: l) {
			c.preRender(p);
			c.render(p);
			c.postRender(p);
		}
	}

	public void add(Component c) {
		getComponents().add(c);
		c.setParent(this);
	}

	public void add(Component c, Object constraints) {
		this.add(c);
	}

	public List<Component> getComponents() {
		return components;
	}

	public void remove(Component c) {
		getComponents().remove(c);
		c.setParent(null);
	}
	
	@Override
	public boolean canAcceptMoreControlPoints() {
		if (super.canAcceptMoreControlPoints()) {
			return true;
		}
		else {
			for (Component c: getComponents()) {
				if (c.canAcceptMoreControlPoints())
					return true;
			}
			return false;
		}
	}
	
	@Override
	public boolean isUnderPoint(ControlPoint cp) {
		if (super.isUnderPoint(cp)) {
			return true;
		}
		else {
			for (Component c: getComponents()) {
				if (c.isUnderPoint(cp))
					return true;
			}
			return false;
		}
	}
	
	@Override
	public boolean offer(ControlPoint cp) {
		ListIterator<Component> i = getComponents().listIterator(getComponents().size());
		while (i.hasPrevious()) {
			Component c = i.previous();
			if (c.isUnderPoint(cp) && c.offer(cp)) {
				i.remove();
				getComponents().add(c);
				return true;
			}
		}
		if (super.canAcceptMoreControlPoints() && super.isUnderPoint(cp)) {
			return super.offer(cp);
		}
		else return false;
	}
	
	@Override
	public void controlPointCreated(ControlPoint cp) {
		PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
		
		boolean cornerMode = false;
		for (ControlPoint p: cornerCPs) {
			if (p != null) {
				cornerMode = true;
				break;
			}
		}
		
		if (controlPoints.size() == 0) {
			if (cornerCPs[UPPER_LEFT] == null && cornerCPs[LOWER_LEFT] == null && cornerCPs[UPPER_RIGHT] == null &&
					l.x > -getWidth()/2 && l.x < -getWidth()*0.45 &&
					l.y > -getHeight()/2 && l.y < -getHeight()*0.45) {
				cornerCPs[UPPER_LEFT] = cp;
			}
			else if (cornerCPs[UPPER_RIGHT] == null && cornerCPs[UPPER_LEFT] == null && cornerCPs[LOWER_RIGHT] == null &&
					l.x < getWidth()/2 && l.x > getWidth()*0.45 &&
					l.y > -getHeight()/2 && l.y < -getHeight()*0.45) {
				cornerCPs[UPPER_RIGHT] = cp;
			}
			else if (cornerCPs[LOWER_RIGHT] == null && cornerCPs[UPPER_RIGHT] == null && cornerCPs[LOWER_LEFT] == null &&
					l.x < getWidth()/2 && l.x > getWidth()*0.45 &&
					l.y < getHeight()/2 && l.y > getHeight()*0.45) {
				cornerCPs[LOWER_RIGHT] = cp;
			}
			else if (cornerCPs[LOWER_LEFT] == null && cornerCPs[LOWER_RIGHT] == null && cornerCPs[UPPER_LEFT] == null &&
					l.x > -getWidth()/2 && l.x < -getWidth()*0.45 &&
					l.y < getHeight()/2 && l.y > getHeight()*0.45) {
				cornerCPs[LOWER_LEFT] = cp;
			}
			else if (!cornerMode) {
				super.controlPointCreated(cp);
			}
		}
		else {
			super.controlPointCreated(cp);
		}
	}
	
	@Override
	public void controlPointDied(ControlPoint cp) {
		boolean isCornerPoint = false;
		for (int i = 0; i < cornerCPs.length; i++) {
			if (cornerCPs[i] != null && cornerCPs[i].equals(cp)) {
				cornerCPs[i] = null;
				isCornerPoint = true;
				break;
			}
		}
		if (!isCornerPoint) {
			super.controlPointDied(cp);
		}
	}
	
	@Override
	public void controlPointUpdated(ControlPoint cp) {
		PVector newPos = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
		PVector oldPos = transformToLocalSpace(new PVector(cp.getPreviousX(), cp.getPreviousY()));
		float diffX = newPos.x - oldPos.x;
		float diffY = newPos.y - oldPos.y;
		
		if (cornerCPs[UPPER_LEFT] != null && cornerCPs[UPPER_LEFT].equals(cp)) {
			setWidth(getWidth()-diffX);
			setHeight(getHeight()-diffY);
			getTransformMatrix().translate(diffX/2, diffY/2);
		}
		else if (cornerCPs[UPPER_RIGHT] != null && cornerCPs[UPPER_RIGHT].equals(cp)) {
			setWidth(getWidth()+diffX);
			setHeight(getHeight()-diffY);
			getTransformMatrix().translate(diffX/2, diffY/2);
		}
		else if (cornerCPs[LOWER_RIGHT] != null && cornerCPs[LOWER_RIGHT].equals(cp)) {
			setWidth(getWidth()+diffX);
			setHeight(getHeight()+diffY);
			getTransformMatrix().translate(diffX/2, diffY/2);
		}
		else if (cornerCPs[LOWER_LEFT] != null && cornerCPs[LOWER_LEFT].equals(cp)) {
			setWidth(getWidth()-diffX);
			setHeight(getHeight()+diffY);
			getTransformMatrix().translate(diffX/2, diffY/2);
		}
		else {
			super.controlPointUpdated(cp);
		}
	}

}
