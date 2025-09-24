package net.woopa.dungeon.core;

import net.woopa.dungeon.core.CoreRoom.RoomType;
import net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Material;
import net.woopa.dungeon.managers.SettingsManager;

public class RoomPopulator {
	private final CoreRoom room;

	public RoomPopulator(CoreRoom r) {
		this.room = r;
	}

	public void dressRoom() {
		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.EnchantPct)))
			enchantRandom();

		/*if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.TrapPct)))
			trapRandom();*/

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.ChestPct)))
			chestRandom();

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.SpawnerPct)))
			objectRandom(CoreMaterial.SPAWNER);

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.ShroomPct)))
			for (int i = 0; i <= RandomUtil.nextInt(3); i++)
				objectRandom(CoreMaterial.SHROOM);

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.BenchPct)))
			objectRandom(CoreMaterial.WORKBENCH);

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.OvenPct)))
			objectRandom(CoreMaterial.FURNACE);

		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.AnvilPct)))
			objectRandom(CoreMaterial.ANVIL);

		if (!(room.getType().equals(RoomType.SPECIAL))
				&& RandomUtil.chance(SettingsManager
						.getInt(CoreSettings.PoolPct)))
			poolRandom();

		if (!(room.getType().equals(RoomType.SPECIAL))
				&& RandomUtil.chance(SettingsManager
						.getInt(CoreSettings.SandPct)))
			allFloor(CoreMaterial.SOULSAND, 2);
	}

	private void allFloor(Material m, int inset) {
		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		for (int x = 0; x < px; x++) {
			for (int y = 0; y < py; y++) {
				int px1 = room.getOriginX() + x + inset;
				int py1 = room.getOriginY() + y + inset;
				if (room.getGrid().get(px1, py1) == CoreMaterial.FLOOR) {
					room.getGrid().set(px1, py1, m);
				}
			}
		}
	}

/*	private Boolean trapRandom() {
		wallLoc loc;
		if (true) {
			loc = wallLocation(this);
			if (loc != null) {
				int dx = loc.dir.rotate180().dx(1);
				int dy = loc.dir.rotate180().dy(1);
				if (room.getGrid().get(loc.x, loc.y) == CoreMaterial.WALL
						&& room.getGrid().get(loc.x + dx, loc.y + dy) == CoreMaterial.FLOOR
						&& room.getGrid().get(loc.x + dx * 2, loc.y + dy * 2) == CoreMaterial.FLOOR
						&& room.getGrid().get(loc.x + dx * 3, loc.y + dy * 3) == CoreMaterial.FLOOR) {
					room.getGrid().set(loc.x, loc.y, CoreMaterial.ARROW);
					room.getGrid().set(loc.x + dx, loc.y + dy, CoreMaterial.RED2);
					room.getGrid().set(loc.x + dx * 2, loc.y + dy * 2, CoreMaterial.RED1);
					room.getGrid().set(loc.x + dx * 3, loc.y + dy * 3,
							CoreMaterial.PRESSURE);
					return true;
				}
			}
		}
		return false;
	}*/

	private Boolean chestRandom() {
		int inset = 1;

		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		if (px > 0 && py > 0 && (room.getSizeX() > 3 && room.getSizeY() > 3)) {
			int px1, py1;
			for (int at = 0; at < 100; at++) {
				px1 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py1 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
				if (okForChest(px1, py1, room.getGrid())) {
					room.getGrid().set(px1, py1, CoreMaterial.CHEST);
					return true;
				}
			}
		}
		return false;
	}

	private boolean okForChest(int x, int y, Grid g) {
		if (g.get(x, y) != CoreMaterial.FLOOR) // Must be plain floor (not fixed
												// foor etc)
			return false;
		if (g.isChest(x + 1, y) || g.isChest(x - 1, y) || g.isChest(x, y + 1)
				|| g.isChest(x, y - 1))
			return false;
		return true;
	}

	private Boolean enchantRandom() {
		int inset = 3;

		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		if (px > 0 && py > 0 && (room.getSizeX() > 3 && room.getSizeY() > 3)) {
			int px1, py1;
			for (int at = 0; at < 100; at++) {
				px1 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py1 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
				if (room.getGrid().get(px1, py1) == CoreMaterial.FLOOR) {
					room.getGrid().set(px1, py1, CoreMaterial.ENCHANT);
					for (int i = -2; i < 2; i++) {
						if (RandomUtil.chance(50)
								&& room.getGrid().get(px1 - 2, py1 + i) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 - 2, py1 + i, CoreMaterial.BOOKCASE);
						if (RandomUtil.chance(50)
								&& room.getGrid().get(px1 + 2, py1 - i) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 + 2, py1 - i, CoreMaterial.BOOKCASE);
						if (RandomUtil.chance(50)
								&& room.getGrid().get(px1 - i, py1 - 2) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 - i, py1 - 2, CoreMaterial.BOOKCASE);
						if (RandomUtil.chance(50)
								&& room.getGrid().get(px1 + i, py1 + 2) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 + i, py1 + 2, CoreMaterial.BOOKCASE);
					}
					for (int i = -1; i < 1; i++) {
						if (room.getGrid().get(px1 - 1, py1 + i) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 - 1, py1 + i, CoreMaterial.FIXEDFLOOR);
						if (room.getGrid().get(px1 + 1, py1 - i) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 + 1, py1 - i, CoreMaterial.FIXEDFLOOR);
						if (room.getGrid().get(px1 - i, py1 - 1) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 - i, py1 - 1, CoreMaterial.FIXEDFLOOR);
						if (room.getGrid().get(px1 + i, py1 + 1) == CoreMaterial.FLOOR)
							room.getGrid().set(px1 + i, py1 + 1, CoreMaterial.FIXEDFLOOR);
					}
					return true;
				}
			}
		}
		return false;
	}

	Boolean chestDoubleRandom() {
		int inset = 1;

		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		if (px > 0 && py > 0 && (room.getSizeX() > 3 && room.getSizeY() > 3)) {
			int px1, py1;
			for (int at = 0; at < 100; at++) {
				px1 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py1 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
				int px2 = px1;
				int py2 = py1;
				switch (RandomUtil.nextInt(4)) {
				case 0:
					px2 += 1;
					break;
				case 1:
					px2 -= 1;
					break;
				case 2:
					py2 += 1;
					break;
				case 3:
					py2 -= 1;
					break;
				}
				if (okForChest(px1, py1, room.getGrid()) && okForChest(px2, py2, room.getGrid())) {
					room.getGrid().set(px1, py1, CoreMaterial.MIDCHEST);
					room.getGrid().set(
							px2,
							py2,
							(SettingsManager
									.getBoolean(CoreSettings.MedHalfEmpty)) ? CoreMaterial.EMPTYCHEST
									: CoreMaterial.CHEST);
					return true;
				}
			}
		}
		return false;
	}

	private void poolRandom() {
		Material pool = CoreMaterial.WATER;// TODO hardcoded
		int inset = 3;

		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		if (px > 0 && py > 0 && (room.getSizeX() > 3 && room.getSizeY() > 3)) {
			int px1, px2, py1, py2;
			if (RandomUtil.chance(SettingsManager
					.getInt(CoreSettings.FullPoolPct))) { // Full pool
				px1 = room.getOriginX() + inset;
				py1 = room.getOriginY() + inset;
				px2 = room.getOriginX() + px - 1 + inset;
				py2 = room.getOriginY() + py - 1 + inset;
			} else {
				px1 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py1 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
				px2 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py2 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
			}
			for (px = Math.min(px1, px2); px <= Math.max(px1, px2); px++) {
				for (py = Math.min(py1, py2); py <= Math.max(py1, py2); py++) {
					if (room.getGrid().get(px, py) == CoreMaterial.FLOOR) {
						room.getGrid().set(px, py, pool);
					}
				}
			}
		}
	}

	Boolean objectRandom(Material m) {
		int inset = 1;

		int px = room.getSizeX() - inset * 2;
		int py = room.getSizeY() - inset * 2;

		if (px > 0 && py > 0 && (room.getSizeX() > 3 && room.getSizeY() > 3)) {
			int px1, py1;
			for (int at = 0; at < 100; at++) {// TODO hard coded
				px1 = room.getOriginX() + RandomUtil.nextInt(px) + inset;
				py1 = room.getOriginY() + RandomUtil.nextInt(py) + inset;
				if (room.getGrid().get(px1, py1) == CoreMaterial.FLOOR) {
					room.getGrid().set(px1, py1, m);
					return true;
				}
			}
		}
		return false;
	}
}
