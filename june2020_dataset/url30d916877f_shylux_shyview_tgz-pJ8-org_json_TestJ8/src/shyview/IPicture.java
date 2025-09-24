package shyview;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import shyview.Picture.StillLoadingException;

public interface IPicture {
	public void setName(String name);
	public String getName();
	public String getPath();
	public Image getPicture() throws StillLoadingException, FileNotFoundException;
	public void interrupt();
	public Dimension getDimension();
	/**
	 * Loads picture in cache.
	 */
	public void preload();
	/**
	 * Removes picture from cache to save memory.
	 */
	public void flush();
	
	public void setActionListener(ActionListener al);
}
