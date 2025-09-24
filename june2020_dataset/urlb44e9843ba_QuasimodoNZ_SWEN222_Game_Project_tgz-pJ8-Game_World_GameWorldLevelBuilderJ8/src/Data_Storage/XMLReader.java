package Data_Storage;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Game_World.GameWorld;

/**
 * Reads a GameWorld object from an .xml file
 * @author Alix Schultze 300170774 hoschurayn
 *
 */
public class XMLReader {

	private BufferedInputStream input;
	private FileInputStream fileStream;
	private XMLDecoder decoder;

	/**
	 * Gets a specified file for reading
	 * @param fileName	is assumed to be the name of a .xml file
	 * @throws FileNotFoundException
	 */
	public XMLReader(String fileName) throws FileNotFoundException{
		fileStream = new FileInputStream(fileName);
		input = new BufferedInputStream(fileStream);
		decoder = new XMLDecoder(input);
	}

	/**
	 * This XMLReader takes a File instead of a String
	 * @param file	will be checked to ensure it's a .xml file
	 * @throws FileNotFoundException
	 */
	public XMLReader(File file) throws FileNotFoundException{
		String fileName = file.getName();
		if(fileName.endsWith(".xml")){
			fileStream = new FileInputStream(file);
			input = new BufferedInputStream(fileStream);
			decoder = new XMLDecoder(input);
		} else {
			throw new IllegalArgumentException("File must be a .xml file");
		}
	}

	/**
	 * Reads and returns a GameWorld from the file
	 * @return
	 */
	public GameWorld readGameWorld(){
		return (GameWorld) decoder.readObject();
	}

	public Object readObject(){
		return decoder.readObject();
	}

	public void close(){
		decoder.close();
	}
}
