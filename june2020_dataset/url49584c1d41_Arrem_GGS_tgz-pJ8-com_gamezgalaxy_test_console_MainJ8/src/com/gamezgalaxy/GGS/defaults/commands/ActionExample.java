/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.defaults.commands;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.API.action.Action;
import com.gamezgalaxy.GGS.API.action.ChatAction;
import com.gamezgalaxy.GGS.API.plugin.PlayerCommand;
import com.gamezgalaxy.GGS.iomodel.Player;

public class ActionExample extends PlayerCommand
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "actoinexe";
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

	public class Test extends Thread {
		public Player player;
		@Override
		public void run() {
			Action<ChatAction> a = new ChatAction();
			a.setPlayer(player);
			try {
				ChatAction c = a.waitForResponse();
				player.sendMessage("You said " + c.getMessage() + "!");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void execute(Player player, String[] args)
	{
		Test t = new Test();
		t.player = player;
		t.start();
		player.sendMessage("Say something!");
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/actiontest - a test command");	
	}
}
