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

public class Spawn extends PlayerCommand
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "spawn";
	}

	@Override
	public boolean isOpCommand()
	{
		return false;
	}

	@Override
	public int getDefaultPermissionLevel()
	{
		return 0; // DON'T KNOW THE PERMISSION LEVEL FOR STANDARD USER.
	}

	@Override
	public void execute(Player player, String[] args)
	{
		player.setPos((short)((0.5 + player.getLevel().spawnx) * 32), (short)((1 + player.getLevel().spawny) * 32), (short)((0.5 + player.getLevel().spawnz) * 32));
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/spawn - sends you to the spawn of the level you're in");	
	}
}
