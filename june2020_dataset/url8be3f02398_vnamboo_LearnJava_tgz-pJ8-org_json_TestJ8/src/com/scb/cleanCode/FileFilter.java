package com.scb.cleanCode;

import java.io.File;
import java.util.List;

public interface FileFilter {
	List<File> listFiles(File[] files);
}
