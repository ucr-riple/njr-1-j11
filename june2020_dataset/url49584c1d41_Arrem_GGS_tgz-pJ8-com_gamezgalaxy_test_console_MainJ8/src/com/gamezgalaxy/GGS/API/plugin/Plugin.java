/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.plugin;

import com.gamezgalaxy.GGS.server.Server;

import java.util.Properties;

/**
 * The plugin class allows dev's to extend GGS easily without
 * needing to modify the source.
 * 
 * Simply extend the plugin class.
 * 
 * The onLoad method is called when the plugin jar is loaded which is on server startup
 * The onUnload method is called when the plugin is being unloaded from the server or when
 * the server shutdowns.
 */
public abstract class Plugin
{
	private Server _server;
	private Properties properties;
	
	public Plugin(Server server, Properties properties)
	{
		this._server = server;
		this.properties = properties;
	}
	public Plugin(Server server) {
		this._server = server;
	}
	
	/**
	 * The onLoad method is called when the plugin jar is loaded which is on server startup
	 * @param args
	 *            Arguments passed to the plugin on startup. If the server started
	 *            the plugin on startup, then the argument -normal is passed.
	 */
	public abstract void onLoad(String[] args);
	
	/**
	 * The onUnload method is called when the plugin 
	 * is being unloaded from the server or when the server shutdowns.
	 */
	public abstract void onUnload();

	/**
	 * Get the name of this plugin
	 * @return
	 *        The name
	 */
	public String getName()
	{
		return properties.getProperty("name");
	}
	
	
	public void setProperties(Properties prop) {
		this.properties = prop;
	}

	/**
	 * Get the version of this plugin
	 * @return
	 *        The version
	 */
	public String getVersion()
	{
		return properties.getProperty("version");
	}

	/**
	 * Get the author of this plugin
	 * @return
	 *        The author
	 */
	public String getAuthor()
	{
		return properties.getProperty("author");
	}

	/**
	 * Get the server this plugin is running in
	 * @return
	 *        The {@link Server} object
	 */
	public Server getServer()
	{
		return _server;
	}

	/**
	 * Get the properties of the plugin
	 * @return
	 *        The properties
	 */
	public Properties getProperties()
	{
		return properties;
	}
}
