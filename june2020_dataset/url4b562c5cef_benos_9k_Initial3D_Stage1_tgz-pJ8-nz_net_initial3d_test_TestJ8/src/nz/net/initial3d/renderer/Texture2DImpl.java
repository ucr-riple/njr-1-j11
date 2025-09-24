package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import nz.net.initial3d.*;
import nz.net.initial3d.renderer.Type.tex2d_t;

import sun.misc.Unsafe;

final class Texture2DImpl extends Texture2D {

	private static final Unsafe unsafe = getUnsafe();

	// pointer to entire texture
	final long pTex;
	// stride (in bytes) between scanlines
	final int stride_tex;
	// pointer to top mip-map
	final long pTop;
	// size of the entire texture (in bytes)
	final int alloc;
	// highest u and v mip-map levels
	final int levelu_top, levelv_top;
	// dimensions (in texels) of top mip-map level
	final int sizeu, sizev;

	static int levelOffset(int stride, int levelu, int levelv) {
		// TODO faster texture level offset
		// stride in bytes
		return (stride << levelv) + (4 << levelu);
	}

	static int texelOffset(int stride, int levelu, int levelv, int u, int v) {
		// TODO faster texture texel offset
		// u and v as 16.16 fixed point
		u = u & 0x0000FFFF;
		v = v & 0x0000FFFF;
		// no it doesn't... v >>> 16 is a no-no
		// int probablyworks = ((stride + (v >>> 16)) << levelv) + ((4 + (u >>>
		// 14)) << levelu);
		return (stride << levelv) + (v >>> (16 - levelv)) + (4 << levelu) + (u >>> (14 - levelu));
	}

	Texture2DImpl(int sizeu_, int sizev_) {
		// number of u-levels
		short levelu_ = 0;
		switch (sizeu_) {
		case 1024:
			levelu_++;
		case 512:
			levelu_++;
		case 256:
			levelu_++;
		case 128:
			levelu_++;
		case 64:
			levelu_++;
		case 32:
			levelu_++;
		case 16:
			levelu_++;
		case 8:
			levelu_++;
		case 4:
			levelu_++;
		case 2:
			levelu_++;
		case 1:
			break;
		default:
			throw new IllegalArgumentException("Illegal u-size for texture: " + sizeu_);
		}
		// number of v-levels
		short levelv_ = 0;
		switch (sizev_) {
		case 1024:
			levelv_++;
		case 512:
			levelv_++;
		case 256:
			levelv_++;
		case 128:
			levelv_++;
		case 64:
			levelv_++;
		case 32:
			levelv_++;
		case 16:
			levelv_++;
		case 8:
			levelv_++;
		case 4:
			levelv_++;
		case 2:
			levelv_++;
		case 1:
			break;
		default:
			throw new IllegalArgumentException("Illegal v-size for texture: " + sizev_);
		}
		// ok to create texture
		levelu_top = levelu_;
		levelv_top = levelv_;
		sizeu = sizeu_;
		sizev = sizev_;
		// x2 for rip-maps, 4 bytes per pixel
		stride_tex = sizeu * 2 * 4;
		// x4 for rip-maps, 4 bytes per pixel
		alloc = sizeu * sizev * 4 * 4;
		pTex = unsafe.allocateMemory(alloc);
		pTop = pTex + levelOffset(stride_tex, levelu_top, levelv_top);
		// set level ceilings
		unsafe.putByte(pTex + tex2d_t.uceil(), (byte) levelu_top);
		unsafe.putByte(pTex + tex2d_t.vceil(), (byte) levelv_top);
		// set level floors
		unsafe.putByte(pTex + tex2d_t.ufloor(), (byte) 0);
		unsafe.putByte(pTex + tex2d_t.vfloor(), (byte) 0);
		// init flags
		unsafe.putShort(pTex + tex2d_t.flags(), (short) 0);
		// init texture to black
		clear();
	}

	@Override
	protected void finalize() {
		unsafe.freeMemory(pTex);
	}

	@Override
	public int sizeU() {
		return sizeu;
	}

	@Override
	public int sizeV() {
		return sizev;
	}

	@Override
	public void setMipMapsEnabled(boolean b) {
		if (b) {
			unsafe.putShort(pTex + tex2d_t.flags(), (short) (unsafe.getShort(pTex + tex2d_t.flags()) | 0x1));
		} else {
			unsafe.putShort(pTex + tex2d_t.flags(), (short) (unsafe.getShort(pTex + tex2d_t.flags()) & ~0x1));
		}
	}

	@Override
	public boolean getMipMapsEnabled() {
		return (unsafe.getShort(pTex + tex2d_t.flags()) & 0x1) == 0x1;
	}

	@Override
	public void setMipMapFloor(int i) {
		unsafe.putByte(pTex + tex2d_t.ufloor(), (byte) i);
		unsafe.putByte(pTex + tex2d_t.vfloor(), (byte) i);
	}

	@Override
	public int getMipMapFloor() {
		return unsafe.getByte(pTex + tex2d_t.ufloor());
	}

	@Override
	public void createMipMaps() {
		for (int levelu = levelu_top; levelu >= 0; levelu--) {
			if (levelu < levelu_top) {
				// compose mipmap for top v-level
				for (int u = 1 << levelu; u-- > 0;) {
					for (int v = sizev; v-- > 0;) {
						// for all pixels
						int argb0 = unsafe.getInt(texelPointer(levelu + 1, levelv_top, u * 2, v));
						int argb1 = unsafe.getInt(texelPointer(levelu + 1, levelv_top, u * 2 + 1, v));
						unsafe.putInt(texelPointer(levelu, levelv_top, u, v), colorAvg(argb0, argb1));
					}
				}
			}
			for (int levelv = levelv_top; levelv-- > 0;) {
				// for all mipmaps below top v-level
				for (int u = 1 << levelu; u-- > 0;) {
					for (int v = 1 << levelv; v-- > 0;) {
						// for all pixels
						int argb0 = unsafe.getInt(texelPointer(levelu, levelv + 1, u, v * 2));
						int argb1 = unsafe.getInt(texelPointer(levelu, levelv + 1, u, v * 2 + 1));
						unsafe.putInt(texelPointer(levelu, levelv, u, v), colorAvg(argb0, argb1));
					}
				}
			}
		}
	}

	private long texelPointer(int levelu, int levelv, int u, int v) {
		// integer u, v
		if (u < 0 || u >= sizeu || v < 0 || v >= sizev) System.out.println("wrapping! u=" + u + ", v=" + v);
		u &= (sizeu - 1);
		v &= (sizev - 1);
		return pTex + levelOffset(stride_tex, levelu, levelv) + stride_tex * v + 4 * u;
	}

	private long texelPointer(int u, int v) {
		// integer u, v
		return texelPointer(levelu_top, levelv_top, u, v);
	}

	@Override
	public int getTexel(int u, int v, boolean wrap) {
		if (!wrap && (u < 0 || u >= sizeu || v < 0 || v >= sizev)) return 0;
		return unsafe.getInt(texelPointer(u, v));
	}

	@Override
	public float getTexelComponentFloat(int u, int v, Channel ch, boolean wrap) {
		return getTexelComponent(u, v, ch, wrap) / 255f;
	}

	@Override
	public int getTexelComponent(int u, int v, Channel ch, boolean wrap) {
		int argb = getTexel(u, v, wrap);
		switch (ch) {
		case ALPHA:
			return (argb >>> 24) & 0xFF;
		case RED:
			return (argb >>> 16) & 0xFF;
		case GREEN:
			return (argb >>> 8) & 0xFF;
		case BLUE:
			return argb & 0xFF;
		default:
			throw nope("Invalid texture channel.");
		}
	}

	@Override
	public void setTexel(int u, int v, int argb, boolean wrap) {
		if (!wrap && (u < 0 || u >= sizeu || v < 0 || v >= sizev)) return;
		unsafe.putInt(texelPointer(u, v), argb);
	}

	@Override
	public void setTexelComponentFloat(int u, int v, Channel ch, float val, boolean wrap) {
		setTexelComponent(u, v, ch, (int) (clamp(val, 0f, 1f) * 255f), wrap);
	}

	@Override
	public void setTexelComponent(int u, int v, Channel ch, int val, boolean wrap) {
		int argb = getTexel(u, v, wrap);
		switch (ch) {
		case ALPHA:
			argb = (argb & 0x00FFFFFF) | ((val & 0xFF) << 24);
			break;
		case RED:
			argb = (argb & 0xFF00FFFF) | ((val & 0xFF) << 16);
			break;
		case GREEN:
			argb = (argb & 0xFFFF00FF) | ((val & 0xFF) << 8);
			break;
		case BLUE:
			argb = (argb & 0xFFFFFF00) | (val & 0xFF);
		default:
			throw nope("Invalid texture channel.");
		}
		setTexel(u, v, argb, wrap);
	}

	@Override
	public void clear() {
		// zero out the texture data
		int offset = levelOffset(stride_tex, 0, 0);
		unsafe.setMemory(pTex + offset, alloc - offset, (byte) 0);
	}

	public BufferedImage extractAll() {
		// just to test if the mip-mapping works properly
		BufferedImage img = new BufferedImage(sizeu * 2, sizev * 2, BufferedImage.TYPE_INT_ARGB);
		int[] imgdata = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		for (long p = pTex, o = unsafe.arrayBaseOffset(int[].class); p < pTex + alloc; p += 4, o += 4) {
			unsafe.putInt(imgdata, o, unsafe.getInt(p));
		}
		return img;
	}

}
