/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.system;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import com.gamezgalaxy.GGS.util.FileUtils;

public class BanHandler {
	static ArrayList<String> banned = new ArrayList<String>();
	
	/**
	 * Load/reload the banned list. This should only be used if the file was
	 * edited not using the BanHandler methods
	 */
	public static void init() {
		banned.clear();

		try {
			FileUtils.CreateIfNotExist(FileUtils.PROPS_DIR , FileUtils.BANNED_FILE);
			FileInputStream fstream = new FileInputStream(FileUtils.PROPS_DIR + FileUtils.BANNED_FILE);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			while((line = br.readLine()) != null)
				banned.add(line);

			fstream.close();
			in.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the banned list
	 * @throws FileNotFoundException
	 *                              This is thrown the banned.txt file cant be found.
	 */
	public static void save() throws FileNotFoundException {
		FileUtils.DeleteIfExist(FileUtils.PROPS_DIR + FileUtils.BANNED_FILE);
		PrintWriter out = new PrintWriter(FileUtils.PROPS_DIR + FileUtils.BANNED_FILE);
		for (String s : banned) {
			out.println(s);
		}
		out.close();
	}
	
	/**
	 * Get when the player with the name <b>name</b> ban will expire.
	 * @param name
	 *            The name of the player with the ban
	 * @return
	 *        Returns the date as a String, returns "null" if the ban will
	 *        never expire, returns "" if the user cant be found.
	 */
	public static String getExpire(String name) {
		for (String s : banned) {
			if (s.split("\\:")[0].equalsIgnoreCase(name))
				return s.split("\\:")[1];
		}
		return "";
	}
	
	/**
	 * Ban a user with a given date to have the ban
	 * expire. If you dont know the format for the date, please use
	 * {@link BanHandler#ban(String, Date)} instead.
	 * @param username
	 *                The name of the player getting banned
	 * @param expire
	 *              The formated date as a String the ban will expire, if the
	 *              string is "null", then the ban will never expire.
	 */
	public static void ban(String username, String expire) {
		banned.add(username + ":" + expire);
		try {
			save();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Unban a player that is currently banned
	 * @param username
	 *                The username of the player to unban.
	 */
	public static void unban(String username) {
		
		for(int i=0; i<banned.size() ;i++)
		{
			String[] ban = banned.get(i).split(":");
			if(ban[0].equalsIgnoreCase(username))
			{
				banned.remove(i);
				break;
			}
		}
		
		try {
			save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ban a player forever.
	 * @param username
	 *                The name of the user to ban
	 */
	public static void ban(String username) {
		ban(username, "null");
	}
	
	/**
	 * Ban a player and have it expire at a given date.
	 * @param username
	 *                The name of the user to ban.
	 * @param date
	 *            The date the ban will expire.
	 */
	public static void ban(String username, Date date) {
		ban(username, date.toString());
	}
	
	/**
	 * Weather the user ban has expired.
	 * @param username
	 *                The name of the user to check.
	 * @return
	 *        Returns true for the following reasons:
	 *        * The user is not banned
	 *        * The ban is past expire paste
	 *        
	 *        Otherwise, it will return false.
	 */
	public static boolean pastDate(String username) {
		if (!isBanned(username))
			return true;
		String date = getExpire(username);
		if (date.equals("null"))
			return false;
		Date now = new Date();
		@SuppressWarnings("deprecation")
		Date then = new Date(date);
		return then.before(now);
	}
	
	/**
	 * Weather or not the user is banned. This will also
	 * check if the ban is expired or not
	 * @param name
	 *            The name of the user to check
	 * @return
	 *        Returns true if the user is banned, returns false if the
	 *        user is not banned.
	 */
	public static boolean isBanned(String name) {
		init();
		for (String s : banned) {
			if (s.split("\\:")[0].equalsIgnoreCase(name))
				return !pastDate(name);
		}
		return false;
	}

}
