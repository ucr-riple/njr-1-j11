/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.system.heartbeat;

import java.util.ArrayList;

import com.gamezgalaxy.GGS.server.Server;

public class Beat {
	private Server server;

	private ArrayList<Heart> hearts = new ArrayList<Heart>();

	private Thread beater;
	
	private boolean running;

	/**
	 * Create a new Beater
	 * @param server
	 *             The server this beater will
	 *             beat for.
	 */
	public Beat(Server server) {
		this.server = server;
	}

	/**
	 * Add a heart to beat
	 * @param h
	 *         The {@link Heart} object
	 */
	public void addHeart(Heart h) {
		synchronized(hearts) {
			if (!hearts.contains(h))
				hearts.add(h);
		}
	}
	
	/**
	 * Remove a heart currently beating.
	 * @param h
	 *         The {@link Heart} object
	 */
	public void removeHeart(Heart h) {
		synchronized(hearts) {
			if (hearts.contains(h))
				hearts.remove(h);
		}
	}
	
	/**
	 * Start beating. If the beater already started beating, then
	 * nothing will happen.
	 */
	public void start() {
		if (running)
			return;
		running = true;
		beater = new Beater(this);
		beater.start();
	}
	
	/**
	 * Stop beating. If the beater is already stopped, then
	 * nothing will happen.
	 */
	public void stop() {
		if (!running)
			return;
		running = false;
	}

	/**
	 * Get the server this beater is beating for.
	 * @return
	 *        The {@link Server} object
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Set the server this beater is beating for.
	 * @param server
	 *              The {@link Server} object
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Get an {@link ArrayList} of hearts currently
	 * beating.
	 * @return
	 *        An {@link ArrayList} of hearts
	 */
	public ArrayList<Heart> getHearts() {
		return hearts;
	}

	/**
	 * Weather or not this beater is running
	 * @return
	 *        Returns true if the beater is running, otherwise it will
	 *        return false.
	 */
	public boolean isRunning() {
		return running;
	}
}
