/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.browser;

import java.io.UnsupportedEncodingException;

import com.gamezgalaxy.GGS.iomodel.Browser;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class GET extends Packet {

	public GET(String name, byte ID, PacketManager parent, PacketType packetType) {
		super(name, ID, parent, packetType);
	}
	
	public GET(PacketManager parent) {
		this("GET", (byte)'G', parent, PacketType.Client_to_Server);
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
		if (player instanceof Browser) {
			String full = "";
			try {
				full = new String(message, "US-ASCII");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String url = full.split("\\ ")[1];
			((Browser)player).GET(url, full, server);
		}
	}

	@Override
	public void Write(IOClient player, Server server) {
	}

}
