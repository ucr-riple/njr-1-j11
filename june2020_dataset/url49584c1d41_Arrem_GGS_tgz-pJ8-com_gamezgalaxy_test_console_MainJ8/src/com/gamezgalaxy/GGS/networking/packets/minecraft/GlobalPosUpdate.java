/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.IOException;

import com.gamezgalaxy.GGS.API.io.PacketPrepareEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.PacketType;
import com.gamezgalaxy.GGS.server.Server;

public class GlobalPosUpdate extends Packet {

	public GlobalPosUpdate(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}
	
	public GlobalPosUpdate(PacketManager pm) {
		super("GlobalPosUpdate", (byte)0x09, pm, PacketType.Server_to_Client);
	}
	
	public byte[] toSend(Player p) {
		byte[] finals;
		if (posUpdate(p) && rotUpdate(p)) {
			finals = new byte[7];
			finals[0] = ID;
			finals[1] = p.getID();
			finals[2] = (byte)(p.getX() - p.oldX);
			finals[3] = (byte)(p.getY() - p.oldY);
			finals[4] = (byte)(p.getZ() - p.oldZ);
			finals[5] = p.yaw;
			finals[6] = p.pitch;
		}
		else if (posUpdate(p)) {
			finals = new byte[5];
			finals[0] = (byte)0x0a;
			finals[1] = p.getID();
			finals[2] = (byte)(p.getX() - p.oldX);
			finals[3] = (byte)(p.getY() - p.oldY);
			finals[4] = (byte)(p.getZ() - p.oldZ);
		}
		else if (rotUpdate(p)) {
			finals = new byte[4];
			finals[0] = (byte)0x0b;
			finals[1] = p.getID();
			finals[2] = p.yaw;
			finals[3] = p.pitch;
		}
		else { //PING!
			finals = new byte[1];
			finals[0] = (byte)0x01;
		}
		return finals;
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
	}

	@Override
	public void Write(IOClient p, Server server, Object...parma) {
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
		byte[] finals;
		Player toupdate = (Player)parma[0];
		if (posUpdate(toupdate) && rotUpdate(toupdate)) {
			finals = new byte[7];
			finals[0] = ID;
			finals[1] = toupdate.getID();
			finals[2] = (byte)(toupdate.getX() - toupdate.oldX);
			finals[3] = (byte)(toupdate.getY() - toupdate.oldY);
			finals[4] = (byte)(toupdate.getZ() - toupdate.oldZ);
			finals[5] = toupdate.yaw;
			finals[6] = toupdate.pitch;
		}
		else if (posUpdate(toupdate)) {
			finals = new byte[5];
			finals[0] = (byte)0x0a;
			finals[1] = toupdate.getID();
			finals[2] = (byte)(toupdate.getX() - toupdate.oldX);
			finals[3] = (byte)(toupdate.getY() - toupdate.oldY);
			finals[4] = (byte)(toupdate.getZ() - toupdate.oldZ);
		}
		else if (rotUpdate(toupdate)) {
			finals = new byte[4];
			finals[0] = (byte)0x0b;
			finals[1] = toupdate.getID();
			finals[2] = toupdate.yaw;
			finals[3] = toupdate.pitch;
		}
		else { //PING!
			finals = new byte[1];
			finals[0] = (byte)0x01;
		}
		try {
			player.WriteData(finals);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toupdate.oldX = toupdate.getX();
		toupdate.oldY = toupdate.getY();
		toupdate.oldZ = toupdate.getZ();
		toupdate.oldyaw = toupdate.yaw;
		toupdate.oldpitch = toupdate.pitch;
	}
	
	public boolean posUpdate(Player toupdate) {
		return toupdate.getX() - toupdate.oldX != 0 || toupdate.getY() - toupdate.oldY != 0 || toupdate.getZ() - toupdate.oldZ != 0;
	}
	
	public boolean rotUpdate(Player toupdate) {
		return toupdate.yaw - toupdate.oldyaw != 0 || toupdate.pitch - toupdate.oldpitch != 0;
	}

	@Override
	public void Write(IOClient player, Server servers) {
	}

}
