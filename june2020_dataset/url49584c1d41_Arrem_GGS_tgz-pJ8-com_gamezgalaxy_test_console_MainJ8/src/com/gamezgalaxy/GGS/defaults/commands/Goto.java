/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.defaults.commands;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.API.plugin.PlayerCommand;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.world.Level;
import com.gamezgalaxy.GGS.world.LevelHandler;

public class Goto extends PlayerCommand
{
	@Override
	public String[] getShortcuts()
	{
		return new String[] { "g" };
	}

	@Override
	public String getName()
	{
		return "goto";
	}

	@Override
	public boolean isOpCommand()
	{
		return false;
	}

	@Override
	public int getDefaultPermissionLevel()
	{
		return 0;
	}

	@Override
	public void execute(Player player, String[] args)
	{
		if(args.length == 1)
		{
			LevelHandler handler = player.getServer().getLevelHandler();
			Level level = handler.findLevel(args[0]);

			if(level != null)
			{
				player.changeLevel(level);
			} else {
				player.sendMessage("Level doesn't exist...");
			}
		} else {
			help(player);
		}
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/goto <levelname> - moves you to the specified level");	
	}
}
