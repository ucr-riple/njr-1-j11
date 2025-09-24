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

public abstract class Heart {

	/**
	 * Get the string that will be attached to the URL.
	 * Example, if the Heart URL is <b>"http://www.minecraft.net/heartbeat.jsp"</b>
	 * Then the Prepare method will return all the parameters for that url.
	 * @return
	 *        The parameters to append to the URL
	 */
	public abstract String Prepare(Server server);
	
	/**
	 * The base URL to beat to
	 * @return
	 *        The URL
	 */
	public abstract String getURL();

	/**
	 * What the beater should do while connected to the
	 * URL.
	 * This can be used to parse a page for content or text.
	 * @param rdr
	 *           The BufferedReader currently reading the site
	 * @param server
	 *             The server the beater belongs to
	 * @return
	 *        A string. 
	 *        The returned value isnt used in the beater.
	 * @throws IOException
	 *                    If theres an error reading from the URL
	 */
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
		return url;
	}
}
