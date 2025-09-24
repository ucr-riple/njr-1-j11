/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.sql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gamezgalaxy.GGS.server.Server;
import java.sql.Connection;

public class MySQL implements ISQL {

	protected Connection connection;
	protected String IP;
	protected int port;
	protected String DB;
	protected String prefix;
	protected String username;
	protected String pass;
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private Server server;
	@Override
	public void ExecuteQuery(String command) {
		try {
			if (connection.isClosed())
				Connect(server);
		} catch (SQLException e) {
			Connect(server);
		}
		try {
			PreparedStatement pstm = connection.prepareStatement(command);
			pstm.executeUpdate();
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
					server.Log("ERROR EXECUTING STATEMENT!");
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
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(getURL() + DB, username, pass);
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
	/**
	 * Get the URL to the mysql server
	 * @return The URL
	 */
	public String getURL() {
		return "jdbc:mysql://" + IP + ":" + port + "/";
	}
	/**
	 * Get the URL to the mysql server including the database
	 * @return The URL
	 */
	public String getFullURL() {
		return getURL() + DB;
	}

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String getPrefix() {
		return prefix;
	}
	
	public void setUsername(String user) {
		this.username = user;
	}
	
	public void setPassword(String pass) {
		this.pass = pass;
	}
	
	public void setDatabase(String DB) {
		this.DB = DB;
	}

}
