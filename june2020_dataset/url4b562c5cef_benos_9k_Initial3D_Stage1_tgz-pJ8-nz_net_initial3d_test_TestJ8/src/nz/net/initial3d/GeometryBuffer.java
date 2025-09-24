package nz.net.initial3d;

public abstract class GeometryBuffer {

	public static final int POINTS = 1;
	public static final int LINE_STRIPS = 2;
	public static final int POLYGONS = 3;

	public abstract int mode();

	public abstract int count();

	public abstract void clear();

	/**
	 * Add a geometric primitive with only one vertex (convenience), i.e. a point.
	 *
	 * @param v
	 * @param vt
	 * @param vn
	 * @param vc0
	 * @param vc1
	 */
	public abstract void add(int v, int vt, int vn, int vc0, int vc1);

	/**
	 * Add a geometric primitive.
	 *
	 * @param v
	 * @param vt
	 * @param vn
	 * @param vc0
	 * @param vc1
	 */
	public abstract void add(int[] v, int[] vt, int[] vn, int[] vc0, int[] vc1);

}
