package org.ejml.example;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;

/**
 * Read the CSV file store it into an ArrayList<ArrayList<String>>
 * Write specific data into HBASE
 * @author wenbo
 *
 */
public class CSVReader {
	
	private static String basePath = "/Users/orangemr/Desktop/GPS Collect/";
	private static String inputFileName=basePath+"JavaTest.csv";
	public static String lineSep=System.getProperty("line.separator");

	public static ArrayList<ArrayList<String>> readFile(String filename) throws IOException{
		
		ArrayList<String> header=new ArrayList<String>();
		ArrayList<ArrayList<String>> content=new ArrayList<ArrayList<String>>();						
		ArrayList<String> bodyX=new ArrayList<String>();
		ArrayList<String> bodyY=new ArrayList<String>();

		boolean firstTime=true; //used for read the header
		CsvReader csvReader = new CsvReader(filename, ',',Charset.forName("GBK"));
		csvReader.readHeaders();

		while (csvReader.readRecord()) {
			//Read the header of the CSV file and store them into an ArrayList called header

			if(firstTime){
				int colNum=csvReader.getColumnCount();
				for(int i=0;i<colNum;i++){
					header.add(csvReader.getHeader(i));
					if(i==3){
						bodyX.add(csvReader.getHeader(i));
					}
					else if(i==5){
						bodyY.add(csvReader.getHeader(i));
					}
				}
				
				firstTime=false;
			}
			//Read the body of the CSV file store into "content"
			

			ArrayList<String> body=new ArrayList<String>();
			
			bodyX.add(csvReader.get(0));
			bodyY.add(csvReader.get(1));
			
		}
		content.add(bodyX);
		content.add(bodyY);

		return content;
	}
	
	
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> message=readFile(inputFileName);
		System.out.println(message.get(0));
		System.out.println(message.get(1));
	}


}

