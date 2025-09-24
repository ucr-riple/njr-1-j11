/**
 *
 */
package Graphics_Renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * Specifies the tile set for the representation of an object and the available
 * states for it
 *
 * @author benjamin
 *
 */
public class Tileset implements Serializable {
	private String imageLocation;
	transient BufferedImage image;
	private int tileWidth;
	private int tileHeight;

	/**
	 *
	 * @param i
	 *            The tileset that will be divided up
	 * @param tw
	 *            The width of each tile
	 * @param th
	 *            The height of each tile
	 */
	public Tileset(String location, int tw, int th) {
		
		imageLocation = location;
		initiliseImage();
		tileWidth = tw;
		tileHeight = th;
	}

	private void initiliseImage() {
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			System.out.println(imageLocation);
			e.printStackTrace();
		}
	}

	public Tileset(String location, int dimensions) {
		this(location, dimensions, dimensions);
	}

	public Tileset() {
	}; // default constructor for XML

	public Image getTile(int x, int y) {
		if (image == null)
			initiliseImage();
		if (x < 0 || y < 0 || x + tileWidth > image.getWidth()
				|| y + tileHeight > image.getHeight())
			throw new IllegalArgumentException(
					String.format(
							"Tile [%d,%d] is out of bounds. x and y must be >=0 and thay cannot be outside the image",
							x, y));
		return image.getSubimage(x * tileWidth, y * tileHeight, tileWidth,
				tileHeight);
	}

	public Image getTile(int i) {
		if (i >= getColumns() * getRows())
			throw new IllegalArgumentException(
					String.format(
							"There are only %d tiles available, input has to be 0 <= %d <= %d",
							getColumns() * getRows(), i, getColumns()
									* getRows() - 1));
		return getTile(i % getColumns(), i / getColumns());
	}

	public int getColumns() {
		if (image == null)
			initiliseImage();
		return image.getWidth() / tileWidth;
	}

	public int getRows() {
		if (image == null)
			initiliseImage();
		return image.getHeight() / tileHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	@Override
	public String toString() {
		return imageLocation;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	

}
