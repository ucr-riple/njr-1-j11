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


public interface ControlPointListener {
	
	public void controlPointCreated(ControlPoint cp);
	public void controlPointDied(ControlPoint cp);
	public void controlPointUpdated(ControlPoint cp);
	
	/**
	 * @return this Component can accept offers of additional ControlPoints
	 */
	public boolean canAcceptMoreControlPoints();
	
	/**
	 * @param a ControlPoint to potentially be offered to this Component
	 * @return the ControlPoint falls within the interactive area of a Component and thus should be offered
	 */
	public boolean isUnderPoint(ControlPoint cp);
	
	/**
	 *  This function assumes that recent calls have been made to canAcceptMoreControlPoints()
	 *  and isUnderPoint() before the point is offered.
	 * @param a ControlPoint to be offered to this Component
	 * @return the ControlPoint is accepted and no other Components should be offered it.
	 */
	public boolean offer(ControlPoint cp);

}
