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
package nui.squirt.event;

import nui.squirt.Valuable;

public class ValueEvent extends Event {
	
	private float oldValue;
	private float newValue;

	public ValueEvent(Valuable source) {
		super(source);
	}

	public float getOldValue() {
		return oldValue;
	}

	public void setOldValue(float oldValue) {
		this.oldValue = oldValue;
	}

	public float getNewValue() {
		return newValue;
	}

	public void setNewValue(float newValue) {
		this.newValue = newValue;
	}

}
