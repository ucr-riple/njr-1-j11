package logfilegen.allmodels.record;

public class Size {
	private long size;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public Size(long size){
		this.size = size;
	}
	
	public String toString(){
		return Long.toString(size);
	}
}
