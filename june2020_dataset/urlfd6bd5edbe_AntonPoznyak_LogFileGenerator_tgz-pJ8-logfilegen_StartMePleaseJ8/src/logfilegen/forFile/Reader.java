package logfilegen.forFile;

import java.io.IOException;

public class Reader {
private String file;
	
	public Reader(String file){
		this.file = file;
	}
	
	public String read(){
		java.io.FileReader fileR;
		try {
			fileR = new java.io.FileReader(file);
		
			char[] buffer = new char[100000];
			int count = fileR.read(buffer);
			fileR.close();
			
			return String.copyValueOf(buffer, 0, count);
		} catch (IOException e) {
			System.out.println("Ńan not be read the file :( ");
			return null;
		}
	}
}
