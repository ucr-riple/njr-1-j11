package mSystem;

import mLibrary.mContext;
import mLibrary.mParent;

public class mSystem extends mParent {
	private Status status;
	private License license;
	private Encryption encryption;
	private Process process;
	private Version version;
	private OBJ obj;
	
	public mSystem(mContext m$) {
		super(m$);
		status = new Status(m$);
		license = new License(m$);
		encryption = new Encryption(m$);
		process = new Process(m$);
		version = new Version(m$);
		// TODO Auto-generated constructor stub
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}
	public Encryption getEncryption() {
		return encryption;
	}
	public void setEncryption(Encryption encryption) {
		this.encryption = encryption;
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}

	public OBJ getOBJ() {
		return obj;
	}
	
	


}
