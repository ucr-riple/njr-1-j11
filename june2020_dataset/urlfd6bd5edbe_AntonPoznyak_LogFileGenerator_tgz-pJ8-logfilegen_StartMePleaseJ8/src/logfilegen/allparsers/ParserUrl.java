package logfilegen.allparsers;

import logfilegen.allmodels.record.request.Url;

public class ParserUrl {
	private ParserPath parserPath = new ParserPath();
		private ParserExtension parserExt = new ParserExtension();
	public Url parser(String urlStr){
		Url url = new Url();
		String[] split = urlStr.split("\\.");
		url.setPath(parserPath.parser(split[0]));
		url.setExtension(parserExt.parser(split[1]));
		
		return url;
		
	}
}
