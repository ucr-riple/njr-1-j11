package nz.net.initial3d;

/**
 * Initial3D Rendering API.
 *
 * Yes, much of this does mimic OpenGL 1 / 2.
 *
 * All methods will throw <code>I3DException</code> if they want to, and may throw <code>NullPointerException</code> if
 * any parameter is null, unless explicity stated otherwise.
 *
 * @author Ben Allen
 *
 */
public abstract class Initial3D {

	/**
	 * Interface to support querying for functions at runtime.
	 */
	public static interface Method {

		public Object call(Object... args);

	}

	/*
	 * Enum Constants
	 */

	public static final int ZERO = 0;
	public static final int NONE = 0;
	public static final int ONE = 1;

	// capabilities
	public static final int SCISSOR_TEST = 2;
	public static final int ALPHA_TEST = 3;
	public static final int DEPTH_TEST = 4;
	public static final int STENCIL_TEST = 5;
	public static final int CULL_FACE = 6;
	public static final int BLEND = 7;
	public static final int FOG = 8;
	public static final int LIGHTING = 9;
	public static final int TWO_SIDED_LIGHTING = 10;
	public static final int TEXTURE_2D = 11;
	public static final int MIPMAPS = 12;
	public static final int COLOR_SUM = 13;
	public static final int SEPARATE_SPECULAR = 14;

	public static final int LIGHT0 = 1000;
	public static final int LIGHT1 = 1001;
	public static final int LIGHT2 = 1002;
	public static final int LIGHT3 = 1003;
	public static final int LIGHT4 = 1004;
	public static final int LIGHT5 = 1005;
	public static final int LIGHT6 = 1006;
	public static final int LIGHT7 = 1007;
	public static final int LIGHT_MAX = 1999;
	public static final int CLIP_PLANE0 = 2000;
	public static final int CLIP_PLANE1 = 2001;
	public static final int CLIP_PLANE2 = 2002;
	public static final int CLIP_PLANE3 = 2003;
	public static final int CLIP_PLANE_MAX = 2999;

	// buffers, also represents write caps for each buffer
	public static final int BUFFER_COLOR0 = 10000;
	public static final int BUFFER_COLOR1 = 10001;
	public static final int BUFFER_Z = 10002;
	public static final int BUFFER_STENCIL = 10003;
	public static final int BUFFER_ID = 10004;

	// shademodels
	public static final int FLAT = 10100;
	public static final int SMOOTH = 10101;
	public static final int PHONG = 10102;

	// blend func src / dst factors
	// share ZERO, ONE
	// idea: if COLOR_SUM is enabled, do 2 blends with both color buffers then
	// add the result
	public static final int SRC_COLOR = 10200;
	public static final int ONE_MINUS_SRC_COLOR = 10201;
	public static final int DST_COLOR = 10202;
	public static final int ONE_MINUS_DST_COLOR = 10203;
	public static final int SRC_ALPHA = 10204;
	public static final int ONE_MINUS_SRC_ALPHA = 10205;
	public static final int DST_ALPHA = 10206;
	public static final int ONE_MINUS_DST_ALPHA = 10207;

	// blend funcs
	public static final int FUNC_ADD = 10250;
	public static final int FUNC_SUBTRACT = 10251;
	public static final int FUNC_REVERSE_SUBTRACT = 10252;
	public static final int FUNC_MIN = 10253;
	public static final int FUNC_MAX = 10254;

	// comparison functions
	public static final int NEVER = 10300;
	public static final int LESS = 10301;
	public static final int LEQUAL = 10302;
	public static final int GREATER = 10303;
	public static final int GEQUAL = 10304;
	public static final int EQUAL = 10305;
	public static final int NOTEQUAL = 10306;
	public static final int ALWAYS = 10307;

	// stencil ops
	// stencil buffer as unsigned bytes (?)
	// share ZERO
	public static final int KEEP = 10400;
	public static final int REPLACE = 10401;
	public static final int INCR = 10402; // clamp to 255
	public static final int INCR_WRAP = 10403; // wrap to 0
	public static final int DECR = 10404; // clamp to 0
	public static final int DECR_WRAP = 10405; // wrap to 255
	public static final int INVERT = 10406; // bitwise invert

	// faces
	// share NONE
	public static final int FRONT = 10500;
	public static final int BACK = 10501;
	public static final int FRONT_AND_BACK = 10502;

	// light and material
	public static final int AMBIENT = 10600;
	public static final int DIFFUSE = 10601;
	public static final int SPECULAR = 10602;
	public static final int EMISSION = 10603;
	// opacity in diffuse alpha, shininess in specular alpha
	// but you can set them seperately of the color
	public static final int SHININESS = 10604;
	public static final int OPACITY = 10605;
	public static final int POSITION = 10606;
	public static final int SPOT_DIRECTION = 10607;
	public static final int CONSTANT_ATTENUATION = 10608;
	public static final int LINEAR_ATTENUATION = 10609;
	public static final int QUADRATIC_ATTENUATION = 10610;
	public static final int SPOT_CUTOFF = 10611;
	public static final int SPOT_EXPONENT = 10612;
	public static final int EFFECT_RADIUS = 10613;

	// texture targets
	public static final int TEXTURE_2D_KD = 10700;
	public static final int TEXTURE_2D_KS = 10701;
	public static final int TEXTURE_2D_KE = 10702;

	// drawing modes
	public static final int POINT = 10800;
	public static final int LINE = 10801;
	public static final int FILL = 10802;

	// projection mode (OpenGL does this implicitly)
	public static final int ORTHOGRAPHIC = 10900;
	public static final int PERSPECTIVE = 10901;

	// vertex attributes
	public static final int VERTEX_POSITION = 11000;
	public static final int VERTEX_NORMAL = 11001;
	public static final int VERTEX_COLOR0 = 11002;
	public static final int VERTEX_COLOR1 = 11003;
	public static final int VERTEX_TEXCOORD = 11004;

	// fog parameters
	public static final int FOG_COLOR = 11100;

	// use queryEnum() for the other fog params and functions. yes, even my own
	// fog implementation is non-standard!

	// matrices
	public static final int MODELVIEW = 11200;
	public static final int PROJECTION = 11201;

	// begin modes
	public static final int POINTS = 11300;
	public static final int LINES = 11301;
	public static final int LINE_STRIP = 11302;
	public static final int LINE_LOOP = 11303;
	public static final int TRIANGLES = 11304;
	public static final int TRIANGLE_STRIP = 11305;
	public static final int TRIANGLE_FAN = 11306;
	public static final int QUADS = 11307;
	public static final int QUAD_STRIP = 11308;
	public static final int POLYGON = 11309;

	public abstract void pushState();

	public abstract void popState();

	/**
	 * Query the value of a named Initial3D enum constant, including non-standard ones like 'I3DX_FOG_A'.
	 *
	 * @param name
	 *            Name of the constant. Case sensitive.
	 * @return The value of the constant.
	 */
	public int queryEnum(String name) {
		try {
			java.lang.reflect.Field f = Initial3D.class.getField(name);
			return (Integer) f.get(null);
		} catch (Throwable t) {
			throw new I3DException("Unable to get API enum constant " + name, t);
		}
	}

	/**
	 * Query for a named Initial3D method, including non-standard ones like 'flipZSign'.
	 *
	 * @param name
	 *            Name of method. Case sensitive.
	 * @param paramtypes
	 *            Class objects representing the argument types of the method.
	 * @return The <code>Method</code> object representing that method.
	 */
	public Method queryMethod(String name, Class<?>... paramtypes) {
		try {
			final java.lang.reflect.Method m = Initial3D.class.getMethod(name, paramtypes);
			return new Method() {

				@Override
				public Object call(Object... args) {
					try {
						return m.invoke(Initial3D.this, args);
					} catch (Throwable t) {
						throw new I3DException("Unable to call API method " + m.getName(), t);
					}
				}

			};
		} catch (Throwable t) {
			throw new I3DException("Unable to get API method " + name, t);
		}
	}

	public abstract VectorBuffer createVectorBuffer();
	
	public abstract GeometryBuffer createGeometryBuffer(int mode);
	
	public abstract FrameBuffer createFrameBuffer();

	// if null, revert to default
	public abstract void bindFrameBuffer(FrameBuffer fb);

	public abstract FrameBuffer getFrameBuffer();

	public abstract Texture2D createTexture2D(int size_u, int size_v);

	// if null, revert to default
	public abstract void bindTexture(int target, Texture2D tex);

	public abstract void enable(int... caps);

	public abstract void disable(int... caps);

	public abstract boolean isEnabled(int cap);

	public abstract void projectionMode(int mode);

	public abstract void polygonMode(int face, int mode);

	public abstract void shadeModel(int model);

	public abstract void viewport(int x, int y, int w, int h);

	public abstract void clear(int... buffers);

	// if null, revert to default (a fixed buffer with one zero vector)
	public abstract void bindVertexBuffer(int att, VectorBuffer vbuf);

	public abstract void drawGeometry(GeometryBuffer gbuf, int offset, int count);

	public abstract void begin(int mode);

	public abstract void vertex(double x, double y, double z);

	public void vertex(Vec3 v) {
		vertex(v.x, v.y, v.z);
	}

	public abstract void normal(double x, double y, double z);

	public void normal(Vec3 v) {
		normal(v.x, v.y, v.z);
	}

	public abstract void color(double r, double g, double b, double a);

	public void color(double r, double g, double b) {
		color(r, g, b, 1);
	}

	public void color(Color c) {
		color(c, c.a);
	}

	public void color(Color c, double a) {
		color(c.r, c.g, c.b, a);
	}

	public abstract void secondaryColor(double r, double g, double b, double a);

	public void secondaryColor(double r, double g, double b) {
		secondaryColor(r, g, b, 1);
	}

	public void secondaryColor(Color c) {
		secondaryColor(c, c.a);
	}

	public void secondaryColor(Color c, double a) {
		secondaryColor(c.r, c.g, c.b, a);
	}

	public void texCoord(double s) {
		texCoord(s, 0, 0, 1);
	}

	public void texCoord(double s, double t) {
		texCoord(s, t, 0, 1);
	}

	public void texCoord(double s, double t, double r) {
		texCoord(s, t, r, 1);
	}

	public abstract void texCoord(double s, double t, double r, double q);

	public void texCoord(Vec3 v) {
		texCoord(v, 1);
	}

	public void texCoord(Vec3 v, double q) {
		texCoord(v.x, v.y, v.z, q);
	}

	public void texCoord(Vec4 v) {
		texCoord(v.x, v.y, v.z, v.w);
	}

	public abstract void end();

	public abstract void material(int face, int param, double f);

	public abstract void material(int face, int param, double r, double g, double b, double a);

	public void material(int face, int param, double r, double g, double b) {
		material(face, param, r, g, b, 1);
	}

	public void material(int face, int param, Color c) {
		material(face, param, c.r, c.g, c.b, c.a);
	}

	public abstract void blendFunc(int func, int sfactor, int dfactor);

	public abstract void alphaFunc(int func, double ref);

	public abstract void depthFunc(int func);

	public void stencilFunc(int func, int ref, int mask) {
		stencilFuncSeparate(FRONT_AND_BACK, func, ref, mask);
	}

	public abstract void stencilFuncSeparate(int face, int func, int ref, int mask);

	public void stencilOp(int sfail, int dfail, int dpass) {
		stencilOpSeparate(FRONT_AND_BACK, sfail, dfail, dpass);
	}

	public abstract void stencilOpSeparate(int face, int sfail, int dfail, int dpass);

	public abstract void light(int light, int param, double v);

	public abstract void light(int light, int param, double f0, double f1, double f2, double f3);

	public void light(int light, int param, double f0, double f1, double f2) {
		light(light, param, f0, f1, f2, 1);
	}

	public void light(int light, int param, Color c) {
		light(light, param, c.r, c.g, c.b, c.a);
	}

	public void light(int light, int param, Vec4 v) {
		light(light, param, v.x, v.y, v.z, v.w);
	}

	public void light(int light, int param, Vec3 v) {
		light(light, param, v.x, v.y, v.z);
	}

	public abstract void sceneAmbient(double r, double g, double b, double a);

	public void sceneAmbient(double r, double g, double b) {
		sceneAmbient(r, g, b, 1);
	}

	public void sceneAmbient(Color c) {
		sceneAmbient(c.r, c.g, c.b, c.a);
	}

	public abstract void fog(int param, double val);

	public abstract void fog(int param, double r, double g, double b, double a);

	public void fog(int param, double r, double g, double b) {
		fog(param, r, g, b, 1);
	}

	public void fog(int param, Color c) {
		fog(param, c.r, c.g, c.b, c.a);
	}

	public abstract void cullFace(int face);

	public abstract void nearClip(double z);

	public abstract void farCull(double z);

	public abstract void finish();

	// matrices

	public abstract void matrixMode(int mode);

	public abstract Vec3 transformOne(Vec3 v);

	public abstract Vec4 transformOne(Vec4 v);

	public abstract void pushMatrix();

	public abstract void popMatrix();

	public void loadIdentity() {
		loadMatrix(new Mat4());
	}

	public void pushIdentity() {
		pushMatrix();
		loadIdentity();
	}

	public abstract void loadMatrix(Mat4 m);

	public abstract void multMatrix(Mat4 m);

	public abstract Mat4 getMatrix();

	public void translate(Vec3 d) {
		multMatrix(Mat4.translate(d));
	}

	public void translate(double dx, double dy, double dz) {
		multMatrix(Mat4.translate(dx, dy, dz));
	}

	public void scale(double f) {
		multMatrix(Mat4.scale(f, f, f));
	}

	public void scale(Vec3 f) {
		multMatrix(Mat4.scale(f.x, f.y, f.z));
	}

	public void scale(double fx, double fy, double fz) {
		multMatrix(Mat4.scale(fx, fy, fz));
	}

	public void rotate(Quat q) {
		multMatrix(Mat4.rotate(q));
	}

	public void rotate(double angle, Vec3 axis) {
		multMatrix(Mat4.rotate(new Quat(angle, axis)));
	}

	public void rotate(double angle, double ax, double ay, double az) {
		multMatrix(Mat4.rotate(new Quat(angle, new Vec3(ax, ay, az))));
	}

}
