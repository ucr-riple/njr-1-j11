package darwInvest.data;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileManager class provides a convenient way
 * to access and process files.
 * Makes assumptions that everything works as expected,
 * so it's not very safe in a real application.
 * 
 * @author Kevin Dolan
 */
public class FileManager {
	
	/**
	 * Return a string representing the entire file
	 * @param url the url to load
	 * @return	  a string of the entire document contents
	 */
	public static String readWhole(String url) {
		URL page = null;
		try {
			page = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		String result = "";
		try {
			BufferedReader input = getReader(page);
			
			String line = null;
			while (( line = input.readLine() ) != null)
				result += line + "\n";
	
			input.close();
		}
		catch (IOException e){}
		
		return result;
	}
	
	/**
	 * Return a list of each line in the file
	 * @param url the url to load
	 * @return	  a list of lines
	 */
	public static List<String> readLines(String url) {
		URL page = null;
		try {
			page = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		try {
			BufferedReader input = getReader(page);
			
			String line = null;
			while (( line = input.readLine() ) != null)
				result.add(line);
	
			input.close();
		}
		catch (IOException e){}
		
		return result;
	}
	
	/**
	 * Return the buffered reader of a URL
	 * Assumes URL is good, and no IOExceptions are encountered
	 * 
	 * @param url the string url to access
	 * @return	  the buffered reader object
	 */
	private static BufferedReader getReader(URL url) {
		try {
			return new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e) { }
		
		return null;
	}
	
}
