package nl.rug.peerbox.logic.filesystem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MD5Util {

	static String md5(String input) {
		String md5 = null;

		if (null == input) {
			return null;
		}

		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
			// Converts message digest value in base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

	static String md5(File file) {
		String md5 = null;

		if (null == file || !file.exists()) {
			return null;
		}

		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			try (FileInputStream fin = new FileInputStream(file)) {
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = fin.read(buffer)) != -1) {
					// Update input string in message digest
					digest.update(buffer, 0, length);
				}
				// Converts message digest value in base 16 (hex)
				md5 = new BigInteger(1, digest.digest()).toString(16);
			} catch (IOException e) {
				return md5;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
}
