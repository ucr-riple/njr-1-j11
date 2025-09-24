package nz.net.initial3d;

/**
 * <p>
 * An immutable 4-dimensional vector, usually in the context of a 3-dimensional vector with an added homogeneous
 * component.
 * </p>
 * <p>
 * Unless specified otherwise, all methods will throw <code>NullPointerException</code> if called with any argument
 * equal to <code>null</code>.
 * </p>
 * 
 * @author Ben Allen
 */
public final class Vec4 {

	public final double x;
	public final double y;
	public final double z;
	public final double w;

	public Vec4(double x_, double y_, double z_, double w_) {
		x = x_;
		y = y_;
		z = z_;
		w = w_;
	}

	public Vec4(Vec3 xyz_, double w_) {
		x = xyz_.x;
		y = xyz_.y;
		z = xyz_.z;
		w = w_;
	}

	public Vec4(Vec3 xyz_) {
		this(xyz_, 1);
	}

	public Vec3 xyz() {
		return new Vec3(x, y, z);
	}

	public Vec4 homogenise() {
		double iw = 1 / w;
		return new Vec4(x * iw, y * iw, z * iw, 1);
	}

}
