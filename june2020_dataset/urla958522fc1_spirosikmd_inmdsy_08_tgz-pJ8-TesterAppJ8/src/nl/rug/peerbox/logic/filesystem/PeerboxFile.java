package nl.rug.peerbox.logic.filesystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.rug.peerbox.logic.Peer;
import nl.rug.peerbox.logic.Peerbox;

public class PeerboxFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private UFID ufid;
	private int version;
	private Peer owner;
	private String filename;
	private String checksum;
	private long size;
	private transient File file = null;
	private transient List<PeerboxFileListener> listeners;

	public PeerboxFile(String filename, Peer owner) {
		this(filename, owner, null);
	}

	public PeerboxFile(String filename, Peer owner, File file) {
		this.filename = filename;
		this.file = file;
		if (file != null) {
			this.size = file.length();
			this.checksum = MD5Util.md5(file);
		}
		this.ufid = new UFID(filename, owner);
		this.owner = owner;
		listeners = new ArrayList<PeerboxFileListener>();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public UFID getUFID() {
		return ufid;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Peer getOwner() {
		return owner;
	}

	public String getChecksum() {
		return checksum;
	}

	public boolean isOwn() {
		return getOwner().equals(Peerbox.getInstance().getLocalPeer());
	}

	public boolean exists() {
		return (file != null && file.exists());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File sharedFile) {
		this.file = sharedFile;
		notifyListeners();
	}

	public void addListener(PeerboxFileListener l) {
		listeners.add(l);
	}

	public void removeListener(PeerboxFileListener l) {
		listeners.remove(l);
	}

	private void notifyListeners() {
		for (PeerboxFileListener l : listeners) {
			l.modelUpdated();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PeerboxFile)) {
			return false;
		}

		PeerboxFile otherfile = (PeerboxFile) obj;

		return ufid.equals(otherfile.getUFID());
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		listeners = new ArrayList<PeerboxFileListener>();
	}

	public long getSize() {
		return size;
	}

}
