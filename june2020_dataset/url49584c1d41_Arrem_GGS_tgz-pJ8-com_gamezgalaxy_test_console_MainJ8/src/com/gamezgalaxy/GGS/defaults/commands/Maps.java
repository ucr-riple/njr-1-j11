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
import java.io.File;

public class Maps extends Command
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "maps";
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
		File levelsFolder = new File("levels");
		File[] levelFiles = levelsFolder.listFiles();
		StringBuilder finalStr = new StringBuilder();

		for(File f : levelFiles)
		{
			if(f.getName().split("\\.")[1].equals("ggs"))
			{
				finalStr.append(f.getName().split("\\.")[0]);
				finalStr.append(", ");
			}
		}

		player.sendMessage(finalStr.toString());
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/maps - shows the maps the server has");	
	}
}
