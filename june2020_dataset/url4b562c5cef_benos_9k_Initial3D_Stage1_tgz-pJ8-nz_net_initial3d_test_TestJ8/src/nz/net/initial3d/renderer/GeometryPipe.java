package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import static nz.net.initial3d.renderer.Type.*;
import sun.misc.Unsafe;

final class GeometryPipe {

	// FIXME add primitive type flags to each primitive
	// implications:
	//  -	remove geommode, rastermode
	//  -	addPoint(), addLine(), addLineStrip(), addLineLoop(), addPolygon() in GeometryBuffer
	//  -	DisplayLists... ?!

	static final int POINTS = 1;
	static final int LINE_STRIPS = 2;
	static final int POLYGONS = 3;

	private static final Unsafe unsafe = getUnsafe();

	private final long pPolyTemp;

	private RasterPipe rasterpipe;

	GeometryPipe() {
		pPolyTemp = unsafe.allocateMemory(vertex_t.SIZEOF() * 2048);
	}

	@Override
	protected void finalize() {
		unsafe.freeMemory(pPolyTemp);
	}

	void connectRasterPipe(RasterPipe rp) {
		rasterpipe = rp;
	}

	void finish() {
		// nothing to do here, just finish the raster pipe
		rasterpipe.finish();
	}

	/**
	 * This pipe is synchronous so can use client-side state.
	 */
	void feed(Initial3DImpl.State state, int mode, int[] data, int offset, int count) {
		final long pBase = state.pBase;
		int rastermode = 9001;
		switch (mode) {
		case POINTS:
			rastermode = RasterPipe.POINTS;
			break;
		case LINE_STRIPS:
			rastermode = RasterPipe.LINES;
			break;
		case POLYGONS:
			rastermode = RasterPipe.TRIANGLES;
			break;
		default:
			throw nope("Bad geometry mode.");
		}

		// allocate memory to hold unsafe state, all transformed vertex data, any vertex data
		// generated in-pipe and generated raster primitives

		// v, vt, vn, vv, vc0, vc1

		// FIXME geompipe
		Buffer buf = Buffer.alloc(9001);

		// copy unsafe renderer state into buffer
		// basically copy from pBase into start of buffer

		// transform data

		// for all polys:
		// -- plane cull
		// -- face cull
		// -- light
		// -- clip
		// -- triangulate

		// feed buffer to raster pipe
		rasterpipe.feed(rastermode, buf, 0, 0, state.bound_framebuffer.obj_color0);
	}

}
