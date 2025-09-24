package logfilegen.allmodels.record.request;

public class Url {
	private Path path;
	private Extension extension;
	public Url(){};
	public Url(Path path, Extension extension){
		this.extension =extension;
		this.path = path;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public Extension getExtension() {
		return extension;
	}
	public void setExtension(Extension extension) {
		this.extension = extension;
	}
	public String toString(){
		return path+ "." + extension;
	}
}
