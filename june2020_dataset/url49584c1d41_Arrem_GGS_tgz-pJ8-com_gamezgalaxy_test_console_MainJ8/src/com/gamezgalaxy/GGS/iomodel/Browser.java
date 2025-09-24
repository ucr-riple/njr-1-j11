/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.iomodel;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

import com.gamezgalaxy.GGS.API.browser.BrowserGETRequestEvent;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.server.Server;

public class Browser extends IOClient {

	public Browser(Socket client, PacketManager pm, byte opcode) {
		super(client, pm);
		writer = null;
		try {
			writer = new PrintStream(client.getOutputStream(), false, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (opcode != 255) {
			Packet packet = pm.getPacket(opcode);
			if (packet == null) {
				pm.server.Log("Client sent " + opcode);
				pm.server.Log("How do..?");
			} else {
				byte[] message = new byte[0xFF];
				try {
					reader.read(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (message.length < packet.length) {
					pm.server.Log("Bad packet..");
				}
				else
					packet.Handle(message, pm.server, this);
			}
		}
	}
	
	/**
	 * Handle a GET request. 
	 * Once the GET request is handled, the client is then disconnected
	 * @param request
	 * @param full
	 * @param server
	 */
	@SuppressWarnings("deprecation")
	public void GET(String request, String full, Server server) {
		BrowserGETRequestEvent bgre = new BrowserGETRequestEvent(this, request, full);
		server.getEventSystem().callEvent(bgre);
		if (bgre.isCancelled())
			return;
		String respond = bgre.getResponse();
		if (respond.equals("")) 
			writer.println("HTTP/1.1 404 Not Found");
		else {
			writer.println("HTTP/1.1 200 OK");
			writer.println("Date: " + new Date().toLocaleString());
			writer.println("Content-Type: " + (bgre.isHTML() ? "text/html" : "text/plain"));
			writer.println("Content-Lenght: " + respond.getBytes().length);
			writer.println();
			writer.println(respond);
		}
		CloseConnection();
	}

}
