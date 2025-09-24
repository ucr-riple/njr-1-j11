package nl.rug.peerbox.logic.filesystem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peerbox;

import org.apache.log4j.Logger;

public final class FileServer implements Runnable {

	

	private static final Logger logger = Logger.getLogger(FileServer.class);
	private final ExecutorService pool = Executors.newFixedThreadPool(5);
	private final Context ctx;
	boolean alive = true;
	
	public FileServer(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public void run() {
		logger.info("Starting server " + ctx.getLocalPeer().getPort());
		try (ServerSocket server = new ServerSocket()) {
			server.bind(new InetSocketAddress(ctx.getLocalPeer().getAddress(), ctx.getLocalPeer().getPort()));
			while (!Thread.currentThread().isInterrupted()) {
				logger.info("Waiting for incoming connection");
				try {
					final Socket s = server.accept();
					logger.debug("Accepted incoming connection from "
							+ s.getRemoteSocketAddress());
					pool.execute(new SendFileTask(s));
				} catch (IOException e) {
					logger.error(e);
				}
			}
			pool.shutdownNow();

		} catch (IOException e) {
			logger.error(e);
		}

	}

	private final class SendFileTask implements Runnable {
		private final Socket s;
		private final Context ctx;

		private SendFileTask(Socket s) {
			this.s = s;
			this.ctx = Peerbox.getInstance();
		}

		@Override
		public void run() {
			try {
				BufferedReader st = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				String fileid = st.readLine();
				logger.info("File " + fileid + " has been requested.");
				File myFile = new File(ctx.getPathToPeerbox()
						+ System.getProperty("file.separator") + fileid);
				byte[] mybytearray = new byte[(int) myFile.length()];

				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(myFile));

				bis.read(mybytearray, 0, mybytearray.length);

				OutputStream os = s.getOutputStream();
				os.write(mybytearray, 0, mybytearray.length);
				os.flush();
				bis.close();
				os.close();
				st.close();
				logger.info("File " + fileid + " has been transmitted");
			} catch (IOException e) {
				logger.error(e);
			} finally {
				try {
					s.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

}