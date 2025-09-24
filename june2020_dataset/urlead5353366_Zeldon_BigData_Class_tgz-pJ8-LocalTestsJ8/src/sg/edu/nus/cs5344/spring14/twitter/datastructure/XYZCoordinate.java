package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;

/**
 * A location specified by the cartesian coordinates.
 * The coordinate system is specified as having eath's center as
 * the origin and the earth's radius as the radius of the unit sphere.
 * @author Tobias Bertelsen
 *
 */
public class XYZCoordinate {

	private final double x;
	private final double y;
	private final double z;

	public XYZCoordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public LatLong toLatLong() {
		// See http://www.geomidpoint.com/calculation.html
		double hyp = sqrt(x * x + y * y);
		double lat = atan2(z, hyp);
		double lon = atan2(y, x);
		return new LatLong(toDegrees(lat), toDegrees(lon));
	}
}
