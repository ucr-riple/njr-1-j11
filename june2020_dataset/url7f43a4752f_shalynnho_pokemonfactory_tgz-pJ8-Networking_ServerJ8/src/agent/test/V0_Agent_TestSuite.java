package agent.test;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import factory.KitConfig;
import factory.Order;
import factory.PartType;

import agent.*;
import agent.test.mock.MockGraphics;

/**
 * This test suite will run all JUnit tests for agents in the Kitting Cell.
 * @author Daniel Paje
 */
@RunWith(Suite.class)
@SuiteClasses({ V0_JUnit_ConveyorAgent_Test_NormativeScenario.class,
		V0_JUnit_KitRobotAgent_Test_NormativeScenario.class,
		V0_JUnit_StandAgent_Test_NormativeScenario.class,
		V0_JUnit_GantryFeederLaneNestNormativeScenario.class,
		V0_JUnit_PartsRobotAgent_CameraAgent_Test_NormativeScenario.class })
public class V0_Agent_TestSuite {

	/**
	 * Empty method; runs tests
	 */
	@Test
	public void runTestSuite() {
	}

}
