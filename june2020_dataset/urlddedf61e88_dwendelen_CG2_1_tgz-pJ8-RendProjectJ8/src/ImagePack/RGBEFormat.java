package ImagePack;


import java.io.*;

import Color.*;



/**
 * Support for uncompressed RGBE image in/out.
 * @author  fabio
 * @version 0.1
 */

public abstract class RGBEFormat {
  private static int			width, height;
  private static float[]		rgbData;
  private static byte[]			rgbeData;
  private static Double			gamma;
  private static Double			exposure;

  // write file
  public static synchronized void write(Image image, Double gamma,
                                        Double exposure, String filename)
                                        throws IOException {
    setData(image,gamma,exposure);
    OutputStream os = new BufferedOutputStream(new FileOutputStream(filename), 8192);
    writeHeader(os);
    writePixels(os);
  }

  private static void setData(Image image, Double gamma, Double exposure) {
    RGBEFormat.gamma = gamma;
    RGBEFormat.exposure = exposure;
    width = image.width;
    height = image.height;
    rgbData = new float[width*height*3];
    RGBColor rgbcolor;

    for(int y = 0; y < height; y ++) {
      for(int x = 0; x < width; x ++) {
        int offset = (y*width+x)*3;
        rgbcolor = image.getColorOfPixel(x,height-y-1);
        rgbData[offset+0] = (float)(rgbcolor.r);
        rgbData[offset+1] = (float)(rgbcolor.g);
        rgbData[offset+2] = (float)(rgbcolor.b);
      }
    }
  }

  private static void writeHeader(OutputStream o) throws IOException {
    DataOutputStream os = new DataOutputStream(o);
    os.writeBytes("#?RGBE\n");
    if (gamma != null)
      os.writeBytes("GAMMA="+gamma+"\n");
    if (exposure != null)
      os.writeBytes("EXPOSURE="+exposure+"\n");

    os.writeBytes("FORMAT=32-bit_rle_rgbe\n\n");
    os.writeBytes("-Y " + height + " +X " + width + "\n");
  }

  private static void writePixels(OutputStream os) throws IOException {
    if (rgbData == null)
      throw new IOException("No data");

    int numPixels = width*height;
    rgbeData = new byte[numPixels*4];
    int rgbOffset = 0;
    int rgbeOffset = 0;
    for (int i = 0; i < numPixels; i++) {
      RGBToRGBE(rgbData,rgbOffset,rgbeData,rgbeOffset);
      rgbOffset += 3;
      rgbeOffset += 4;
    }
    os.write(rgbeData);
  }

  private static void RGBToRGBE(float [] rgb, int rgbOffset,
                                byte [] rgbe, int rgbeOffset) {
    float v;
    float n;
    int e;

    v = rgb[rgbOffset+0];
    if (rgb[rgbOffset+1] > v) v = rgb[rgbOffset+1];
    if (rgb[rgbOffset+2] > v) v = rgb[rgbOffset+2];
    if (v < 1e-32)
      rgbe[rgbeOffset+0] = rgbe[rgbeOffset+1] = rgbe[rgbeOffset+2] = rgbe[rgbeOffset+3] = 0;
    else {
      e = exponent(v);
      n = normalize(v);
      v = (float)(n * 256.0/v);
      rgbe[rgbeOffset+0] = (byte)((int)(rgb[rgbOffset+0] * v) & 0xff);
      rgbe[rgbeOffset+1] = (byte)((int)(rgb[rgbOffset+1] * v) & 0xff);
      rgbe[rgbeOffset+2] = (byte)((int)(rgb[rgbOffset+2] * v) & 0xff);
      rgbe[rgbeOffset+3] = (byte)((int)(e + 128) & 0xff);
    }
  }

  private static int exponent(float num) {
    int value = Float.floatToIntBits(num);
    int exp = ((value & (0x7f800000)) >> 23)-126;
    return exp;
  }

  private static float normalize(float num) {
    int value = Float.floatToIntBits(num);
    int mantissa = value & (0x007fffff);
    float res = (float)(((double)(mantissa | 0x00800000))/((double)(0x01000000)));
    return res;
  }

}

