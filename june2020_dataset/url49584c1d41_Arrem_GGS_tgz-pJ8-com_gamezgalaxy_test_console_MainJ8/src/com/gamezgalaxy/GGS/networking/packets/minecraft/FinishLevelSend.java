/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.gamezgalaxy.GGS.API.io.PacketPrepareEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class FinishLevelSend extends Packet {

	public FinishLevelSend(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}

	public FinishLevelSend(PacketManager pm) {
		super("Finish Level Send", (byte)0x04, pm, PacketType.Server_to_Client);
	}

	@Override
	public void Write(IOClient p, Server server) {
		PacketPrepareEvent event = new PacketPrepareEvent(p, this, server);
		server.getEventSystem().callEvent(event);
		if (event.isCancelled())
			return;
		Player player;
		if (p instanceof Player) {
			player = (Player)p;
		}
		else
			return;
		try {
			ByteBuffer bb = ByteBuffer.allocate(7);
			bb.order(ByteOrder.BIG_ENDIAN);
			bb.put(ID);
			bb.putShort(player.getLevel().width);
			bb.putShort(player.getLevel().height);
			bb.putShort(player.getLevel().depth);
			player.WriteData(bb.array());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
		// TODO Auto-generated method stub

	}
	public byte[] HTNO(short x) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(2); 
		bb.order(ByteOrder.BIG_ENDIAN); 
		bb.putShort(x); 
		return bb.array();
	}
}
