/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API;
public abstract class Event {
	
	private String name;
	
	public Event() { }
	
	/**
	 * Get a list of registered listeners
	 * @return The list of listeners
	 */
	public abstract EventList getEvents();
	
	/**
	 * Get the name of the event
	 * @return The name
	 */
	public String getEventName() {
		return ( name == null || name.equals("")) ? getClass().getSimpleName() : name;
	}
}
