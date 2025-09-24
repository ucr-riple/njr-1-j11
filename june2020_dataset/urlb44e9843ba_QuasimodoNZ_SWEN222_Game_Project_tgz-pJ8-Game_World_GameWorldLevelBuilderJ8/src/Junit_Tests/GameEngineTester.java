package Junit_Tests;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

import Game_World.BaseLocations;
import Game_World.GameEngine;
import Game_World.GameWorld;
import Game_World.Location;

public class GameEngineTester {

	private GameEngine engine;
	private GameWorld level1;

	public GameEngineTester() throws FileNotFoundException, IntrospectionException{
		engine = new GameEngine(1);
		level1 = new GameWorld();
		Location loc1 = BaseLocations.getInsideHome();
		level1.getLocations().put("LocationOne", loc1);
		engine.setWorld(level1);
		System.out.println(engine.getWorld().getLocations().size());
	}

	@Test
	public void testGameEngineWorldSize(){
		assertTrue(engine.getWorld().getLocations().size() == 1);
	}

	public static void main(String[] args) throws FileNotFoundException, IntrospectionException {
		GameEngineTester obj = new GameEngineTester();
	}

}
