package frs.hotgammon.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({CoreTests.class,  AlternatingTurnTests.class, WinAfterSixTests.class, BetaMonTests.class, GammaMonTests.class, DeltaMonTests.class, BoardTests.class, LocationTests.class } )
public class AllTests {
}
