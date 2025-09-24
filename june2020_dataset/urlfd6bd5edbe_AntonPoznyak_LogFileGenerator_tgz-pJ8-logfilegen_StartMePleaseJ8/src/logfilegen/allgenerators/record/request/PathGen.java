package logfilegen.allgenerators.record.request;

import java.util.Random;

import logfilegen.allmodels.record.request.Path;

public class PathGen {
	private final String letters = "qwertyuiopasfghjklzxcvnm";
	Random random = new Random();
	private String generateString(String symbol, int lenght){
		char[] word = new char[lenght];
		for(int i=0; i<lenght; i++){
			word[i] = symbol.charAt(random.nextInt(symbol.length()));
		}
		return new String(word);
	}
	
	public Path generate(){
		StringBuilder builder = new StringBuilder();
		int lenght2 = random.nextInt(5)+1;
		for(int i=0; i<lenght2; i++){
			int lenght = random.nextInt(5)+1;
			builder.append("/");
			builder.append(generateString(letters, lenght));
		}
		return new Path(builder.toString());
	}
}
