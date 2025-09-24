package nl.rug.peerbox.logic.filesystem;

import java.io.Serializable;

import nl.rug.peerbox.logic.Peer;

public class UFID implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fileid;
	private String owner;
	private String filename;

	public UFID(String filename, Peer owner) {
		this.fileid = MD5Util.md5(filename);
		this.filename = filename;
		this.owner = MD5Util.md5(owner.getName());
		// plus md5 of data
	}

	public String getFileid() {
		return fileid;
	}

	public String getOwner() {
		return owner;
	}
	
	public String getFilename() {
		return filename;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof UFID)) {
			return false;
		}

		UFID otherUFID = (UFID) obj;

		return fileid.equals(otherUFID.getFileid())
				&& owner.equals(otherUFID.getOwner());
	}
	
	@Override
	public int hashCode() {
		return new String(fileid + "" + owner).hashCode();
	}

	@Override
	public String toString() {
		return filename;
	}
}