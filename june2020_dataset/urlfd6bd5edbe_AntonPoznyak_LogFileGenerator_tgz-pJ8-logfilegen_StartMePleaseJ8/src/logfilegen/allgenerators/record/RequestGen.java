package logfilegen.allgenerators.record;

import logfilegen.allgenerators.record.request.MethodGen;
import logfilegen.allgenerators.record.request.ProtocolGen;
import logfilegen.allgenerators.record.request.UrlGen;
import logfilegen.allmodels.record.Request;
import logfilegen.allmodels.record.request.Method;
import logfilegen.allmodels.record.request.Protocol;
import logfilegen.allmodels.record.request.Url;

public class RequestGen {
	public Request generate(){
		Method method = new MethodGen().generate();
		Protocol protocol = new ProtocolGen().generate();
		Url url = new UrlGen().generate();
		return new Request(method, url, protocol);
	}
}
