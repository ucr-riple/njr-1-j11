package com.gamezgalaxy.GGS.defaults.commands;

import com.gamezgalaxy.GGS.API.CommandExecutor;
import com.gamezgalaxy.GGS.API.plugin.PlayerCommand;
import com.gamezgalaxy.GGS.iomodel.Player;
import com.gamezgalaxy.GGS.system.BanHandler;

public class Ban extends PlayerCommand {

	@Override
	public String[] getShortcuts() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "ban";
	}

	@Override
	public boolean isOpCommand() {
		return true;
	}

	@Override
	public int getDefaultPermissionLevel() {
		return 100;
	}

	@Override
	public void execute(Player player, String[] args) {
		if(args.length != 0){
				if(BanHandler.isBanned(args[0])){
					player.sendMessage(args[0]+ " is already banned");
					return;
				}
				if(!args[0].equalsIgnoreCase(player.username)){
					BanHandler.ban(args[0]);
					player.sendMessage("You successfully banned " + args[0]);
				}else{
					player.sendMessage("You cannot ban your self.");
				}
		}
			else 
				help(player);
		//TODO Add expire date
	}
	@Override
	public void help(CommandExecutor player) {
		player.sendMessage("/ban <player> - bans the specified player");	
	}
}
