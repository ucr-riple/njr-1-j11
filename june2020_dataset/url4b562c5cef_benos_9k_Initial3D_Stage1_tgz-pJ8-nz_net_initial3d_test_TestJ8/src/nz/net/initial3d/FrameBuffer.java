package nz.net.initial3d;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;

public abstract class FrameBuffer {

	public abstract void attachBuffer(int type, Texture2D tex);

	public abstract void attachBuffer(int type, int[] buf, int offset, int stride);

	public void attachBuffer(int type, BufferedImage img) {
		DataBufferInt db = (DataBufferInt) img.getRaster().getDataBuffer();
		int stride = ((SinglePixelPackedSampleModel) img.getRaster().getSampleModel()).getScanlineStride();
		attachBuffer(type, db.getData(), db.getOffset(), stride);
	}

	public abstract void detachBuffer(int type);

	public abstract boolean hasBuffer(int type);

}
