package mp1401.examples.misterx.model.gameitems.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;

public class CityImpl extends AbstractGameItemImpl implements City {

  private static final long serialVersionUID = 4472170963074184150L;

  private final String name;
  private final Point point;

  public CityImpl(final String name, final Point point) {
    super();
    this.name = name;
    this.point = point;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Point getPosition() {
    return point;
  }

  @Override
  public List<Connection> getConnections() {
    final List<Connection> relevantConnections = new ArrayList<Connection>();
    for (final Connection connection : getGame().getConnections()) {
      if (isRelevantConnection(connection)) {
        relevantConnections.add(connection);
      }
    }
    return relevantConnections;
  }

  public boolean isRelevantConnection(final Connection connection) {
    return connection.getCityA().equals(this) || connection.getCityB().equals(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return getName().equals(((City) obj).getName());
  }

  @Override
  public String toString() {
    return getName();
  }
}
