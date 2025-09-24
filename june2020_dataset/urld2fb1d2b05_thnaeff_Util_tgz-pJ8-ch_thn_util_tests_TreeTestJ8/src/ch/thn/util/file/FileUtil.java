/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class FileUtil {


	/**
	 * Returns the separator which separates files/folders in a path (backslash "\"
	 * for windows, slash "/" for unix, ...)
	 * 
	 * @return
	 */
	public static String getPathSeparator() {
		return File.separator;
	}

	/**
	 * Returns the available filesystem roots.<br />
	 * On an unix system this would return "/", whereas on a Windows system
	 * it returns "A:/", "C:/" etc. depending on the available drives.
	 * 
	 * @return
	 * @see File#listRoots()
	 */
	public static File[] getFileSystemRoots() {
		return File.listRoots();
	}

	/**
	 * Checks the given path if it starts with any of the available file system
	 * roots.<br />
	 * For more information about file system roots, see {@link File#listRoots()}
	 * 
	 * @param path
	 * @return
	 */
	public static boolean hasFileSystemRoot(String path) {
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			if (path.startsWith(roots[i].getPath())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds the path separator to the end of the path if not there yet
	 * 
	 * @param path The path to check
	 * @return The checked and, if necessary, corrected path
	 */
	public static String addPathSeparator(String path) {
		//Add path separator if not there
		if (path != null && !path.endsWith(File.separator)) {
			path = path + File.separator;
		}

		return path;
	}

	/**
	 * Returns the current working directory
	 * 
	 * @return
	 */
	public static String getWorkingDirectory() {
		//Since System.getProperty("user.dir") and user.home etc. can be confusing
		//and I haven't quite figured out if they really are reliable on all systems,
		//I chose a little more complicated approach. This is exactly the same
		//like if a file is created relatively, like new File("testfile.txt"), which
		//would be relative to the working directory.

		File f = new File(".");

		try {
			return addPathSeparator(f.getCanonicalPath());
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Returns the parent path of the given path.<br />
	 * <br />
	 * Example:<br />
	 * /home/me/somefolder/ returns the path string /home/me/
	 * 
	 * @param path
	 * @return
	 */
	public static String getParentPath(String path) {
		Path ret = Paths.get(path).getParent();

		if (ret == null) {
			return "";
		} else {
			return ret.toString();
		}
	}

	/**
	 * Returns the file object of the parent path.<br />
	 * <br />
	 * Example:<br />
	 * /home/me/somefolder/ returns the file object for /home/me/
	 * 
	 * @param file
	 * @return
	 */
	public static File getParentFile(File file) {
		return file.getParentFile();
	}

	/**
	 * Returns the next existing parent directory of the given file.<br />
	 * <br />
	 * Example:<br />
	 * /home/me/somefolder/file.test and "somefolder" does not exist, it returns /home/me/<br />
	 * /home/me/somefolder/file.test and "somefolder" does exist, it returns /home/me/somefolder/<br />
	 * 
	 * @param file
	 * @return
	 */
	public static File getNextExistingParentDirectory(File file) {

		file = file.getParentFile();

		while (file != null && !file.isDirectory()) {
			file = file.getParentFile();
		}

		return file;
	}

	/**
	 * Returns the next existing parent directory of the given path.<br />
	 * <br />
	 * Example:<br />
	 * /home/me/somefolder/file.test and "somefolder" does not exist, it returns /home/me/<br />
	 * /home/me/somefolder/file.test and "somefolder" does exist, it returns /home/me/somefolder/<br />
	 * 
	 * @param path
	 * @return
	 */
	public static String getNextExistingParentDirectory(String path) {
		return getNextExistingParentDirectory(new File(path)).getPath();
	}

	/**
	 * Checks if the given parent file really is a parent of the base path. It only
	 * returns true if the parent file is a parent of the base path and does
	 * not return true if they are equal.<br />
	 * <br />
	 * Examples:<br />
	 * /home/me/ is a parent of /home/me/somefolder/<br />
	 * /home/me/Desktop/ is a parent of /home/me/Desktop/somestuff/morestuff/
	 * 
	 * @param parent
	 * @param base
	 * @return
	 */
	public static boolean isParentOf(File parent, File base) {
		if (parent == null || base == null) {
			return false;
		}

		File baseTemp = base.getParentFile();

		while (baseTemp != null) {
			if (baseTemp.equals(parent)) {
				//Parent path found in base path
				return true;
			}

			baseTemp = baseTemp.getParentFile();
		}

		//Parent path not found in base path
		return false;
	}

	/**
	 * Checks if the given parent file really is a parent of the base path. It only
	 * returns true if the parent file is a parent of the base path and does
	 * not return true if they are equal.<br />
	 * <br />
	 * Examples:<br />
	 * /home/me/ is a parent of /home/me/somefolder/<br />
	 * /home/me/Desktop/ is a parent of /home/me/Desktop/somestuff/morestuff/
	 * 
	 * @param parent
	 * @param base
	 * @return
	 */
	public static boolean isParentOf(String parent, String base) {
		if (parent == null || base == null) {
			return false;
		}

		return isParentOf(new File(parent), new File(base));
	}

	/**
	 * Checks if the given child path is really a child path of the given base
	 * path. If the base path is an existing file, directory of the file is used
	 * as base path.<br />
	 * <br />
	 * Examples:<br />
	 * /home/me/somefolder/ is a child of /home/me/<br />
	 * /home/me/Desktop/somestuff/morestuff/ is a child of /home/me/Desktop/<br />
	 * /home/me/Desk/top/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktopabc/def/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktop/abc/def/ is a child of /home/me/Desktop/<br />
	 * 
	 * @param childPath
	 * @param base
	 * @return
	 */
	private static boolean isChildOf(String childPath, File base) {
		if (childPath == null || base == null) {
			return false;
		}

		String basePath = null;

		try {
			basePath = base.getCanonicalPath();
		} catch (IOException e) {
			return false;
		}

		//It is not possible to be a child path of a file path, thus if it is
		//a file then take its directory as base path
		if (base.isFile()) {
			int lastIndex = basePath.lastIndexOf(base.getName());
			basePath = basePath.substring(0, lastIndex);
		} else {
			//Make sure it has a path separator at the end
			basePath = addPathSeparator(basePath);
		}

		if (childPath.startsWith(basePath)) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the given child path is really a child path of the given base
	 * path. If the base path is an existing file, directory of the file is used
	 * as base path.<br />
	 * <br />
	 * Examples:<br />
	 * /home/me/somefolder/ is a child of /home/me/<br />
	 * /home/me/Desktop/somestuff/morestuff/ is a child of /home/me/Desktop/<br />
	 * /home/me/Desk/top/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktopabc/def/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktop/abc/def/ is a child of /home/me/Desktop/<br />
	 * 
	 * @param child
	 * @param base
	 * @return
	 */
	public static boolean isChildOf(File child, File base) {
		if (child == null) {
			return false;
		}

		try {
			return isChildOf(child.getCanonicalPath(), base);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Checks if the given child path is really a child path of the given base
	 * path. If the base path is an existing file, directory of the file is used
	 * as base path.<br />
	 * <br />
	 * Examples:<br />
	 * /home/me/somefolder/ is a child of /home/me/<br />
	 * /home/me/Desktop/somestuff/morestuff/ is a child of /home/me/Desktop/<br />
	 * /home/me/Desk/top/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktopabc/def/ is not a child of /home/me/Desktop/<br />
	 * /home/me/Desktop/abc/def/ is a child of /home/me/Desktop/<br />
	 * 
	 * @param child
	 * @param base
	 * @return
	 */
	public static boolean isChildOf(String child, String base) {
		if (base == null) {
			return false;
		}

		return isChildOf(child, new File(base));
	}




	/**
	 * A very simple way to write to a file.
	 * Uses a buffered stream writer to write to the file given with <code>filePath</code>.
	 * 
	 * @param filePath
	 * @param string
	 */
	public static void writeStringToFile(String filePath, StringBuilder string) {
		Writer output = null;

		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
		} catch (IOException e) {
			throw new FileUtilError("Failed to open writer");
		}

		try {
			output.write(string.toString());
		} catch (IOException e1) {
			try {
				output.close();
			} catch (IOException e) {}

			throw new FileUtilError("Failed to write to file");
		}

		try {
			output.close();
		} catch (IOException e) {
			throw new FileUtilError("failed to close file stream");
		}
	}

}
