/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class FileUtils.
 */
public class FileUtils {

	/**
	 * The directory for the log files
	 */
	public static final String LOG_DIR = "logs" + File.separator;

	/**
	 * The directory for the properties file
	 */
	public static final String PROPS_DIR = "properties" + File.separator;

	/**
	 * The file name for the banned file
	 */
	public static final String BANNED_FILE = "banned.txt";

	/**
	 * Creates the directory/file if it doesn't exist.
	 * 
	 * @param dir
	 *            the dir
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void CreateIfNotExist(String path, String fileName, String contents) throws IOException {
		File filePath = new File(path);
		File fileFile = new File(path, fileName);

		filePath.mkdirs();

		if (!fileFile.exists()) {
			fileFile.createNewFile();
			
			PrintWriter writer = new PrintWriter(fileFile);
			writer.write(contents);
			
			try {
				writer.close();
				writer.flush();
			}
			finally {
				writer = null;
			}
		}
	}
	
	/**
	 * Creates a file if it does not exists. if the file does exists,
	 * then nothing happens
	 * @param path
	 *            The directory of the file
	 * @param fileName
	 *                The file name
	 * @throws IOException
	 *                    This is thrown if there's a problem writing the file
	 */
	public static void CreateIfNotExist(String path, String fileName) throws IOException {
		CreateIfNotExist(path, fileName, "");
	}

	/**
	 * Creates a file if it does not exists. if the file does exists,
	 * then nothing happens
	 * @param fileName
	 *                The file name
	 * @throws IOException
	 *                    This is thrown if there's a problem writing the file
	 */
	public static void CreateIfNotExist(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	/**
	 * Delete the file/directory if exist.
	 * 
	 * @param element
	 *              the element
	 */
	public static void DeleteIfExist(String element) {
		File file = new File(element);
		if (file.exists()) {
			file.delete();
		}
	}

}
