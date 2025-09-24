package nl.rug.peerbox.logic.filesystem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.util.concurrent.TimeoutException;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peer;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

public final class FileRequestTask implements Runnable {

	private static final int SOCKET_TIMEOUT = 5000;

	private static final Logger logger = Logger
			.getLogger(FileRequestTask.class);

	private final Peer h;
	private final String filename;
	private final Context ctx;
	private final PeerboxFile file;

	public FileRequestTask(PeerboxFile file, Context ctx) {
		this.file = file;
		this.h = file.getOwner();
		this.filename = file.getFilename();
		this.ctx = ctx;
	}

	File hide(File src) {
		if (SystemUtils.IS_OS_WINDOWS) {
			Process p;
			try {
				p = Runtime.getRuntime().exec("attrib +h " + src.getPath());
				p.waitFor();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return src;
	}

	@Override
	public void run() {
		String tempFilename = "." + filename;
		File tempFile;
		try {
			tempFile = File.createTempFile("peerbox_", filename);
		} catch (IOException e1) {
			tempFile = new File(ctx.getPathToPeerbox(), tempFilename);
		}

		try (Socket s = new Socket()) {
			s.connect(new InetSocketAddress(h.getAddress(), h.getPort()),
					SOCKET_TIMEOUT);
			logger.debug("Created direct connection to "
					+ h.getAddress().toString() + ":" + h.getPort());
			PrintWriter put = new PrintWriter(s.getOutputStream(), true);
			put.println(file.getUFID().getFilename());
			byte[] mybytearray = new byte[1024];
			InputStream is = s.getInputStream();

			FileOutputStream fos = new FileOutputStream(tempFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int bytesRead;
			while ((bytesRead = is.read(mybytearray, 0, mybytearray.length)) != -1) {
				bos.write(mybytearray, 0, bytesRead);
			}
			bos.close();
			put.close();
			logger.info("File " + filename + " has been received");
		} catch (IOException e) {
			logger.error(e);
		} finally {
			boolean valid = true;
			if (tempFile.length() != file.getSize()) {
				logger.debug("Filesize of " + filename + " incorrect");
				valid = false;
			}
			String checksum = MD5Util.md5(tempFile);
			if (!file.getChecksum().equals(checksum)) {
				logger.debug("Checksum of " + filename + " incorrect");
				valid = false;
			}
			if (valid) {
				synchronized (VirtualFileSystem.class) {
					File sharedFile = new File(ctx.getPathToPeerbox(), filename);
					tempFile.renameTo(sharedFile);
					file.setFile(sharedFile);
				}
			} else {
				file.setFile(null);
			}
			tempFile.delete();
			tempFile = null;
		}

		// if successful
		// rename tempFilename to filename
		// verify file with peerboxfile properties
		// filesize and content checksum
	}
}