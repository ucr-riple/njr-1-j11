package mp1401.examples.misterx.model.gameitems;

import java.awt.Point;
import java.util.List;

public interface City extends GameItem {

	public String getName();

	public Point getPosition();

	public List<Connection> getConnections();

}
