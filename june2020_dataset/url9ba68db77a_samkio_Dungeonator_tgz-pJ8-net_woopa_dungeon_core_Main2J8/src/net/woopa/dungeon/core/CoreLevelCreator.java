package net.woopa.dungeon.core;

import java.util.ArrayList;

import net.woopa.dungeon.core.CoreRoom.RoomType;
import net.woopa.dungeon.datatypes.Direction;
import net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.LevelCreator;
import net.woopa.dungeon.datatypes.Vector2D;

public class CoreLevelCreator implements LevelCreator {
	private Vector2D levelStart, levelEnd;
	private Direction startDir, endDir;
	private ArrayList<CoreRoom> rooms = new ArrayList<CoreRoom>();

	@Override
	public Vector2D levelStart() {
		return levelStart;
	}

	@Override
	public Vector2D levelEnd() {
		return levelEnd;
	}

	@Override
	public Grid generate(Vector2D levelSize, Vector2D startLocation,
			Direction startDirection) {
		Grid grid = new Grid(levelSize);
		this.levelStart = startLocation;
		CoreRoom start = new CoreRoom(grid);
		if (!start.startRoom(startLocation.getX(), startLocation.getY(),
				startDirection)) {
			// Couldn't place the starting room
			return null;
		}
		// start_dir = r.room_dir();
		rooms.add(start);
		CoreRoom n = new CoreRoom(grid);
		CoreRoom from = start;
		int max_gen = 0;
		// Place rooms until we have to back track too much
		for (int t = 0; t < 1000 && from != null; t++) {
			if (from.getExtensionAttempts() >= 400) {// TODO hardcoded int
				from = getRoomNearEnd();
			}
			if (from != null && n.nextRoom(from)) {
				rooms.add(n);
				if (n.getGen() > max_gen)
					max_gen = n.getGen();
				if (grid.percentUtilized() > 85.0) {// TODO hardcoded float
					from = null;
				} else {
					from = this.rooms.get(RandomUtil.nextInt(rooms.size()));
				}
				n = new CoreRoom(grid);
			}
		}
		// Find a location for the end room
		clearRoomAttempts();
		from = getRoomGen();
		for (int t = 0; t < 1000 && from != null; t++) {
			if (from.getExtensionAttempts() >= 400) {// TODO hardcoded int
				from = getRoomGen();
			}
			if (from != null && n.endRoom(from)) {
				levelEnd = n.wayin().clone();
				endDir = n.getRoomDir();
				from = null;
				n = new CoreRoom(grid);
			}
		}
		if (true && from == null) {
			// Fill the rest of the map
			from = getRoomNearEnd();
			for (int t = 0; t < 1000 && from != null; t++) {
				if (from.getExtensionAttempts() >= 400) {// TODO hardcoded int
					from = getRoomNearEnd();
				}
				if (from != null && n.nextRoom(from)) {
					rooms.add(n);
					from = n;
					n = new CoreRoom(grid);
				}
			}

			if (endDir == null) {
				// No end room
			}
		} else {
			// No start room
		}
		this.randomlyAddDoors(grid);
		return grid;
	}

	private CoreRoom getRoomGen() {
		CoreRoom r = null;
		int max = 0;
		if (rooms != null) {
			for (CoreRoom x : rooms) {
				if (!(x.getExtensionAttempts() >= 400)) {// TODO hardcoded int
					if (x.getGen() >= max && x.getType().equals(RoomType.ROOM)) {
						r = x;
						max = x.getGen();
					}
				}
			}
		}
		return r;
	}

	private void clearRoomAttempts() {
		if (rooms != null) {
			for (CoreRoom x : rooms) {
				x.clearAttempts();
			}
		}
	}

	private void randomlyAddDoors(Grid grid) {
		for (int x = 0; x < grid.getSize().getX(); x++) {
			for (int y = 0; y < grid.getSize().getY(); y++) {
				if (grid.get(x, y).isWall()) {
					Direction dir = Direction.randomDirection();
					if (grid.get(dir.left_x(x), dir.left_y(y)).equals(
							CoreMaterial.WALL)
							&& grid.get(dir.right_x(x), dir.right_y(y)).equals(
									CoreMaterial.WALL)
							&& grid.get(dir.backwards_x(x), dir.backwards_y(y))
									.equals(CoreMaterial.FLOOR)
							&& grid.get(dir.forwards_x(x), dir.forwards_y(y))
									.equals(CoreMaterial.FLOOR)) {
						StandardMethods.build_door(dir, new Vector2D(x, y),
								CoreMaterial.CAKE, grid);
					}
				}
			}
		}
	}

	private CoreRoom getRoomNearEnd() {
		CoreRoom r = null;
		if (rooms != null) {
			for (CoreRoom x : rooms) {
				if (!(x.getExtensionAttempts() >= 400)) { // TODO hardcoded int
					r = x;
				}
			}
		}
		return r;
	}

	@Override
	public Direction endDirection() {
		return endDir;
	}

	@Override
	public Direction startDirection() {
		return startDir;
	}
	
	public void clean(){
		this.rooms.clear();
	}

}
