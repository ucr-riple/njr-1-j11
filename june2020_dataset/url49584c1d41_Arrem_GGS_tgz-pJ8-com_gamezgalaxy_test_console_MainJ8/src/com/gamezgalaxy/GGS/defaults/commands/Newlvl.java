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
import com.gamezgalaxy.GGS.world.LevelHandler;


public class Newlvl extends Command
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "newlvl";
	}

	@Override
	public boolean isOpCommand()
	{
		return true;
	}

	@Override
	public int getDefaultPermissionLevel()
	{
		return 0;
	}

	@Override
	public void execute(CommandExecutor player, String[] args)
	{
		short w = 0;
		if (args.length == 1) {
			w = 64;
		}
		else if(args.length == 4) {
			LevelHandler handler = player.getServer().getLevelHandler();
			handler.loadLevels();
			if(handler.findLevel(args[0]) == null) {
				if (w != 0)
					handler.newLevel(args[0], w , w, w);
				else
					handler.newLevel(args[0], Short.valueOf(args[1]), Short.valueOf(args[2]), Short.valueOf(args[3]));
				player.sendMessage("Created new level: \"" + args[0] + "\"");
			} else {
				player.sendMessage("Level already exists...");
			}
		}
		else
			help(player);
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/newlvl <name> <width> <height> <length> - creates a new level with the specified name and size");
	}
}