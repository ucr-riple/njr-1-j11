/**
 *
 */
package Junit_Tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.Test;

import Game_World.*;
import Game_World_Objects.*;

/**
 * @author riddelbenj
 * 
 */
public class MovementTests {
	private GameWorld newWorld() {
		GameWorld world = new GameWorld();
		world.getLocations().put("testRoom1", new Location("testRoom1"));
		world.getLocations().put("testRoom2", new Location("testRoom2"));
		return world;
	}

	private GameWorld newWorldWithAvatar() {
		GameWorld world = newWorld();

		Location testRoom = world.getLocations().get("testRoom1");

		Avatar avatar = new Avatar("JUnit", 0);
		avatar.setLocationName(testRoom.getName());
		avatar.setBoundingBox(new Rectangle(0, 0, 20, 20));
		avatar.setStepsize(10);
		avatar.setWorld(world);

		world.getAvatars().add(avatar);
		world.getAvatarLocations().put(avatar, testRoom);
		return world;
	}

	@Test
	public void unobstructedMove() {
		GameWorld world = newWorldWithAvatar();
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void unobstructedMoveWithWall() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Wall(new Rectangle(100, 100, 10, 10)));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void obstructedMoveWithWall() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Wall(new Rectangle(15, 15, 10, 10)));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(0, 0, 20, 20)));
	}

	@Test
	public void obstructedMoveWithPiller() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Relic(new Point(15, 15)));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(0, 0, 20, 20)));
	}

	@Test
	public void unobstructedMoveWithFood() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Food(new Point(15, 0), 0));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void unobstructedMoveWithKey() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Key(new Point(15, 0), 0));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void unobstructedMoveWithTrap() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Trap(new Point(15, 0), 0));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void damagedMoveWithTrap() {
		GameWorld world = newWorldWithAvatar();
		world.getLocations().get("testRoom1").getAllObjects()
				.add(new Trap(new Point(15, 0), 10));
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getHealth() == 90);
	}

	@Test
	public void movementWithInternalDoor() {
		GameWorld world = newWorldWithAvatar();
		InternalDoor door = new InternalDoor(new Point(15, 0));
		door.setLocked(true);
		world.getLocations().get("testRoom1").getAllObjects().add(door);
		Avatar avatar = world.getAvatars().get(0);

		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(0, 0, 20, 20)));
		door.setLocked(false);
		avatar.move(39);
		assertTrue(avatar.getBoundingBox().equals(new Rectangle(10, 0, 20, 20)));
	}

	@Test
	public void moveThroughExternalDoor() {

		GameWorld world = newWorldWithAvatar();
		ExternalDoor door = new ExternalDoor(new Point(0, 0), 1, world,
				"testRoom2", new Point(10, 10));
		world.getLocations().get("testRoom1").getAllObjects().add(door);
		Avatar avatar = world.getAvatars().get(0);

		world.moveThroughDoor(avatar.getName(), door);
		assertTrue(
				"The destination room does not contain the avatar",
				world.getLocations().get("testRoom2").getAllObjects()
						.contains(avatar));
		assertFalse(
				"The source room still contains the avatar",
				world.getLocations().get("testRoom1").getAllObjects()
						.contains(avatar));

		assertTrue(
				"The avatar doesnt know it is in the destination room",
				avatar.getCurrentLocation().equals(
						world.getLocations().get("testRoom2")));
		assertTrue("The avatar doesnt know it is in the destination room",
				avatar.getLocationName().equals("testRoom2"));
		assertTrue("The avatar did not turn in the correct direction",
				avatar.getDirection() == door.getDirection());
		assertTrue("The avatars x position did not change",
				avatar.getBoundingBox().x == 10);
		assertTrue("The avatars y position did not change",
				avatar.getBoundingBox().y == 10);
	}
}
