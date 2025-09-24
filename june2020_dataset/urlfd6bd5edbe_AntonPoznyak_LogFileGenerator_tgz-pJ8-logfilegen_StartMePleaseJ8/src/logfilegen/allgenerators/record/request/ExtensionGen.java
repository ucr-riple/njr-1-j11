package logfilegen.allgenerators.record.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logfilegen.allmodels.record.request.Extension;

public class ExtensionGen {
	Random random = new Random();
	private List<String> extensions = new ArrayList<String>();
	public ExtensionGen(){
		extensions.add("html");
		extensions.add("jpg");
		extensions.add("png");
		extensions.add("bmp");
		extensions.add("mp3");
		extensions.add("avi");
		extensions.add("gif");
		extensions.add("pdf");
		extensions.add("doc");
	}
	
	public Extension generate(){
		int randomExtensionIndex = random.nextInt(extensions.size());
		
		return new Extension(extensions.get(randomExtensionIndex));
	}
}
