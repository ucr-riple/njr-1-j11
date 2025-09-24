/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world.blocks;

import java.util.Random;

import com.gamezgalaxy.GGS.server.Server;
import com.gamezgalaxy.GGS.world.Block;
import com.gamezgalaxy.GGS.world.PhysicsBlock;

public class Lava extends PhysicsBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int time;
	
	private int type;
	
	private final Random random = new Random();

	public Lava(byte ID, String name) {
		super(ID, name);
	}
	
	public Lava() {
		super((byte)10, "Lava");
	}

	public Lava(byte b, String string, Server s) {
		super(b, string, s);
	}

	@Override
	public PhysicsBlock clone(Server s) {
		Lava l = new Lava((byte)10, "Lava", s);
		l.type = type;
		if (l.type == 0)
			l.type = random.nextInt(6) + 1;
		return l;
	}

	@Override
	public void tick() {
		if (!nearSponge(getLevel().PosToInt(getX(), getY(), getZ()))) {
			if (time < random.nextInt(6)) {
				if (type == 2 && check(getLevel().PosToInt(getX(), getY() - 1, getZ())))
					add(getX(), getY() - 1, getZ());
				time++;
				return;
			}
			int x1 = random.nextInt(3);
			int x2 = random.nextInt(3);
			int z1 = random.nextInt(3);
			int z2 = random.nextInt(3);
			int i = 0;
			while (i != x1 && (getLevel().getTile(getX() + i + 1, getY(), getZ()).getVisableBlock() == 0 || getLevel().getTile(getX() + i + 1, getY(), getZ()).getVisableBlock() == this.getVisableBlock())) {
				i++;
				if (!check(getLevel().PosToInt(getX() + i, getY(), getZ())))
					break;
				else
					add(getX() + i, getY(), getZ());
			}
			i = 0;
			while (i != x2 && (getLevel().getTile(getX() - i - 1, getY(), getZ()).getVisableBlock() == 0 || getLevel().getTile(getX() - i - 1, getY(), getZ()).getVisableBlock() == this.getVisableBlock())) {
				i++;
				if (!check(getLevel().PosToInt(getX() - i, getY(), getZ())))
					break;
				else
					add(getX() - i, getY(), getZ());
			}
			i = 0;
			while (i != z1 && (getLevel().getTile(getX(), getY(), getZ() + i + 1).getVisableBlock() == 0 || getLevel().getTile(getX(), getY(), getZ() + i + 1).getVisableBlock() == this.getVisableBlock())) {
				i++;
				if (!check(getLevel().PosToInt(getX(), getY(), getZ() + i)))
					break;
				else
					add(getX(), getY(), getZ() + i);
			}
			i = 0;
			while (i != z2 && (getLevel().getTile(getX(), getY(), getZ() - i - 1).getVisableBlock() == 0 || getLevel().getTile(getX(), getY(), getZ() - i - 1).getVisableBlock() == this.getVisableBlock())) {
				i++;
				if (!check(getLevel().PosToInt(getX(), getY(), getZ() - i)))
					break;
				else
					add(getX(), getY(), getZ() - i);
			}
			if (check(getLevel().PosToInt(getX(), getY() - 1, getZ())))
				add(getX(), getY() - 1, getZ());
			//TODO Add other kinds of physics
		}
	}
	public boolean check(int x, int y, int z) {
		return check(getLevel().PosToInt(x, y, z));
	}
	public boolean check(int b) {
		if (b < 0 || b >= getLevel().getLength())
			return false;
		Block bb = getLevel().getTile(b);
		byte btype = bb.getVisableBlock();
		if (bb.getVisableBlock() == 0 && !nearSponge(b))
			return true;
		if ((bb.getVisableBlock() == 41 || bb.getVisableBlock() == 15) && type >= 5)
			return true;
		if ((btype == 16 || btype == 47) && type >= 4)
			return true;
		if (btype >= 21 && btype <= 36 && type >= 3)
			return true;
		if ((btype == 5 || btype == 17 || btype == 18 || btype == 39 || btype == 40) && type > 1)
			return true;
		if ((btype == 1 || btype == 4 || btype == 9 || btype == 20 || btype == 42 || btype == 49) && type == 6)
			return true;
		if (btype == this.getVisableBlock())
			return false;
		return false;
	}
	public boolean nearSponge(int b) {
		for (int x = -2; x <= +2; ++x) {
			for (int y = -2; y <= +2; ++y) {
				for (int z = -2; z <= +2; ++z) {
					if (getLevel().getTile(IntOffset(b, x, y, z)) == Block.getBlock((byte)19))
						return true;
				}
			}
		}
		return false;
	}
	public int IntOffset(int index, int x, int y, int z) {
		return index + x + z * getLevel().width + y * getLevel().width * getLevel().height;
	}

}
