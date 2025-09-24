package nz.net.initial3d;

public class Color {

	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);
	public static final Color YELLOW = new Color(1, 1, 0);
	public static final Color CYAN = new Color(0, 1, 1);
	public static final Color MAGENTA = new Color(1, 0, 1);
	public static final Color ORANGE = new Color(1, 0.5, 0);
	
	public static Color fromHSB(double h, double s, double b) {
		// FIXME Color.fromHSB()
		return null;
	}
	
	public static Color fromHSL(double h, double s, double l) {
		// FIXME Color.fromHSL()
		return null;
	}

	/** Alpha component (0 == transparent, 1 == opaque). */
	public final float a;

	/** Red component. */
	public final float r;

	/** Green component. */
	public final float g;

	/** Blue component. */
	public final float b;

	public Color(float r_, float g_, float b_, float a_) {
		a = a_;
		r = r_;
		g = g_;
		b = b_;
	}

	public Color(float r_, float g_, float b_) {
		this(r_, g_, b_, 1f);
	}

	public Color(double r_, double g_, double b_, double a_) {
		a = (float) a_;
		r = (float) r_;
		g = (float) g_;
		b = (float) b_;
	}

	public Color(double r_, double g_, double b_) {
		this(r_, g_, b_, 1d);
	}

	public Color(int argb_) {
		b = (argb_ & 0xFF) / 255f;
		argb_ >>>= 8;
		g = (argb_ & 0xFF) / 255f;
		argb_ >>>= 8;
		r = (argb_ & 0xFF) / 255f;
		argb_ >>>= 8;
		a = (argb_ & 0xFF) / 255f;
	}

	public int toARGB() {
		return (((int) (a * 255f)) << 24) | (((int) (r * 255f)) << 16) | (((int) (g * 255f)) << 8) | ((int) (b * 255f));
	}

	public Color withAlpha(float a2) {
		return new Color(r, g, b, a2);
	}

	public Color withAlpha(double a2) {
		return withAlpha((float) a2);
	}

	public Color withRed(float r2) {
		return new Color(r2, g, b, a);
	}

	public Color withRed(double r2) {
		return withRed((float) r2);
	}

	public Color withGreen(float g2) {
		return new Color(r, g2, b, a);
	}

	public Color withGreen(double g2) {
		return withGreen((float) g2);
	}

	public Color withBlue(float b2) {
		return new Color(r, g, b2, a);
	}

	public Color withBlue(double b2) {
		return withBlue((float) b2);
	}

}
