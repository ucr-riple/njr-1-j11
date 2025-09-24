package nz.net.initial3d;

public abstract class VectorBuffer {

	public abstract int count();

	public abstract void clear();

	public abstract void add(double x, double y, double z, double w);

	public abstract Vec4 get(int i);

	public void add(Vec3 v) {
		add(v, 1);
	}

	public void add(Vec3 v, double w) {
		add(v.x, v.y, v.z, w);
	}

	public void add(Vec4 v) {
		add(v.x, v.y, v.z, v.w);
	}

	public void add(Color c) {
		add(c.a, c.r, c.g, c.b);
	}

}
