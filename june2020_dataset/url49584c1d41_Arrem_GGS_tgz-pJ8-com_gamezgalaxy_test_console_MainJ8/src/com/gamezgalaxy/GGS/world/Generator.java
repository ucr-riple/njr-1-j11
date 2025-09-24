/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world;

public interface Generator {
	
	/**
	 * Generate the structure/world
	 * @param l
	 *         The level where to generate
	 */
	public void generate(Level l);
	
	/**
	 * Generate the structure/world
	 * @param l
	 *         The level where to generate
	 * @param x
	 *         The x position where to generate
	 * @param y
	 *         The y position where to generate
	 * @param z
	 *         The z position where to generate
	 */
	public void generate(Level l, int x, int y, int z);
}
