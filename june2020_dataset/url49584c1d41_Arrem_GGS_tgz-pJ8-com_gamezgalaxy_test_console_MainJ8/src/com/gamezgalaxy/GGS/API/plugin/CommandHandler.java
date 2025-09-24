/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.plugin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.server.Server;

public class CommandHandler {
	public ArrayList<Command> commands = new ArrayList<Command>();
	
	private Server _server;
	
	public CommandHandler(Server server) {
		this._server = server;
	}
	
	/**
	 * Execute a command. This will check permissions to see if <b>player</b>
	 * can execute the command based on the group the <b>player</b> is in.
	 * @param player
	 *              The client that is executing the command
	 * @param name
	 *            The name of the command
	 * @param message
	 *               The append arguments as a String
	 */
	public void execute(CommandExecutor player, String name, String message) {
		execute(player, name, message.split(" "));
	}
	
	/**
	 * Find a command based on the name.
	 * This will also look for shortcuts
	 * @param name
	 *           The name/shortcut of the command
	 * @return
	 *        The {@link Command} object
	 */
	public Command find(String name) {
		for (Command c : commands) {
			if (name.equalsIgnoreCase(c.getName()))
				return c;
			else {
				for (String shortcut : c.getShortcuts()) {
					if (name.equalsIgnoreCase(shortcut))
						return c;
				}
			}
		}
		return null;
	}

	/**
	 * Have <b>player</b> execute a command. This method will
	 * check for group permissions, if the <b>player</b> can't
	 * execute the command, the player will be notified. 
	 * @param player
	 *              The player executing the command
	 * @param command
	 *               The command the player will use
	 * @param args
	 *            Any arguments that will be passed to this command
	 */
	public void execute(CommandExecutor player, String command, String[] args) {
		if (find(command) == null) {
			if (player != null)
				player.sendMessage("Command not found!");
			else
				_server.Log("Command not found!");
		}
		Command c = find(command);
		if (args.length == 1 && args[0].equals(""))
			args = new String[0];
		if(c != null)
		{
			if (player != null && !player.getGroup().canExecute(c))
				player.sendMessage("Sorry, you dont have permission to execute this command!");
			else {
				_server.Log(player.getName() + " used /" + c.getName() + arrayToString(args));
				c.execute(player, args);
			}
		}
	}
	
	private String arrayToString(String[] args) {
		String finals = "";
		for (String s : args) {
			finals += " " + s;
		}
		return finals;
	}
	
	/**
	 * Add a command to the command list. This will load permissions
	 * for this command, if no permissions are found for it, it will
	 * save the default permissions for it.
	 * @param cmd
	 *           The command to add.
	 */
	public void addCommand(Command cmd) {
		if (commands.contains(cmd))
			return;
		commands.add(cmd);
		try {
			loadPermissions();
			savePermissions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove a command from the list
	 * @param cmd
	 *           The command to remove
	 */
	public void removeCommand(Command cmd) {
		if (!commands.contains(cmd))
			return;
		commands.remove(cmd);
	}
	
	/**
	 * Remove a command from the list.
	 * @param name
	 *            The command name.
	 */
	public void removeCommand(String name) {
		Command c = find(name);
		if (c == null)
			return;
		removeCommand(c);
	}
	
	private void loadPermissions() throws IOException {
		if (!new File("properties").exists())
			new File("properties").mkdir();
		if (!new File("properties/commands.config").exists())
			makeDefault();
		FileInputStream fstream = new FileInputStream("properties/commands.config");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			if (strLine.startsWith("#"))
				continue;
			String cmdname = strLine.split("\\:")[0];
			for (Command c : commands) {
				if (c.getName().equalsIgnoreCase(cmdname)) {
					c.setPermissionLevel(Integer.parseInt(strLine.split("\\:")[1]));
					break;
				}
			}
		}
		in.close();
	}
	
	private void makeDefault() {
		PrintWriter out = null;
		try {
			new File("properties/commands.config").createNewFile();
			out = new PrintWriter("properties/commands.config");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("#Permission file for commands");
		out.println("#Name:Permission Level");
		for (Command c : commands) {
			out.println(c.getName() + ":" + c.getDefaultPermissionLevel());
		}
		out.flush();
		out.close();
	}
	
	private void savePermissions() throws IOException {
		if (new File("properties/commands.config").exists())
			new File("properties/commands.config").delete();
		new File("properties/commands.config").createNewFile();
		PrintWriter out = null;
		try {
			out = new PrintWriter("properties/commands.config");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		out.println("#Permission file for commands");
		out.println("#Name:Permission Level");
		for (Command c : commands) {
			out.println(c.getName() + ":" + c.getPermissionLevel());
		}
		out.flush();
		out.close();
	}

}
