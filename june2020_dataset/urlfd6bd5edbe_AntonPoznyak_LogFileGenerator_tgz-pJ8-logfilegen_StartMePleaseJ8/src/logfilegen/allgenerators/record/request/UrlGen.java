package logfilegen.allgenerators.record.request;

import logfilegen.allmodels.record.request.Extension;
import logfilegen.allmodels.record.request.Path;
import logfilegen.allmodels.record.request.Url;

public class UrlGen {
	public Url generate(){
		Path path = new PathGen().generate();
		Extension extension = new ExtensionGen().generate();
		return new Url(path, extension);
	}
}
