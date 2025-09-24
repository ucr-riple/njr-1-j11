package mp1401.examples.misterx.model.gameitems.impl;

import java.awt.Point;

import mp1401.examples.misterx.model.factory.GameItemFactory;
import mp1401.examples.misterx.model.factory.GameItemFactoryImpl;
import mp1401.examples.misterx.model.gameitems.City;

import org.junit.Assert;
import org.junit.Test;

public class CityImplTest {

  private static final GameItemFactory FACTORY = GameItemFactoryImpl.getInstance();

  @Test
  public void testEquals() {
    final City city1 = FACTORY.createCity("Zuerich", new Point(1, 1));
    final City city2 = FACTORY.createCity("Zuerich", new Point(2, 1));
    Assert.assertEquals("Expected cities to be equal", city1.equals(city2), true);
  }
}
