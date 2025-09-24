/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world;

public enum PlaceMode {
	/**
	 * The player broke the block
	 */
	BREAK((byte)0),
	/**
	 * The player placed the block
	 */
	PLACE((byte)1);

	private byte type;

	PlaceMode(byte type) { this.type = type; }
	
	/**
	 * Convert a {@link PlaceMode} object to a byte
	 * @return
	 *         The converted byte
	 */
	public byte getType() {
		return type;
	}
	
	/**
	 * Convert a byte to a {@link PlaceMode}
	 * @param mode
	 *            The byte
	 * @return
	 *        The converted {@link PlaceMode} object
	 */
	public static PlaceMode parse(byte mode) {
		switch (mode) {
		case 0:
			return BREAK;
		case 1:
			return PLACE;
		default:
			return BREAK;
		}
	}
}
