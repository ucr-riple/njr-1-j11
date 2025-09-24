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

import processing.core.PApplet;


public class Label extends Rectangle {

	protected String text;

	public Label(float x, float y, String text) {
		super(x, y, 0, 0);
		this.text = text;
		setFillColor(Color.BLACK);
		setStrokeColor(Color.BLACK);
	}

	public String getText() {
		return text;
	}

	public void setText(String t) {
		this.text = t;
	}
	
	@Override
	public void preRender(PApplet p) {
		super.preRender(p);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		setWidth(p.textWidth(getText()));
		setHeight(p.textAscent()+p.textDescent());
	}

	@Override
	public void render(PApplet p) {
		p.text(getText(), 0, 0);
	}

}
