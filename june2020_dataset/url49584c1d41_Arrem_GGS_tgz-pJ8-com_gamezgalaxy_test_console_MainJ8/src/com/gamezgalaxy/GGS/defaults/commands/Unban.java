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
import com.gamezgalaxy.GGS.system.BanHandler;

public class Unban extends Command
{

	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "unban";
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
		if(args.length == 1){
			if(BanHandler.isBanned(args[0]))
			{
				BanHandler.unban(args[0]);
				player.sendMessage("You unbanned " + args[0]);
			}else{
				player.sendMessage(args[0] + " is not banned.");
			}
		}else{
			help(player);
		}
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/unban <player> - unbans the specified player");
	}
}
