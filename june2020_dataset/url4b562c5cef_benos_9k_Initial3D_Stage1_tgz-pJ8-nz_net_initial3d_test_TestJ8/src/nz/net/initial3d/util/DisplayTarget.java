package nz.net.initial3d.util;

import java.awt.image.BufferedImage;

public interface DisplayTarget {

	public int getDisplayWidth();

	public int getDisplayHeight();

	public void display(BufferedImage bi);

}
