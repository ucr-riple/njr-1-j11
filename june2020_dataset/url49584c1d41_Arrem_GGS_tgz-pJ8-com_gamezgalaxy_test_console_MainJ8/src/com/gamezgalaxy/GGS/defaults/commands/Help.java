/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.defaults.commands;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.API.plugin.Command;

public class Help extends Command
{
	@Override
	public String[] getShortcuts() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public boolean isOpCommand() {
		return false;
	}

	@Override
	public int getDefaultPermissionLevel() {
		return 0;
	}
	@Override
	public void execute(CommandExecutor player, String[] args) {
		if (args.length == 0) { 
			help(player); 
			return; 
		}
		Command cmd = player.getServer().getCommandHandler().find(args[0]);
		if (cmd == null) { 
			player.sendMessage("The specified command wasn't found!"); 
			return;
		}
		cmd.help(player);
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/help <commandname> - shows help for the specified command");
	}
}
