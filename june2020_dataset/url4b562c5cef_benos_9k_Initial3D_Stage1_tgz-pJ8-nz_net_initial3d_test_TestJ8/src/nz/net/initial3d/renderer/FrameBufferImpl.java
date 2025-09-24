package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import sun.misc.Unsafe;
import nz.net.initial3d.*;
import nz.net.initial3d.renderer.Type.framebuf_t;

final class FrameBufferImpl extends FrameBuffer {

	private static final Unsafe unsafe = getUnsafe();

	// viewport
	int viewport_x = 0, viewport_y = 0;
	int viewport_w = 0, viewport_h = 0;

	// buffer color0
	Object obj_color0;
	long pColor0;
	int stride_color0;

	// buffer color1
	long pColor1;
	int stride_color1;

	// buffer z
	long pZ;
	int stride_z;
	long pSZ;
	int stride_sz;
	long pZSign;

	// buffer stencil
	long pStencil;
	int stride_stencil;

	// buffer id
	long pID;
	int stride_id;

	// the fog correction table. not attachable.
	long pFC;

	FrameBufferImpl() {
		pFC = unsafe.allocateMemory(1024 * 1024 * 4);
	}

	@Override
	protected void finalize() {
		unsafe.freeMemory(pFC);
	}

	int getZSign() {
		return unsafe.getInt(pZSign);
	}

	void setZSign(int sign) {
		unsafe.putInt(pZSign, sign);
	}

	@Override
	public void attachBuffer(int type, Texture2D tex) {
		Texture2DImpl texim = (Texture2DImpl) tex;
		switch (type) {
		case Initial3D.BUFFER_COLOR0:
			obj_color0 = null;
			pColor0 = texim.pTop;
			stride_color0 = texim.stride_tex;
			break;
		case Initial3D.BUFFER_COLOR1:
			pColor1 = texim.pTop;
			stride_color1 = texim.stride_tex;
			break;
		case Initial3D.BUFFER_Z:
			// attach the top level as the z buffer
			pZ = texim.pTop;
			stride_z = texim.stride_tex;
			// and one level down as the 'small' z buffer
			pSZ = texim.pTex + Texture2DImpl.levelOffset(texim.stride_tex, texim.levelu_top - 1, texim.levelv_top - 1);
			stride_sz = texim.stride_tex;
			// and level 0,0 for the z-sign
			pZSign = texim.pTex + Texture2DImpl.levelOffset(texim.stride_tex, 0, 0);
			// transparently init the z-sign
			setZSign(getZSign() >= 0 ? 1 : -1);
			break;
		case Initial3D.BUFFER_STENCIL:
			pStencil = texim.pTop;
			stride_stencil = texim.stride_tex;
			break;
		case Initial3D.BUFFER_ID:
			pID = texim.pTop;
			stride_id = texim.stride_tex;
			break;
		default:
			throw nope("Invalid enum.");
		}
	}

	@Override
	public void attachBuffer(int type, int[] buf, int offset, int stride) {
		if (type != Initial3D.BUFFER_COLOR0) throw nope("Can only attach array to BUFFER_COLOR0.");
		obj_color0 = buf;
		pColor0 = unsafe.arrayBaseOffset(int[].class) + offset * 4;
		stride_color0 = stride * 4;
	}

	@Override
	public void detachBuffer(int type) {
		switch (type) {
		case Initial3D.BUFFER_COLOR0:
			obj_color0 = null;
			pColor0 = 0;
			break;
		case Initial3D.BUFFER_COLOR1:
			pColor1 = 0;
			break;
		case Initial3D.BUFFER_Z:
			pZ = 0;
			break;
		case Initial3D.BUFFER_STENCIL:
			pStencil = 0;
			break;
		case Initial3D.BUFFER_ID:
			pID = 0;
			break;
		default:
			throw nope("Invalid enum.");
		}
	}

	@Override
	public boolean hasBuffer(int type) {
		switch (type) {
		case Initial3D.BUFFER_COLOR0:
			return (obj_color0 != null) || (pColor0 != 0);
		case Initial3D.BUFFER_COLOR1:
			return pColor1 != 0;
		case Initial3D.BUFFER_Z:
			return pZ != 0;
		case Initial3D.BUFFER_STENCIL:
			return pStencil != 0;
		case Initial3D.BUFFER_ID:
			return pID != 0;
		default:
			throw nope("Invalid enum.");
		}
	}

	void viewport(int x, int y, int w, int h) {
		viewport_x = x;
		viewport_y = y;
		viewport_w = w;
		viewport_h = h;
	}

	void clear(int type) {
		// TODO checkthis
		if (!hasBuffer(type)) return;
		switch (type) {
		case Initial3D.BUFFER_COLOR0:
			clearlines(obj_color0, pColor0 + stride_color0 * viewport_y, viewport_h, viewport_w * 4, stride_color0);
			break;
		case Initial3D.BUFFER_COLOR1:
			clearlines(null, pColor1 + stride_color1 * viewport_y, viewport_h, viewport_w * 4, stride_color1);
			break;
		case Initial3D.BUFFER_Z:
			clearlines(null, pZ + stride_z * viewport_y, viewport_h, viewport_w * 4, stride_z);
			clearlines(null, pSZ + stride_sz * (viewport_y / 8), (viewport_h / 8), (viewport_w / 2), stride_sz);
			setZSign(1);
			break;
		case Initial3D.BUFFER_STENCIL:
			// FIXME stencil element size?
			clearlines(null, pStencil + stride_stencil * viewport_y, viewport_h, viewport_w * 4, stride_stencil);
			break;
		case Initial3D.BUFFER_ID:
			clearlines(null, pID + stride_id * viewport_y, viewport_h, viewport_w * 4, stride_id);
			break;
		default:
			throw nope("Invalid enum.");
		}
	}

	void clearlines(Object o, long p, int lines, int w, int stride) {
		// TODO checkthis
		for (final long p_end = p + lines * stride; p < p_end; p += stride) {
			for (final long p_end2 = p + w; p < p_end2; p += 4) {
				unsafe.putInt(o, p, 0);
			}
		}
	}

	void writeUnsafeState(long pFrameBuf) {
		unsafe.putInt(pFrameBuf + framebuf_t.viewport_x(), viewport_x);
		unsafe.putInt(pFrameBuf + framebuf_t.viewport_y(), viewport_y);
		unsafe.putInt(pFrameBuf + framebuf_t.viewport_w(), viewport_w);
		unsafe.putInt(pFrameBuf + framebuf_t.viewport_h(), viewport_h);
		unsafe.putAddress(pFrameBuf + framebuf_t.pColor0(), pColor0);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_color0(), stride_color0);
		unsafe.putAddress(pFrameBuf + framebuf_t.pColor1(), pColor1);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_color1(), stride_color1);
		unsafe.putAddress(pFrameBuf + framebuf_t.pZ(), pZ);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_z(), stride_z);
		unsafe.putAddress(pFrameBuf + framebuf_t.pSZ(), pSZ);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_sz(), stride_sz);
		unsafe.putAddress(pFrameBuf + framebuf_t.pZSign(), pZSign);
		unsafe.putAddress(pFrameBuf + framebuf_t.pStencil(), pStencil);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_stencil(), stride_stencil);
		unsafe.putAddress(pFrameBuf + framebuf_t.pID(), pID);
		unsafe.putInt(pFrameBuf + framebuf_t.stride_id(), stride_id);
		unsafe.putAddress(pFrameBuf + framebuf_t.pFC(), pFC);
	}
}













