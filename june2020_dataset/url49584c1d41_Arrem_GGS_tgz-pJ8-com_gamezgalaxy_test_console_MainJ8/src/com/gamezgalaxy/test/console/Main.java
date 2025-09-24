/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.test.console;

import java.util.Scanner;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.chat.ChatColor;
import com.gamezgalaxy.GGS.chat.Messages;
import com.gamezgalaxy.GGS.groups.Group;
import com.gamezgalaxy.GGS.server.Server;

public class Main implements CommandExecutor {
	Server s;
	public static void main(String[] args) {
		new Main().start();
	}

	@Override
	public void sendMessage(String message) {
		s.Log(message);
	}
	
	public void start() {
		s = new Server("Test", 25565, "Test");
		Messages m = new Messages(s);
		s.Start();
		while (s.Running) {
			String line = new Scanner(System.in).nextLine();
			if (line.startsWith("/")) {
				line = line.substring(1); //Get rid of the / at the beginning
				if (line.split("\\ ").length > 1)
					s.getCommandHandler().execute(this, line.split("\\ ")[0], line.substring(line.indexOf(line.split("\\ ")[1])));
				else
					s.getCommandHandler().execute(this, line, "");
			}
			else {
				m.serverBroadcast(ChatColor.Purple + "[Server] " + ChatColor.White + line);
				s.Log("[Server] " + line);
			}
		}
		System.out.println("Server stopped..");
		System.exit(0);
	}

	@Override
	public Server getServer() {
		return s;
	}

	@Override
	public Group getGroup() {
		return Group.getGroupList().get(Group.getGroupList().size() - 1);
	}

	@Override
	public String getName() {
		return "Console";
	}
}
