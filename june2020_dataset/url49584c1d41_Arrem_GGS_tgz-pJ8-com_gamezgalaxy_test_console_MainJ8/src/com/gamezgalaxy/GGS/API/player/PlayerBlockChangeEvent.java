/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.player;

import com.gamezgalaxy.GGS.API.Cancelable;
import com.gamezgalaxy.GGS.API.EventList;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.world.Block;
import com.gamezgalaxy.GGS.world.Level;
import com.gamezgalaxy.GGS.world.PlaceMode;

public class PlayerBlockChangeEvent extends PlayerEvent implements Cancelable {

	private static EventList events = new EventList();
	
	private boolean _canceled;
	
	private PlaceMode _type;
	
	private Block block;
	
	private short X;
	private short Y;
	private short Z;
	private Level level;
	private Server server;
	
	
	public PlayerBlockChangeEvent(Player who, short X, short Y, short Z, Block id, Level level, Server server, PlaceMode place) {
		super(who);
		this.block = id;
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.level = level;
		this.server = server;
		this._type = place;
	}
	
	public short getX() {
		return X;
	}
	
	public short getY() {
		return Y;
	}
	
	public short getZ() {
		return Z;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Server getServer() {
		return server;
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
	
	public Block getBlock() {
		return block;
	}
	
	public PlaceMode getPlaceType() {
		return _type;
	}
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	@Override
	public boolean isCancelled() {
		return _canceled;
	}

	@Override
	public void Cancel(boolean cancel) {
		_canceled = cancel;
	}
}
