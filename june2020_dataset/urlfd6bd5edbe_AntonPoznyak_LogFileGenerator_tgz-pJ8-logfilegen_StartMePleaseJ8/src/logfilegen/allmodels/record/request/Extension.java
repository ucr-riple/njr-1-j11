package logfilegen.allmodels.record.request;

public class Extension {
	private String extension;
	public Extension(){};
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Extension(String extension){
		this.extension = extension;
	}
	public String toString(){
		return extension;
	}
}
