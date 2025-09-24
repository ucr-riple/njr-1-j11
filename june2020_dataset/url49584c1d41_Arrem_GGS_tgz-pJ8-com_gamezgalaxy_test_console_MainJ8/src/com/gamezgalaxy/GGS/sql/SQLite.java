/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gamezgalaxy.GGS.server.Server;

public class SQLite implements ISQL {

	protected Connection connection;
	private Server server;
	private String prefix;
	private final String PATH = "jdbc:sqlite:ggs.db";
	private final String DRIVER = "org.sqlite.JDBC";
	@Override
	public void ExecuteQuery(String command) {
		try {
			if (connection.isClosed())
				Connect(server);
		} catch (SQLException e) {
			Connect(server);
		}
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void ExecuteQuery(String[] commands) {
		try {
			if (connection.isClosed())
				Connect(server);
		} catch (SQLException e) {
			Connect(server);
		}
		try {
			Statement statement = connection.createStatement();
			for (String s : commands) {
				try {
					statement.executeUpdate(s);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet fillData(String command) {
		try {
			if (connection.isClosed())
				Connect(server);
		} catch (SQLException e) {
			Connect(server);
		}
		try {
			return connection.prepareStatement(command).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void Connect(Server server) {
		this.server = server;
		try {
			if (!new File("ggs.db").exists())
				new File("ggs.db").createNewFile();
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get the connection
	 * @return java.sql.connection
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String getPrefix() {
		return prefix;
	}
}
