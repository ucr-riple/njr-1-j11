/**
 * 
 */
package Graphics_Renderer;

import java.awt.Image;

/**
 * @author benjamin
 * 
 */
public class ImageLayer implements java.io.Serializable {
	private Tileset tileset;
	private int tileIDs[][];

	public ImageLayer(String tileImage, int ids[][]) {
		tileset = new Tileset(tileImage, 40); // TODO allow the value 40 to be
												// dynamic
		tileIDs = ids;
	}

	public ImageLayer() {
	}; // default constructor for XML

	public Image toImage() {
		return GraphicsRenderer.combineTiles(tileIDs, tileset);
	}

	public void setIDs(int[][] ids) {
		tileIDs = ids;
	}

	public int[][] getIDs() {
		return tileIDs;
	}

	public int getWidth() {
		return tileIDs[0].length;
	}

	public int getHeight() {
		return tileIDs.length;
	}

	@Override
	public String toString() {
		return tileset.toString();
	}

	public Tileset getTileset() {
		return tileset;
	}

	public void setTileset(Tileset tileset) {
		this.tileset = tileset;
	}

	public int[][] getTileIDs() {
		return tileIDs;
	}

	public void setTileIDs(int[][] tileIDs) {
		this.tileIDs = tileIDs;
	}

}
