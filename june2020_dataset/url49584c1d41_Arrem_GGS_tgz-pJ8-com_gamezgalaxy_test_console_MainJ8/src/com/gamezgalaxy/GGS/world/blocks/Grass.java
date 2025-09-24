/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world.blocks;

import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.world.Block;
import com.gamezgalaxy.GGS.world.Level;

public class Grass extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Grass(byte ID, String name) {
		super(ID, name);
		// TODO Auto-generated constructor stub
	}
	
	public Grass() {
		super((byte)2, "Grass");
	}
	
	@Override
	public void onPlace(Level l, int index, Server server) {
		int[] pos = l.IntToPos(index);
		int x = pos[0];
		int y = pos[1];
		int z = pos[2];
		if (l.getTile(x, y - 1, z) == Block.getBlock("Grass"))
			l.setTile(Block.getBlock("Dirt"), x, y - 1, z, server);
	}
}
