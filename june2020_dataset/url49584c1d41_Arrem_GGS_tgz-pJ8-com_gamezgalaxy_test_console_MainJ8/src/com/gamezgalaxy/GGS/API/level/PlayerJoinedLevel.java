/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.level;

import com.gamezgalaxy.GGS.API.EventList;
import com.gamezgalaxy.GGS.API.player.PlayerEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.world.Level;

public class PlayerJoinedLevel extends PlayerEvent {

	private static EventList events = new EventList();
	Level _level;
	public PlayerJoinedLevel(Player who, Level level) {
		super(who);
	}

	@Override
	public EventList getEvents() {
		return events;
	}
	
	/**
	 * Get a list of registered listeners
	 * @return The list of listeners
	 */
	public static EventList getEventList() {
		return events;
	}
	
	/**
	 * Get the level the player joined
	 * @return The level object
	 */
	public Level getLevel() {
		return _level;
	}

}
