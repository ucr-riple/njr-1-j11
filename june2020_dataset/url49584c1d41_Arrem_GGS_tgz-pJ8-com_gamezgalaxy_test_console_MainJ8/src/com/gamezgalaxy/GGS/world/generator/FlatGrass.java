/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world.generator;

import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.world.Block;
import com.gamezgalaxy.GGS.world.Generator;
import com.gamezgalaxy.GGS.world.Level;

public class FlatGrass implements Generator {

	private Server _server;
	
	/**
	 * The constructor for the flatgrass generator
	 * @param server
	 *              The server the level is in
	 */
	public FlatGrass(Server server) {
		this._server = server;
	}
	@Override
	public void generate(Level l) {
		for (int x = 0; x < l.width; x++) {
			for (int y = 0; y < l.height; y++) {
				for (int z = 0; z < l.depth; z++) {
					if (y < l.height / 2)
						l.setTile(Block.getBlock("dirt"), x, y, z, _server);
					else if (y == l.height / 2)
						l.setTile(Block.getBlock("grass"), x, y, z, _server);
					else
						l.setTile(Block.getBlock("air"), x, y, z, _server);
				}
			}
		}
	}
	@Override
	public void generate(Level l, int x, int y, int z) {
		generate(l);
	}

}
