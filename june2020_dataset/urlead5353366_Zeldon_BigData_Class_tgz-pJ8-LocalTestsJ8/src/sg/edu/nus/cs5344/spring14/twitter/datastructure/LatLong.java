package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import org.apache.hadoop.io.DoubleWritable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.Pair;

/**
 * A location represented as latitude/longitude coordinates.
 * @author Tobias Bertelsen
 *
 */
public class LatLong extends Pair<DoubleWritable, DoubleWritable> implements Copyable<LatLong>{

	private static final double EARTH_RADIUS_M = 6371000.0;

	public LatLong() {
		super(new DoubleWritable(), new DoubleWritable());
	}

	public LatLong(double lat, double lon){
		super(new DoubleWritable(lat), new DoubleWritable(lon));
	}

	public double getLat() {
		return getFirst().get();
	}

	public double getLong() {
		return getSecond().get();
	}

	@Override
	public LatLong copy() {
		return new LatLong(getLat(), getLong());
	}

	public XYZCoordinate toXYZ() {
		// See http://www.geomidpoint.com/calculation.html
		double latRad = toRadians(getLat());
		double lonRad = toRadians(getLong());

		double x = cos(latRad) * cos(lonRad);
		double y = cos(latRad) * sin(lonRad);
		double z = sin(latRad);

		return new XYZCoordinate(x, y, z);
	}

	/**
	 * Calculates the spherical distance between this and an other locations.
	 * @param other the other location.
	 * @return the distance in meters
	 */
	public double distanceInMetersTo(LatLong other) {
		// See: http://en.wikipedia.org/wiki/Great-circle_distance
		double lat1 = toRadians(getLat());
		double lat2 = toRadians(other.getLat());
		double lonDiff = toRadians(abs((getLong()) - other.getLong()));
		double arcDist = Math.acos(sin(lat1)*sin(lat2) + cos(lat1)+cos(lat2)*lonDiff);
		return arcDist*EARTH_RADIUS_M;
	}
}
