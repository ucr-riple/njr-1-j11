/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import com.gamezgalaxy.GGS.API.io.PacketReceivedEvent;
import com.gamezgalaxy.GGS.API.io.PacketSentEvent;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;

public class IOClient {
	protected Socket client;
	
	protected PrintStream writer;
	
	protected DataInputStream reader;
	
	protected Thread readerthread;
	
	protected PacketManager pm;
	
	protected long readID;
	
	
	/**
	 * Get the thread ID for the thread thats currently
	 * reading packets.
	 * @return
	 *        The Thread ID as a long
	 */
	public long getReaderThreadID() {
		return readID;
	}
	/**
	 * The constructor for IOClient
	 * @param client
	 *              The socket that connected to the server
	 * @param pm
	 *          The PacketManager that recieved the connection
	 */
	public IOClient(Socket client, PacketManager pm) {
		if (client == null)
			return;
		this.client = client;
		this.pm = pm;
		try {
			writer = new PrintStream(client.getOutputStream());
			//writer.flush();
			reader = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			pm.server.Log("Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Start listening and receiving packet from this
	 * client.
	 */
	public void Listen() {
		if (reader == null)
			return;
		readerthread = new Reader(this);
		readerthread.start();
		pm.server.Log("Listening..");
	}
	
	/**
	 * Disconnect this client from the server
	 */
	public void CloseConnection() {
		try {
			pm.server.Log("Closing connection");
			writer.close();
			reader.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send this client some data
	 * (MMmmm...yummy data)
	 * @param data
	 *            The data in a byte array
	 * @throws IOException
	 *                    If there's an error sending the data to
	 *                    the client.
	 */
	public void WriteData(byte[] data) throws IOException {
		Packet p = pm.getPacket(data[0]);
		if (p != null) {
			PacketSentEvent event = new PacketSentEvent(this, pm.server, p);
			pm.server.getEventSystem().callEvent(event);
		}
		writer.write(data);
		writer.flush();
	}
	
	private class Reader extends Thread {
		IOClient client;
		
		public Reader(IOClient client) { this.client = client; }
		@Override
		public void run() {
			readID = Thread.currentThread().getId();
			while (pm.server.Running && client.client.isConnected()) {
				try {
					byte opCode = reader.readByte();
					PacketReceivedEvent event = new PacketReceivedEvent(client, pm.server, reader, opCode);
					pm.server.getEventSystem().callEvent(event);
					if (event.isCancelled())
						continue;
					Packet packet = pm.getPacket(opCode);
					if (packet == null) {
						pm.server.Log("Client sent " + opCode);
						pm.server.Log("How do..?");
						continue;
					}
					byte[] message = new byte[packet.length];
					reader.read(message);
					if (message.length < packet.length) {
						pm.server.Log("Bad packet..");
						continue;
					}
					packet.Handle(message, pm.server, (Player)client);
				} catch (IOException e) {
					CloseConnection();
					break;
				}
				catch (Exception e) {
					e.printStackTrace();
					if (client instanceof Player)
						((Player)client).Kick("ERROR!");
					CloseConnection();
					break;
				}
			}
		}
	}
}
