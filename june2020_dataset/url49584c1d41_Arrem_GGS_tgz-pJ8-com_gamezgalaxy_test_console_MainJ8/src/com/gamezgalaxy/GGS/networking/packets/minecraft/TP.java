/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.gamezgalaxy.GGS.API.io.PacketPrepareEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class TP extends Packet {
	public TP(String name, byte ID, PacketManager parent, PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}

	public TP(PacketManager pm) {
		super("TP", (byte)0x08, pm, PacketType.Server_to_Client);
	}
	
	public byte[] toSend(Player toteleport) {
		ByteBuffer bb = ByteBuffer.allocate(10);
		bb.put(ID);
		bb.put(toteleport.getID());
		bb.putShort(toteleport.getX());
		bb.putShort(toteleport.getY());
		bb.putShort(toteleport.getZ());
		bb.put(toteleport.yaw);
		bb.put(toteleport.pitch);
		return bb.array();
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Write(IOClient p, Server server, Object...parma) { //PLAYER IS THE PLAYER RECIEVING THE DATA, TP IS THE PERSON TP'ING
		PacketPrepareEvent event = new PacketPrepareEvent(p, this, server);
		server.getEventSystem().callEvent(event);
		if (event.isCancelled())
			return;
		Player player;
		Player tp = (Player)parma[0];
		if (p instanceof Player) {
			player = (Player)p;
		}
		else
			return;
		try {
			ByteBuffer bb = ByteBuffer.allocate(10);
			bb.put(ID);
			bb.put((tp == p) ? (byte)-1 : tp.getID());
			bb.putShort(tp.getX());
			bb.putShort(tp.getY());
			bb.putShort(tp.getZ());
			bb.put(tp.yaw);
			bb.put(tp.pitch);
			player.WriteData(bb.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public byte[] HTNO(short x) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeShort(x);
		dos.flush();
		return baos.toByteArray();
	}

	@Override
	public void Write(IOClient player, Server servers) {
	}

}
