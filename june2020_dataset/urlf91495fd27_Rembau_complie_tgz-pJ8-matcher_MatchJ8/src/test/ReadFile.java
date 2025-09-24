package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ReadFile {
	public String readProgramFile(String filePath){
		File file = new File(filePath);
		StringBuffer strB = new StringBuffer(); 
		BufferedReader  dr=null;
		try {
			dr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str_=null;
		try {
			while((str_=dr.readLine())!=null){
				if(str_.trim().equals("")){
					continue;
				}
				int i=0;
				/**
				 * 去掉单行注释
				 */
				if((i=str_.indexOf("//"))!=-1){
					str_=str_.substring(0,i);
					//System.out.println(str_);
				}
				strB.append(str_);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				dr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end try
		String content = new String(strB);
		return content;
	}//end readProgramFile
	public Word[] readWordFile(String filePath){
		Word[] words=null;
		BufferedReader  dr=null;
		File file = new File(filePath);
		try {
			dr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str_=null;
		LinkedList<String> list = new LinkedList<String>();
		try {
			while((str_=dr.readLine())!=null){
				list.add(str_);
				//System.out.println(str_);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				dr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end try
		words= new Word[list.size()];
		int i=0;
		for(String strLine:list){
			String str[]=strLine.split(" ");
			Word word= new Word();
			word.setName(str[0]);
			word.setMark(str[1]);
			if(str[1].equals("0")){
				word.setWords(str[2].split("1"));
			}
			words[i++]=word;
		}//end if
		return words;
	}//end readWordFile
	public static void main(String[] args) {
		/*Word[] w=new ReadFile().readWordFile("E:\\t\\word.txt");
		for(int i=0;i<w.length;i++){
			System.out.println(w[i].getName());
			if(w[i].getMark().equals("0")){
				for(int j=0;j<w[i].getWords().length;j++){
					System.out.print(w[i].getWords()[j]+" ");
				}
				System.out.println();
			}
		}*/
		System.out.println(new ReadFile().readProgramFile("E:\\t\\cx.txt"));
	}
}
