package logfilegen.allconverters.record.request;

import logfilegen.allmodels.record.request.Extension;

public class ExtensionConv {
	private Extension extension;
	
	public ExtensionConv(Extension extension){
		this.extension = extension;
	}
	public String convertToString(){
		return extension.getExtension();
	}
}
