package mp1401.examples.misterx.tests;

import mp1401.examples.misterx.model.game.GameImplTest;
import mp1401.examples.misterx.model.gameitems.adapters.LatLonCityAdapterTest;
import mp1401.examples.misterx.model.gameitems.impl.CityImplTest;
import mp1401.examples.misterx.model.gameitems.impl.collections.GameItemListImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  GameImplTest.class,
  LatLonCityAdapterTest.class,
  CityImplTest.class,
  GameItemListImplTest.class
})//@formatter:on
public class AllTests {}