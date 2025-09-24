/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets;

public enum PacketType {
	/**
	 * Weather the packet only sends from the Server to the Client
	 */
	Server_to_Client,
	/**
	 * Weather the packet is only received from the Client 
	 * <b>or</b>
	 * is both sent and received.
	 */
	Client_to_Server
}
