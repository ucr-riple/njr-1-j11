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

import java.io.File;

public class Load extends Command {
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "load";
	}

	@Override
	public boolean isOpCommand()
	{
		return true;
	}

	@Override
	public int getDefaultPermissionLevel()
	{
		return 100;
	}

	@Override
	public void execute(CommandExecutor player, String[] args)
	{
		if(args.length == 1)
		{
			LevelHandler handler = player.getServer().getLevelHandler();
			File levelFile = new File("levels/" + args[0] + ".ggs");

			if(levelFile.exists())
			{
				handler.loadLevel(levelFile.getPath());
			} else {
				player.sendMessage("Level does not exist.");
			}
		}
		else
			help(player);
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/load <levelname> - loads the specified level");	
	}
}