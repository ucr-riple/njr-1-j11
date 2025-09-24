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

import java.util.ArrayList;
import java.util.Collection;

import nui.squirt.ControlPoint;
import nui.squirt.ControlPointListener;


public abstract class AbstractControlPoint implements ControlPoint {
	
	public boolean dead = false;
	private Collection<ControlPointListener> listeners = new ArrayList<ControlPointListener>();
	
	public boolean isDead() {
		return dead;
	}
	
	public void kill() {
		this.dead = true;
		fireControlPointDiedEvent();
	}
	
	public void addControlPointListener(ControlPointListener l) {
		listeners.add(l);
	}
	
	public void removeControlPointListener(ControlPointListener l) {
		listeners.remove(l);
	}
	
	protected Collection<ControlPointListener> getControlPointListeners() {
		return listeners;
	}
	
	public void fireControlPointCreatedEvent() {
		for (ControlPointListener l: getControlPointListeners()) {
			l.controlPointCreated(this);
		}
	}
	
	public void fireControlPointDiedEvent() {
		for (ControlPointListener l: getControlPointListeners()) {
			l.controlPointDied(this);
		}
	}
	
	public void fireControlPointUpdatedEvent() {
		for (ControlPointListener l: getControlPointListeners()) {
			l.controlPointUpdated(this);
		}
	}
	
}
