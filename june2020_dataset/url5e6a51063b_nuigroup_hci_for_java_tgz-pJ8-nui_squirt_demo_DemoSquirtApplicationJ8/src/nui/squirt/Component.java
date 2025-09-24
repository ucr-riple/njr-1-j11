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
package nui.squirt;

import java.awt.geom.AffineTransform;


import processing.core.PApplet;
import processing.core.PVector;

public interface Component extends ControlPointListener {
	
	public void update();
	
	public void preRender(PApplet p);
	public void render(PApplet p);
	public void postRender(PApplet p);
	
	public AffineTransform getTransformMatrix();
	public AffineTransform getAbsoluteTransformMatrix();
	
	public PVector transformToLocalSpace(PVector v);
	
	public void setParent(Container p);
	public Container getParent();
	
}
