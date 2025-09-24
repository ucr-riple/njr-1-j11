/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world.blocks;

import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.world.PhysicsBlock;

public class Water extends PhysicsBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Water(byte ID, String name) {
		super(ID, name);
		// TODO Auto-generated constructor stub
	}
	
	public Water() {
		super((byte)8, "Water");
	}
	
	public Water(byte b, String string, Server s) {
		super(b, string, s);
	}

	@Override
	public PhysicsBlock clone(Server s) {
		Water w = new Water((byte)8, "Water", s);
		return w;
	}

	@Override
	public void tick() {
		//TODO Add physics for water
	}

}
