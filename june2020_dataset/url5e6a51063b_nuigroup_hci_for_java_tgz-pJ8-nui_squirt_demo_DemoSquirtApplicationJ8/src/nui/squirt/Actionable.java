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

import nui.squirt.event.ActionEvent;
import nui.squirt.listener.ActionListener;

/*
 * Interface representing Components capable of firing ActionEvents
 */
public interface Actionable extends Component{
	
	public void addActionListener(ActionListener a);
	public void removerActionListener(ActionListener a);
	
	public void fireAction(ActionEvent e);
	
	public String getAction();
	public void setAction(String action);
}
