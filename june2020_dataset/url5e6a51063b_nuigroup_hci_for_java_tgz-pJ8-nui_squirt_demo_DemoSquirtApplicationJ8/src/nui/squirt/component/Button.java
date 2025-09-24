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

import nui.squirt.Actionable;
import nui.squirt.ControlPoint;
import nui.squirt.event.ActionEvent;
import nui.squirt.listener.ActionListener;
import processing.core.PApplet;
import processing.core.PVector;


public class Button extends Rectangle implements Actionable {
	
	private static final Color PRESSED_COLOR = Color.GRAY;
	private static final Color NOT_PRESSED_COLOR = Color.LIGHT_GRAY;
	private static final Color BORDER_COLOR = Color.BLACK;
	private static final float BORDER_WIDTH = 4;

	private Label label;

	private boolean pressed;

	private Collection<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private ControlPoint current = null;
	
	public Button(float x, float y, String text) {
		super(x, y, 0, 0);
		this.label = new Label(0, 0, text);
	}

	public Label getLabel() {
		return label;
	}
	
	public void setText(String text) {
		getLabel().setText(text);
	}

	public boolean isPressed() {
		return pressed;
	}
	
	public void press() {
		this.pressed = true;
	}
	
	public void release() {
		this.pressed = false;
		fireAction(new ActionEvent(this));
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}

	public void removerActionListener(ActionListener a) {
		listeners.remove(a);
	}

	public void fireAction(ActionEvent e) {
		for (ActionListener l: listeners) {
			l.actionPerformed(e);
		}
	}

	public String getAction() {
		return getLabel().getText();
	}

	public void setAction(String action) {
		setText(action);
	}
	
	@Override
	public void update() {
		super.update();
		
		setFillColor(isPressed() ? PRESSED_COLOR : NOT_PRESSED_COLOR);
		setStrokeColor(BORDER_COLOR);
		setStrokeWeight(BORDER_WIDTH);
		
		setWidth((float) (label.getWidth() * 1.4));
		setHeight((float) (label.getHeight() * 1.6));
	}
	
	@Override
	public void render(PApplet p) {
		super.render(p);
		
		getLabel().preRender(p);
		getLabel().render(p);
		getLabel().postRender(p);
	}
	
	@Override
	public boolean canAcceptMoreControlPoints() {
		return current == null;
	}
	
	@Override
	public void controlPointCreated(ControlPoint cp) {
		if (current == null) {
			// TODO swap code below with call to isUnderPoint()
			PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			if (l.x > -getWidth()/2 && l.x < getWidth()/2 && l.y > -getHeight()/2 && l.y < getHeight()/2) {
				press();
				current = cp;
			}
		}
	}
	
	@Override
	public void controlPointUpdated(ControlPoint cp) {
		if (current != null && cp.equals(current)) {
			// TODO swap code below with call to isUnderPoint()
			PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			if (l.x > -getWidth()/2 && l.x < getWidth()/2 && l.y > -getHeight()/2 && l.y < getHeight()/2) {
				pressed = true;
			}
			else pressed = false;
		}
	}
	
	@Override
	public void controlPointDied(ControlPoint cp) {
		if (current != null && cp.equals(current)) {
			// TODO swap code below with call to isUnderPoint()
			PVector l = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			if (l.x > -getWidth()/2 && l.x < getWidth()/2 && l.y > -getHeight()/2 && l.y < getHeight()/2) {
				release();
			}
			else pressed = false;
			current = null;
		}
	}
	
}
