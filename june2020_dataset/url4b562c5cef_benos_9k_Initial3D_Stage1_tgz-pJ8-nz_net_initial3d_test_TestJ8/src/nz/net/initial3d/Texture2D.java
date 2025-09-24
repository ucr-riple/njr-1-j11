package nz.net.initial3d;

import java.awt.image.BufferedImage;

public abstract class Texture2D {

	public static enum Channel {
		ALPHA, RED, GREEN, BLUE;
	}

	public static int sizePOT(int size) {
		if (size <= 1) return 1;
		size--;
		int size_pot = 1;
		while (size != 0) {
			size >>>= 1;
			size_pot <<= 1;
		}
		return size_pot;
	}

	public abstract int sizeU();

	public abstract int sizeV();

	public abstract void setMipMapsEnabled(boolean b);

	public abstract boolean getMipMapsEnabled();

	public abstract void setMipMapFloor(int i);

	public abstract int getMipMapFloor();

	public abstract void createMipMaps();

	public int getTexel(int u, int v) {
		return getTexel(u, v, false);
	}

	public abstract int getTexel(int u, int v, boolean wrap);

	public float getTexelComponentFloat(int u, int v, Channel ch) {
		return getTexelComponentFloat(u, v, ch, false);
	}

	public abstract float getTexelComponentFloat(int u, int v, Channel ch, boolean wrap);

	public int getTexelComponent(int u, int v, Channel ch) {
		return getTexelComponent(u, v, ch, false);
	}

	public abstract int getTexelComponent(int u, int v, Channel ch, boolean wrap);

	public void setTexel(int u, int v, int argb) {
		setTexel(u, v, argb, false);
	}

	public abstract void setTexel(int u, int v, int argb, boolean wrap);

	public void setTexelComponentFloat(int u, int v, Channel ch, float val) {
		setTexelComponentFloat(u, v, ch, val, false);
	}

	public abstract void setTexelComponentFloat(int u, int v, Channel ch, float val, boolean wrap);

	public void setTexelComponent(int u, int v, Channel ch, int val) {
		setTexelComponent(u, v, ch, val, false);
	}

	public abstract void setTexelComponent(int u, int v, Channel ch, int val, boolean wrap);

	public abstract void clear();

	/**
	 * Draw an image onto this texture. No scaling takes place.
	 *
	 * @param u
	 *            The u coordinate to start drawing at.
	 * @param v
	 *            The v coordinate to start drawing at.
	 * @param img
	 *            The BufferedImage to copy from.
	 * @param imgx
	 *            The x coordinate in the image to start copying from.
	 * @param imgy
	 *            The y coordinate in the image to start copying from.
	 * @param imgw
	 *            The width, in pixels, of image data to copy.
	 * @param imgh
	 *            The height, in pixels, of image data to copy.
	 * @param wrap
	 *            If texture coords should be wrapped.
	 */
	public void drawImage(int u, int v, BufferedImage img, int imgx, int imgy, int imgw, int imgh, boolean wrap) {
		int imgxmax = imgx + imgw;
		int imgymax = imgy + imgh;
		for (; imgx < imgxmax; u++, imgx++) {
			for (; imgy < imgymax; v++, imgy++) {
				int rgb = img.getRGB(imgx, imgy);
				setTexel(u, v, rgb, wrap);
			}
			imgy -= imgh;
			v -= imgh;
		}
	}

	/**
	 * Draw an image onto this texture. Bad scaling takes place.
	 *
	 * @param u
	 *            The u coordinate to start drawing at.
	 * @param v
	 *            The v coordinate to start drawing at.
	 * @param usize
	 *            The width in texels to scale the image to. Can be -ve.
	 * @param vsize
	 *            The height in texels to scale the image to. Can be -ve.
	 * @param img
	 *            The BufferedImage to copy from.
	 * @param imgx
	 *            The x coordinate in the image to start copying from.
	 * @param imgy
	 *            The y coordinate in the image to start copying from.
	 * @param imgw
	 *            The width, in pixels, of image data to copy.
	 * @param imgh
	 *            The height, in pixels, of image data to copy.
	 * @param wrap
	 *            If texture coords should be wrapped.
	 */
	public void drawImage(int u, int v, int usize, int vsize, BufferedImage img, int imgx, int imgy, int imgw,
			int imgh, boolean wrap) {
		double dimgx = imgw / (double) Math.abs(usize);
		double dimgy = imgh / (double) Math.abs(vsize);
		double imgx_cd = 0;
		double imgy_cd = 0;
		int imgxmax = imgx + imgw;
		int imgymax = imgy + imgh;
		int v_base = v;
		int u_inc = (usize > 0 ? 1 : -1), v_inc = (vsize > 0 ? 1 : -1);
		for (; imgx < imgxmax; u += u_inc) {
			imgy_cd = 0;
			imgy = imgymax - imgh;
			v = v_base;
			for (; imgy < imgymax; v += v_inc) {
				int rgb = img.getRGB(imgx, imgy);
				setTexel(u, v, rgb, wrap);
				double imgy_new = imgy + imgy_cd + dimgy;
				imgy = (int) (imgy_new);
				imgy_cd = imgy_new - imgy;
			}
			double imgx_new = imgx + imgx_cd + dimgx;
			imgx = (int) (imgx_new);
			imgx_cd = imgx_new - imgx;
		}
	}

	/** Draw the entirety of an image (badly) scaled to fit this entire texture. */
	public void drawImage(BufferedImage img) {
		drawImage(0, 0, sizeU(), sizeV(), img, 0, 0, img.getWidth(), img.getHeight(), false);
	}
}
