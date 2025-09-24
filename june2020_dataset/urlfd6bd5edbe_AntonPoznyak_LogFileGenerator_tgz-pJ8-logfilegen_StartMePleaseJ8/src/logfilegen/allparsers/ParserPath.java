package logfilegen.allparsers;

import logfilegen.allmodels.record.request.Path;

public class ParserPath {
	public Path parser(String pathStr){
		return new Path(pathStr);
	}
}
