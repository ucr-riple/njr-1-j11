package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;

import java.util.Arrays;

import nz.net.initial3d.*;

final class GeometryBufferImpl extends GeometryBuffer {

	private static final int INIT_CAPACITY = 11;

	private final int mode;
	private int[] data;
	private int count; // in primitives
	private int next; // index of next vertex

	// can put flags into vcount if needed, only need < 16 bits for actual vcount
	// [vcount, v, vt, vn, vc0, vc1]

	GeometryBufferImpl(int mode_) {
		switch (mode_) {
		case POINTS:
		case LINE_STRIPS:
		case POLYGONS:
			break;
		default:
			throw nope("Invalid enum.");
		}
		mode = mode_;
		data = new int[INIT_CAPACITY * 6];
		count = 0;
		next = 0;
	}

	private void ensureCapacity(int n) {
		if (next + 6 * n <= data.length) return;
		int length2 = data.length * 2;
		while (next + 6 * n > length2) {
			length2 *= 2;
		}
		data = Arrays.copyOf(data, length2);
	}

	@Override
	public int mode() {
		return mode;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public void clear() {
		data = new int[INIT_CAPACITY * 6];
		count = 0;
		next = 0;
	}

	private void writeVertex(int vcount, int v, int vt, int vn, int vc0, int vc1) {
		data[next] = vcount;
		data[next + 1] = v;
		data[next + 2] = vt < 0 ? 0 : vt;
		data[next + 3] = vn < 0 ? 0 : vn;
		data[next + 4] = vc0 < 0 ? 0 : vc0;
		data[next + 5] = vc1 < 0 ? 0 : vc1;
		next += 6;
	}

	@Override
	public void add(int v, int vt, int vn, int vc0, int vc1) {
		if (mode != POINTS) throw nope("Can only use single-vertex add in POINTS mode.");
		if (v < 0) throw nope("Vertex index must be valid.");
		ensureCapacity(1);
		writeVertex(1, v, vt, vn, vc0, vc1);
		count++;
	}

	private static void validate(int[] inds, int vcount, String name) {
		if (inds == null) return;
		if (inds.length != vcount) throw nope("Incorrect size for " + name + ".");
		for (int i : inds) {
			if (i < 0) throw nope("Indices in " + name + " must be valid.");
		}
	}

	@Override
	public void add(int[] v, int[] vt, int[] vn, int[] vc0, int[] vc1) {
		if (v == null) throw new NullPointerException();
		if (mode == POLYGONS && v.length < 3) throw nope("Polygon must have at least 3 vertices.");
		if (mode == LINE_STRIPS && v.length < 2) throw nope("Line strip must have at least 2 vertices.");
		if (mode == POINTS && v.length != 1) throw nope("Point must have exactly one vertex.");
		validate(v, v.length, "v");
		validate(vt, v.length, "vt");
		validate(vn, v.length, "vn");
		validate(vc0, v.length, "vc0");
		validate(vc1, v.length, "vc1");
		ensureCapacity(v.length);
		for (int i = 0; i < v.length; i++) {
			int vt_i = vt == null ? 0 : vt[i];
			int vn_i = vn == null ? 0 : vn[i];
			int vc0_i = vc0 == null ? 0 : vc0[i];
			int vc1_i = vc1 == null ? 0 : vc1[i];
			writeVertex(v.length, v[i], vt_i, vn_i, vc0_i, vc1_i);
		}
		count++;
	}

	int[] getData() {
		return data;
	}
}
