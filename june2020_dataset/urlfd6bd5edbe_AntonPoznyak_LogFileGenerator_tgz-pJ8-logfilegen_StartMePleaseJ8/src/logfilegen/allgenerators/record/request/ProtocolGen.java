package logfilegen.allgenerators.record.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logfilegen.allmodels.record.request.Protocol;

public class ProtocolGen {
	Random random = new Random();
	private List<String> protocols = new ArrayList<String>();
	private List<String> versions = new ArrayList<String>();
	public ProtocolGen(){
		protocols.add("HTTP");
		protocols.add("HTTPS");
		protocols.add("FTP");
		versions.add("0.9");
		versions.add("1.0");
		versions.add("1.1");
		versions.add("1.2");
		versions.add("2.0");
		versions.add("3.0");
	}
	public Protocol generate(){
		int randomProtocolNum = random.nextInt(protocols.size());
		int randomVersionNum = random.nextInt(versions.size());
		
		return new Protocol(protocols.get(randomProtocolNum), versions.get(randomVersionNum));
	}
}
