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

import nui.squirt.event.ValueEvent;
import nui.squirt.listener.ValueListener;

/*
 * Interface representing Components that have a modifiable continuous value
 */
public interface Valuable extends Component {

	public void addValueListener(ValueListener l);
	public void removeValueListener(ValueListener l);
	
	public void fireValueChanged(ValueEvent e);
	
	public float getValue();
	public void setValue(float v);
	
	public float getMaxValue();
	public void setMaxValue(float maxValue);
	public float getMinValue();
	public void setMinValue(float minValue);
	
	public float getValueRange();
	
	public float getCenterValue();
}
