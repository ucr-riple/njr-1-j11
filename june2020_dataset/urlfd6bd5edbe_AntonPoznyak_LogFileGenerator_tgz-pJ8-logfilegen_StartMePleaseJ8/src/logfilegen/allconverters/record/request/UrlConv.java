package logfilegen.allconverters.record.request;

import logfilegen.allmodels.record.request.Url;

public class UrlConv {
	private Url url;
	public UrlConv(Url url){
		this.url = url;
	}
	
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
		PathConv path = new PathConv(url.getPath());
		builder.append(path.convertToString());
		builder.append(".");
		ExtensionConv extension = new ExtensionConv(url.getExtension());
		builder.append(extension.convertToString());
		
		return builder.toString();
	}
}
