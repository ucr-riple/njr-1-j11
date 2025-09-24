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
import com.gamezgalaxy.GGS.world.Level;
import com.gamezgalaxy.GGS.world.LevelHandler;

public class Loaded extends Command
{
	@Override
	public String[] getShortcuts()
	{
		return new String[] { "levels" };
	}

	@Override
	public String getName()
	{
		return "loaded";
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
	public void execute(CommandExecutor player, String[] args)
	{
		LevelHandler handler = player.getServer().getLevelHandler();
		StringBuilder finalStr = new StringBuilder();

		for(Level l : handler.getLevelList())
		{
			finalStr.append(l.name);
			finalStr.append(", ");
		}

		player.sendMessage(finalStr.toString());
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/loaded - shows the loaded levels");	
	}
}
