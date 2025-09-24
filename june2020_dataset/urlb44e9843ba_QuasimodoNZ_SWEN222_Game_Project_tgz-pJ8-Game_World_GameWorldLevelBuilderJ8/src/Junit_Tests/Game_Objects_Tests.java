package Junit_Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import Game_World.Avatar;
import Game_World.BaseLocations;
import Game_World.GameWorld;
import Game_World.Location;

public class Game_Objects_Tests {

	private GameWorld level1;

	public Game_Objects_Tests(){
		level1 = new GameWorld();
		HashMap<String,Location> gameLocations = new HashMap<String,Location> ();
		Location loc1 = BaseLocations.getInsideHome();
		System.out.println(loc1.getAllObjects().size());
		gameLocations.put("LocationOne", loc1);
		level1.setLocations(gameLocations);
	}

	@Test
	public void testGameWorldContentsSize(){
		assertTrue(level1.getLocations().size() == 1);
	}

	@Test
	public void testGameWorldLocationSize(){
		assertTrue(level1.getLocations().get("LocationOne").getContents().size() == 24);
	}

	public GameWorld getLevel1(){
		return level1;
	}

	public static void main(String[] args) {
		Game_World_tests obj = new Game_World_tests();
	}

	@Test
	public void testAvatar() {
		Avatar a2 = new Avatar("Charles", 1);
		List<Avatar> avatars = new ArrayList<Avatar>();
		avatars.add(a2);
		System.out.println(a2.getPlayerID());


		assertEquals(a2.getName(),"Charles");

	}

}
