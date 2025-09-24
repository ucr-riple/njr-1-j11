package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({myDatabaseTest.class,myTradeTest.class,TestMain.class,MyListTest.class,MyStrategyTest.class,TestStrategyAndEval.class})
public class AllTestSuite {

}
