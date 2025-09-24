package shyview;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

public class ShyluxFileFilter extends FileFilter {
	private ArrayList<String> extensions = new ArrayList<String>();
	
	public ShyluxFileFilter(ArrayList<String> extensions) {
		this.extensions.addAll(extensions);
	}
	public ShyluxFileFilter() {};
	public void addExtension(String ext) {
		this.extensions.add(ext);
	}
	public ArrayList<String> getExtensions() {
		return this.extensions;
	}
	@Override
	public boolean accept(File f) {
		String filename = f.getName().toLowerCase();
		for (String ext: this.extensions) {
			if (filename.endsWith(ext)) return true;
		}
		if (f.isDirectory()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
