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

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import nui.squirt.Component;
import nui.squirt.Container;
import processing.core.PApplet;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PVector;

public abstract class AbstractComponent implements Component {
	
	private AffineTransform transformMatrix;
	
	private Container parent;
	
	public AbstractComponent() {
		this.transformMatrix = new AffineTransform();
	}
	
	public AbstractComponent(float x, float y) {
		this.transformMatrix = AffineTransform.getTranslateInstance(x, y);
	}
	
	public void preRender(PApplet p) {
		update();
		
		p.pushMatrix();
		double[] matrix = new double[6];
		getTransformMatrix().getMatrix(matrix);
		PMatrix m = new PMatrix2D((float)matrix[0], (float)matrix[2], (float)matrix[4], (float)matrix[1], (float)matrix[3], (float)matrix[5]);
		p.applyMatrix(m);
	}
	
	public void postRender(PApplet p) {
		p.popMatrix();
	}
	
	public AffineTransform getTransformMatrix() {
		return transformMatrix;
	}
	
	public AffineTransform getAbsoluteTransformMatrix() {
		AffineTransform t = new AffineTransform();
		if (parent != null) {
			t = parent.getAbsoluteTransformMatrix();
		}
		t.concatenate(getTransformMatrix());
		return t;
	}
	
	public PVector transformToLocalSpace(PVector v) {
		PVector result = new PVector();
		double[] points = { v.x, v.y };
		try {
			getAbsoluteTransformMatrix().inverseTransform(points, 0, points, 0, 1);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.x = (float) points[0];
		result.y = (float) points[1];
		return result;
	}
	
	public Container getParent() {
		return parent;
	}
	
	public void setParent(Container p) {
		this.parent = p;
	}
	
}
