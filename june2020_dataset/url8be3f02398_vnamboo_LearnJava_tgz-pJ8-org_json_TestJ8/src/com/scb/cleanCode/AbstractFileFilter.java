package com.scb.cleanCode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileFilter implements FileFilter {

	@Override
	public List<File> listFiles(File[] files) {
		List<File> listedFiles=new ArrayList<File>();
		for(File file:files){
			if(accept(file)){
				listedFiles.add(file);
			}
		}
		return null;
	}
	public abstract boolean accept(File file);
}
