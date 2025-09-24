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
package nui.squirt.controlpoint;

import java.util.Vector;

import processing.core.PVector;

public class MouseControlPoint extends AbstractControlPoint {
	
	Vector<PVector> path = new Vector<PVector>();
	
	public MouseControlPoint(PVector initialPoint) {
		path.add(initialPoint);
	}

	public float getPreviousX() {
		return path.size() > 1 ? path.get(path.size() - 2).x : path.lastElement().x;
	}

	public float getPreviousY() {
		return path.size() > 1 ? path.get(path.size() -2).y : path.lastElement().y;
	}

	public float getX() {
		return path.lastElement().x;
	}

	public float getY() {
		return path.lastElement().y;
	}

	public void addToPath(PVector newPoint) {
		path.add(newPoint);
		fireControlPointUpdatedEvent();
	}
}
