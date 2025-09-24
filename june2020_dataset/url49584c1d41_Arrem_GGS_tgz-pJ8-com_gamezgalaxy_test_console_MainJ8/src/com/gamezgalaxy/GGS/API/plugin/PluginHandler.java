/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.gamezgalaxy.GGS.server.Server;

public class PluginHandler {
	private ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	
	private ArrayList<Game> games = new ArrayList<Game>();

	public void loadplugins(Server server)
	{
		File pluginFolder = new File("plugins/");

		if(!pluginFolder.exists())
		{
			System.out.println("Mods folder does not exist.");

			return;
		}

		File[] pluginFiles = pluginFolder.listFiles();

		for(int i = 0; i < pluginFiles.length; i++)
		{
			if(pluginFiles[i].isFile() && pluginFiles[i].getName().endsWith(".jar"))
			{
				JarFile file = null;

				try {
					file = new JarFile(pluginFiles[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if(file != null)
				{
					Enumeration<JarEntry> entries = file.entries();

					if(entries != null)
					{
						while(entries.hasMoreElements())
						{
							JarEntry fileName = entries.nextElement();

							if(fileName.getName().endsWith(".config"))
							{
								Properties properties = new Properties();

								try {
									properties.load(file.getInputStream(fileName));
								} catch (IOException e) {
									e.printStackTrace();
								}

								File pluginFile = new File("plugins/" + pluginFiles[i].getName());
								String[] args = new String[] { "-normal" };

								loadplugin(pluginFile, properties.getProperty("main-class"), args, server, properties);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void loadplugin(File file, String classpath, String[] args, Server server, Properties properties)
	{
		try {
			URL[] urls = new URL[] { file.toURL() };
			ClassLoader loader = URLClassLoader.newInstance(urls, getClass().getClassLoader());
			Class<?> class_ = Class.forName(classpath, true, loader);
			Class<? extends Plugin> runClass = class_.asSubclass(Plugin.class);
			Constructor<? extends Plugin> constructor = runClass.getConstructor(Server.class);
			Plugin plugin = constructor.newInstance(server);
			plugin.setProperties(properties);
			plugin.onLoad(args);
			if(plugin instanceof Game)
			{
				games.add((Game)plugin);
				server.Add((Game)plugin);
			} else {
				plugins.add(plugin);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
