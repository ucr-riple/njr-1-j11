/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API;

import com.gamezgalaxy.GGS.groups.Group;
import com.gamezgalaxy.GGS.server.Server;

public interface CommandExecutor {
	
	public void sendMessage(String message);
	
	public Server getServer();
	
	public Group getGroup();
	
	public String getName();
}
