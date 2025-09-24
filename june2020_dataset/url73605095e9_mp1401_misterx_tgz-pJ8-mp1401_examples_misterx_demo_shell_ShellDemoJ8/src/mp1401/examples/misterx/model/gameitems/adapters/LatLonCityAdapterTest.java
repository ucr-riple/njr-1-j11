package mp1401.examples.misterx.model.gameitems.adapters;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Point;

import mp1401.examples.misterx.model.gameitems.City;

import org.junit.Test;

public class LatLonCityAdapterTest {

  @Test
  public void testGetPosition() {
    final City city = new LatLonCityAdapter(new LatLonCity("City 1", 1.2245, 3.65565));
    assertThat(city.getPosition()).isEqualTo(new Point(4, 1));
  }

}
