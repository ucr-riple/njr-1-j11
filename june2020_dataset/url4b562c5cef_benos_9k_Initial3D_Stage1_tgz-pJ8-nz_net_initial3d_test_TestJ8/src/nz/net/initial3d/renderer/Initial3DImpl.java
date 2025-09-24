package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import static nz.net.initial3d.renderer.Type.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import nz.net.initial3d.*;
import sun.misc.Unsafe;

public final class Initial3DImpl extends Initial3D {

	private static final Unsafe unsafe = getUnsafe();

	// exposed methods and enum constants
	private static final Set<String> exposed_methods = new HashSet<String>();
	private static final Set<String> exposed_enums = new HashSet<String>();

	// map api enums to enable bits
	private static final Map<Integer, Integer> enable_bits = new HashMap<Integer, Integer>();

	// enum constants
	public static final int ALPHAREF_RANDOM = 999;
	public static final int AUTO_FLIP_ZSIGN = 998;
	public static final int I3DX_FOG_A = 11199;
	public static final int I3DX_FOG_B = 11198;

	// pipes
	private final GeometryPipe geompipe;
	private final RasterPipe rasterpipe;

	// defaults
	private final FrameBufferImpl default_framebuffer;
	private final Texture2DImpl default_tex;
	private final VectorBufferImpl default_vbo, default_vbo_color;

	// state
	Stack<State> state = new Stack<State>();

	static {
		// expose methods
		exposed_methods.add("flipZSign");
		exposed_methods.add("initFog");
		// expose enum constants
		exposed_enums.add("I3DX_FOG_A");
		exposed_enums.add("I3DX_FOG_B");
		exposed_enums.add("ALPHAREF_RANDOM");
		exposed_enums.add("AUTO_FLIP_ZSIGN");
		// enum constants -> bit flags
		enable_bits.put(BUFFER_COLOR0, 0x1);
		enable_bits.put(BUFFER_COLOR1, 0x2);
		enable_bits.put(BUFFER_Z, 0x4);
		enable_bits.put(BUFFER_STENCIL, 0x8);
		enable_bits.put(BUFFER_ID, 0x10);
		enable_bits.put(SCISSOR_TEST, 0x20);
		enable_bits.put(ALPHA_TEST, 0x40);
		enable_bits.put(DEPTH_TEST, 0x80);
		enable_bits.put(STENCIL_TEST, 0x100);
		enable_bits.put(CULL_FACE, 0x200);
		enable_bits.put(BLEND, 0x400);
		enable_bits.put(FOG, 0x800);
		enable_bits.put(LIGHTING, 0x1000);
		enable_bits.put(TWO_SIDED_LIGHTING, 0x2000);
		enable_bits.put(TEXTURE_2D, 0x4000);
		enable_bits.put(MIPMAPS, 0x8000);
		enable_bits.put(COLOR_SUM, 0x10000);
		enable_bits.put(SEPARATE_SPECULAR, 0x20000);
		enable_bits.put(ALPHAREF_RANDOM, 0x40000);
		enable_bits.put(AUTO_FLIP_ZSIGN, 0x80000);
	}

	class State {

		final Buffer buf_base;
		final long pBase;

		FrameBufferImpl bound_framebuffer;
		VectorBufferImpl bound_vbo_v, bound_vbo_vt, bound_vbo_vn, bound_vbo_c0, bound_vbo_c1;
		int begin_mode = 0;
		final VectorBufferImpl begin_vbo_v, begin_vbo_vt, begin_vbo_vn, begin_vbo_c0, begin_vbo_c1;

		// vertex array structs
		// [vcount, v, vt, vn, vc0, vc1]
		final int[] protogeom = new int[6144];
		final int[] protoprim = new int[6144];
		// index of next primitive
		int prim_next = 0;
		// number of primitives
		int prim_count = 0;
		// index of next vertex
		int vert_next = 0;
		// number of vertices
		int vert_count = 0;

		final Stack<Mat4> modelview;
		final Stack<Mat4> projection;
		Stack<Mat4> matrix_active;
		int matrix_mode;

		State() {
			// memory obtained here is not zeroed!
			buf_base = Buffer.alloc(i3d_t.SIZEOF(), -1);
			pBase = buf_base.getPointer();

			// init the unsafe state (stuff that's not enabled is left uninitialised)
			enable(BUFFER_COLOR0);
			enable(BUFFER_Z);
			enable(DEPTH_TEST);
			enable(CULL_FACE);
			projectionMode(ORTHOGRAPHIC);
			shadeModel(FLAT);
			polygonMode(FRONT_AND_BACK, FILL);
			cullFace(BACK);
			nearClip(0.1);
			farCull(100);
			material(FRONT_AND_BACK, AMBIENT, 0, 0, 0, 1);
			material(FRONT_AND_BACK, DIFFUSE, 1, 1, 1, 1);
			material(FRONT_AND_BACK, SPECULAR, 0, 0, 0, 1);
			material(FRONT_AND_BACK, EMISSION, 0, 0, 0, 1);
			sceneAmbient(0, 0, 0, 0);
			depthFunc(LEQUAL);

			// bind default framebuffer
			bound_framebuffer = default_framebuffer;

			// bind default vector buffers
			bound_vbo_v = default_vbo;
			bound_vbo_vt = default_vbo;
			bound_vbo_vn = default_vbo;
			bound_vbo_c0 = default_vbo_color;
			bound_vbo_c1 = default_vbo_color;

			// create vbos used by begin / end
			begin_vbo_v = new VectorBufferImpl();
			begin_vbo_vt = new VectorBufferImpl();
			begin_vbo_vn = new VectorBufferImpl();
			begin_vbo_c0 = new VectorBufferImpl();
			begin_vbo_c1 = new VectorBufferImpl();

			// setup matrices
			modelview = new Stack<Mat4>();
			projection = new Stack<Mat4>();
			matrix_active = modelview;
			matrix_mode = MODELVIEW;
			modelview.push(new Mat4());
			projection.push(new Mat4());
		}

		State(State other_) {
			buf_base = Buffer.alloc(i3d_t.SIZEOF(), -1);
			pBase = buf_base.getPointer();

			// copy unsafe state directly
			unsafe.copyMemory(other_.pBase, pBase, i3d_t.SIZEOF());

			// bind framebuffer
			bound_framebuffer = other_.bound_framebuffer;

			// bind vector buffers
			bound_vbo_v = other_.bound_vbo_v;
			bound_vbo_vt = other_.bound_vbo_vt;
			bound_vbo_vn = other_.bound_vbo_vn;
			bound_vbo_c0 = other_.bound_vbo_c0;
			bound_vbo_c1 = other_.bound_vbo_c1;

			// copy stuff used by begin / end
			begin_vbo_v = new VectorBufferImpl(other_.begin_vbo_v);
			begin_vbo_vt = new VectorBufferImpl(other_.begin_vbo_vt);
			begin_vbo_vn = new VectorBufferImpl(other_.begin_vbo_vn);
			begin_vbo_c0 = new VectorBufferImpl(other_.begin_vbo_c0);
			begin_vbo_c1 = new VectorBufferImpl(other_.begin_vbo_c1);
			begin_mode = other_.begin_mode;
			System.arraycopy(other_.protogeom, 0, protogeom, 0, protogeom.length);
			System.arraycopy(other_.protoprim, 0, protoprim, 0, protoprim.length);
			prim_next = other_.prim_next;
			prim_count = other_.prim_count;
			vert_next = other_.vert_next;
			vert_count = other_.vert_count;

			// copy matrices
			modelview = new Stack<Mat4>();
			for (Mat4 m : other_.modelview) {
				modelview.push(m.clone());
			}
			projection = new Stack<Mat4>();
			for (Mat4 m : other_.projection) {
				projection.push(m.clone());
			}
			// activate the correct matrix stack
			matrixMode(other_.matrix_mode);
		}

		@Override
		protected void finalize() {
			buf_base.release();
		}

		private void genClipFunc(Mat4 inv_p, Vec3 p0, Vec3 p1, Vec3 p2, long pClipFunc) {
			// the side with the acw normal is the 'keep' side
			// transform
			p0 = inv_p.mul(p0);
			p1 = inv_p.mul(p1);
			p2 = inv_p.mul(p2);
			// calculate new normal
			Vec3 n = Vec3.planeNorm(p0, p1, p2);
			// eval clip func at one point
			double cutoff = n.dot(p0);
			writeVector(unsafe, pClipFunc, n.x, n.y, n.z, cutoff);
		}

		private void loadUnsafeState() {
			// this shouldn't require pipeline to be finished
			// load the 'unsafe' parts of the state that depend on 'safe' parts

			// copy matrices
			// TODO optimise matrix re-calculation
			Mat4 mv = modelview.peek();
			Mat4 p = projection.peek();
			Mat4 n = mv.inv().xpose();
			// texture matrix not exposed yet
			Mat4 t = new Mat4();
			writeMat(unsafe, pBase + i3d_t.mat_mv(), mv);
			writeMat(unsafe, pBase + i3d_t.mat_p(), p);
			writeMat(unsafe, pBase + i3d_t.mat_mvp(), p.mul(mv));
			writeMat(unsafe, pBase + i3d_t.mat_inv_mv(), mv.inv());
			writeMat(unsafe, pBase + i3d_t.mat_inv_p(), p.inv());
			writeMat(unsafe, pBase + i3d_t.mat_inv_mvp(), (p.mul(mv)).inv());
			writeMat(unsafe, pBase + i3d_t.mat_n(), n);
			writeMat(unsafe, pBase + i3d_t.mat_inv_n(), n.inv());
			writeMat(unsafe, pBase + i3d_t.mat_t(), t);
			writeMat(unsafe, pBase + i3d_t.mat_inv_t(), t.inv());

			// gen clipfuncs for sides of view frustum
			genClipFunc(p.inv(), new Vec3(1, 0, 0), new Vec3(1, 0, 1), new Vec3(1, 1, 1), pBase + i3d_t.clip_left());
			genClipFunc(p.inv(), new Vec3(-1, 0, 0), new Vec3(-1, 1, 0), new Vec3(-1, 1, 1), pBase + i3d_t.clip_right());
			genClipFunc(p.inv(), new Vec3(0, 1, 0), new Vec3(1, 1, 0), new Vec3(1, 1, 1), pBase + i3d_t.clip_top());
			genClipFunc(p.inv(), new Vec3(0, -1, 0), new Vec3(-1, -1, 0), new Vec3(-1, -1, 1),
					pBase + i3d_t.clip_bottom());

			// copy info from framebuffer
			bound_framebuffer.writeUnsafeState(pBase + i3d_t.framebuf());
		}

		void bindFrameBuffer(FrameBuffer fb) {
			if (fb == null) {
				bound_framebuffer = default_framebuffer;
			} else {
				bound_framebuffer = (FrameBufferImpl) fb;
			}
		}

		FrameBuffer getFrameBuffer() {
			return bound_framebuffer;
		}

		void bindTexture(int target, Texture2D tex) {
			if (tex == null) tex = default_tex;
			long pTex = ((Texture2DImpl) tex).pTex;
			// set texture for front and back mtl
			switch (target) {
			case TEXTURE_2D_KD:
				unsafe.putAddress(pBase + i3d_t.mtl_front() + material_t.pMap_kd(), pTex);
				unsafe.putAddress(pBase + i3d_t.mtl_back() + material_t.pMap_kd(), pTex);
				break;
			case TEXTURE_2D_KS:
				unsafe.putAddress(pBase + i3d_t.mtl_front() + material_t.pMap_ks(), pTex);
				unsafe.putAddress(pBase + i3d_t.mtl_back() + material_t.pMap_ks(), pTex);
				break;
			case TEXTURE_2D_KE:
				unsafe.putAddress(pBase + i3d_t.mtl_front() + material_t.pMap_ke(), pTex);
				unsafe.putAddress(pBase + i3d_t.mtl_back() + material_t.pMap_ke(), pTex);
				break;
			default:
				throw nope("Invalid enum.");
			}

		}

		void enable(int... caps) {
			for (int cap : caps) {
				Integer bit = enable_bits.get(cap);
				if (bit != null) {
					int flags = unsafe.getInt(pBase + i3d_t.flags0());
					flags |= bit;
					unsafe.putInt(pBase + i3d_t.flags0(), flags);
				} else {
					throw nope("Invalid enum.");
				}
			}
		}

		void disable(int... caps) {
			for (int cap : caps) {
				Integer bit = enable_bits.get(cap);
				if (bit != null) {
					int flags = unsafe.getInt(pBase + i3d_t.flags0());
					flags &= ~bit;
					unsafe.putInt(pBase + i3d_t.flags0(), flags);
				} else {
					throw nope("Invalid enum.");
				}
			}
		}

		boolean isEnabled(int cap) {
			try {
				int bit = enable_bits.get(cap);
				int flags = unsafe.getInt(pBase + i3d_t.flags0());
				return (flags & bit) == bit;
			} catch (NullPointerException e) {
				throw nope("Invalid enum.");
			}
		}

		void projectionMode(int mode) {
			switch (mode) {
			case ORTHOGRAPHIC:
				unsafe.putInt(pBase + i3d_t.projection_mode(), 0);
				break;
			case PERSPECTIVE:
				unsafe.putInt(pBase + i3d_t.projection_mode(), 1);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		void polygonMode(int face, int mode) {
			switch (mode) {
			case POINT:
				mode = 0;
				break;
			case LINE:
				mode = 1;
				break;
			case FILL:
				mode = 2;
				break;
			default:
				throw nope("Invalid enum.");
			}
			switch (face) {
			case FRONT:
				unsafe.putInt(pBase + i3d_t.polymode_front(), mode);
				break;
			case BACK:
				unsafe.putInt(pBase + i3d_t.polymode_back(), mode);
				break;
			case FRONT_AND_BACK:
				unsafe.putInt(pBase + i3d_t.polymode_front(), mode);
				unsafe.putInt(pBase + i3d_t.polymode_back(), mode);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		void shadeModel(int model) {
			switch (model) {
			case FLAT:
				unsafe.putInt(pBase + i3d_t.shade_model(), 0);
				break;
			case SMOOTH:
				unsafe.putInt(pBase + i3d_t.shade_model(), 1);
				break;
			case PHONG:
				throw nope("Phong shading not supported (yet).");
			default:
				throw nope("Invalid enum");
			}
		}

		void viewport(int x, int y, int w, int h) {
			// can do this without finish()
			bound_framebuffer.viewport(x, y, w, h);
		}

		void clear(int... buffers) {
			finish();
			for (int buffer : buffers) {
				bound_framebuffer.clear(buffer);
			}
		}

		void flipZSign() {
			// it should be possible to do this without a finish(), but only if
			// draw calls in progress at any one time span no more than one
			// z-flip. so let's leave that for the moment.
			finish();
			bound_framebuffer.setZSign(bound_framebuffer.getZSign() * -1);
		}

		void bindVertexBuffer(int att, VectorBuffer vbuf) {
			if (vbuf == null) {
				// revert to default
				switch (att) {
				case VERTEX_POSITION:
					bound_vbo_v = default_vbo;
					break;
				case VERTEX_TEXCOORD:
					bound_vbo_vt = default_vbo;
					break;
				case VERTEX_NORMAL:
					bound_vbo_vn = default_vbo;
					break;
				case VERTEX_COLOR0:
					bound_vbo_c0 = default_vbo_color;
					break;
				case VERTEX_COLOR1:
					bound_vbo_c1 = default_vbo_color;
					break;
				default:
					throw nope("Invalid enum.");
				}
			} else {
				// bind new
				switch (att) {
				case VERTEX_POSITION:
					bound_vbo_v = (VectorBufferImpl) vbuf;
					break;
				case VERTEX_TEXCOORD:
					bound_vbo_vt = (VectorBufferImpl) vbuf;
					break;
				case VERTEX_NORMAL:
					bound_vbo_vn = (VectorBufferImpl) vbuf;
					break;
				case VERTEX_COLOR0:
					bound_vbo_c0 = (VectorBufferImpl) vbuf;
					break;
				case VERTEX_COLOR1:
					bound_vbo_c1 = (VectorBufferImpl) vbuf;
					break;
				default:
					throw nope("Invalid enum.");
				}
			}
		}

		void drawGeometry(GeometryBuffer gbuf, int offset, int count) {
			loadUnsafeState();
			if (offset + count > gbuf.count()) throw new IndexOutOfBoundsException();
			int[] data = ((GeometryBufferImpl) gbuf).getData();
			geompipe.feed(this, gbuf.mode(), data, offset, count);
		}

		void begin(int mode) {
			// reset proto-geometry
			prim_next = 0;
			prim_count = 0;
			vert_next = 0;
			vert_count = 0;
			// clear buffers
			begin_vbo_v.clear();
			begin_vbo_vt.clear();
			begin_vbo_vn.clear();
			begin_vbo_c0.clear();
			begin_vbo_c1.clear();
			// need valid first vector
			begin_vbo_v.add(0, 0, 0, 0);
			begin_vbo_vt.add(0, 0, 0, 0);
			begin_vbo_vn.add(0, 0, 0, 0);
			begin_vbo_c0.add(1, 1, 1, 1);
			begin_vbo_c1.add(1, 1, 1, 1);
			// sanitise mode
			switch (mode) {
			case POINTS:
			case LINES:
			case LINE_STRIP:
			case LINE_LOOP:
			case TRIANGLES:
			case TRIANGLE_STRIP:
			case TRIANGLE_FAN:
			case QUADS:
			case QUAD_STRIP:
			case POLYGON:
				// a-ok
				break;
			default:
				throw nope("Invalid enum.");
			}
			begin_mode = mode;
		}

		private void primitive(int... v) {
			int gvi = prim_next;
			for (int i = 0; i < v.length; i++, gvi += 6) {
				int vi = v[i] * 6 + v[i] < 0 ? vert_next : 0;
				protogeom[gvi] = 0;
				protogeom[gvi + 1] = protoprim[vi + 1];
				protogeom[gvi + 2] = protoprim[vi + 2];
				protogeom[gvi + 3] = protoprim[vi + 3];
				protogeom[gvi + 4] = protoprim[vi + 4];
				protogeom[gvi + 5] = protoprim[vi + 5];
			}
			// write actual vcount
			protogeom[prim_next] = v.length;
			prim_next = gvi;
			// no resetting vert_next (protoprim accumulates all vertices)
			vert_count = 0;
		}

		void vertex(double x, double y, double z) {
			begin_vbo_v.add(x, y, z, 1);
			// bind to previously set normal / texcoord / etc.
			protoprim[vert_next] = 0;
			protoprim[vert_next + 1] = begin_vbo_v.count() - 1;
			protoprim[vert_next + 2] = begin_vbo_vt.count() - 1;
			protoprim[vert_next + 3] = begin_vbo_vn.count() - 1;
			protoprim[vert_next + 4] = begin_vbo_c0.count() - 1;
			protoprim[vert_next + 5] = begin_vbo_c1.count() - 1;
			vert_next += 6;
			vert_count++;
			switch (begin_mode) {
			case POINTS:
				primitive(-1);
				break;
			case LINES:
				if (vert_count >= 2) {
					primitive(-2, -1);
				}
				break;
			case LINE_STRIP:
				// primitive construction on end()
				break;
			case LINE_LOOP:
				// primitive construction on end()
				break;
			case TRIANGLES:
				if (vert_count >= 3) {
					primitive(-3, -2, -1);
				}
				break;
			case TRIANGLE_STRIP:
				if (vert_count >= 3) {
					// initial tri
					primitive(-3, -2, -1);
				} else if (prim_count >= 1 && prim_count % 2 == 0) {
					// acw
					primitive(-3, -2, -1);
				} else if (prim_count >= 1 && prim_count % 2 == 1) {
					// cw
					primitive(-2, -3, -1);
				}
				break;
			case TRIANGLE_FAN:
				if (vert_count >= 3) {
					// initial tri
					primitive(-3, -2, -1);
				} else if (prim_count >= 1) {
					primitive(0, -2, -1);
				}
				break;
			case QUADS:
				if (vert_count >= 4) {
					primitive(-4, -3, -2, -1);
				}
				break;
			case QUAD_STRIP:
				if (vert_count >= 4) {
					// initial quad
					primitive(-4, -3, -1, -2);
				} else if (vert_count >= 2 && prim_count >= 1) {
					primitive(-4, -3, -1, -2);
				}
				break;
			case POLYGON:
				// primitive construction on end()
				break;
			default:
				throw nope("Bad begin mode.");
			}
		}

		void normal(double x, double y, double z) {
			begin_vbo_vn.add(x, y, z, 0);
		}

		void color(double r, double g, double b, double a) {
			begin_vbo_c0.add(a, r, g, b);
		}

		void secondaryColor(double r, double g, double b, double a) {
			begin_vbo_c1.add(a, r, g, b);
		}

		void texCoord(double s, double t, double r, double q) {
			begin_vbo_vt.add(s, t, r, q);
		}

		void end() {
			loadUnsafeState();
			int geommode = 9001;
			int[] v;
			switch (begin_mode) {
			case POINTS:
				geommode = GeometryPipe.POINTS;
				break;
			case LINES:
				geommode = GeometryPipe.LINE_STRIPS;
				break;
			case LINE_STRIP:
				// construct primitive
				v = new int[vert_count];
				for (int i = 0; i < v.length; i++)
					v[i] = i;
				primitive(v);
				geommode = GeometryPipe.LINE_STRIPS;
				break;
			case LINE_LOOP:
				// construct primitive
				v = new int[vert_count + 1];
				for (int i = 0; i < v.length; i++)
					v[i] = i;
				primitive(v);
				geommode = GeometryPipe.LINE_STRIPS;
				break;
			case TRIANGLES:
				geommode = GeometryPipe.POLYGONS;
				break;
			case TRIANGLE_STRIP:
				geommode = GeometryPipe.POLYGONS;
				break;
			case TRIANGLE_FAN:
				geommode = GeometryPipe.POLYGONS;
				break;
			case QUADS:
				geommode = GeometryPipe.POLYGONS;
				break;
			case QUAD_STRIP:
				geommode = GeometryPipe.POLYGONS;
				break;
			case POLYGON:
				// construct primitive
				v = new int[vert_count];
				for (int i = 0; i < v.length; i++)
					v[i] = i;
				primitive(v);
				geommode = GeometryPipe.POLYGONS;
				break;
			default:
				throw nope("Bad begin mode.");
			}
			geompipe.feed(this, geommode, protogeom, 0, prim_count);
			begin_mode = 0;
		}

		void material(int face, int param, double f) {
			int field = 0;
			switch (param) {
			case SHININESS:
				field = material_t.ks_a_shininess();
				break;
			case OPACITY:
				field = material_t.kd_a_opacity();
				break;
			default:
				throw nope("Invalid enum");
			}
			switch (face) {
			case FRONT:
				unsafe.putFloat(pBase + i3d_t.mtl_front() + field, (float) f);
				break;
			case BACK:
				unsafe.putFloat(pBase + i3d_t.mtl_back() + field, (float) f);
				break;
			case FRONT_AND_BACK:
				unsafe.putFloat(pBase + i3d_t.mtl_front() + field, (float) f);
				unsafe.putFloat(pBase + i3d_t.mtl_back() + field, (float) f);
				break;
			default:
				throw nope("Invalid enum");
			}
		}

		void material(int face, int param, double r, double g, double b, double a) {
			int field = 0;
			switch (param) {
			case AMBIENT:
				field = material_t.ka_a_unused();
				break;
			case DIFFUSE:
				field = material_t.kd_a_opacity();
				break;
			case SPECULAR:
				field = material_t.ks_a_shininess();
				break;
			case EMISSION:
				field = material_t.ke_a_unused();
				break;
			default:
				throw nope("Invalid enum.");
			}
			switch (face) {
			case FRONT:
				writeVector_float(unsafe, pBase + i3d_t.mtl_front() + field, (float) a, (float) r, (float) g, (float) b);
				break;
			case BACK:
				writeVector_float(unsafe, pBase + i3d_t.mtl_back() + field, (float) a, (float) r, (float) g, (float) b);
				break;
			case FRONT_AND_BACK:
				writeVector_float(unsafe, pBase + i3d_t.mtl_front() + field, (float) a, (float) r, (float) g, (float) b);
				writeVector_float(unsafe, pBase + i3d_t.mtl_back() + field, (float) a, (float) r, (float) g, (float) b);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		private int blendFactorSanitise(int factor) {
			switch (factor) {
			case ZERO:
				return 0;
			case ONE:
				return 1;
			case SRC_COLOR:
				return 2;
			case ONE_MINUS_SRC_COLOR:
				return 3;
			case DST_COLOR:
				return 4;
			case ONE_MINUS_DST_COLOR:
				return 5;
			case SRC_ALPHA:
				return 6;
			case ONE_MINUS_SRC_ALPHA:
				return 7;
			case DST_ALPHA:
				return 8;
			case ONE_MINUS_DST_ALPHA:
				return 9;
			default:
				throw nope("Invalid enum.");
			}
		}

		void blendFunc(int func, int sfactor, int dfactor) {
			switch (func) {
			case FUNC_ADD:
				func = 0;
				break;
			case FUNC_SUBTRACT:
				func = 1;
				break;
			case FUNC_REVERSE_SUBTRACT:
				func = 2;
				break;
			case FUNC_MIN:
				func = 3;
				break;
			case FUNC_MAX:
				func = 4;
				break;
			default:
				throw nope("Invalid enum.");
			}
			sfactor = blendFactorSanitise(sfactor);
			dfactor = blendFactorSanitise(dfactor);
			// set for front and back
			unsafe.putInt(pBase + i3d_t.blend_func_front_mode(), func);
			unsafe.putInt(pBase + i3d_t.blend_func_front_sfactor(), sfactor);
			unsafe.putInt(pBase + i3d_t.blend_func_front_dfactor(), dfactor);
			unsafe.putInt(pBase + i3d_t.blend_func_back_mode(), func);
			unsafe.putInt(pBase + i3d_t.blend_func_back_sfactor(), sfactor);
			unsafe.putInt(pBase + i3d_t.blend_func_back_dfactor(), dfactor);
		}

		private int compareFuncSanitise(int func) {
			switch (func) {
			case NEVER:
				return 0;
			case LESS:
				return 1;
			case LEQUAL:
				return 2;
			case GREATER:
				return 3;
			case GEQUAL:
				return 4;
			case EQUAL:
				return 5;
			case NOTEQUAL:
				return 6;
			case ALWAYS:
				return 7;
			default:
				throw nope("Invalid enum.");
			}
		}

		void alphaFunc(int func, double ref) {
			func = compareFuncSanitise(func);
			unsafe.putInt(pBase + i3d_t.alpha_func(), func);
			unsafe.putFloat(pBase + i3d_t.alpha_ref(), (float) ref);
		}

		void depthFunc(int func) {
			func = compareFuncSanitise(func);
			unsafe.putInt(pBase + i3d_t.depth_func(), func);
		}

		void stencilFuncSeparate(int face, int func, int ref, int mask) {
			func = compareFuncSanitise(func);
			switch (face) {
			case FRONT:
				unsafe.putInt(pBase + i3d_t.stencil_func_front(), func);
				unsafe.putInt(pBase + i3d_t.stencil_func_front_ref(), ref);
				unsafe.putInt(pBase + i3d_t.stencil_func_front_mask(), mask);
				break;
			case BACK:
				unsafe.putInt(pBase + i3d_t.stencil_func_back(), func);
				unsafe.putInt(pBase + i3d_t.stencil_func_back_ref(), ref);
				unsafe.putInt(pBase + i3d_t.stencil_func_back_mask(), mask);
				break;
			case FRONT_AND_BACK:
				unsafe.putInt(pBase + i3d_t.stencil_func_front(), func);
				unsafe.putInt(pBase + i3d_t.stencil_func_front_ref(), ref);
				unsafe.putInt(pBase + i3d_t.stencil_func_front_mask(), mask);
				unsafe.putInt(pBase + i3d_t.stencil_func_back(), func);
				unsafe.putInt(pBase + i3d_t.stencil_func_back_ref(), ref);
				unsafe.putInt(pBase + i3d_t.stencil_func_back_mask(), mask);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		private int stencilOpSanitise(int op) {
			switch (op) {
			case ZERO:
				return 0;
			case KEEP:
				return 1;
			case REPLACE:
				return 2;
			case INCR:
				return 3;
			case INCR_WRAP:
				return 4;
			case DECR:
				return 5;
			case DECR_WRAP:
				return 6;
			case INVERT:
				return 7;
			default:
				throw nope("Invalid enum.");
			}
		}

		void stencilOpSeparate(int face, int sfail, int dfail, int dpass) {
			sfail = stencilOpSanitise(sfail);
			dfail = stencilOpSanitise(dfail);
			dpass = stencilOpSanitise(dpass);
			switch (face) {
			case FRONT:
				unsafe.putInt(pBase + i3d_t.stencil_op_front_sfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_front_dfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_front_dpass(), sfail);
				break;
			case BACK:
				unsafe.putInt(pBase + i3d_t.stencil_op_back_sfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_back_dfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_back_dpass(), sfail);
				break;
			case FRONT_AND_BACK:
				unsafe.putInt(pBase + i3d_t.stencil_op_front_sfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_front_dfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_front_dpass(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_back_sfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_back_dfail(), sfail);
				unsafe.putInt(pBase + i3d_t.stencil_op_back_dpass(), sfail);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		void light(int light, int param, double v) {
			if (light < 0 || light >= i3d_t.MAX_LIGHTS()) throw nope("Invalid enum.");
			long pLight = pBase + i3d_t.light0() + light_t.SIZEOF() * light;
			switch (param) {
			case CONSTANT_ATTENUATION:
				unsafe.putFloat(pLight + light_t.constant_attenuation(), (float) v);
				break;
			case LINEAR_ATTENUATION:
				unsafe.putFloat(pLight + light_t.linear_attenuation(), (float) v);
				break;
			case QUADRATIC_ATTENUATION:
				unsafe.putFloat(pLight + light_t.quadratic_attenuation(), (float) v);
				break;
			case SPOT_CUTOFF:
				unsafe.putFloat(pLight + light_t.spot_cutoff(), (float) Math.cos(v));
				break;
			case SPOT_EXPONENT:
				unsafe.putFloat(pLight + light_t.spot_exp(), (float) v);
				break;
			case EFFECT_RADIUS:
				unsafe.putFloat(pLight + light_t.inv_effect_rad(), (float) (1 / v));
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		void light(int light, int param, double f0, double f1, double f2, double f3) {
			if (light < 0 || light >= i3d_t.MAX_LIGHTS()) throw nope("Invalid enum.");
			long pLight = pBase + i3d_t.light0() + light_t.SIZEOF() * light;
			switch (param) {
			// colors given as rgba, stored as argb
			case AMBIENT:
				writeVector_float(unsafe, pLight + light_t.ia_a(), (float) f3, (float) f0, (float) f1, (float) f2);
				break;
			case DIFFUSE:
				writeVector_float(unsafe, pLight + light_t.id_a(), (float) f3, (float) f0, (float) f1, (float) f2);
				break;
			case SPECULAR:
				writeVector_float(unsafe, pLight + light_t.is_a(), (float) f3, (float) f0, (float) f1, (float) f2);
				break;
			case POSITION:
				// transform position through modelview and homogenise
				Vec4 pos = modelview.peek().mul(new Vec4(f0, f1, f2, f3)).homogenise();
				writeVector_float(unsafe, pLight + light_t.pos_x(), (float) pos.x, (float) pos.y, (float) pos.z, 1f);
				break;
			case SPOT_DIRECTION:
				// transform normal through normal transform and normalise
				Vec3 dir = modelview.peek().inv().xpose().mul(new Vec4(f0, f1, f2, 0)).xyz().unit();
				writeVector_float(unsafe, pLight + light_t.dir_x(), (float) dir.x, (float) dir.y, (float) dir.z, 0f);
				break;
			default:
				throw nope("Invalid enum.");
			}
		}

		void sceneAmbient(double r, double g, double b, double a) {
			writeVector_float(unsafe, pBase + i3d_t.scene_ambient(), (float) a, (float) r, (float) g, (float) b);
		}

		void fog(int param, double val) {
			switch (param) {
			case I3DX_FOG_A:
				unsafe.putFloat(pBase + i3d_t.fog_a(), (float) val);
				break;
			case I3DX_FOG_B:
				unsafe.putFloat(pBase + i3d_t.fog_b(), (float) val);
				break;
			default:
				throw nope("Invalid enum.");
			}

		}

		void fog(int param, double r, double g, double b, double a) {
			switch (param) {
			case FOG_COLOR:
				writeVector(unsafe, pBase + i3d_t.fog_color(), (float) a, (float) r, (float) g, (float) b);
				break;
			default:
				throw nope("Invalid enum.");
			}

		}

		void initFog() {
			// TODO init fog
			finish();
			loadUnsafeState();

		}

		void cullFace(int face) {
			switch (face) {
			case NONE:
				unsafe.putInt(pBase + i3d_t.face_cull(), 0);
				break;
			case FRONT:
				unsafe.putInt(pBase + i3d_t.face_cull(), 1);
				break;
			case BACK:
				unsafe.putInt(pBase + i3d_t.face_cull(), 2);
				break;
			case FRONT_AND_BACK:
				unsafe.putInt(pBase + i3d_t.face_cull(), 3);
				break;
			default:
				throw nope("Invalid enum.");
			}

		}

		void nearClip(double z) {
			unsafe.putDouble(pBase + i3d_t.near_clip(), z);
		}

		void farCull(double z) {
			unsafe.putDouble(pBase + i3d_t.far_cull(), z);
		}

		void finish() {
			geompipe.finish();
			if (isEnabled(AUTO_FLIP_ZSIGN)) {
				flipZSign();
			}
		}

		void matrixMode(int mode) {
			switch (mode) {
			case MODELVIEW:
				matrix_active = modelview;
				break;
			case PROJECTION:
				matrix_active = projection;
				break;
			default:
				throw nope("Invalid enum.");
			}
			matrix_mode = mode;
		}

		Vec3 transformOne(Vec3 v) {
			return matrix_active.peek().mul(v);
		}

		Vec4 transformOne(Vec4 v) {
			return matrix_active.peek().mul(v);
		}

		void pushMatrix() {
			matrix_active.push(matrix_active.peek().clone());
		}

		void popMatrix() {
			if (matrix_active.size() > 1) {
				matrix_active.pop();
			} else {
				throw nope("Can't pop the last matrix off the stack!");
			}
		}

		void loadMatrix(Mat4 m) {
			matrix_active.peek().set(m);
		}

		void multMatrix(Mat4 m) {
			matrix_active.peek().setMul(m);
		}

		Mat4 getMatrix() {
			return matrix_active.peek().clone();
		}

	}

	public Initial3DImpl() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public Initial3DImpl(int rasterthreads_) {
		rasterpipe = new RasterPipe(rasterthreads_);
		geompipe = new GeometryPipe();
		geompipe.connectRasterPipe(rasterpipe);

		// create default framebuffer
		default_framebuffer = new FrameBufferImpl();
		default_framebuffer.attachBuffer(BUFFER_COLOR0, new Texture2DImpl(1024, 1024));
		default_framebuffer.attachBuffer(BUFFER_Z, new Texture2DImpl(1024, 1024));
		default_framebuffer.setZSign(1);

		// create default texture (1x1 texel black)
		default_tex = new Texture2DImpl(1, 1);

		// create default vector buffers
		default_vbo = new VectorBufferImpl();
		default_vbo.add(0, 0, 0, 0);
		default_vbo_color = new VectorBufferImpl();
		default_vbo_color.add(1, 1, 1, 1);

		// setup initial state
		state.push(new State());
	}

	@Override
	protected void finalize() {
		// don't think anything needs to be here?
	}

	@Override
	public void pushState() {
		state.push(new State(state.peek()));
	}

	@Override
	public void popState() {
		if (state.size() > 1) {
			state.pop();
		} else {
			throw nope("Can't pop the last state off the stack!");
		}
	}

	@Override
	public int queryEnum(String name) {
		if (exposed_enums.contains(name)) {
			return getEnumWithReflection(name);
		}
		// else just use the api base method
		else {
			return super.queryEnum(name);
		}
	}

	private int getEnumWithReflection(String name) {
		try {
			java.lang.reflect.Field f = Initial3DImpl.class.getField(name);
			return (Integer) f.get(null);
		} catch (Throwable t) {
			throw new I3DException("Unable to get implementation enum constant " + name, t);
		}
	}

	@Override
	public Method queryMethod(String name, Class<?>... paramtypes) {
		if (exposed_methods.contains(name)) {
			return getMethodWithReflection(name, paramtypes);
		}
		// else just use the api base method
		else {
			return super.queryMethod(name, paramtypes);
		}
	}

	private Method getMethodWithReflection(String name, Class<?>... paramtypes) {
		try {
			final java.lang.reflect.Method m = Initial3DImpl.class.getMethod(name, paramtypes);
			return new Method() {

				@Override
				public Object call(Object... args) {
					try {
						return m.invoke(Initial3DImpl.this, args);
					} catch (Throwable t) {
						throw nope("Unable to call implementation method " + m.getName(), t);
					}
				}

			};
		} catch (Throwable t) {
			throw nope("Unable to get implementation method " + name, t);
		}
	}

	@Override
	public VectorBuffer createVectorBuffer() {
		return new VectorBufferImpl();
	}

	@Override
	public GeometryBuffer createGeometryBuffer(int mode) {
		return new GeometryBufferImpl(mode);
	}

	@Override
	public FrameBuffer createFrameBuffer() {
		return new FrameBufferImpl();
	}

	@Override
	public void bindFrameBuffer(FrameBuffer fb) {
		state.peek().bindFrameBuffer(fb);
	}

	@Override
	public FrameBuffer getFrameBuffer() {
		return state.peek().getFrameBuffer();
	}

	@Override
	public Texture2D createTexture2D(int size_u, int size_v) {
		return new Texture2DImpl(size_u, size_v);
	}

	@Override
	public void bindTexture(int target, Texture2D tex) {
		state.peek().bindTexture(target, tex);
	}

	@Override
	public void enable(int... caps) {
		state.peek().enable(caps);
	}

	@Override
	public void disable(int... caps) {
		state.peek().disable(caps);
	}

	@Override
	public boolean isEnabled(int cap) {
		return state.peek().isEnabled(cap);
	}

	@Override
	public void projectionMode(int mode) {
		state.peek().projectionMode(mode);
	}

	@Override
	public void polygonMode(int face, int mode) {
		state.peek().polygonMode(face, mode);
	}

	@Override
	public void shadeModel(int model) {
		state.peek().shadeModel(model);
	}

	@Override
	public void viewport(int x, int y, int w, int h) {
		state.peek().viewport(x, y, w, h);
	}

	@Override
	public void clear(int... buffers) {
		state.peek().clear(buffers);
	}

	public void flipZSign() {
		state.peek().flipZSign();
	}

	@Override
	public void bindVertexBuffer(int att, VectorBuffer vbuf) {
		state.peek().bindVertexBuffer(att, vbuf);
	}

	@Override
	public void drawGeometry(GeometryBuffer gbuf, int offset, int count) {
		state.peek().drawGeometry(gbuf, offset, count);
	}

	@Override
	public void begin(int mode) {
		state.peek().begin(mode);
	}

	@Override
	public void vertex(double x, double y, double z) {
		state.peek().vertex(x, y, z);
	}

	@Override
	public void normal(double x, double y, double z) {
		state.peek().normal(x, y, z);
	}

	@Override
	public void color(double r, double g, double b, double a) {
		state.peek().color(r, g, b, a);
	}

	@Override
	public void secondaryColor(double r, double g, double b, double a) {
		state.peek().secondaryColor(r, g, b, a);
	}

	@Override
	public void texCoord(double s, double t, double r, double q) {
		state.peek().texCoord(s, t, r, q);
	}

	@Override
	public void end() {
		state.peek().end();
	}

	@Override
	public void material(int face, int param, double f) {
		state.peek().material(face, param, f);
	}

	@Override
	public void material(int face, int param, double r, double g, double b, double a) {
		state.peek().material(face, param, r, g, b, a);
	}

	@Override
	public void blendFunc(int func, int sfactor, int dfactor) {
		state.peek().blendFunc(func, sfactor, dfactor);
	}

	@Override
	public void alphaFunc(int func, double ref) {
		state.peek().alphaFunc(func, ref);
	}

	@Override
	public void depthFunc(int func) {
		state.peek().depthFunc(func);
	}

	@Override
	public void stencilFuncSeparate(int face, int func, int ref, int mask) {
		state.peek().stencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void stencilOpSeparate(int face, int sfail, int dfail, int dpass) {
		state.peek().stencilOpSeparate(face, sfail, dfail, dpass);
	}

	@Override
	public void light(int light, int param, double v) {
		state.peek().light(light, param, v);
	}

	@Override
	public void light(int light, int param, double f0, double f1, double f2, double f3) {
		state.peek().light(light, param, f0, f1, f2, f3);
	}

	@Override
	public void sceneAmbient(double r, double g, double b, double a) {
		state.peek().sceneAmbient(r, g, b, a);
	}

	@Override
	public void fog(int param, double val) {
		state.peek().fog(param, val);
	}

	@Override
	public void fog(int param, double r, double g, double b, double a) {
		state.peek().fog(param, r, g, b, a);
	}

	public void initFog() {
		state.peek().initFog();
	}

	@Override
	public void cullFace(int face) {
		state.peek().cullFace(face);
	}

	@Override
	public void nearClip(double z) {
		state.peek().nearClip(z);
	}

	@Override
	public void farCull(double z) {
		state.peek().farCull(z);
	}

	@Override
	public void finish() {
		state.peek().finish();
	}

	@Override
	public void matrixMode(int mode) {
		state.peek().matrixMode(mode);
	}

	@Override
	public Vec3 transformOne(Vec3 v) {
		return state.peek().transformOne(v);
	}

	@Override
	public Vec4 transformOne(Vec4 v) {
		return state.peek().transformOne(v);
	}

	@Override
	public void pushMatrix() {
		state.peek().pushMatrix();
	}

	@Override
	public void popMatrix() {
		state.peek().popMatrix();
	}

	@Override
	public void loadMatrix(Mat4 m) {
		state.peek().loadMatrix(m);
	}

	@Override
	public void multMatrix(Mat4 m) {
		state.peek().multMatrix(m);
	}

	@Override
	public Mat4 getMatrix() {
		return state.peek().getMatrix();
	}

}
