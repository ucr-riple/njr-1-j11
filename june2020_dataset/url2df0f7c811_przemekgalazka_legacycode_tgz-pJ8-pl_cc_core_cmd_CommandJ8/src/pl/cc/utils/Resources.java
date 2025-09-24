package pl.cc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Obsługa zasobów
 * 
 * @since Jan 18, 2008
 */
public class Resources {
	
	/**
	 * Dostęp do zasobu zawartego w pliku Jar bądź do pliku w katalogu bin
	 * Plik znajduje się w katalogu ROOT w Jar'ze
	 * @param fileName nazwa pliku
	 * @return inputStream do tego pliku
	 * @throws IOException
	 */
	public static InputStream getInputStream(String fileName) throws IOException{
		String resource = "/"+fileName;
		URL url = IniFile.class.getResource( resource );
        if (url==null) throw new IOException();
		return url.openStream();
	}
	
	
}
