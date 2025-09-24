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



public interface ControlPoint {
	
	public boolean isDead();
	public void kill();

	public float getX();
	public float getY();
	public float getPreviousX();
	public float getPreviousY();
	
	public void addControlPointListener(ControlPointListener l);
	public void removeControlPointListener(ControlPointListener l);
	
	public void fireControlPointCreatedEvent();
	public void fireControlPointDiedEvent();
	public void fireControlPointUpdatedEvent();
}
