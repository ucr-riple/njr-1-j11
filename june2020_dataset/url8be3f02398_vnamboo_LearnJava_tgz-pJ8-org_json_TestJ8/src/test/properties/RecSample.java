package test.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecSample {

	public static void main(String[] args) {
		System.out.println(new RecSample().fact(10));
		System.out.println(new RecSample().listFiles(new File("c:\\test"),"test.xml").size());
	}
	
	int fact(int number){
		if(number==0) return 1;
		return number*fact(--number);
	}
	
	private List<File> listFiles(File directory,String fileName){
		List<File> retList=new ArrayList<File>();
		for(File file:directory.listFiles()){
			if(file.isDirectory()){
				retList.addAll(listFiles(file,fileName));
			}else {
				if(file.getName().equals(fileName)){
					retList.add(file);
				}	
			}
		}
		return retList;
	}
}
