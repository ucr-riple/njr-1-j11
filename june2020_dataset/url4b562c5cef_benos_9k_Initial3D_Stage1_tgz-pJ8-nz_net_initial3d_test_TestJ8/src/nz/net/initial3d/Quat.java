package nz.net.initial3d;

/**
 * <p>
 * An immutable quaternion (for orientation / rotation).
 * </p>
 * <p>
 * Unless specified otherwise, all methods will throw <code>NullPointerException</code> if called with any argument
 * equal to <code>null</code>.
 * </p>
 * 
 * @author Ben Allen
 */
public final class Quat {

	/** The zero Quat, equal to (0,0,0,0). */
	public static final Quat zero = new Quat(0, 0, 0, 0);

	/** The (first) basis quaternion, equal to (1,0,0,0). */
	public static final Quat one = new Quat(1, 0, 0, 0);

	/** The (second) basis quaternion, equal to (0,1,0,0). */
	public static final Quat i = new Quat(0, 1, 0, 0);

	/** The (third) basis quaternion, equal to (0,0,1,0). */
	public static final Quat j = new Quat(0, 0, 1, 0);

	/** The (fourth) basis quaternion, equal to (0,0,0,1). */
	public static final Quat k = new Quat(0, 0, 0, 1);

	/** The real component. */
	public final double w;

	/** The <i>i</i> imaginary component. */
	public final double x;

	/** The <i>j</i> imaginary component. */
	public final double y;

	/** The <i>k</i> imaginary component. */
	public final double z;

	/**
	 * Construct a Quat from components.
	 * 
	 * @param w_
	 *            Real component.
	 * @param x_
	 *            <i>i</i> imaginary component.
	 * @param y_
	 *            <i>j</i> imaginary component.
	 * @param z_
	 *            <i>k</i> imaginary component.
	 */
	public Quat(double w_, double x_, double y_, double z_) {
		w = w_;
		x = x_;
		y = y_;
		z = z_;
	}

	/**
	 * Construct a Quat describing a rotation of an angle about an axis.
	 * 
	 * @param angle
	 *            Angle of rotation (radians).
	 * @param axis
	 *            Axis of rotation.
	 */
	public Quat(double angle, Vec3 axis) {
		// angle(+-2*PI) <=> w(-1) and angle(0) <=> w(1)
		axis = axis.unit();
		double sin_a = Math.sin(angle * 0.5);
		w = Math.cos(angle * 0.5);
		x = axis.x * sin_a;
		y = axis.y * sin_a;
		z = axis.z * sin_a;
	}

	/**
	 * Construct a Quat describing a rotation of an angle about an axis (represented as a single rotation vector). If
	 * the rotation vector's inverse magnitude is inifinite, the 'one' Quat will be constructed.
	 * 
	 * @param rot
	 *            Axis of rotation. Angle of rotation (radians) is equal to its magnitude.
	 */
	public Quat(Vec3 rot) {
		if (Double.isInfinite(rot.invMag())) {
			w = 1;
			x = 0;
			y = 0;
			z = 0;
		} else {
			double angle = rot.mag();
			Vec3 axis = rot.unit();
			// angle(+-2*PI) <=> w(-1) and angle(0) <=> w(1)
			double sin_a = Math.sin(angle * 0.5);
			w = Math.cos(angle * 0.5);
			x = axis.x * sin_a;
			y = axis.y * sin_a;
			z = axis.z * sin_a;
		}
	}

	/**
	 * @return The norm ( == magnitude) of this Quat.
	 */
	public double norm() {
		return Math.sqrt(w * w + x * x + y * y + z * z);
	}

	/**
	 * Scale this Quat.
	 * 
	 * @param f
	 *            Scale factor.
	 * @return A new Quat, equal to this Quat scaled by <code>f</code>.
	 */
	public Quat scale(double f) {
		return new Quat(w * f, x * f, y * f, z * f);
	}

	/**
	 * Normalise this Quat.
	 * 
	 * @return A new Quat, equal to this Quat scaled to norm 1.
	 */
	public Quat unit() {
		return scale(1d / norm());
	}

	/**
	 * @return A new Quat, equal to the conjugate of this Quat. If this Quat has norm == 1, then its conjugate is also
	 *         its inverse.
	 */
	public Quat conj() {
		return new Quat(w, -x, -y, -z);
	}

	/**
	 * @return A new Quat, equal to the multiplicative inverse of this Quat.
	 */
	public Quat inv() {
		double inorm2 = 1 / (w * w + x * x + y * y + z * z);
		return new Quat(inorm2 * w, -inorm2 * x, -inorm2 * y, -inorm2 * z);
	}

	/**
	 * Multiply this Quat by another.
	 * 
	 * @param rhs
	 *            Right-hand-side of the multiplication.
	 * @return The product.
	 */
	public Quat mul(Quat rhs) {
		return new Quat(w * rhs.w - x * rhs.x - y * rhs.y - z * rhs.z, w * rhs.x + x * rhs.w + y * rhs.z - z * rhs.y, w
				* rhs.y - x * rhs.z + y * rhs.w + z * rhs.x, w * rhs.z + x * rhs.y - y * rhs.x + z * rhs.w);
	}

	/**
	 * Multiply this Quat by another given as components.
	 * 
	 * @param rightw
	 *            W-component of right-hand-side of the multiplication.
	 * @param rightx
	 *            X-component of right-hand-side of the multiplication.
	 * @param righty
	 *            Y-component of right-hand-side of the multiplication.
	 * @param rightz
	 *            Z-component of right-hand-side of the multiplication.
	 * @return The product.
	 */
	public Quat mul(double rightw, double rightx, double righty, double rightz) {
		return new Quat(w * rightw - x * rightx - y * righty - z * rightz, w * rightx + x * rightw + y * rightz - z
				* righty, w * righty - x * rightz + y * rightw + z * rightx, w * rightz + x * righty - y * rightx + z
				* rightw);
	}

	/**
	 * Multiply this Quat by another.
	 * 
	 * @param lhs
	 *            Left-hand-side of the multiplication.
	 * @return The product.
	 */
	public Quat lmul(Quat lhs) {
		return new Quat(lhs.w * w - lhs.x * x - lhs.y * y - lhs.z * z, lhs.w * x + lhs.x * w + lhs.y * z - lhs.z * y,
				lhs.w * y - lhs.x * z + lhs.y * w + lhs.z * x, lhs.w * z + lhs.x * y - lhs.y * x + lhs.z * w);
	}

	/**
	 * Multiply this Quat by another given as components.
	 * 
	 * @param leftw
	 *            W-component of left-hand-side of the multiplication.
	 * @param leftx
	 *            X-component of left-hand-side of the multiplication.
	 * @param lefty
	 *            Y-component of left-hand-side of the multiplication.
	 * @param leftz
	 *            Z-component of left-hand-side of the multiplication.
	 * @return The product.
	 */
	public Quat lmul(double leftw, double leftx, double lefty, double leftz) {
		return new Quat(leftw * w - leftx * x - lefty * y - leftz * z, leftw * x + leftx * w + lefty * z - leftz * y,
				leftw * y - leftx * z + lefty * w + leftz * x, leftw * z + leftx * y - lefty * x + leftz * w);
	}

	/**
	 * Rotate a Vec3 by the rotation described by this Quat. Assumes this Quat has norm == 1 (because in this case, a
	 * quaternion's conjugate is also its inverse).
	 * 
	 * @param v
	 *            Vec3 to rotate.
	 * @return The rotated Vec3.
	 */
	public Vec3 rot(Vec3 v) {
		// this * v * this^(-1)
		Quat q = this.mul(0, v.x, v.y, v.z).mul(this.conj());
		return new Vec3(q.x, q.y, q.z);
	}

	/**
	 * Get the angle of the rotation transform described by this Quat. This method will never return a negative number.
	 * Note that a negative rotation about one axis is a positive rotation about the opposite axis.
	 * 
	 * @return The angle (in radians).
	 */
	public double angle() {
		return 2 * Math.acos(w / norm());
	}

	/**
	 * Get the axis of the rotation transform described by this Quat. Note that a negative rotation about one axis is a
	 * positive rotation about the opposite axis.
	 * 
	 * @return The axis as a unit Vec3.
	 */
	public Vec3 axis() {
		double ivm = 1 / Math.sqrt(x * x + y * y + z * z);
		return new Vec3(ivm * x, ivm * y, ivm * z);
	}

	/**
	 * Raise this Quat to an arbitrary real exponent.
	 * 
	 * @param alpha
	 *            Exponent.
	 * @return The resulting Quat.
	 */
	public Quat pow(double alpha) {
		double qm = norm();
		double theta = Math.acos(w / qm);
		double ivm = 1 / Math.sqrt(x * x + y * y + z * z);
		double ivmalphatheta = ivm * alpha * theta;
		Quat p = new Quat(0, x * ivmalphatheta, y * ivmalphatheta, z * ivmalphatheta);
		return p.exp().scale(Math.pow(qm, alpha));
	}

	/**
	 * @return e (the natural logarithm base) to the power of this Quat.
	 */
	public Quat exp() {
		double expw = Math.exp(w);
		double vm = Math.sqrt(x * x + y * y + z * z);
		double ivm = 1 / vm;
		if (Double.isInfinite(ivm)) {
			return new Quat(expw * Math.cos(vm), 0, 0, 0);
		} else {
			double vf = expw * Math.sin(vm) * ivm;
			return new Quat(expw * Math.cos(vm), x * vf, y * vf, z * vf);
		}
	}

	/**
	 * @return The natural logarithm of this Quat.
	 */
	public Quat log() {
		double qm = norm();
		double vf = Math.acos(w / qm) / Math.sqrt(x * x + y * y + z * z);
		return new Quat(Math.log(qm), x * vf, y * vf, z * vf);
	}

	/**
	 * Add this Quat to another.<br>
	 * <br>
	 * <b>WARNING</b>: Quaternion addition <i>is not used to compose rotations!</i> Multiplication is used for that.
	 * 
	 * @param rhs
	 *            Right-hand-side of the addition.
	 * @return The sum.
	 */
	public Quat add(Quat rhs) {
		return new Quat(w + rhs.w, x + rhs.x, y + rhs.y, z + rhs.z);
	}

	/**
	 * Subtract a Quat from this Quat.
	 * 
	 * @param rhs
	 *            Right-hand-side of the subtraction.
	 * @return The difference.
	 */
	public Quat sub(Quat rhs) {
		return new Quat(w - rhs.w, x - rhs.x, y - rhs.y, z - rhs.z);
	}

	/**
	 * Get the distance from this Quat to another, defined as the norm of their difference.
	 * 
	 * @param q
	 *            Other Quat.
	 * @return The distance.
	 */
	public double dist(Quat q) {
		double dw = w - q.w;
		double dx = x - q.x;
		double dy = y - q.y;
		double dz = z - q.z;
		return Math.sqrt(dw * dw + dx * dx + dy * dy + dz * dz);
	}

	/**
	 * Interpolate two Quats by <b>S</b>pherical <b>L</b>inear Int<b>erp</b>olation. Delivers smoother results (constant
	 * angular velocity) than <code>nlerp</code>, but is more computationally intensive.
	 * 
	 * TODO does quaternion slerp always take the 'short' way?
	 * 
	 * @param q0
	 *            Start of interpolation range, at <code>t == 0</code>. Must be normalised.
	 * @param q1
	 *            End of interpolation range, at <code>t == 1</code>. Must be normalised.
	 * @param t
	 *            Position in interpolation range.
	 * @return The interpolated Quat.
	 */
	public static final Quat slerp(Quat q0, Quat q1, double t) {
		// magic... not very optimised magic
		// FIXME ensure slerp takes a <= pi path
		return q1.mul(q0.conj()).pow(t).mul(q0);
	}

	/**
	 * Interpolate two Quats by <b>N</b>ormalised <b>L</b>inear Int<b>erp</b>olation. Delivers rougher results
	 * (non-constant angular velocity) than <code>slerp</code>, but is computationally cheaper.
	 * 
	 * TODO does quaternion nlerp always take the 'short' way?
	 * 
	 * @param q0
	 *            Start of interpolation range, at <code>t == 0</code>. Must be normalised.
	 * @param q1
	 *            End of interpolation range, at <code>t == 1</code>. Must be normalised.
	 * @param t
	 *            Position in interpolation range.
	 * @return The interpolated Quat.
	 */
	public static final Quat nlerp(Quat q0, Quat q1, double t) {
		double u = 1 - t;
		return new Quat(q0.w * u + q1.w * t, q0.x * u + q1.x * t, q0.y * u + q1.y * t, q0.z * u + q1.z * t).unit();
	}

	@Override
	public String toString() {
		return String.format("(%.4f, %.4f, %.4f, %.4f)", w, x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(w);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Quat other = (Quat) obj;
		if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w)) return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) return false;
		return true;
	}

}
