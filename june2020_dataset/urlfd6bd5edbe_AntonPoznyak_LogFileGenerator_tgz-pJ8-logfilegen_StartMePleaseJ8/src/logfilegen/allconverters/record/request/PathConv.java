package logfilegen.allconverters.record.request;

import logfilegen.allmodels.record.request.Path;

public class PathConv {
	private Path path;
	
	public PathConv(Path path){
		this.path = path;
	}
	
	public String convertToString(){
		return path.getPath();
	}
}
