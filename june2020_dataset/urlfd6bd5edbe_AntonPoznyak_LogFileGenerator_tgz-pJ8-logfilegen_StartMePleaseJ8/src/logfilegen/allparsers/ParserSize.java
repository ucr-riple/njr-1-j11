package logfilegen.allparsers;

import logfilegen.allmodels.record.Size;

public class ParserSize {
	public Size parser(String sizeStr){
		Long size = Long.parseLong(sizeStr);
        
        return new Size(size);
	}
}
