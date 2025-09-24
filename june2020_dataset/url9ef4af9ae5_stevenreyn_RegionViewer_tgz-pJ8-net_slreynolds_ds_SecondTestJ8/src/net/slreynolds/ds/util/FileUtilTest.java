package net.slreynolds.ds.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import net.slreynolds.ds.internal.util.FileUtil;

import org.junit.Test;

public class FileUtilTest {

	@Test(timeout=3500)
	public void testCreateFile() throws IOException {
		checkCreateFile("tempTestCreateFileForUnitTest.txt");
		checkCreateFile("tempTestCreateFileForUnitTest_something");
		final String someDirAsString = "somedirForCreateFileUnitTest";
		File someDir = new File(someDirAsString);
		someDir.delete();
		checkCreateFile(someDirAsString+"/tempTestCreateFileForUnitTest.txt");
		checkCreateFile(someDirAsString+"/tempTestCreateFileForUnitTest.txt");
		someDir.delete();
	}
	
	private void checkCreateFile(String filename) throws IOException {
		File temp = new File(filename);
		if (temp.exists()) {
			if (!temp.delete()) fail("Delete failed of " + temp.getAbsolutePath());
		}
		if (temp.exists()) {
			fail("Delete failed of " + temp.getAbsolutePath());
		}
		FileUtil.createEmptyWritableFile(temp.getCanonicalPath());
		if (!temp.exists()) {
			fail("Was not able to create " + temp.getCanonicalPath());
		}
		if (!temp.isFile()) {
			fail("Created something that's not a file " + temp.getCanonicalPath());
		}		
		if (!temp.canWrite()) {
			fail("Created file that's not writable " + temp.getCanonicalPath());
		}
		temp.delete();
	}

}
