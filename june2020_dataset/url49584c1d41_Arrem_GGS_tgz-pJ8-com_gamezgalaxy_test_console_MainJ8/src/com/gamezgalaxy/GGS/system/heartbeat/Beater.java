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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

final class Beater extends Thread {

	public Beater(Beat beat)
	{
		this.beat = beat;
	}

	private final Beat beat;

	@Override
	public void run() {
		URL url;
		HttpURLConnection connection = null;
		BufferedReader reader;
		byte[] data;
		while (beat.isRunning()) {
			synchronized(beat.getHearts()) {
				for (Heart h : beat.getHearts()) {
					try {
						URL u = new URL(h.getURL());
						HttpURLConnection con = (HttpURLConnection)u.openConnection();
						con.connect();

						if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
						{
							url = new URL(h.getURL() + "?" + h.Prepare(beat.getServer()));
							connection = (HttpURLConnection)url.openConnection();
							data = h.Prepare(beat.getServer()).getBytes();
							connection.setDoOutput(true);
							connection.setRequestProperty("Content-Lenght", String.valueOf(data.length));
							connection.setUseCaches(false);
							connection.setDoInput(true);
							connection.setDoOutput(true);
							connection.connect();
							try {
								reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
								try {
									h.onPump(reader, beat.getServer());
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								e.printStackTrace();
								beat.getServer().Log("Error pumping " + h.getURL() + " heart!");
							}
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Unable to connect to " + h.getURL() + "!");
					} finally {
						if (connection != null)
							connection.disconnect();
					}
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
