package shyview;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

public class Picture implements IPicture {
	
	private URL picres;
	Future<Image> swapimage = null;
	String alternative_name = null;
	ActionListener listener;
	
	public Picture(URL url) {
		picres = url;
	}
	public Picture(String strurl) throws MalformedURLException {
		picres = new URL(strurl);
	}
	public Picture(File f) throws MalformedURLException {
		picres = f.toURI().toURL();
	}

	@Override
	public String getName() {
		if (alternative_name != null) return alternative_name;
		return picres.getPath().substring(picres.getPath().lastIndexOf("/")+1);
	}

	@Override
	public String getPath() {
		return picres.toString();
	}

	@Override
	public Image getPicture() throws StillLoadingException, FileNotFoundException {
		if (swapimage == null || swapimage.isCancelled()) preload();
		if (!swapimage.isDone()) throw new StillLoadingException();
		Image tmp;
		try {
			tmp = swapimage.get();
		} catch (Exception e) {e.printStackTrace(); throw new FileNotFoundException();}
		if (tmp == null) throw new FileNotFoundException();
		return tmp;
	}
	

	@Override
	public Dimension getDimension() {
		if (swapimage != null) {
			Dimension d = new Dimension();
			Image pic;
			try {
			pic = getPicture();
			} catch(Exception e) {return null;}
			d.width = pic.getWidth(null);
			d.height = pic.getHeight(null);
			return d;
		}
		return null;
	}

	@Override
	public void flush() {
		swapimage = null;
	}
	
	public void interrupt() {
		if (swapimage != null) swapimage.cancel(true);
	}
	
	public void preload() {
		//System.out.println("Preloading "+getName());
		if (swapimage == null) {
			ExecutorService exs = Executors.newSingleThreadExecutor();
			swapimage = exs.submit(new ImageLoadTask(this)); // start task
		} else {
			listener.actionPerformed(new ActionEvent(this, 0, "loading finished"));
		}
	}
	class ImageLoadTask implements Callable<Image> {
		Picture parent;
		public ImageLoadTask(Picture parparent) {
			parent = parparent;
		}
		public Image call() throws Exception {
			if (swapimage.isDone()) return swapimage.get();
			try {
				System.out.println(System.currentTimeMillis()+" "+ getName() +" start loading..");
				BufferedImage img = ImageIO.read(picres);
				System.out.println(System.currentTimeMillis()+" "+ getName() +" finished loading..");
				if (parent.listener != null) 
					parent.listener.actionPerformed(new ActionEvent(parent, 0, "loading finished"));
				return img;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	class StillLoadingException extends IOException {
		private static final long serialVersionUID = 1L;
	}

	@Override
	public void setName(String name) {
		alternative_name = name;
	}
	
	public void setActionListener(ActionListener al) {
		listener = al;
	}
}
