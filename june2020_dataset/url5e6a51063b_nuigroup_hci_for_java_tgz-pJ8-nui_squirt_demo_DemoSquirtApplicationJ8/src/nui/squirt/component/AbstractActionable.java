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

import java.util.ArrayList;
import java.util.List;

import nui.squirt.Actionable;
import nui.squirt.event.ActionEvent;
import nui.squirt.listener.ActionListener;

public abstract class AbstractActionable extends AbstractComponent implements Actionable {

	private List<ActionListener> actionListeners = new ArrayList<ActionListener>();
	
	private String action;

	public AbstractActionable(float x, float y) {
		super(x, y);
	}

	public void addActionListener(ActionListener a) {
		this.actionListeners.add(a);
	}

	public void fireAction(ActionEvent e) {
		for (ActionListener l: actionListeners) {
			l.actionPerformed(e);
		}
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
