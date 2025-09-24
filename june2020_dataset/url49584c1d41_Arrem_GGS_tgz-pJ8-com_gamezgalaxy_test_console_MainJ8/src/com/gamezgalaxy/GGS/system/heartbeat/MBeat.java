/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.system.heartbeat;

import java.io.BufferedReader;
import java.io.IOException;

import com.gamezgalaxy.GGS.server.Server;

public class MBeat extends Heart {
	private boolean show = false;
	@Override
	public String Prepare(Server server) {
		try {
			return "port=" + server.Port +
					"&max=" + server.MaxPlayers +
					"&name=" + server.Name.trim().replace(" ", "%20") +
					"&public=" + server.Public +
					"&version=7" +
					"&salt=" + server.getSalt() +
					"&users=" + server.players.size();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String onPump(BufferedReader rdr, Server server) throws IOException {
		String url = "";
		String line = "";
		try {
			while (line != null) {
				line = rdr.readLine();
				if (line != null)
					url = line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rdr.close();
		}
		if (!show) {
			server.Log("URL: " + url);

			show = true;
		}
		return url;
	}

	@Override
	public String getURL() {
		return "http://www.minecraft.net/heartbeat.jsp";
	}
}
