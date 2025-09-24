/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.gamezgalaxy.GGS.API.io.PacketPrepareEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class Welcome extends Packet {

	public Welcome(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}

	public Welcome(PacketManager pm) {
		super("Welcome", (byte)0x00, pm, PacketType.Server_to_Client);
	}

	@Override
	public void Write(IOClient player, Server server) {
		PacketPrepareEvent event = new PacketPrepareEvent(player, this, server);
		server.getEventSystem().callEvent(event);
		if (event.isCancelled())
			return;
		try {
			boolean isop = false;
			if (player instanceof Player) {
				isop = ((Player)player).getGroup().isOP;
			}
			byte[] finals = new byte[131];
			finals[0] = ID;
			finals[1] = 0x07;
			while (server.Name.length() < 64)
				server.Name += " ";
			while (server.MOTD.length() < 64)
				server.MOTD += " ";
			byte[] name = server.Name.getBytes("US-ASCII");
			byte[] motd = server.MOTD.getBytes("US-ASCII");
			System.arraycopy(name, 0, finals, 2, name.length);
			System.arraycopy(motd, 0, finals, name.length + 2, motd.length);
			finals[130] = (isop ? (byte)100 : (byte)0);
			player.WriteData(finals);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
		// TODO Auto-generated method stub

	}

}
