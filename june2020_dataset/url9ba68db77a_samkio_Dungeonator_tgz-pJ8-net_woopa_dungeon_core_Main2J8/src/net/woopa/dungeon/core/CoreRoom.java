package net.woopa.dungeon.core;

import net.woopa.dungeon.datatypes.Direction;
import net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Schematic;
import net.woopa.dungeon.datatypes.Vector2D;
import net.woopa.dungeon.managers.SchematicManager;
import net.woopa.dungeon.managers.SettingsManager;

public class CoreRoom {
	public enum RoomType {
		ROOM, CORRIDOR, SPECIAL
	}

	private int size_x, size_y;
	private int origin_x, origin_y;
	private int gen = 0;
	private int extension_attempts = 0;
	private final Grid grid;
	private RoomType type;
	private Schematic room_map = null;
	private Direction special_dir;

	private final RoomPopulator roomPopulator;;

	private int wayin_x, wayin_y;
	private Direction room_dir;

	public CoreRoom(Grid grid) {
		this.roomPopulator = new RoomPopulator(this);
		this.grid = grid;
		generateRandom();
		placeRandom();
	}

	public void clearAttempts() {
		extension_attempts = 0;
	}

	// TODO this is better but still horrible
	public Boolean startRoom(int x, int y, Direction orig_dir) {
		int cnt = 0;
		Direction dir;
		do {
			dir = placeFrom(x, y, orig_dir);
			if (!grid.fits(origin_x, origin_y, size_x, size_y) && (dir != null)) {
				generateRandom();
			} else {
				break;
			}
			cnt++;
		} while (cnt < 5000);

		if (cnt >= 5000)
			// Can't find a place
			return false;
		renderRoom();
		StandardMethods.startUpStaircase(x, y, CoreMaterial.UP, grid, dir);
		gen = 1;
		roomPopulator.dressRoom();
		return true;
	}

	public Boolean nextRoom(CoreRoom from) {
		int cnt = 0;
		Direction dir = null;
		do {
			dir = placeFrom(from);
			if (!((dir != null)
					&& grid.fits(origin_x, origin_y, size_x, size_y)
					&& (grid.get(wayin_x, wayin_y) == CoreMaterial.WALL)
					&& grid.isFloor(dir.backwards_x(wayin_x),
							dir.backwards_y(wayin_y)))) {
				generateRandom();
			}else break;
			from.extension_attempts++;
			cnt++;
		} while ((cnt < 20)); // Try different sizes and shapes //TODO
		// hardcoded
		if (cnt>=20)
			// Can't find a place
			return false;

		renderRoom();
		StandardMethods.build_door(dir, new Vector2D(wayin_x, wayin_y),
				CoreMaterial.DOOR, grid);
		gen = from.gen + 1;
		roomPopulator.dressRoom();
		return true;
	}

	public Boolean endRoom(CoreRoom from) {
		this.type = RoomType.SPECIAL;
		generateSpecialRoomRandom(SchematicManager.randomDownSchematic());

		final Direction dir = placeFrom(from);
		final Boolean ok = (dir != null)
				&& grid.fits(origin_x, origin_y, size_x, size_y)
				&& (grid.get(wayin_x, wayin_y) == CoreMaterial.WALL)
				&& grid.isFloor(dir.backwards_x(wayin_x),
						dir.backwards_y(wayin_y));

		if (ok) {
			//extension_attempts = 100000; // TODO HC
			this.renderRoom();
			gen = from.gen + 1;
			if (grid.get(wayin_x, wayin_y) == CoreMaterial.DOWN) {
				grid.set(dir.backwards_x(wayin_x), dir.backwards_y(wayin_y),
						CoreMaterial.FIXEDFLOORDOWN);
			} else {
				StandardMethods.build_door(dir, new Vector2D(wayin_x, wayin_y),
						CoreMaterial.DOOR, grid);
			}
			roomPopulator.chestDoubleRandom();
		}
		return ok;
	}

	private Direction placeFrom(CoreRoom from) {
		final Direction dir = Direction.randomDirection();
		final int offset = randomOffset(from, dir);
		if (offset < 0)
			return null;

		switch (dir) {
		case EAST:
			wayin_x = (from.origin_x + from.size_x) - 1;
			wayin_y = from.origin_y + offset;
			break;
		case WEST:
			wayin_x = from.origin_x;
			wayin_y = from.origin_y + offset;
			break;
		case NORTH:
			wayin_x = from.origin_x + offset;
			wayin_y = (from.origin_y + from.size_y) - 1;
			break;
		case SOUTH:
			wayin_x = from.origin_x + offset;
			wayin_y = from.origin_y;
			break;
		}
		return this.placeFrom(wayin_x, wayin_y, dir);
	}

	private Direction placeFrom(int x, int y, Direction dir) {
		wayin_x = x;
		wayin_y = y;
		setRoomDir(dir);
		final int offset = randomOffset(this, dir);
		if (offset < 0)
			return null;

		if (dir.isHorizontal()) {
			origin_x = (dir == Direction.EAST) ? x : (x - size_x) + 1;
			origin_y = y - offset;
		} else {
			origin_y = (dir == Direction.NORTH) ? y : (y - size_y) + 1;
			origin_x = x - offset;
		}
		return dir;
	}

	public int corridorWidth() {
		if (RandomUtil.chance(SettingsManager
				.getInt(CoreSettings.CorridorW3Pct)))
			return 3;
		if (RandomUtil.chance(SettingsManager
				.getInt(CoreSettings.CorridorW3Pct)
				+ SettingsManager.getInt(CoreSettings.CorridorW2Pct)))
			return 2;
		return 1;
	}

	private void generateCorridorRandom() {
		final int width = corridorWidth();
		if (RandomUtil.chance(50)) {
			size_x = randomCorridorSize() + 2;
			size_y = width + 2;
		} else {
			size_x = width + 2;
			size_y = randomCorridorSize() + 2;
		}
	}

	private void generateRandom() {
		if (RandomUtil.chance(SettingsManager.getInt(CoreSettings.CorridorPct))) {
			generateCorridorRandom();
			this.type = RoomType.CORRIDOR;
		} else {
			if (RandomUtil.chance(SettingsManager
					.getInt(CoreSettings.SpecialPct))) {
				generateSpecialRoomRandom(null);
				this.type = RoomType.SPECIAL;
			} else {
				generateRoomRandom();
				this.type = RoomType.ROOM;
			}
		}
	}

	private void generateRoomRandom() {
		size_x = randomRoomSize();
		size_y = randomRoomSize();

	}

	private void generateSpecialRoomRandom(Schematic s) {
		if (s == null) {
			room_map = SchematicManager.randomRoomSchematic();
		} else {
			room_map = s;
		}
		special_dir = Direction.randomDirection();
		size_x = room_map.sx(special_dir);
		size_y = room_map.sy(special_dir);
	}

	public int getExtensionAttempts() {
		return this.extension_attempts;
	}

	public int getGen() {
		return this.gen;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public int getOriginX() {
		return this.origin_x;
	}

	public int getOriginY() {
		return this.origin_y;
	}

	public Direction getRoomDir() {
		return room_dir;
	}

	public int getSizeX() {
		return this.size_x;
	}

	public int getSizeY() {
		return this.size_y;
	}

	public RoomType getType() {
		return type;
	}

	private void placeRandom() {
		if ((((grid.getSize().getX() - size_x) + 1) < 1)
				|| (((grid.getSize().getY() - size_y) + 1) < 1)) {
			origin_x = 0;
			origin_y = 0;
		} else {
			origin_x = RandomUtil.nextInt((grid.getSize().getX() - size_x) + 1);
			origin_y = RandomUtil.nextInt((grid.getSize().getY() - size_y) + 1);
		}
	}

	public int randomCorridorSize() {
		final int cmax = SettingsManager.getInt(CoreSettings.CorridorMax);
		final int cmin = SettingsManager.getInt(CoreSettings.CorridorMin);
		return RandomUtil.nextInt((cmax - cmin) + 1) + cmin;
	}

	private int randomOffset(CoreRoom from, Direction dir) {
		int size = 0;
		if (dir.isHorizontal()) {
			size = from.size_y - 2;
		} else {
			size = from.size_x - 2;
		}
		return (from.getType().equals(RoomType.SPECIAL)) ? from.room_map
				.getAccess(dir, from.special_dir)
				: RandomUtil.nextInt(size) + 1;
	}

	private int randomRoomSize() {
		final int rmax = SettingsManager.getInt(CoreSettings.RoomMax);
		final int rmin = SettingsManager.getInt(CoreSettings.RoomMin);
		return RandomUtil.nextInt((rmax - rmin) + 1) + rmin + 2;
	}

	private void renderRoom() {
		if (type == RoomType.SPECIAL) {
			grid.renderSchematic(origin_x, origin_y, room_map, special_dir);
		} else {
			grid.renderBasicEmptyRoom(origin_x, origin_y, size_x, size_y,
					CoreMaterial.WALL, CoreMaterial.FLOOR);
		}
	}

	public void setRoomDir(Direction room_dir) {
		this.room_dir = room_dir;
	}

	@Override
	public String toString() {
		return "ROOM:(" + origin_x + "," + origin_y + ") size(" + size_x + ","
				+ size_y + ") gen=" + gen + " att=" + extension_attempts;
	}

	public Vector2D wayin() {
		return new Vector2D(wayin_x, wayin_y);
	}
}
