package nz.net.initial3d;

/**
 * <p>
 * An immutable 3-dimensional vector.
 * </p>
 * <p>
 * Unless specified otherwise, all methods will throw
 * <code>NullPointerException</code> if called with any argument equal to
 * <code>null</code>.
 * </p>
 *
 * @author Ben Allen
 */

public final class Vec3 {

	/** The zero vector, equal to (0,0,0). */
	public static final Vec3 zero;

	/** The one vector, equal to (1,1,1). */
	public static final Vec3 one;

	/** The (first) basis vector i, equal to (1,0,0). */
	public static final Vec3 i;

	/** The (second) basis vector j, equal to (0,1,0). */
	public static final Vec3 j;

	/** The (third) basis vector k, equal to (0,0,1). */
	public static final Vec3 k;

	/** The first component of this vector. */
	public final double x;

	/** The second component of this vector. */
	public final double y;

	/** The third component of this vector. */
	public final double z;

	// private cache
	// magnitude can never be < 0, so -1 means not initialised
	private double m = -1;
	private double im = -1;

	static {
		// i suppose this could be done with a private constructor?
		zero = new Vec3(0, 0, 0);
		zero.m = 0;
		zero.im = Double.POSITIVE_INFINITY;
		one = new Vec3(1, 1, 1);
		i = new Vec3(1, 0, 0);
		i.m = 1;
		i.im = 1;
		j = new Vec3(0, 1, 0);
		j.m = 1;
		j.im = 1;
		k = new Vec3(0, 0, 1);
		k.m = 1;
		k.im = 1;
	}

	/**
	 * Construct a Vec3 from components.
	 *
	 * @param x_
	 *            X component.
	 * @param y_
	 *            Y component.
	 * @param z_
	 *            Z component.
	 */
	public Vec3(double x_, double y_, double z_) {
		x = x_;
		y = y_;
		z = z_;
	}

	/**
	 * Randomly generate a Vec3, with each component between the respective
	 * minimum (inclusive) and maximum (exclusive) of the input vectors.
	 * Returned values are chosen pseudorandomly with (approximately) uniform
	 * distribution from that range.
	 *
	 * @param a
	 *            A Vec3.
	 * @param b
	 *            A Vec3.
	 * @return A pseudorandomly generated Vec3.
	 */
	public static final Vec3 random(Vec3 a, Vec3 b) {
		Vec3 max = positiveExtremes(a, b);
		Vec3 min = negativeExtremes(a, b);
		double x = Math.random() * (max.x - min.x) + min.x;
		double y = Math.random() * (max.y - min.y) + min.y;
		double z = Math.random() * (max.z - min.z) + min.z;
		return new Vec3(x, y, z);
	}

	/**
	 * Construct the normal of a plane from 3 points on it.
	 *
	 * @param p0
	 *            First point on the plane.
	 * @param p1
	 *            Second point on the plane.
	 * @param p2
	 *            Third point on the plane.
	 * @return The normal of a plane defined by the 3 points such that the
	 *         normal lies on the side of the plane from which the points appear
	 *         to be ordered anticlockwise, ie <i>normal == (p1 - p0) x (p2 -
	 *         p1)</i>.
	 */
	public static final Vec3 planeNorm(Vec3 p0, Vec3 p1, Vec3 p2) {
		return p1.sub(p0).cross(p2.sub(p1));
	}

	/**
	 * @param a
	 *            A Vec3.
	 * @param b
	 *            A Vec3.
	 * @return A new Vec3 constructed from the per-component positive extremes
	 *         of <code>a</code> and <code>b</code>.
	 */
	public static final Vec3 positiveExtremes(Vec3 a, Vec3 b) {
		return new Vec3(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
	}

	/**
	 * @param a
	 *            A Vec3.
	 * @param b
	 *            A Vec3.
	 * @return A new Vec3 constructed from the per-component negative extremes
	 *         of <code>a</code> and <code>b</code>.
	 */
	public static final Vec3 negativeExtremes(Vec3 a, Vec3 b) {
		return new Vec3(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
	}

	/**
	 * Add this Vec3 to another.
	 *
	 * @param rhs
	 *            Right-hand-side of the addition.
	 * @return The sum.
	 */
	public Vec3 add(Vec3 rhs) {
		return new Vec3(x + rhs.x, y + rhs.y, z + rhs.z);
	}

	/**
	 * Add this Vec3 to another given as components.
	 *
	 * @param rightx
	 *            X component of the right-hand-side of the addition.
	 * @param righty
	 *            Y component of the right-hand-side of the addition.
	 * @param rightz
	 *            Z component of the right-hand-side of the addition.
	 * @return The sum.
	 */
	public Vec3 add(double rightx, double righty, double rightz) {
		return new Vec3(x + rightx, y + righty, z + rightz);
	}

	/**
	 * Subtract a Vec3 from this one.
	 *
	 * @param rhs
	 *            Right-hand-side of the subtraction.
	 * @return The difference.
	 */
	public Vec3 sub(Vec3 rhs) {
		return new Vec3(x - rhs.x, y - rhs.y, z - rhs.z);
	}

	/**
	 * Subtract a Vec3 given as components from this one.
	 *
	 * @param rightx
	 *            X component of the right-hand-side of the subtraction.
	 * @param righty
	 *            Y component of the right-hand-side of the subtraction.
	 * @param rightz
	 *            Z component of the right-hand-side of the subtraction.
	 * @return The difference.
	 */
	public Vec3 sub(double rightx, double righty, double rightz) {
		return new Vec3(x - rightx, y - righty, z - rightz);
	}

	/**
	 * @return A new Vec3 with the same magnitude as this one, but pointing in
	 *         the opposite direction.
	 */
	public Vec3 neg() {
		Vec3 negvec = new Vec3(-x, -y, -z);
		// if mag and/or invmag are known, set
		if (m > 0) negvec.m = m;
		if (im > 0) negvec.im = im;
		return negvec;
	}

	/**
	 * Get the distance from this Vec3 to another, defined as the magnitude of
	 * their difference.
	 *
	 * @param v
	 *            Other Vec3.
	 * @return The distance.
	 */
	public double dist(Vec3 v) {
		double dx = x - v.x;
		double dy = y - v.y;
		double dz = z - v.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * Compute the dot product of this Vec3 and another.
	 *
	 * @param rhs
	 *            Right-hand-side of the dot product.
	 * @return The dot product.
	 */
	public double dot(Vec3 rhs) {
		return x * rhs.x + y * rhs.y + z * rhs.z;
	}

	/**
	 * Compute the cross product of this Vec3 and another.
	 *
	 * @param rhs
	 *            Right-hand-side of the cross product.
	 * @return The cross product.
	 */
	public Vec3 cross(Vec3 rhs) {
		return new Vec3(y * rhs.z - z * rhs.y, z * rhs.x - x * rhs.z, x * rhs.y - y * rhs.x);
	}

	/**
	 * Compute the magnitude of the cross product of this Vec3 and another.
	 *
	 * @param rhs
	 *            Right-hand-side of the cross product.
	 * @return The magnitude of the cross product.
	 */
	public double crossMag(Vec3 rhs) {
		return Math.sqrt(Math.pow(y * rhs.z - z * rhs.y, 2) + Math.pow(z * rhs.x - x * rhs.z, 2)
				+ Math.pow(x * rhs.y - y * rhs.x, 2));
	}

	/**
	 * Compute the included angle between this Vec3 and another.
	 *
	 * @param vec
	 *            A Vec3.
	 * @return The included angle, in radians.
	 */
	public double inc(Vec3 vec) {
		return Math.acos(dot(vec) / (mag() * vec.mag()));
	}

	/**
	 * Multiply this Vec3 by a scale factor.
	 *
	 * @param f
	 *            Scale factor.
	 * @return A new Vec3, parallel to this Vec3 and with magnitude
	 *         <code>f</code> times the magnitude of this Vec3.
	 */
	public Vec3 mul(double f) {
		Vec3 scalevec = new Vec3(x * f, y * f, z * f);
		// if mag was already calculated, scale that as well
		if (m > 0) scalevec.m = m * f;
		return scalevec;
	}

	/**
	 * Scale this Vec3 to a specified magnitude.
	 *
	 * @param m
	 *            Magnitude to scale to.
	 * @return A new Vec3, parallel to this Vec3 and with magnitude as close to
	 *         <code>m</code> as possible.
	 * @throws IllegalStateException
	 *             If any of the components of the resulting vector are NaN.
	 */
	public Vec3 withMag(double m) {
		Vec3 v = mul(invMag() * m);
		v.m = m;
		if (Double.isNaN(v.x + v.y + v.z)) throw new IllegalStateException("NaN bug intercepted in Vec3 scaling.");
		return v;
	}

	/**
	 * @return The magnitude of this Vec3.
	 */
	public double mag() {
		if (m < 0) m = Math.sqrt(x * x + y * y + z * z);
		return m;
	}

	/**
	 * @return The multiplicative inverse of the magnitude of this Vec3.
	 */
	public double invMag() {
		if (im < 0) im = 1d / mag();
		return im;
	}

	/**
	 * Normalise this Vec3.
	 *
	 * @return A new Vec3, parallel to this Vec3 and with magnitude as close to
	 *         1 as possible.
	 * @throws IllegalStateException
	 *             If any of the components of the resulting vector are NaN.
	 */
	public Vec3 unit() {
		Vec3 v = mul(invMag());
		v.m = 1;
		v.im = 1;
		if (Double.isNaN(v.x + v.y + v.z))
			throw new IllegalStateException("NaN bug intercepted in Vec3 normalisation.");
		return v;
	}

	/**
	 * Compute the vector projection of this Vec3 onto another Vec3. If the
	 * other Vec3 is a plane normal, this will result in the rejection of this
	 * Vec3 from that plane.
	 *
	 * @param vec
	 *            Vec3 to project onto.
	 * @return The projection.
	 */
	public Vec3 project(Vec3 vec) {
		return vec.mul(this.dot(vec) / vec.dot(vec));
	}

	/**
	 * Compute the vector rejection of this Vec3 from another Vec3. If the other
	 * Vec3 is a plane normal, this will result in the projection of this Vec3
	 * onto that plane.
	 *
	 * @param vec
	 *            Vec3 to reject from.
	 * @return The rejection.
	 */
	public Vec3 reject(Vec3 vec) {
		return this.sub(vec.mul(this.dot(vec) / vec.dot(vec)));
	}

	/**
	 * @param x
	 *            New x value.
	 * @return A copy of this Vec3, except with <code>x</code> set to the
	 *         specified value.
	 */
	public Vec3 withX(double x) {
		return new Vec3(x, y, z);
	}

	/**
	 * @param y
	 *            New y value.
	 * @return A copy of this Vec3, except with <code>y</code> set to the
	 *         specified value.
	 */
	public Vec3 withY(double y) {
		return new Vec3(x, y, z);
	}

	/**
	 * @param z
	 *            New z value.
	 * @return A copy of this Vec3, except with <code>z</code> set to the
	 *         specified value.
	 */
	public Vec3 withZ(double z) {
		return new Vec3(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("(%.4f, %.4f, %.4f)", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vec3 other = (Vec3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) return false;
		return true;
	}

}
