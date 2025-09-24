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

public class TP extends PlayerCommand
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "tp";
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
	public void execute(Player player, String[] args)
	{
		if(args.length == 1)
		{
			Player other = player.getServer().getPlayer(args[0]);
			if(other != null)
			{
				player.setPos(other.getX(), other.getY(), other.getZ());
			} else {
				player.sendMessage(args[1] + " is not online.");
			}
		} else if(args.length == 2) {
			Player otherTPing = player.getServer().getPlayer(args[0]);
			Player otherTPto = player.getServer().getPlayer(args[1]);

			if(otherTPing != null && otherTPto != null)
			{
				otherTPing.setPos(otherTPto.getX(), otherTPto.getY(), otherTPto.getZ());
			} else {
				player.sendMessage("Either " + args[0] + " or " + args[1] + " is not online.");
			}
		} else {
			help(player);
		}
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/help <player> - teleports you to the specified player");
		player.sendMessage("/help <player1> <player2> - teleports player1 to player2");	
	}
}