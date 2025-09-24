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

import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioTime;

public class TUIOControlPoint extends AbstractControlPoint implements TuioListener {
	
	private TuioCursor cursor;
	
	private float screenWidth;
	private float screenHeight;

	public TUIOControlPoint(TuioCursor c, float screenWidth, float screenHeight) {
		this.cursor = c;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public TuioCursor getCursor() {
		return cursor;
	}

	public float getX() {
		return cursor.getScreenX((int) screenWidth);
	}

	public float getY() {
		return cursor.getScreenY((int) screenHeight);
	}

	public float getPreviousX() {
		Vector<TuioPoint> v = cursor.getPath();
		return v.get(v.size()-2).getScreenX((int) screenWidth);
	}

	public float getPreviousY() {
		Vector<TuioPoint> v = cursor.getPath();
		return v.get(v.size()-2).getScreenY((int) screenHeight);
	}

	public void addTuioCursor(TuioCursor tcur) {}

	public void removeTuioCursor(TuioCursor tcur) {
		if (tcur.equals(cursor)) {
			kill();
		}
	}

	public void updateTuioCursor(TuioCursor tcur) {
		if (tcur.equals(cursor)) {
			fireControlPointUpdatedEvent();
		}
	}

	public void refresh(TuioTime ftime) {}

	public void addTuioObject(TuioObject tobj) {}

	public void removeTuioObject(TuioObject tobj) {}

	public void updateTuioObject(TuioObject tobj) {}

}
