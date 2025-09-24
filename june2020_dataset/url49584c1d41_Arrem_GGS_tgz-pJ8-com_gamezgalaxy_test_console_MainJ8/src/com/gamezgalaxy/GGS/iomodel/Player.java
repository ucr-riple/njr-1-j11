/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.iomodel;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gamezgalaxy.GGS.API.level.PlayerJoinedLevel;
import com.gamezgalaxy.GGS.API.player.PlayerBlockChangeEvent;
import com.gamezgalaxy.GGS.API.player.PlayerChatEvent;
import com.gamezgalaxy.GGS.API.player.PlayerCommandEvent;
import com.gamezgalaxy.GGS.API.player.PlayerDisconnectEvent;
import com.gamezgalaxy.GGS.chat.ChatColor;
import com.gamezgalaxy.GGS.chat.Messages;
import com.gamezgalaxy.GGS.groups.Group;
import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.packets.Packet;
import com.gamezgalaxy.GGS.networking.packets.PacketManager;
import com.gamezgalaxy.GGS.networking.packets.minecraft.GlobalPosUpdate;
import com.gamezgalaxy.GGS.networking.packets.minecraft.TP;
import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.server.Tick;
import com.gamezgalaxy.GGS.world.Block;
import com.gamezgalaxy.GGS.world.Level;
import com.gamezgalaxy.GGS.world.PlaceMode;
import com.gamezgalaxy.GGS.API.CommandExecutor;

public class Player extends IOClient implements CommandExecutor {
	protected short X;
	protected short Y;
	protected short Z;
	protected byte ID;
	protected Level level;
	protected Thread levelsender;
	protected Messages chat;
	protected ArrayList<Player> seeable = new ArrayList<Player>();
	protected Ping tick = new Ping(this);
	protected ChatColor color = ChatColor.White;
	private HashMap<String, Object> extra = new HashMap<String, Object>();
	/**
	 * Weather or not the player is logged in
	 */
	public boolean isLoggedin;
	/**
	 * The reason why the player was kicked
	 */
	public String kickreason;
	/**
	 * The username of the player
	 */
	public String username;
	/**
	 * The world the player is currently in
	 */
	public String world;
	/**
	 * The mppass the user used to login
	 */
	public String mppass;
	/**
	 * The message the player last send
	 */
	public String message;
	/**
	 * Weather or not the player is connected
	 */
	public boolean isConnected;
	/**
	 * Weather or not the player can use color codes
	 */
	public boolean cc = true; //Can Player use color codes
	/**
	 * What type of client the player is using.
	 * On WoM and minecraft.net, this value should be 0
	 */
	public byte ClientType; //This might be used for custom clients *hint hint*
	/**
	 * The last X pos of the player
	 */
	public short oldX;
	/**
	 * The last Y pos of the player
	 */
	public short oldY;
	/**
	 * The last Z pos of the player
	 */
	public short oldZ;
	/**
	 * The current yaw of the player
	 */
	public byte yaw;
	/**
	 * The current pitch of the player
	 */
	public byte pitch;
	/**
	 * The old yaw of the player
	 */
	public byte oldyaw;
	/**
	 * The old pitch of the player
	 */
	public byte oldpitch;
	
	public static MessageDigest digest;
	/**
	 * Create a new Player object
	 * @param client The socket the player used to connect
	 * @param pm The PacketManager the player connected to
	 */

	/**
	 * Local variable referencing to the Server class.
	 */
	private Server server;

	/**
	 * The activity of the player.
	 */
	private boolean afk;

	/**
	 * Most recent player this player has pm'd with.
	 */
	public Player lastCommunication;

	public Player(Socket client, PacketManager pm, Server server) {
		this(client, pm, (byte) 255, server);
	}
	public Player(Socket client, PacketManager pm, byte opCode, Server server) {
		super(client, pm);
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		this.server = server;
		ID = getFreeID();
		this.chat = new Messages(pm.server);
		if (opCode != 255) {
			Packet packet = pm.getPacket(opCode);
			if (packet == null) {
				pm.server.Log("Client sent " + opCode);
				pm.server.Log("How do..?");
			} else {
				byte[] message = new byte[packet.length];
				try {
					reader.read(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (message.length < packet.length) {
					pm.server.Log("Bad packet..");
				}
				else
					packet.Handle(message, pm.server, this);
			}
		}

		afk = false;

		Listen();
		pm.server.Add(tick);
	}
	
	/**
	 * Get the username the client will see above the player's head
	 * @return 
	 *        The username with the color at the beginning.
	 */
	public String getDisplayName() {
		return color.toString() + username;
	}
	
	/**
	 * Get the color of the player's username
	 * @return
	 *        The color
	 */
	public ChatColor getDisplayColor() {
		return color;
	}
	
	/**
	 * Set the color for this player
	 * @param color 
	 *             The color
	 */
	public void setDisplayColor(ChatColor color) {
		this.color = color;
		for (Player p : getServer().players) {
			p.Despawn(this);
			p.spawnPlayer(this);
		}
	}
	
	/**
	 * Verify the player is using a valid account
	 * @return Returns true if the account is valid, otherwise it will return false
	 */
	public boolean VerifyLogin() {
		return mppass.equals(getRealmppass());
	}
	
	public String getRealmppass() {
		try {
			digest.update((String.valueOf(server.getSalt()) + username).getBytes());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new BigInteger(1, digest.digest()).toString(16);
	}
	
	/**
	 * Returns extra data stored in the player
	 * @param key The name of the data
	 * @return The data that was stored. The data will most likely be a string, you need to convert the String to the proper object
	 */
	public String getValue(String key) {
		if (!extra.containsKey(key)) {
			Object value = null;
			ResultSet r = server.getSQL().fillData("SElECT count(*) FROM " + server.getSQL().getPrefix() + "_extra WHERE name='" + username + "' AND setting='" + key + "'");
			int size = 0;
			try {
				size = r.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			if (size == 0)
				return null;
			else {
				r = server.getSQL().fillData("SElECT * FROM " + server.getSQL().getPrefix() + "_extra WHERE name='" + username + "' AND setting='" + key + "'");
				try {
					value = r.getObject("value");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				extra.put(key, value);
				return "" + value;
			}
		}
		return "" + extra.get(key);
	}
	
	/**
	 * Store extra data into the player, you can get this data back by
	 * using the getObject method
	 * @param key The name of the data
	 * @param object The object to save
	 */
	public void setValue(String key, Object object) {
		if (extra.containsKey(key))
			extra.remove(key);
		extra.put(key, object);
	}
	
	public void saveValue(String key) throws SQLException {
		if (!extra.containsKey(key))
			return;
		ResultSet r = server.getSQL().fillData("SElECT count(*) FROM " + server.getSQL().getPrefix() + "_extra WHERE name='" + username + "' AND setting='" + key + "'");
		int size = r.getInt(1);
		if (size == 0)
			server.getSQL().ExecuteQuery("INSERT INTO " + server.getSQL().getPrefix() + "_extra (name, setting, value) VALUES ('" + username + "', '" + key + "', '" + extra.get(key).toString() + "')");
		else
			server.getSQL().ExecuteQuery("UPDATE " + server.getSQL().getPrefix() + "_extra SET value='" + extra.get(key).toString() + "' WHERE name='" + username + "' AND setting='" + key + "'");
	}
	
	/**
	 * Login the player
	 * @throws InterruptedException 
	 */
	public void Login() throws InterruptedException {
		if (isLoggedin)
			return;
		if (Group.getGroup(this) == null)
			setGroup(Group.getDefault());
		SendWelcome();
		setLevel(pm.server.MainLevel);
		levelsender.join(); //Wait for finish
		X = (short)((0.5 + level.spawnx) * 32);
		Y = (short)((1 + level.spawny) * 32);
		Z = (short)((0.5 + level.spawnz) * 32);
		oldX = X;
		oldY = Y;
		oldZ = Z;
		pm.server.Log(this.username + " has joined the server.");
		chat.serverBroadcast(this.username + " has joined the server.");
		spawnPlayer(this);
		setPos((short)((0.5 + level.spawnx) * 32), (short)((1 + level.spawny) * 32), (short)((0.5 + level.spawnz) * 32));
		for (Player p : pm.server.players) {
			if (p.level == level) {
				spawnPlayer(p); //Spawn p for me
				p.spawnPlayer(this); //Spawn me for p
			}
		}
		setPos((short)((0.5 + level.spawnx) * 32), (short)((1 + level.spawny) * 32), (short)((0.5 + level.spawnz) * 32));
		isLoggedin = true;
	}
	
	/**
	 * Get the current group the player is in
	 * @return The group
	 */
	public Group getGroup() {
		return Group.getGroup(this);
	}
	
	/**
	 * Change the group the player is in
	 */
	public void setGroup(Group newgroup) {
		Group old = Group.getGroup(this);
		if (Group.getGroup(this) != null)
			Group.getGroup(this).removePlayer(this);
		newgroup.addPlayer(this);
		if (!this.isLoggedin)
			return;
		if ((old != null && old.isOP && !newgroup.isOP) || (old != null && !old.isOP && newgroup.isOP) || old == null) {
			Packet p = pm.getPacket((byte)0x0f);
			p.Write(this, server);
		}
	}
	
	/**
	 * Handle the block change packet
	 * @param X The X pos where the client modified
	 * @param Y The Y pos where the client modified
	 * @param Z The Z pos where the client modifed
	 * @param type The type of action it did
	 * @param holding What the client was holding
	 */
	public void HandleBlockChange(short X, short Y, short Z, PlaceMode type, byte holding) {
		Block getOrginal = level.getTile(X, Y, Z);
		//TODO Call event
		//TODO Check other stuff
		if (holding > 49) {
			Kick("Hack Client detected!");
			return;
		}
		PlayerBlockChangeEvent event = new PlayerBlockChangeEvent(this, X, Y, Z, Block.getBlock(holding), level, pm.server, type);
		pm.server.getEventSystem().callEvent(event);
		if (event.isCancelled()) {
			SendBlockChange(X, Y, Z, getOrginal);
			return;
		}
		Block place = event.getBlock();
		if (type == PlaceMode.PLACE)
			GlobalBlockChange(X, Y, Z, place, level, pm.server);
		else if (type == PlaceMode.BREAK)
			GlobalBlockChange(X, Y, Z, Block.getBlock((byte)0), level, pm.server);
	}
	
	/**
	 * Send a block update to all the players on level "l" in server "s"
	 * @param X The X pos of the udpate
	 * @param Y The Y pos of the update
	 * @param Z The Z pos of the update
	 * @param block The block to send
	 * @param l The level the update happened in
	 * @param s The server the update happened in
	 * @param updateLevel Weather the level should be updated
	 */
	public static void GlobalBlockChange(short X, short Y, short Z, Block block, Level l, Server s, boolean updateLevel) {
		if (updateLevel)
			l.setTile(block, X, Y, Z, s);
		//Do this way to save on packet overhead
		Packet sb = s.getPacketManager().getPacket((byte)0x05);
		for (Player p : s.players)
			if (p.level == l)
				sb.Write(p, s, X, Y, Z, block.getVisableBlock());
	}
	
	/**
	 * Send a block to this player
	 * @param X The X coord of the block
	 * @param Y The Y coord of the block
	 * @param Z The Z coord of the block
 	 * @param block The block to send
	 */
	public void SendBlockChange(short X, short Y, short Z, Block block) {
		server.getPacketManager().getPacket((byte)0x05).Write(this, server, X, Y, Z, block.getVisableBlock());
	}
	
	/**
	 * Send a block update to all the players on level "l" in server "s"
	 * @param X The X pos of the udpate
	 * @param Y The Y pos of the update
	 * @param Z The Z pos of the update
	 * @param block The block to send
	 * @param l The level the update happened in
	 * @param s The server the update happened in
	 */
	public static void GlobalBlockChange(short X, short Y, short Z, Block block, Level l, Server s) {
		GlobalBlockChange(X, Y, Z, block, l, s, true);
	}
	
	/**
	 * Get a list of who the player can see
	 * @return An ArrayList of players
	 */
	public ArrayList<Player> getSeeable() {
		return seeable;
	}
	
	/**
	 * Send the player an MoTD screen
	 * You can have the user hang on the MoTD screen when the player first joins
	 * @param topline The top line of the MoTD screen
	 * @param bottomline The bottom line of the MoTD screen
	 */
	public void sendMoTD(String topline, String bottomline) {
		pm.getPacket("MOTD").Write(this, pm.server, topline, bottomline);
	}
	
	public void UpdatePos() throws IOException {
		if (!isLoggedin)
			return;

		TP t = (TP)(pm.getPacket("TP"));
		GlobalPosUpdate gps = (GlobalPosUpdate)(pm.getPacket("GlobalPosUpdate"));
		byte[] tosend;
		if (Math.abs(getX() - oldX) >= 127 || Math.abs(getY() - oldY) >= 127 || Math.abs(getZ() - oldZ) >= 127)
			tosend = t.toSend(this);
		else
			tosend = gps.toSend(this);
		for (Player p : pm.server.players) {
			if (p == this)
				continue;
			if (p.level == level)
				p.WriteData(tosend);
		}
	}
	
	/**
	 * Spawn a new player for this player
	 * @param p The player to spawn
	 */
	public void spawnPlayer(Player p) {
		if (seeable.contains(p))
			return;
		pm.getPacket((byte)0x07).Write(this, server, p);
		seeable.add(p);
	}

	/**
	 * Get the level this player is currently on
	 * @return The level
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Send a new level to the player
	 * @param level The level to send
	 */
	private void setLevel(Level level) {
		if (this.level == level)
			return;
		this.level = level;
		server.Log(username + " moved to " + level.name);
		levelsender = new SendLevel(this);
		levelsender.start();
	}
	
	/**
	 * Weather or not the player is loading the level
	 * @return True if the player is loading the level, false if the player is not
	 */
	public boolean isLoading() {
		return levelsender != null;
	}
	
	/**
	 * Wait for the player to finish loading the level
	 * The method blocks until the player finishes loading the level 
	 * This will block 
	 * @throws InterruptedException
	 */
	public void waitForLoaded() throws InterruptedException {
		if (levelsender == null)
			return;
		levelsender.join();
	}
	
	
	protected void SendWelcome() {
		pm.getPacket("Welcome").Write(this, pm.server);
	}

	/**
	 * Kick the player from the server
	 * @param reason The reason why he was kicked
	 */
	public void Kick(String reason) {
		if (reason.equals(""))
			reason = "No reason given";
		Packet p = pm.getPacket("Kick");
		this.kickreason = reason;
		server.players.remove(this);
		p.Write(this, pm.server);
	}

	private byte getFreeID() {
		boolean found = true;
		byte toreturn = 0;
		for (int i = 0; i < 255; i++) {
			found = true;
			for (Player p : pm.server.players) {
				if (p.ID == i) {
					found = false;
					break;
				}
			}
			if (found) {
				toreturn = (byte)i;
				break;
			}
		}
		return toreturn;
	}

	/**
	 * Get the X cord. of the player
	 * This is NOT in block cord.
	 * @return The value
	 */
	public short getX() {
		return X;
	}
	
	/**
	 * Get the X cord. of the player on the level
	 * This is in block cord.
	 * @return The value
	 */
	public int getBlockX() {
		return X / 32;
	}
	/**
	 * Get the X cord. of the player
	 * This is NOT in block cord.
	 * @return The value
	 */
	public short getY() {
		return Y;
	}
	/**
	 * Get the X cord. of the player on the level
	 * This is in block cord.
	 * @return The value
	 */
	public int getBlockY() {
		return Y / 32;
	}
	/**
	 * Get the ID of the player
	 * @return The value
	 */
	public byte getID() {
		return ID;
	}
	/**
	 * Get the X cord. of the player
	 * This is NOT in block cord.
	 * @return The value
	 */
	public short getZ() {
		return Z;
	}
	/**
	 * Get the X cord. of the player on the level
	 * This is in block cord.
	 * @return The value
	 */
	public int getBlockZ() {
		return Z / 32;
	}
	/**
	 * Set the current X pos of the player
	 * This will NOT teleport the player
	 * To teleport the player, use {@link #setPos(short, short, short)}
	 * @param value
	 */
	public void setX(short value) {
		oldX = X;
		X = value;
	}
	/**
	 * Set the current Y pos of the player
	 * This will NOT teleport the player
	 * To teleport the player, use {@link #setPos(short, short, short)}
	 * @param value
	 */
	public void setY(short value) {
		oldY = Y;
		Y = value;
	}
	/**
	 * Set the current Z pos of the player
	 * This will NOT teleport the player
	 * To teleport the player, use {@link #setPos(short, short, short)}
	 * @param value
	 */
	public void setZ(short value) {
		oldZ = Z;
		Z = value;
	}
	/**
	 * Teleport the player
	 * @param x The X pos to teleport to
	 * @param y The Y pos to teleport to
	 * @param z The Z pos to teleport to
	 * @param yaw The new yaw
	 * @param pitch The new pitch
	 */
	public void setPos(short x, short y, short z, byte yaw, byte pitch) {
		setX(x);
		setY(y);
		setZ(z);
		oldyaw = this.yaw;
		this.yaw = yaw;
		oldpitch = this.pitch;
		this.pitch = pitch;
		TP();
	}
	/**
	 * Teleport the player
	 * @param x The X pos to teleport to
	 * @param y The Y pos to teleport to
	 * @param z The Z pos to teleport to
	 */
	public void setPos(short x, short y, short z) {
		setPos(x, y, z, yaw, pitch);
	}
	
	protected void TP() {
		Packet p = pm.getPacket("TP");
		for (Player pp : pm.server.players) {
			if (pp.level == level)
				p.Write(pp, pp.pm.server, this, ID); //Tell all the other players as well...
		}
	}
	
	/**
	 * Sends a message to the player.
	 * If the message is longer than 64 chars, then it will not send.
	 * 
	 * @param message
	 *               The message to send
	 */
	@Override
	public void sendMessage(String message){
		Packet p = pm.getPacket("Message");
		String[] messages = chat.split(message);
		for (String m : messages) {
			this.message = m;
			p.Write(this, pm.server);
		}
	}
	/**
	 * Change the level the player is currently in. This method will
	 * call {@link #changeLevel(Level, boolean)} with threading being false.
	 * So this method will block until the level sending is finished.
	 * @param level The new level the player will be moved to.
	 */
	public void changeLevel(Level level) {
		changeLevel(level, false);
	}
	/**
	 * Change the level the player is currently in. This method will
	 * block if threading is false. If threading is true, level sending will
	 * begin in a separate thread and this method wont block.
	 * @param level The new level the player will be moved to.
	 * @param threading Weather to make this call in a separate thread or not.
	 */
	public void changeLevel(Level level, boolean threading)
	{
		if (!threading) {
			setLevel(level);
			try {
				levelsender.join(); //Wait for finish
			} catch (InterruptedException e) { } 
			X = (short)((0.5 + level.spawnx) * 32);
			Y = (short)((1 + level.spawny) * 32);
			Z = (short)((0.5 + level.spawnz) * 32);
			oldX = X;
			oldY = Y;
			oldZ = Z;
			spawnPlayer(this);
			setPos((short)((0.5 + level.spawnx) * 32), (short)((1 + level.spawny) * 32), (short)((0.5 + level.spawnz) * 32));
			for (Player p : pm.server.players) {
				if (p.level == level) {
					spawnPlayer(p); //Spawn p for me
					p.spawnPlayer(this); //Spawn me for p
				}
			}
			setPos((short) ((0.5 + level.spawnx) * 32), (short) ((1 + level.spawny) * 32), (short) ((0.5 + level.spawnz) * 32));
			PlayerJoinedLevel event = new PlayerJoinedLevel(this, this.level);
			server.getEventSystem().callEvent(event);
		}
		else {
			Thread aynct = new asyncLevel(level);
			aynct.start();
		}
	}
	
	/**
	 * This clears the chat screen for the client
	 * by sending 20 blank messages
	 */
	public void clearChatScreen() {
		for (int i = 0; i < 20; i++)
			sendMessage("");
	}

	public void processCommand(String message)
	{
		message = message.substring(1); //Get rid of the / at the beginning
		if (message.split("\\ ").length > 1)
			server.getCommandHandler().execute(this, message.split("\\ ")[0], message.substring(message.indexOf(message.split("\\ ")[1])));
		else
			server.getCommandHandler().execute(this, message, "");
		/* Leaving this here so you can reference the old commands
		 * List<String> list = new ArrayList<String>();
		Collections.addAll(list, message.split(" "));

		String[] args = new String[list.size() - 1];

		for(int i = 1; i < list.size(); i++)
		{
			args[i - 1] = list.get(i);
		}

		String command = message.split(" ")[0];

		if(command.equals("/cc"))
		{
			if(this.cc)
			{
				this.sendMessage("Color Codes have been disabled.");
				this.cc = false;
			}else{
				this.sendMessage("Color Codes have been enabled.");
				this.cc = true;
			}
		}
		else if (command.equals("/spawn"))
			setPos((short)((0.5 + level.spawnx) * 32), (short)((1 + level.spawny) * 32), (short)((0.5 + level.spawnz) * 32));
		else if(command.equals("/stop")) {
			try {
				server.Stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(command.equals("/ban")) {
			if(args.length == 1)
			{
				try {
					FileWriter out = new FileWriter("properties/banned.txt", true);

					out.write(args[0] + "\n");

					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if(command.equals("/unban")) {
			if(args.length == 1)
			{
				server.removeLineFromFile("properties/banned.txt", args[0]);
			}
		} else if(command.equals("/newlvl")) {
			if(args.length == 4)
			{
				LevelHandler handler = server.getLevelHandler();
				handler.loadLevels();
				Level[] levels = handler.levels.toArray(new Level[handler.levels.size()]);

				if(handler.findLevel(args[0]) == null)
				{
					handler.newLevel(args[0], Short.valueOf(args[1]), Short.valueOf(args[2]), Short.valueOf(args[3]));

					sendMessage("Created new level: " + args[0] + ".");
				} else {
					sendMessage("Level already exists...");
				}
			}
		} else if(command.equals("/g")) {
			if(args.length == 1)
			{
				LevelHandler handler = server.getLevelHandler();
				Level level = handler.findLevel(args[0]);

				if(level != null)
				{
					//Despawn(this);
					try {
						changeLevel(level);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					sendMessage("Level doesn't exist...");
				}
			}
		} else if(command.equals("/loaded")) {
			LevelHandler handler = server.getLevelHandler();
			Level[] levels = handler.levels.toArray(new Level[handler.levels.size()]);

			for(Level l : levels)
			{
				if(l != null)
				{
					sendMessage(l.name);
				}
			}
		}

		// TODO: Automatically find all classes that extend to GGPlugin.
		// TODO: Create a system where the plugin is in a separate JAR.

		try {
			Class c = Class.forName("com.gamezgalaxy.test.console.TestPlugin");
			Constructor<? extends GGSPlugin> constructor = c.getConstructor();
			GGSPlugin result = constructor.newInstance();

			result.onCommand(this, command, args);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Handles the messages a player sends to the server, could be used in the future for run command as player
	 * 
	 * @param message
	 * @return void
	 */
	public void recieveMessage(String message){
		if(message.startsWith("/"))
		{	
			PlayerCommandEvent event = new PlayerCommandEvent(this, message);
			pm.server.getEventSystem().callEvent(event);
			if (event.isCancelled())
				return;
			processCommand(message);
		}else{
			String m = message;
			if(!m.matches(".*%([0-9]|[a-f]|[k-r])%([0-9]|[a-f]|[k-r])%([0-9]|[a-f]|[k-r])")){
				if(m.matches(".*%([0-9]|[a-f]|[k-r])(.+?).*") && this.cc){
					Pattern pattern = Pattern.compile("%([0-9]|[a-f]|[k-r])(.+?)");
					Matcher matcher = pattern.matcher(m);
					while (matcher.find()) {
					  String code = matcher.group().substring(1);
					  m = m.replaceAll("%"+code, "&"+code);
					}
				}
			}
			PlayerChatEvent event = new PlayerChatEvent(this, message);
			pm.server.getEventSystem().callEvent(event);
			if (event.isCancelled())
				return;
			pm.server.Log("User "+ this.username + " sent: " + message);
			chat.serverBroadcast(this.getDisplayName() + ChatColor.White + ": " + m);
		}
	}
	
	/**
	 * Get the current server object the player is in
	 * @return
	 *        The server
	 */
	@Override
	public Server getServer() {
		return server;
	}
	
	/**
	 * Despawn a player for this player
	 * @param p The player to despawn
	 */
	public void Despawn(Player p) {
		if (!seeable.contains(p))
			return;
		pm.getPacket((byte)0x0c).Write(this, pm.server, p.ID);
		seeable.remove(p);
	}
	
	/**
	 * Close the connection of this client
	 * If you want to kick the player, use {@link #Kick(String)}
	 */
	@Override
	public void CloseConnection() {
		if (pm.server.players.contains(this))
			pm.server.players.remove(this);
		if(this.username != null)
		{
			pm.server.Log(this.username + " has left the server.");
			chat.serverBroadcast(this.username + " has left the server.");
		}
		for (Player p : pm.server.players)
			p.Despawn(this);
		PlayerDisconnectEvent event = new PlayerDisconnectEvent(this);
		server.getEventSystem().callEvent(event);
		super.CloseConnection();
		pm.server.Remove(tick); //Do this last as this takes a while to remove
	}

	protected void finishLevel() {
		levelsender = null;
	}

	protected class Ping implements Tick {

		Player p;
		public Ping(Player p) { this.p = p; }
		@Override
		public void tick() {
			Packet pa;
			pa = pm.getPacket((byte)0x01);
			pa.Write(p, pm.server);
			pa = null;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected class asyncLevel extends Thread {
		
		Level level;
		
		public asyncLevel(Level level) { this.level = level; }
		@Override
		public void run() {
			changeLevel(level, false);
		}
	}

	protected class SendLevel extends Thread {

		Player p;
		public SendLevel(Player p) { this.p = p; }
		@Override
		public void run() {
			Packet pa;
			pa = pm.getPacket((byte)0x02);
			pa.Write(p, pm.server);
			pa = null;
			pa = pm.getPacket((byte)0x03);
			pa.Write(p, pm.server);
			pa = null;
			pa = pm.getPacket((byte)0x04);
			pa.Write(p, pm.server);
			pa = null;
			p.finishLevel();
		}
	}

	public Messages getChat()
	{
		return chat;
	}

	public boolean isAfk()
	{
		return afk;
	}

	public void setAfk(boolean afk)
	{
		this.afk = afk;
	}
	@Override
	public String getName() {
		return username;
	}
}
