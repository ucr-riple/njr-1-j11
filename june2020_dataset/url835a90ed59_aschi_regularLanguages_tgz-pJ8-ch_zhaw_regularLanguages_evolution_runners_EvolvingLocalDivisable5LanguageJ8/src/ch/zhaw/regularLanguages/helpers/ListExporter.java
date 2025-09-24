package ch.zhaw.regularLanguages.helpers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ListExporter <T>{
	
	public void exportList(List<T> l, String filename){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(writer != null){
			for(T e : l){
				writer.println(e.toString());
			}
			writer.close();
		}
	}
}
