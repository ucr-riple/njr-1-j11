/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.chat;

public enum ChatColor {
	Black('&', '0'),
	Dark_Blue('&', '1'),
	Dark_Green('&','2'),
	Dark_Aqua('&','3'),
	Dark_Red('&','4'),
	Purple('&','5'),
	Orange('&','6'),
	Grey('&', '7'),
	Dark_Grey('&','8'),
	Indigo('&','9'),
	Bright_Green('&','a'),
	Aqua('&', 'b'),
	Red('&', 'c'),
	Pink('&', 'd'),
	Yellow('&', 'e'),
	White('&', 'f');
	
	private char id;
	private char type;
	ChatColor(char id, char type) { this.id = id; this.type = type; }
	
	public char getColor() {
		return type;
	}
	public char getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "" + id + type;
	}
	
	/**
	 * Parse the String <b>color</b> to a ChatColor object
	 * @param color The String to parse
	 * @return The ChatColor object that equals the String
	 */
	public static ChatColor parse(String color) {
		return parse(color.toCharArray()[1]);
	}
	
	/**
	 * Parse the char <b>color</b> to a ChatColor object.
	 * @param color The char to parse
	 * @return The ChatColor object that equals the char
	 */
	public static ChatColor parse(char color) {
		if (color == '0')
			return ChatColor.Black;
		else if (color == '1')
			return ChatColor.Dark_Blue;
		else if (color == '2')
			return ChatColor.Dark_Green;
		else if (color == '3')
			return ChatColor.Dark_Aqua;
		else if (color == '4')
			return ChatColor.Dark_Red;
		else if (color == '5')
			return ChatColor.Purple;
		else if (color == '6')
			return ChatColor.Orange;
		else if (color == '7')
			return ChatColor.Grey;
		else if (color == '8')
			return ChatColor.Dark_Grey;
		else if (color == '9')
			return ChatColor.Indigo;
		else if (color == 'a')
			return ChatColor.Bright_Green;
		else if (color == 'b')
			return ChatColor.Aqua;
		else if (color == 'c')
			return ChatColor.Red;
		else if (color == 'd')
			return ChatColor.Pink;
		else if (color == 'e')
			return ChatColor.Yellow;
		else if (color == 'f')
			return ChatColor.White;
		throw ColorFormatException.forInputString("" + color);
	}

}
