package logfilegen.forFile;

import java.io.FileWriter;
import java.io.IOException;

public class WriterFile {
	private String file;
	private String source;
	public WriterFile(String file,String source){
		this.file= file;
		this.source = source;
	}
	
	public void write(){
		java.io.FileWriter fileWriter;
		try{
			fileWriter = new FileWriter(file);
			fileWriter.write(source);
			fileWriter.close();
		} catch (IOException e){
			System.out.println("Ńan not be written to the file :( ");
		}
	}
}
