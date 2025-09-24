
package frs.hotgammon.tests.preGuiTests.mariatests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import frs.hotgammon.tests.preGuiTests.EpsilonMonTests;
import frs.hotgammon.tests.preGuiTests.RandomRollDeterminerTests;
import frs.hotgammon.tests.preGuiTests.SemiMonTests;


@RunWith(Suite.class)
@SuiteClasses({SemiMonTests.class, RandomRollDeterminerTests.class, EpsilonMonTests.class, HandicapMonTests.class, FactoryCoreTests.class, PairSequenceDeterminerTests.class, LocationTests.class, BoardTests.class, CoreTests.class, BetaMonTests.class, AlternatingTurnTests.class, DeltaMonTests.class, GammaMonTests.class, SimpleMoveValidatorTests.class, SixMoveWinnerTests.class} )
public class AllTests {
}
