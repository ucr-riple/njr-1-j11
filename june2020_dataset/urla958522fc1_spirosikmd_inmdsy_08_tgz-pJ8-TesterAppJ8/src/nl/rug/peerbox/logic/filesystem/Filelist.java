package nl.rug.peerbox.logic.filesystem;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.log4j.Logger;

class Filelist extends ConcurrentHashMap<UFID, PeerboxFile> {

	private final static int INITIAL_CAPACITY = 1;
	private final static float LOAD_FACTOR = 0.9f;
	private final static int CONCURRENCY_LEVEL = 1;
	private final static long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(Filelist.class);

	Filelist() {
		super(INITIAL_CAPACITY, LOAD_FACTOR, CONCURRENCY_LEVEL);
	}

	void serialize(String datafile, String path) {
//		try {
//			OutputStream file = new FileOutputStream(path
//					+ System.getProperty("file.separator") + datafile);
//			OutputStream buffer = new BufferedOutputStream(file);
//			try (ObjectOutput output = new ObjectOutputStream(buffer)) {
//				output.writeObject(this);
//			}
//		} catch (IOException e) {
//			logger.error(e);
//		}
	}

	static Filelist deserialize(String datafile, String path) {
		Filelist filelist = null;
		try {
			InputStream file = new FileInputStream(path
					+ System.getProperty("file.separator") + datafile);
			InputStream buffer = new BufferedInputStream(file);
			try (ObjectInput input = new ObjectInputStream(buffer)) {
				filelist = (Filelist) input.readObject();
			}
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return filelist;
	}
}
