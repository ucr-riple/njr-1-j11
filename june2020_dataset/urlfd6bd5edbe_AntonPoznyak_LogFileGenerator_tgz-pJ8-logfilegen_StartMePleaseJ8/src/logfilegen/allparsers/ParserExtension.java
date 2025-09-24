package logfilegen.allparsers;

import logfilegen.allmodels.record.request.Extension;

public class ParserExtension {
	public Extension parser(String extensionStr){
		return new Extension(extensionStr);
		
	}
}
