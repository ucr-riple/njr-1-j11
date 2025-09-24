/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.sql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.gamezgalaxy.GGS.server.Server;

public interface ISQL {
	
	void ExecuteQuery(String command);
	
	void ExecuteQuery(String[] commands);
	
	ResultSet fillData(String command);
	
	void Connect(Server server);
	
	void setPrefix(String prefix);
	
	String getPrefix();
	
	Connection getConnection();

}
