/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.IOException;

import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class UpdateUser extends Packet {

	public UpdateUser(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}
	
	public UpdateUser(PacketManager pm) {
		super("UpdateUser", (byte)0x0f, pm, PacketType.Server_to_Client);
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
	}

	@Override
	public void Write(IOClient player, Server server) {
		Player p = null;
		if (player instanceof Player) {
			p = (Player)player;
		}
		else
			return;
		byte[] final1 = new byte[2];
		final1[0] = (byte)0x0f;
		final1[1] = (byte)(p.getGroup().isOP ? 0x64 : 0x00);
		try {
			player.WriteData(final1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
